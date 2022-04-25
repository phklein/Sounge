package soungegroup.soungeapi.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import soungegroup.soungeapi.enums.RoleName;
import soungegroup.soungeapi.model.Role;

import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for role repository")
class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    @DisplayName("Find by name returns role when names match")
    void findByName_ReturnsRole_WhenNamesMatch() {
        Role role = createRole();
        roleRepository.save(role);

        Optional<Role> roleOptional = roleRepository.findByName(role.getName());

        Assertions.assertTrue(roleOptional.isPresent());
        Assertions.assertEquals(role.getName(), roleOptional.get().getName());
    }

    @Test
    @DisplayName("Find by name returns empty optional when names don't match")
    void findByName_ReturnsEmptyOptional_WhenNamesMatch() {
        Role role = createRole();
        roleRepository.save(role);

        Optional<Role> roleOptional = roleRepository.findByName(RoleName.PIANIST);

        Assertions.assertFalse(roleOptional.isPresent());
    }

    private Role createRole() {
        Role role = new Role();
        role.setName(RoleName.GUITARIST);

        return role;
    }
}