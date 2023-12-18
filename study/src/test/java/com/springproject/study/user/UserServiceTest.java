package com.springproject.study.user;

import com.springproject.study.constant.Role;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
@EnableWebSecurity
@TestPropertySource(locations = "classpath:application-test.properties")
class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("saveUser 테스트")
    void saveUser() {
        User user = new User();
        String password = passwordEncoder.encode("1234");
        user.setEmail("test@test.com");
        user.setMobile("01012345678");
        user.setName("테스트");
        user.setPassword(password);
        user.setRole(Role.USER);
        User savedUser = userService.saveUser(user);

        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getPassword(), savedUser.getPassword());
        assertEquals(user.getName(), savedUser.getName());
        assertEquals(user.getMobile(), savedUser.getMobile());
        assertEquals(user.getRole(), savedUser.getRole());
    }
}