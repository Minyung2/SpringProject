package com.springproject.study.utils;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.springproject.study.user.QUser;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Transactional
public class CommonQueryDSL {
    private final JPAQueryFactory queryFactory;

    public CommonQueryDSL(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public <T> List<T> selectList(EntityPathBase<T> entityPath) {
        return queryFactory.selectFrom(entityPath).fetch();
    }

    public <T> List<T> selectListWithCondition(EntityPathBase<T> entityPath, Predicate predicate) {
        return queryFactory.selectFrom(entityPath)
                .where(predicate)
                .fetch();
    }

    public <T> Boolean existWithoutLimit(String email) {
        QUser qUser = QUser.user; // QUser 인스턴스
        return queryFactory
                .selectFrom(qUser)
                .where(qUser.email.eq(email))
                .fetchCount() > 0;
    }

    public <T> Boolean existWithLimit(EntityPathBase<T> entityPath, StringPath stringPath, String email) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(entityPath)
                .where(stringPath.eq(email))
                .fetchFirst();
        return fetchOne != null;
    }
}

