package soungegroup.soungeapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import soungegroup.soungeapi.adapter.PostAdapter;
import soungegroup.soungeapi.adapter.UserAdapter;
import soungegroup.soungeapi.enums.*;
import soungegroup.soungeapi.model.*;
import soungegroup.soungeapi.repository.*;
import soungegroup.soungeapi.request.*;
import soungegroup.soungeapi.response.*;
import soungegroup.soungeapi.service.UserService;
import soungegroup.soungeapi.util.Fila;
import soungegroup.soungeapi.util.ListaObj;
import soungegroup.soungeapi.util.LocationUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Pageable PAGEABLE = Pageable.ofSize(30);

    private final UserRepository repository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final GenreRepository genreRepository;
    private final RoleRepository roleRepository;
    private final GroupRepository groupRepository;
    private final NotificationRepository notificationRepository;

    private final UserAdapter adapter;
    private final PostAdapter postAdapter;
    private final List<UserLoginResponse> sessions;

    private final LocationUtil locationUtil;

    @Override
    public ResponseEntity<UserLoginResponse> saveAndLogin(UserSaveRequest body) {
        User user = adapter.toUser(body);

        if (user != null) {
            Map<String, Double> coordinates = locationUtil.coordinates(user.getCity(), user.getState());
            user.setLatitude(coordinates.get("lat"));
            user.setLongitude(coordinates.get("lon"));
            user = repository.save(user);
            UserLoginResponse loginResponse = adapter.toLoginResponse(user);
            loginResponse.setNewNotifications(notificationRepository.countNewByUserId(user.getId()));
            loginResponse.setNewMatches(notificationRepository.countNewMatchesByUserId(user.getId()));

            pushSession(loginResponse);
            return ResponseEntity.status(HttpStatus.CREATED).body(loginResponse);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Override
    public ResponseEntity<UserLoginResponse> login(UserLoginRequest body) {
        List<UserLoginResponse> foundUsers = repository.findUserByEmailAndPassword(body.getEmail(), body.getPassword());

        if (foundUsers.size() == 1) {
            UserLoginResponse user = foundUsers.get(0);
            pushSession(user);
            user.setNewNotifications(notificationRepository.countNewByUserId(user.getId()));
            user.setNewMatches(notificationRepository.countNewMatchesByUserId(user.getId()));
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } else if (foundUsers.size() > 1) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public void pushSession(UserLoginResponse user) {
        sessions.add(user);
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
                user.getLikedPosts().add(post);
                repository.save(user);

                Notification notification = new Notification();
                notification.setType(NotificationType.LIKE);
                notification.setSender(user);
                notification.setReceiver(post.getUser());
                notification.setCreationDateTime(LocalDateTime.now());
                notification.setText(String.format("%s deu like em seu post", user.getName()));
                notificationRepository.save(notification);

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
    public ResponseEntity<Void> likeComment(Long id, Long commentId) {
        Optional<User> userOptional = repository.findById(id);
        Optional<Comment> commentOptional = commentRepository.findById(commentId);

        if (userOptional.isPresent() && commentOptional.isPresent()) {
            User user = userOptional.get();
            Comment comment = commentOptional.get();

            if (!user.getLikedComments().contains(comment)) {
                user.getLikedComments().add(comment);
                repository.save(user);

                Notification notification = new Notification();
                notification.setType(NotificationType.LIKE);
                notification.setSender(user);
                notification.setReceiver(comment.getUser());
                notification.setCreationDateTime(LocalDateTime.now());
                notification.setText(String.format("%s deu like em seu comentário", user.getName()));
                notificationRepository.save(notification);

                return ResponseEntity.status(HttpStatus.CREATED).build();
            }

            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<Void> unlikeComment(Long id, Long commentId) {
        Optional<User> userOptional = repository.findById(id);
        Optional<Comment> commentOptional = commentRepository.findById(commentId);

        if (userOptional.isPresent() && commentOptional.isPresent()) {
            User user = userOptional.get();
            Comment comment = commentOptional.get();

            if (user.getLikedComments().contains(comment)) {
                user.getLikedComments().remove(comment);
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
                liker.getRecentLikes().push(liked);
                repository.save(liker);

                if (liked.getLikedUsers().contains(liker)) {
                    Notification likedNotification = new Notification();
                    likedNotification.setType(NotificationType.MATCH);
                    likedNotification.setSender(liker);
                    likedNotification.setReceiver(liked);
                    likedNotification.setCreationDateTime(LocalDateTime.now());
                    likedNotification.setText(String.format("Você sintonizou com %s", liker.getName()));

                    Notification likerNotification = new Notification();
                    likerNotification.setType(NotificationType.MATCH);
                    likerNotification.setSender(liked);
                    likerNotification.setReceiver(liker);
                    likerNotification.setCreationDateTime(LocalDateTime.now());
                    likerNotification.setText(String.format("Você sintonizou com %s", liked.getName()));

                    notificationRepository.save(likedNotification);
                    notificationRepository.save(likerNotification);

                    // TODO: Send notification to liker and liked users
                }

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

            if (user.isLeader()) {
                user.setLeader(false);
                Group group = user.getGroup();
                user.setGroup(null);

                if (group.getUsers().size() > 1) {
                    User nextLeader = group.getUsers().stream()
                            .filter(u -> !u.getId().equals(user.getId()))
                            .collect(Collectors.toList()).get(0);
                    nextLeader.setLeader(true);
                    repository.save(nextLeader);
                    return ResponseEntity.status(HttpStatus.OK).build();
                } else {
                    repository.save(user);
                    groupRepository.delete(group);
                    return ResponseEntity.status(HttpStatus.OK).build();
                }
            }

            if (user.getGroup() != null) {
                user.setGroup(null);
                repository.save(user);
                return ResponseEntity.status(HttpStatus.OK).build();
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
    public ResponseEntity<Void> updateMultipleRoles(Long id, List<RoleName> toAdd, List<RoleName> toRemove) {
        List<Role> rolesToAdd = roleRepository.findByNameIn(toAdd);
        List<Role> rolesToRemove = roleRepository.findByNameIn(toRemove);

        if (rolesToAdd.size() == toAdd.size() && rolesToRemove.size() == toRemove.size()) {
            Optional<User> userOptional = repository.findById(id);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                List<Role> roles = user.getRoles();

                if (roles.containsAll(rolesToRemove) &&
                roles.stream().noneMatch(rolesToAdd::contains)) {
                    roles.removeAll(rolesToRemove);
                    roles.addAll(rolesToAdd);
                    repository.save(user);
                    return ResponseEntity.status(HttpStatus.OK).build();
                }

                return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
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

            Signature signature = user.getSignature();
            signature.setSignatureType(signatureType);
            signature.setExpiryDateTime(signature.isExpired() ?
                    LocalDateTime.now().plusMonths(months) :
                    signature.getExpiryDateTime().plusMonths(months)
            );

            user.setSignature(signature);
            repository.save(user);

            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<Void> updateLocation(Long id, UserLocationUpdateRequest body) {
        Optional<User> userOptional = repository.findById(id);

        if (userOptional.isPresent()){
            User user = userOptional.get();
            user.setLatitude(body.getLatitude());
            user.setLongitude(body.getLongitude());
            repository.save(user);
            return ResponseEntity.status(HttpStatus.OK).build();
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

            profile.setPostList(postAdapter.toSimpleResponseList(postRepository.findByUserIdOrdered(profile.getId(), PAGEABLE)));
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
    public ResponseEntity<List<UserContactResponse>> findContactList(Long id) {
        Optional<User> userOptional = repository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<UserContactResponse> contacts = repository.findContactList(user.getId());

            notificationRepository.setMatchesViewedByUser(user);

            return contacts.isEmpty() ?
                    ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                    ResponseEntity.status(HttpStatus.OK).body(contacts);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<List<NotificationSimpleResponse>> findNotifications(Long id) {
        Optional<User> userOptional = repository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<NotificationSimpleResponse> notifications =
                    notificationRepository.findByUser(user, PAGEABLE);

            notificationRepository.setViewedByUser(user);

            return notifications.isEmpty() ?
                    ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                    ResponseEntity.status(HttpStatus.OK).body(notifications);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<List<NotificationSimpleResponse>> checkNewMatches(Long id) {
        Optional<User> userOptional = repository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<NotificationSimpleResponse> newMatches =
                    notificationRepository.findNewMatchesByUser(user, PAGEABLE);

            notificationRepository.setMatchesViewedByUser(user);

            return newMatches.isEmpty() ?
                    ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                    ResponseEntity.status(HttpStatus.OK).body(newMatches);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<List<UserMatchResponse>> findMatchList(Long id,
                                                                 Integer maxDistance,
                                                                 Optional<Integer> minAge,
                                                                 Optional<Integer> maxAge,
                                                                 Optional<GenreName> genreName,
                                                                 Optional<RoleName> roleName,
                                                                 Optional<Sex> sex,
                                                                 Optional<SkillLevel> skillLevel) {
        Optional<User> userOptional = repository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            Optional<LocalDate> minBirthDate = maxAge.isEmpty() ?
                    Optional.empty() :
                    Optional.of(LocalDate.now().minusYears(maxAge.get()));

            Optional<LocalDate> maxBirthDate = minAge.isEmpty() ?
                    Optional.empty() :
                    Optional.of(LocalDate.now().minusYears(minAge.get()));

            List<UserMatchResponse> matchList = repository.findMatchList(
                    user.getId(),
                    user.getLikedUsers(),
                    minBirthDate.orElse(null),
                    maxBirthDate.orElse(null),
                    genreName.orElse(null),
                    roleName.orElse(null),
                    sex.orElse(null),
                    skillLevel.orElse(null),
                    PAGEABLE
            );

            matchList.forEach(u -> {
                u.setGroup(groupRepository.findByUserId(u.getId()).orElse(null));
                u.setLikedGenres(genreRepository.findByUserId(u.getId()));
                u.setRoles(roleRepository.findByUserId(u.getId()));
                u.setIsOnline(hasSession(u.getId()));
                u.setDistance(locationUtil.distance(
                        user.getLatitude(), user.getLongitude(),
                        u.latitude(), u.longitude()
                ));

                // Calculate relevance, +2 if has signature
                double relevance = u.isHasSignature() ? 2 : 0;

                // 10 - 0.20 for each km away
                relevance += (10 - (u.getDistance() * 0.2));

                // +0.5 for each matching genre
                relevance += 0.5 * u.getLikedGenres().stream().filter(g ->
                        user.getLikedGenres().stream().anyMatch(ug ->
                                ug.getId().equals(g.getId()))).count();

                // +0.5 for each matching roles
                relevance += 0.5 * u.getRoles().stream().filter(r ->
                        user.getRoles().stream().anyMatch(ur ->
                                ur.getId().equals(r.getId()))).count();

                u.setRelevance(relevance);
            });

            matchList = matchList.stream()
                    .filter(u -> u.getDistance() <= maxDistance)
                    .sorted(Comparator.comparing(UserMatchResponse::getRelevance).reversed())
                    .collect(Collectors.toList());
            Fila<UserMatchResponse> fila = new Fila<>(matchList.size());
            for (UserMatchResponse userAux :
                 matchList) {
                fila.insert(userAux);
            }

            return matchList.isEmpty() ?
                    ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                    ResponseEntity.status(HttpStatus.OK).body(matchList);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
           user.setSpotifyID(body.getSpotifyID());
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
            user.setProfilePic(body.getProfilePic());
            user.setBanner(body.getBanner());
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

    @Override

    public ResponseEntity<UserSimpleResponse> roolbackLike(Long id, Long idLike) {
        unlikeUser(id,idLike);
        Optional<User> userOptional = repository.findById(id);
        AtomicReference<User> returnUser = null;
        if (userOptional.get() != null){
             sessions.forEach(userLoginResponse -> {
                 if (userLoginResponse.getId().equals(id)) {
                    returnUser.set(userOptional.get().getRecentLikes().pop());
                 }
             });
        } else {
                 return  ResponseEntity.status(404).build();
            }
            UserSimpleResponse response = adapter.toUserSimpleResponse(returnUser.get());
            return ResponseEntity.status(200).body(response);
        }

    @Override
    public ResponseEntity download(Long id) {
        Optional<User> userOptional = repository.findById(id);

        String body = "";
        String finalString = "";
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String header = "00USERS2022";
            header += LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-mm-yyyy HH:mm:ss"));
            header += "00USERS2022";
            finalString += header;
            body += "02";
            body += String.format("%-100.100s", user.getEmail());
            body += String.format("%-45.45s", user.getPassword());
            body += String.format("%-100.100s", user.getName());
            body += String.format("%-3.3s", user.getSex().name());
            body += String.format("%-256.256s", user.getDescription());
            body += String.format("%-10.10s", user.getBirthDate().toString());
            body += String.format("%-2.2s", user.getState().name());
            body += String.format("%-100.100s", user.getCity());
            body += String.format("%020.20f", user.getLatitude());
            body += String.format("%020.20f", user.getLongitude());
            body += String.format("%-5.5s", Boolean.toString(user.isLeader()));
            body += String.format("%-12.12s", user.getSkillLevel().name());
            body += "\n";
            if (user.getGroup() != null) {
             Optional<Group> groupOptional = groupRepository.findById(user.getGroup().getId());
                if (groupOptional.isPresent()) {
                    Group group = groupOptional.get();
                    body += "03";
                    body += String.format("%-45.45s", group.getName());
                    body += String.format("%-45.45s", group.getDescription());
                    body += String.format("%-10.10s", group.getCreationDate().toString());
                    body += String.format("%-10.10s", group.getGenres().stream().findFirst().get().getName());
                }
            }
            finalString += body;
            String trailer = "01";
            trailer += "02";
            finalString += trailer;
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + "user" + ".txt\"")
                    .header("Content-Type", "text/plain")
                    .body(finalString);
        }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
    }
}


