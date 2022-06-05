package soungegroup.soungeapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import soungegroup.soungeapi.adapter.GroupAdapter;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.enums.RoleName;
import soungegroup.soungeapi.model.Group;
import soungegroup.soungeapi.model.User;
import soungegroup.soungeapi.repository.GenreRepository;
import soungegroup.soungeapi.repository.GroupRepository;
import soungegroup.soungeapi.repository.RoleRepository;
import soungegroup.soungeapi.repository.UserRepository;
import soungegroup.soungeapi.request.GroupPageUpdateRequest;
import soungegroup.soungeapi.request.GroupSaveRequest;
import soungegroup.soungeapi.request.PictureUpdateRequest;
import soungegroup.soungeapi.response.GroupMatchResponse;
import soungegroup.soungeapi.response.GroupPageResponse;
import soungegroup.soungeapi.response.GroupSimpleResponse;
import soungegroup.soungeapi.service.GroupService;
import soungegroup.soungeapi.util.LocationUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private static final Pageable PAGEABLE = Pageable.ofSize(10);

    private final GroupRepository repository;
    private final GenreRepository genreRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

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
    public ResponseEntity<GroupPageResponse> findPageById(Long id) {
        Optional<GroupPageResponse> pageOptional = repository.findPage(id);

        if (pageOptional.isPresent()) {
            GroupPageResponse page = pageOptional.get();

            page.setGenres(genreRepository.findByGroupId(page.getId()));
            page.setUsers(userRepository.findByGroupId(page.getId()));

            return ResponseEntity.status(HttpStatus.OK).body(page);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<List<GroupMatchResponse>> findMatchList(Long userId,
                                                                  Integer maxDistance,
                                                                  Optional<GenreName> genreName,
                                                                  Optional<Integer> minSize,
                                                                  Optional<Integer> maxSize,
                                                                  Optional<RoleName> missingRoleName) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            List<GroupMatchResponse> matchList = repository.findMatchList(
                    user.getId(),
                    user.getLikedUsers(),
                    genreName.orElse(null),
                    minSize.orElse(null),
                    maxSize.orElse(null),
                    missingRoleName.orElse(null),
                    PAGEABLE
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

            matchList = matchList.stream()
                    .filter(u -> u.getLeaderDistance() <= maxDistance)
                    .sorted(Comparator.comparing(GroupMatchResponse::getRelevance).reversed())
                    .collect(Collectors.toList());

            return matchList.isEmpty() ?
                    ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                    ResponseEntity.status(HttpStatus.OK).body(matchList);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<List<GroupSimpleResponse>> findByName(String nameLike) {
        List<GroupSimpleResponse> foundGroups = repository.findByName(nameLike, PAGEABLE);

        return foundGroups.isEmpty() ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.OK).body(foundGroups);
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

    @Override
    public ResponseEntity<Long> upload(String file) {
        GroupSaveRequest group = new GroupSaveRequest();
        group.setName(file.substring(0,9));
        group.setDescription(file.substring(10,20));
        group.setCreationDate(LocalDate.now());
        List<GenreName> genres = new ArrayList<>();
        genres.add(GenreName.valueOf(file.substring(21,25)));
        group.setGenres(genres);
        return this.save(group);


    }
}
