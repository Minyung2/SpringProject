package com.springproject.study.utils;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.impl.JPAQueryFactory;
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


}

