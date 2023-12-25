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
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Slf4j
@Transactional
//@EnableWebSecurity
@TestPropertySource(locations = "classpath:application-test.properties")
class UserServiceTest {

    @Autowired
    private CommonQueryDSL commonQueryDSL;

    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("saveUser 테스트")
    private User saveUser() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setMobile("01012345678");
        user.setName("테스트");
        user.setPassword("1");
        user.setRole(Role.USER);

        return user;
    }

    @Test
    @DisplayName("select QueryDSL List")
    void selectList() {
        User user = saveUser();
        userService.saveUser(user);

        List<User> userList = commonQueryDSL.selectList(QUser.user);
        assertEquals(1, userList.size());
        User retrievedUser = userList.get(0);
        log.info("user : " + retrievedUser.toString());
        assertEquals("test@test.com", retrievedUser.getEmail());
    }

    @Test
    @DisplayName("Duplicate User Test")
    @Transactional
    void saveDuplicateUser() {
        User savedUser = saveUser();
        User newUser = saveUser();
        userService.saveUser(savedUser);

//      중복되지 않은 이메일 테스트
//      newUser.setEmail("test2@test.com");

        Throwable e = assertThrows(IllegalStateException.class, () -> {
            userService.saveUser(newUser);
        });
        assertEquals("이미 가입된 회원입니다.", e.getMessage());
    }


}