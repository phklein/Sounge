package soungegroup.soungeapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import soungegroup.soungeapi.adapter.GroupAdapter;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.enums.RoleName;
import soungegroup.soungeapi.model.Group;
import soungegroup.soungeapi.model.User;
import soungegroup.soungeapi.repository.*;
import soungegroup.soungeapi.request.GroupPageUpdateRequest;
import soungegroup.soungeapi.request.GroupSaveRequest;
import soungegroup.soungeapi.request.PictureUpdateRequest;
import soungegroup.soungeapi.response.GroupMatchResponse;
import soungegroup.soungeapi.response.GroupPageResponse;
import soungegroup.soungeapi.response.GroupSimpleResponse;
import soungegroup.soungeapi.response.PostSimpleResponse;
import soungegroup.soungeapi.service.GroupService;
import soungegroup.soungeapi.util.LocationUtil;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository repository;
    private final GenreRepository genreRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private final GroupAdapter adapter;

    private final LocationUtil locationUtil;

    @Override
    public ResponseEntity<Long> save(GroupSaveRequest body) {
        Group group = adapter.toGroup(body);
        Optional<User> leaderOptional = userRepository.findById(body.getLeaderId());

        if (group != null && leaderOptional.isPresent()) {
            User leader = leaderOptional.get();
            group = repository.save(group);
            leader.setLeader(true);
            leader.setGroup(group);
            userRepository.save(leader);
            return ResponseEntity.status(HttpStatus.CREATED).body(group.getId());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Override
    public ResponseEntity<GroupPageResponse> findPageById(Long viewerId, Long id) {
        Optional<User> viewerOptional = userRepository.findById(viewerId);
        Optional<GroupPageResponse> pageOptional = repository.findPage(id);

        if (viewerOptional.isPresent() && pageOptional.isPresent()) {
            User viewer = viewerOptional.get();
            GroupPageResponse page = pageOptional.get();

            page.setGenres(genreRepository.findByGroupId(page.getId()));
            page.setUsers(userRepository.findByGroupId(page.getId()));
            page.setPostList(postRepository.findByGroupIdOrdered(viewerId,
                    Pageable.ofSize(50).withPage(0)));
            page.getPostList().forEach(p -> p.setHasLiked(viewer.getLikedPosts().stream()
                    .anyMatch(lp -> lp.getId().equals(p.getId()))));

            return ResponseEntity.status(HttpStatus.OK).body(page);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<Page<PostSimpleResponse>> getPostsById(Long viewerId, Long id, Integer page) {
        Optional<User> viewerOptional = userRepository.findById(viewerId);

        if (viewerOptional.isPresent() && repository.existsById(id)) {
            User viewer = viewerOptional.get();

            Page<PostSimpleResponse> postList = postRepository.findByUserIdOrdered(id,
                    Pageable.ofSize(50).withPage(page));

            postList.forEach(p -> p.setHasLiked(viewer.getLikedPosts().stream()
                    .anyMatch(lp -> lp.getId().equals(p.getId()))));

            return ResponseEntity.status(HttpStatus.OK).body(postList);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<Page<GroupMatchResponse>> findMatchList(Long userId,
                                                                  Integer maxDistance,
                                                                  Optional<GenreName> genreName,
                                                                  Optional<Integer> minSize,
                                                                  Optional<Integer> maxSize,
                                                                  Optional<RoleName> missingRoleName,
                                                                  Integer page) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            Page<GroupMatchResponse> matchList = repository.findMatchList(
                    user.getId(),
                    user.getLikedUsers(),
                    genreName.orElse(null),
                    minSize.orElse(null),
                    maxSize.orElse(null),
                    missingRoleName.orElse(null),
                    Pageable.ofSize(50).withPage(page)
            );

            matchList.forEach(gp -> {
                gp.setRolesFilled(roleRepository.findByGroupId(gp.getId()));
                gp.setGenres(genreRepository.findByGroupId(gp.getId()));
                gp.setLeaderDistance(locationUtil.distance(
                        user.getLatitude(), user.getLongitude(),
                        gp.latitude(), gp.longitude()
                ));

                // Calculate relevance, +2 if has signature
                double relevance = gp.isLeaderHasSignature() ? 2 : 0;

                // 5 - 0.20 for each km away
                relevance += (5 - (gp.getLeaderDistance() * 0.2));

                // +0.5 for each matching genre
                relevance += 0.5 * gp.getGenres().stream().filter(g ->
                        user.getLikedGenres().stream().anyMatch(ug ->
                                ug.getId().equals(g.getId()))).count();

                // +0.5 for each missing role that user has
                relevance += 0.5 * user.getRoles().stream().filter(r ->
                        gp.getRolesFilled().stream().noneMatch(rf ->
                                rf.getId().equals(r.getId()))).count();

                gp.setRelevance(relevance);
            });

            matchList = new PageImpl<>(matchList.stream()
                    .filter(u -> u.getLeaderDistance() <= maxDistance)
                    .sorted(Comparator.comparing(GroupMatchResponse::getRelevance).reversed())
                    .collect(Collectors.toList()));

            return ResponseEntity.status(HttpStatus.OK).body(matchList);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<Page<GroupSimpleResponse>> findByName(String nameLike, Integer page) {
        Page<GroupSimpleResponse> foundGroups = repository.findByName(nameLike,
                Pageable.ofSize(50).withPage(page));

        return ResponseEntity.status(HttpStatus.OK).body(foundGroups);
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        Optional<Group> groupOptional = repository.findById(id);

        if (groupOptional.isPresent()) {
            Group group = groupOptional.get();
            group.getUsers().forEach(u -> u.setGroup(null));
            userRepository.saveAll(group.getUsers());
            repository.delete(group);
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<Void> update(Long id, GroupPageUpdateRequest body) {
        Optional<Group> groupOptional = repository.findById(id);

        if (groupOptional.isPresent()) {
            Group group = groupOptional.get();
            group.setName(body.getName());
            group.setDescription(body.getDescription());
            repository.save(group);
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<Void> updatePicture(Long id, PictureUpdateRequest body) {
        Optional<Group> groupOptional = repository.findById(id);

        if (groupOptional.isPresent()) {
            Group group = groupOptional.get();
            group.setProfilePic(body.getProfilePic());
            group.setBanner(body.getBanner());
            repository.save(group);
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
