package com.momodo.todolist.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TodoListRepositoryImpl implements TodoListRepositoryCustom{

    private final JPAQueryFactory queryFactory;


}
