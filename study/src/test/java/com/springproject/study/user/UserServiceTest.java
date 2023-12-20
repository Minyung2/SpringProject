package com.springproject.study.user;

import com.springproject.study.constant.Role;
import com.springproject.study.utils.CommonQueryDSL;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Slf4j
@Transactional
//@EnableWebSecurity
@TestPropertySource(locations = "classpath:application-test.properties")
class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    CommonQueryDSL commonQueryDSL;
//    @Autowired
//    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("saveUser 테스트")
    void saveUser() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setMobile("01012345678");
        user.setName("테스트");
        user.setPassword("1");
        user.setRole(Role.USER);
        User savedUser = userService.saveUser(user);

        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getPassword(), savedUser.getPassword());
        assertEquals(user.getName(), savedUser.getName());
        assertEquals(user.getMobile(), savedUser.getMobile());
        assertEquals(user.getRole(), savedUser.getRole());
    }

    @Test
    @DisplayName("select QueryDSL List")
    void selectList() {
        saveUser();

        List<User> userList = commonQueryDSL.selectList(QUser.user);
        assertEquals(1, userList.size());
        User retrievedUser = userList.get(0);
        log.info("user : " + retrievedUser.toString());
        assertEquals("test@test.com", retrievedUser.getEmail());
    }
}