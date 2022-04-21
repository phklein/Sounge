package soungegroup.soungeapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
<<<<<<< HEAD
import soungegroup.soungeapi.model.Artist;
import soungegroup.soungeapi.request.LoginRequest;
import soungegroup.soungeapi.request.UserSaveRequest;
import soungegroup.soungeapi.response.LoginResponse;
import soungegroup.soungeapi.request.ArtistSaveRequest;
import soungegroup.soungeapi.request.GroupRequest;
import soungegroup.soungeapi.mapper.UserMapper;
import soungegroup.soungeapi.model.Group;
import soungegroup.soungeapi.model.User;
import soungegroup.soungeapi.repository.ArtistRepository;
import soungegroup.soungeapi.repository.GroupRepository;
=======
import soungegroup.soungeapi.adapter.UserAdapter;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.enums.RoleName;
import soungegroup.soungeapi.model.Genre;
import soungegroup.soungeapi.model.Group;
import soungegroup.soungeapi.model.Role;
import soungegroup.soungeapi.model.User;
import soungegroup.soungeapi.repository.GenreRepository;
import soungegroup.soungeapi.repository.GroupRepository;
import soungegroup.soungeapi.repository.RoleRepository;
>>>>>>> develop
import soungegroup.soungeapi.repository.UserRepository;
import soungegroup.soungeapi.request.PasswordChangeRequest;
import soungegroup.soungeapi.request.UserLoginRequest;
import soungegroup.soungeapi.request.UserSaveRequest;
import soungegroup.soungeapi.response.UserCsvResponse;
import soungegroup.soungeapi.response.UserLoginResponse;
import soungegroup.soungeapi.response.UserSimpleResponse;
import soungegroup.soungeapi.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
<<<<<<< HEAD
    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final List<User> activeUsers;
    private final GroupRepository groupRepository;
    private final ArtistRepository artistRepository;

    public UserServiceImpl(UserRepository userRepository,
                                         UserMapper mapper, 
                                        GroupRepository groupRepository,
                                        ArtistRepository artistRepository) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.activeUsers = new ArrayList<>();
        this.groupRepository = groupRepository;
        this.artistRepository  = artistRepository;
    }

    @Override
    public ResponseEntity<LoginResponse> saveAndAuthenticate(UserSaveRequest body) {
        User user = null;
        if (body instanceof  ArtistSaveRequest){
             user  = userRepository.save(mapper.toUser(body));
        }else if (body instanceof GroupRequest){
            user = groupRepository.save((Group) mapper.toUser(body));
        }else if (body instanceof ArtistSaveRequest){
            artistRepository.save((Artist) mapper.toUser(body));
        }
        activeUsers.add(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toLoginResponse(user));
=======
    private final UserRepository repository;
    private final GenreRepository genreRepository;
    private final RoleRepository roleRepository;
    private final GroupRepository groupRepository;

    private final UserAdapter adapter;
    private final List<UserLoginResponse> sessions;

    @Override
    public ResponseEntity<UserLoginResponse> saveAndLogin(UserSaveRequest body) {
        User user = adapter.toUser(body);

        if (user != null) {
            user = repository.save(user);
            UserLoginResponse loginResponse = adapter.toLoginResponse(user);

            sessions.add(loginResponse);
            return ResponseEntity.status(HttpStatus.CREATED).body(loginResponse);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
>>>>>>> develop
    }

    @Override
    public ResponseEntity<UserLoginResponse> login(UserLoginRequest body) {
        List<UserLoginResponse> foundUsers = repository.findUserByEmailAndPassword(body.getEmail(), body.getPassword());

        if (foundUsers.size() == 1) {
            UserLoginResponse user = foundUsers.get(0);
            sessions.add(user);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } else if (foundUsers.size() > 1) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<Void> logoff(Long id) {
        if (sessions.removeIf(u -> u.getId().equals(id))) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<Void> joinGroup(Long id, Long groupId) {
        Optional<Group> groupOptional = groupRepository.findById(groupId);

        if (groupOptional.isPresent()) {
            Group group = groupOptional.get();

            Optional<User> userOptional = repository.findById(id);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setGroup(group);

                if (group.getUsers().isEmpty()) {
                    user.setLeader(true);
                }

                repository.save(user);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<Void> leaveGroup(Long id, Long groupId) {
        if (groupRepository.existsById(groupId)) {
            Optional<User> userOptional = repository.findById(id);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setGroup(null);
                repository.save(user);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<Void> addGenre(Long id, GenreName genreName) {
        Optional<Genre> genreOptional = genreRepository.findByName(genreName);

        if (genreOptional.isPresent()) {
            Genre genre = genreOptional.get();

            Optional<User> userOptional = repository.findById(id);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.getLikedGenres().add(genre);
                repository.save(user);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Override
    public ResponseEntity<Void> addRole(Long id, RoleName roleName) {
        Optional<Role> roleOptional = roleRepository.findByName(roleName);

        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();

            Optional<User> userOptional = repository.findById(id);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.getRoles().add(role);
                repository.save(user);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Override
    public ResponseEntity<Void> removeGenre(Long id, GenreName genreName) {
        Optional<Genre> genreOptional = genreRepository.findByName(genreName);

        if (genreOptional.isPresent()) {
            Genre genre = genreOptional.get();

            Optional<User> userOptional = repository.findById(id);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.getLikedGenres().remove(genre);
                repository.save(user);
                return ResponseEntity.status(HttpStatus.OK).build();
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Override
    public ResponseEntity<Void> removeRole(Long id, RoleName roleName) {
        Optional<Role> roleOptional = roleRepository.findByName(roleName);

        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();

            Optional<User> userOptional = repository.findById(id);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.getRoles().remove(role);
                repository.save(user);
                return ResponseEntity.status(HttpStatus.OK).build();
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Override
    public ResponseEntity<Void> changePassword(Long id, PasswordChangeRequest body) {
        Optional<User> userOptional = repository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (user.getPassword().equals(body.getOldPassword())) {
                user.setPassword(body.getNewPassword());
                repository.save(user);
                return ResponseEntity.status(HttpStatus.OK).build();
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<Void> delete(Long id, String password) {
        Optional<User> userOptional = repository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (user.getPassword().equals(password)) {
                repository.delete(user);
                return ResponseEntity.status(HttpStatus.OK).build();
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<List<UserSimpleResponse>> findAll() {
        List<User> foundUsers = repository.findAll();

        return foundUsers.isEmpty() ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.OK).body(adapter.toSimpleResponse(foundUsers));
    }

    @Override
    public ResponseEntity export() {
        List<UserCsvResponse> users = repository.findAllCsv();
        StringBuilder report = new StringBuilder();
        for (UserCsvResponse u : users) {
            report.append(String.format("%d;%s;%s;%s;%s;%s;%s;%s\r\n",
                    u.getId(), u.getName(), u.getSex(), u.getDescription(),
                    u.getBirthDate(), u.getState(), u.getCity(), u.isLeader()));
        }
        return users.isEmpty() ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.OK)
                .header("content-type", "text/csv")
                .header("content-disposition", "filename=\"users.csv\"")
                .body(report.toString());
    }
}
