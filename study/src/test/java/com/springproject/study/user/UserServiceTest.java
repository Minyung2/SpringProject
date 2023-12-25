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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


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

    @Autowired
    UserJdbcTemplateService userJdbcTemplateService;


    User saveUser() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setMobile("01012345678");
        user.setName("테스트");
        user.setPassword("1");
        user.setRole(Role.USER);

        return user;
    }


    @Test
    @DisplayName("100,000 insert")
    @Transactional
    public void insertManyUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 500000; i++) {
            User user = new User();
            user.setId((long) i + 1);
            user.setName("유저" + i);
            user.setEmail("user" + i + "@test.com");
            user.setPassword("pw" + i);
            users.add(user);
        }

        userJdbcTemplateService.insertUsers(users);
        assertEquals(500000, userRepository.count());
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
        insertManyUsers();
        User savedUser = saveUser();
        User newUser = saveUser();
        userService.saveUser(savedUser);

        long startNoLimit = System.nanoTime();
        boolean noLimit = userService.existWithoutLimit(newUser);
        long durationNoLimit = System.nanoTime() - startNoLimit;
        assertTrue(noLimit, "중복되는 사용자가 존재 합니다.");


        // isExist 테스트
        long startIsExist = System.nanoTime();
        Boolean isExist = userService.existWithLimit(newUser);
        long durationIsExist = System.nanoTime() - startIsExist;
        assertTrue(isExist, "중복되는 사용자가 존재 합니다.");

        log.info("noLimit 실행 시간: " + (double) durationNoLimit / 1000000000 + " 나노초");
        log.info("isExist 실행 시간: " + (double) durationIsExist / 1000000000 + " 나노초");

        assertEquals(500001, userRepository.count());
    }


}