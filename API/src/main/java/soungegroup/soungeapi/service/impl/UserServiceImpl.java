package soungegroup.soungeapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import soungegroup.soungeapi.adapter.UserAdapter;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.enums.RoleName;
import soungegroup.soungeapi.enums.SignatureType;
import soungegroup.soungeapi.model.*;
import soungegroup.soungeapi.repository.*;
import soungegroup.soungeapi.request.*;
import soungegroup.soungeapi.response.*;
import soungegroup.soungeapi.service.UserService;
import soungegroup.soungeapi.util.ListaObj;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Pageable PAGEABLE = Pageable.ofSize(50);

    private final UserRepository repository;
    private final PostRepository postRepository;
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

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @Override
    public ResponseEntity<Boolean> checkSession(Long id) {
        Optional<User> userOptional = repository.findById(id);

        if (userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(hasSession(userOptional.get().getId()));
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
    public ResponseEntity<Void> likePost(Long id, Long postId) {
        Optional<User> userOptional = repository.findById(id);
        Optional<Post> postOptional = postRepository.findById(postId);

        if (userOptional.isPresent() && postOptional.isPresent()) {
            User user = userOptional.get();
            Post post = postOptional.get();

            if (!user.getLikedPosts().contains(post)) {
                user.getLikedPosts().add(postOptional.get());
                repository.save(user);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }

            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<Void> unlikePost(Long id, Long postId) {
        Optional<User> userOptional = repository.findById(id);
        Optional<Post> postOptional = postRepository.findById(postId);

        if (userOptional.isPresent() && postOptional.isPresent()) {
            User user = userOptional.get();
            Post post = postOptional.get();

            if (user.getLikedPosts().contains(post)) {
                user.getLikedPosts().remove(post);
                repository.save(user);
                return ResponseEntity.status(HttpStatus.OK).build();
            }

        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<Void> likeUser(Long id, Long likedId) {
        Optional<User> likerOptional = repository.findById(id);
        Optional<User> likedOptional = repository.findById(likedId);

        if (likerOptional.isPresent() && likedOptional.isPresent()) {
            User liker = likerOptional.get();
            User liked = likedOptional.get();

            if (!liker.getLikedUsers().contains(liked)) {
                liker.getLikedUsers().add(liked);
                repository.save(liker);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }

            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<Void> unlikeUser(Long id, Long likedId) {
        Optional<User> likerOptional = repository.findById(id);
        Optional<User> likedOptional = repository.findById(likedId);

        if (likerOptional.isPresent() && likedOptional.isPresent()) {
            User liker = likerOptional.get();
            User liked = likedOptional.get();

            if (liker.getLikedUsers().contains(liked)) {
                liker.getLikedUsers().remove(liked);
                repository.save(liker);
                return ResponseEntity.status(HttpStatus.OK).build();
            }
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
    public ResponseEntity<Void> leaveGroup(Long id) {
        Optional<User> userOptional = repository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setGroup(null);
            repository.save(user);
            return ResponseEntity.status(HttpStatus.OK).build();
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

                if (!user.getLikedGenres().contains(genre)) {
                    user.getLikedGenres().add(genre);
                    repository.save(user);
                    return ResponseEntity.status(HttpStatus.CREATED).build();
                }

                return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
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

                if (!user.getRoles().contains(role)) {
                    user.getRoles().add(role);
                    repository.save(user);
                    return ResponseEntity.status(HttpStatus.CREATED).build();
                }

                return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
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

                if (user.getLikedGenres().contains(genre)) {
                    user.getLikedGenres().remove(genre);
                    repository.save(user);
                    return ResponseEntity.status(HttpStatus.OK).build();
                }

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

                if (user.getRoles().contains(role)) {
                    user.getRoles().remove(role);
                    repository.save(user);
                    return ResponseEntity.status(HttpStatus.OK).build();
                }
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Override
    public ResponseEntity<Void> updateSignature(Long id, SignatureType signatureType) {
        Optional<User> userOptional = repository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Signature signature = new Signature();

            int months = 1;

            switch (signatureType) {
                case YEARLY:
                    months = 12;
                    break;
                case SEMIANNUAL:
                    months = 6;
                    break;
                default:
                    break;
            }

            signature.setSignatureType(signatureType);
            signature.setExpiryDate(signature.getExpiryDate().plusMonths(months));

            user.setSignature(signature);

            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public Boolean checkSignature(User user) {
            return user.getSignature().getExpiryDate().isBefore(LocalDateTime.now());
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

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<String> export() {
        List<UserCsvResponse> users = repository.findAllCsv(PAGEABLE);
        ListaObj<UserCsvResponse> responseObj = new ListaObj<>(users.size());
        for (UserCsvResponse csv: users) {
            responseObj.adiciona(csv);
        }
        StringBuilder report = new StringBuilder();
        for (int i = 0; i < responseObj.getTamanho(); i++) {
            UserCsvResponse u = responseObj.getElemento(i);
            report.append(String.format("%d;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\r%n",
                    u.getId(), u.getName(), u.getSex(), u.getDescription(),
                    u.getBirthDate(), u.getState(), u.getCity(),
                    u.getLatitude(), u.getLongitude(),
                    u.isLeader(), u.getSkillLevel()));
        }
        return users.isEmpty() ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.OK)
                .header("content-type", "text/csv")
                .header("content-disposition", "filename=\"users.csv\"")
                .body(report.toString());
    }

    @Override
    public ResponseEntity<UserProfileResponse> getProfileById(Long viewerId, Long id) {
        Optional<User> viewerOptional = repository.findById(viewerId);
        Optional<UserProfileResponse> profileOptional = repository.findProfile(id);

        if (profileOptional.isPresent() && viewerOptional.isPresent()) {
            User viewer = viewerOptional.get();
            UserProfileResponse profile = profileOptional.get();

            profile.setPostList(postRepository.findByUserIdOrdered(profile.getId(), PAGEABLE));
            profile.setLikedGenres(genreRepository.findByUserId(profile.getId()));
            profile.setRoles(roleRepository.findByUserId(profile.getId()));

            Optional<GroupSimpleResponse> groupOptional = groupRepository.findByUserId(profile.getId());
            profile.setGroup(groupOptional.isPresent() ? groupOptional.get() : null);

            profile.getPostList().forEach(p -> p.setHasLiked(viewer.getLikedPosts().stream()
                    .anyMatch(lp -> lp.getId().equals(p.getId()))));
            profile.setViewerProfile(viewerId.equals(profile.getId()));
            profile.setIsOnline(hasSession(profile.getId()));
            return ResponseEntity.status(HttpStatus.OK).body(profile);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<List<UserMatchResponse>> findMatchList(Long viewerId) {
        return null;
    }

    @Override
    public ResponseEntity<List<UserSimpleResponse>> findByName(String nameLike) {
        List<UserSimpleResponse> foundUsers = repository.findByName(nameLike, PAGEABLE);

        return foundUsers.isEmpty() ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.OK).body(foundUsers);
    }

    private Boolean hasSession(Long id) {
        for (UserLoginResponse ulr: sessions) {
            if (ulr.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    @Override
    public ResponseEntity<Void> updateProfilePage(Long id, UserProfileUpdateRequest body) {
       Optional<User> userOptional = repository.findById(id);

       if (userOptional.isPresent()){
           User user = userOptional.get();
           user.setSpotifyID(body.getSpotifyId());
           user.setDescription(body.getDescription());
           user.setName(body.getName());
           repository.save(user);
           return ResponseEntity.status(HttpStatus.OK).build();
       }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<Void> updatePicture(Long id, PictureUpdateRequest body) {
        Optional<User> userOptional = repository.findById(id);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            user.setProfilePic(body.getUrl());
            repository.save(user);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<Void> updatePassword(Long id, UserPasswordUpdateRequest body) {
        Optional<User> userOptional = repository.findById(id);

        if (userOptional.isPresent()){
            User user = userOptional.get();

            if (body.getOldPassword().equals(user.getPassword())) {
                user.setPassword(body.getNewPassword());
                repository.save(user);
                return ResponseEntity.status(HttpStatus.OK).build();
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}

