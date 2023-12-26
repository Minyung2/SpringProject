package com.springproject.study.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.springproject.study.utils.CommonQueryDSL;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final CommonQueryDSL commonQueryDSL;
    private final UserRepository userRepository;
    private final JPAQueryFactory queryFactory;

    public List<User> findAllUsers() {
        return commonQueryDSL.selectList(QUser.user);
    }

    public long getCount() {
        return userRepository.count();
    }

    public Boolean existWithoutLimit(User user) {
        QUser qUser = QUser.user;
        return queryFactory
                .selectFrom(qUser)
                .where(qUser.email.eq(user.getEmail()))
                .fetchCount() > 0;
    }

    public Boolean existWithLimit(User user) {
        QUser qUser = QUser.user;
        Integer fetchOne = queryFactory
                .selectOne()
                .from(qUser)
                .where(qUser.email.eq(user.getEmail()))
                .fetchFirst();
        return fetchOne != null;
    }

    public User saveUser(User user) {
        User savedUser = userRepository.save(user);
        return savedUser;
    }

}
