package soungegroup.soungeapi.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import soungegroup.soungeapi.model.User;
import soungegroup.soungeapi.repository.NotificationRepository;
import soungegroup.soungeapi.repository.UserRepository;
import soungegroup.soungeapi.request.UserLoginRequest;
import soungegroup.soungeapi.response.UserContactResponse;
import soungegroup.soungeapi.response.UserLoginResponse;
import soungegroup.soungeapi.response.UserMatchResponse;
import soungegroup.soungeapi.response.UserSimpleResponse;
import soungegroup.soungeapi.service.UserService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    @MockBean
    UserRepository userRepository;

    @MockBean
    NotificationRepository notificationRepository;

    @Autowired
    UserService userService;

    @Test
    void loginWithValidUser() {
        User user = mock(User.class);
        UserLoginRequest userRequest = mock(UserLoginRequest.class);
        UserLoginResponse userResponse = mock(UserLoginResponse.class);

        when(userRepository.findUserByEmailAndPassword(user.getEmail(), user.getPassword()))
                .thenReturn(List.of(userResponse));

        assertNotNull(userService.login(userRequest).getBody());
        assertEquals(200, userService.login(userRequest).getStatusCodeValue());
    }

    @Test
    void loginWithInvalidUser() {
        User user = mock(User.class);
        UserLoginRequest userRequest = mock(UserLoginRequest.class);

        when(userRepository.findUserByEmailAndPassword(user.getEmail(), user.getPassword()))
                .thenReturn(List.of());

        assertNull(userService.login(userRequest).getBody());
        assertEquals(401, userService.login(userRequest).getStatusCodeValue());
    }

    @Test
    void loginWithDuplicatedUser() {
        User user = mock(User.class);
        UserLoginRequest userRequest = mock(UserLoginRequest.class);
        UserLoginResponse userResponse = mock(UserLoginResponse.class);

        when(userRepository.findUserByEmailAndPassword(user.getEmail(), user.getPassword()))
                .thenReturn(List.of(userResponse, userResponse));

        assertNull(userService.login(userRequest).getBody());
        assertEquals(409, userService.login(userRequest).getStatusCodeValue());
    }

    @Test
    void logoffWithSession() {
        UserLoginResponse userResponse = new UserLoginResponse();
        userResponse.setId(1L);

        userService.pushSession(userResponse);
        ResponseEntity<Void> response = userService.logoff(1L);

        assertNull(response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void logoffWithoutSession() {
        assertNull(userService.logoff(1L).getBody());
        assertEquals(404, userService.logoff(1L).getStatusCodeValue());
    }

    @Test
    void deleteWithExistingUserAndMatchingPassword() {
        User user = getUser();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        ResponseEntity<Void> response =  userService.delete(user.getId(), user.getPassword());

        assertNull(response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void deleteWithExistingUserAndWrongPassword() {
        User user = getUser();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        ResponseEntity<Void> response =  userService.delete(user.getId(), "Wrong password");

        assertNull(response.getBody());
        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    void deleteWithNonExistentUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response =  userService.delete(1L, "Random password");

        assertNull(response.getBody());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void findContactList() {
        User user = mock(User.class);
        UserContactResponse contactResponse = mock(UserContactResponse.class);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findContactList(any())).thenReturn(List.of(contactResponse));

        doNothing().when(notificationRepository).setMatchesViewedByUser(user);

        ResponseEntity<List<UserContactResponse>> response = userService.findContactList(1L);

        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void findContactListEmpty() {
        User user = mock(User.class);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findContactList(1L)).thenReturn(List.of());

        doNothing().when(notificationRepository).setMatchesViewedByUser(user);

        ResponseEntity<List<UserContactResponse>> response = userService.findContactList(1L);

        assertNull(response.getBody());
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void findMatchList() {
        User user = getUser();
        user.setLikedUsers(List.of());
        UserMatchResponse userMatch = new UserMatchResponse();
        userMatch.setLatitude(user.getLatitude());
        userMatch.setLongitude(user.getLongitude());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findMatchList(any(), any(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(List.of(userMatch));

        ResponseEntity<List<UserMatchResponse>> response = userService.findMatchList(
                1L, 10,
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );

        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void findMatchListEmpty() {
        User user = mock(User.class);
        UserMatchResponse userMatch = mock(UserMatchResponse.class);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findMatchList(any(), any(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(List.of());

        ResponseEntity<List<UserMatchResponse>> response = userService.findMatchList(
                1L, 10,
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );

        assertNull(response.getBody());
        assertEquals(204, response.getStatusCodeValue());
    }

    private User getUser() {
        User user = new User();
        user.setId(1L);
        user.setLatitude(-23.55963439779111);
        user.setLongitude(-46.66361022998323);
        user.setEmail("test@gmail.com");
        user.setPassword("12345678");
        return user;
    }
}