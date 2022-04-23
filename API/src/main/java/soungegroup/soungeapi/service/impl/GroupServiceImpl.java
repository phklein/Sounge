package soungegroup.soungeapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import soungegroup.soungeapi.adapter.GroupAdapter;
import soungegroup.soungeapi.model.Group;
import soungegroup.soungeapi.repository.GroupRepository;
import soungegroup.soungeapi.request.GroupSaveRequest;
import soungegroup.soungeapi.request.UpdateGroupPageRequest;
import soungegroup.soungeapi.response.GroupCsvResponse;
import soungegroup.soungeapi.response.GroupPageResponse;
import soungegroup.soungeapi.response.UserCsvResponse;
import soungegroup.soungeapi.service.GroupService;
import soungegroup.soungeapi.util.ListaObj;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository repository;
    private final GroupAdapter adapter;

    @Override
    public ResponseEntity<Long> save(GroupSaveRequest body) {
        Group group = adapter.toGroup(body);

        if (group != null) {
            group = repository.save(group);
            return ResponseEntity.status(HttpStatus.CREATED).body(group.getId());
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
        ListaObj<GroupCsvResponse> responseObj = new ListaObj<GroupCsvResponse>(groups.size());
        for (GroupCsvResponse csv: groups) {
            responseObj.adiciona(csv);
        }
        StringBuilder report = new StringBuilder();
        for (int i = 0; i < responseObj.getTamanho(); i++) {
            GroupCsvResponse g = responseObj.getElemento(i);
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

    @Override
    public ResponseEntity<Void> update(Long id, UpdateGroupPageRequest body) {
        Optional<Group> groupOptional = repository.findById(id);

        if (groupOptional.isPresent()) {
            Group group = groupOptional.get();
            group.setName(body.getName());
            group.setDescription(body.getDescription());
            group.setPictureUrl(body.getPictureUrl());
            repository.save(group);
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
