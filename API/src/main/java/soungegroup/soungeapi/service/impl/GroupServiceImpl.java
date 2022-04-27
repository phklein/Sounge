package soungegroup.soungeapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import soungegroup.soungeapi.adapter.GroupAdapter;
import soungegroup.soungeapi.model.Group;
import soungegroup.soungeapi.model.User;
import soungegroup.soungeapi.repository.GenreRepository;
import soungegroup.soungeapi.repository.GroupRepository;
import soungegroup.soungeapi.repository.UserRepository;
import soungegroup.soungeapi.request.GroupPageUpdateRequest;
import soungegroup.soungeapi.request.GroupSaveRequest;
import soungegroup.soungeapi.request.PictureUpdateRequest;
import soungegroup.soungeapi.response.GroupPageResponse;
import soungegroup.soungeapi.response.GroupSimpleResponse;
import soungegroup.soungeapi.service.GroupService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private static final Pageable PAGEABLE = Pageable.ofSize(10);

    private final GroupRepository repository;
    private final GenreRepository genreRepository;
    private final UserRepository userRepository;
    private final GroupAdapter adapter;

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
}
