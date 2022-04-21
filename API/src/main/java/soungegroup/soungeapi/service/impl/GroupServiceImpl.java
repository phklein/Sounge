package soungegroup.soungeapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import soungegroup.soungeapi.adapter.GroupAdapter;
import soungegroup.soungeapi.model.Group;
import soungegroup.soungeapi.repository.GroupRepository;
import soungegroup.soungeapi.request.GroupSaveRequest;
import soungegroup.soungeapi.response.GroupCsvResponse;
import soungegroup.soungeapi.response.GroupPageResponse;
import soungegroup.soungeapi.response.GroupSimpleResponse;
import soungegroup.soungeapi.service.GroupService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository repository;
    private final GroupAdapter adapter;

    @Override
    public ResponseEntity<GroupSimpleResponse> save(GroupSaveRequest body) {
        Group group = adapter.toGroup(body);

        if (group != null) {
            group = repository.save(group);
            return ResponseEntity.status(HttpStatus.CREATED).body(adapter.toSimpleResponse(group));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Override
    public ResponseEntity<GroupPageResponse> findById(Long id) {
        Optional<Group> groupOptional = repository.findById(id);

        return groupOptional.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(adapter.toPageResponse(groupOptional.get())) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity export() {
        List<GroupCsvResponse> groups = repository.findAllCsv();
        StringBuilder report = new StringBuilder();
        for (GroupCsvResponse g : groups) {
            report.append(String.format("%d;%s;%s;%s\r\n",
                    g.getId(), g.getName(), g.getDescription(), g.getCreationDate()));
        }
        return groups.isEmpty() ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.OK)
                        .header("content-type", "text/csv")
                        .header("content-disposition", "filename=\"groups.csv\"")
                        .body(report.toString());
    }
}
