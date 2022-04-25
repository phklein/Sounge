package soungegroup.soungeapi.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import soungegroup.soungeapi.enums.Sex;
import soungegroup.soungeapi.enums.SkillLevel;
import soungegroup.soungeapi.enums.State;
import soungegroup.soungeapi.model.User;
import soungegroup.soungeapi.response.UserCsvResponse;
import soungegroup.soungeapi.response.UserLoginResponse;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@DisplayName("Tests for user repository")
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Find by email and password returns non empty list when credentials match")
    void findByEmailAndPassword_ReturnsNonEmptyList_WhenCredentialsMatch() {
        User user = createUser();

        userRepository.save(user);

        List<UserLoginResponse> foundUsers = userRepository.findUserByEmailAndPassword(
                user.getEmail(), user.getPassword());

        Assertions.assertNotNull(foundUsers);
        Assertions.assertNotEquals(0, foundUsers.size());
        Assertions.assertEquals(1, foundUsers.size());
    }

    @Test
    @DisplayName("Find by email and password returns empty list when credentials are wrong")
    void findByEmailAndPassword_ReturnsEmptyList_WhenCredentialsWrong() {
        userRepository.save(createUser());

        List<UserLoginResponse> foundUsers = userRepository.findUserByEmailAndPassword(
                "wrongemail", "wrongpass");

        Assertions.assertNotNull(foundUsers);
        Assertions.assertEquals(0, foundUsers.size());
    }

    @Test
    @DisplayName("Find all csv returns non empty list when table has registers")
    void findAllCsv_ReturnsNonEmptyList_WhenTableHasRegisters() {
        final int count = 10;

        for (int i = 0; i < count; i ++) {
            userRepository.save(createUser());
        }

        List<UserCsvResponse> foundUsers = userRepository.findAllCsv();

        Assertions.assertNotNull(foundUsers);
        Assertions.assertNotEquals(0, foundUsers.size());
        Assertions.assertEquals(count, foundUsers.size());
    }

    @Test
    @DisplayName("Find all csv returns empty list when table has no registers")
    void findAllCsv_ReturnsEmptyList_WhenTableHasNoRegisters() {
        List<UserCsvResponse> foundUsers = userRepository.findAllCsv();

        Assertions.assertNotNull(foundUsers);
        Assertions.assertEquals(0, foundUsers.size());
    }

    private User createUser() {
        User user = new User();
        user.setEmail("testuser@gmail.com");
        user.setPassword("12345678");
        user.setName("Test User");
        user.setSex(Sex.NOT_KNOWN);
        user.setDescription("Test description");
        user.setBirthDate(LocalDate.of(2003, 5, 12));
        user.setState(State.BA);
        user.setCity("Salvador");
        user.setSkillLevel(SkillLevel.ADVANCED);

        return user;
    }
}