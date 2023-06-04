package com.momodo.todo;

import com.momodo.TestConfig;
import com.momodo.todo.dto.TodoResponseDto;
import com.momodo.todo.repository.TodoRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
public class TodoRepositoryTest {

    @Autowired
    private JPAQueryFactory queryFactory;
    @Autowired
    private TodoRepository todoRepository;

    @Test
    @DisplayName("Todo 등록")
    public void create(){
        // given
        Todo todo = createTodo();

        // when
        Todo createdTodo = todoRepository.save(todo);

        // then
        assertThat(todo.getId()).isEqualTo(createdTodo.getId());
        assertThat(todo.getTitle()).isEqualTo(createdTodo.getTitle());
    }

    @Test
    @DisplayName("Todo Id로 조회")
    public void findById(){
        // given
        Todo savedTodo = todoRepository.save(createTodo());

        // when
        Todo findedTodo = todoRepository.findById(savedTodo.getId()).get();

        // then
        assertThat(findedTodo).isNotNull();
        assertThat(findedTodo.getId()).isEqualTo(savedTodo.getId());
    }

    @Test
    @DisplayName("Todo DueDate로 조회")
    public void findAllByDueDate(){
        // given
        List<Todo> savedTodoList = todoRepository.saveAll(createTodoList());

        // when
        List<TodoResponseDto.Info> todoInfoList = todoRepository.findAllByDueDate(LocalDate.now());

        // then
        assertThat(todoInfoList.size()).isEqualTo(2);
        assertThat(todoInfoList.get(0).getDueDate()).isEqualTo(LocalDate.now());
    }

    private Todo createTodo(){
        return Todo.builder()
                .memberId(1L)
                .title("Test Todo")
                .emoji("Test Emoji")
                .dueDate(LocalDate.now())
                .isCompleted(false)
                .repeatDays(null)
                .build();
    }

    private List<Todo> createTodoList(){
        Todo todo1 = new Todo(1L, 1L, "todo1", "emoji1", LocalDate.of(2023,6,2),false,null);
        Todo todo2 = new Todo(2L, 1L, "todo2", "emoji2", LocalDate.of(2023,6,3),false,null);
        Todo todo3 = new Todo(3L, 1L, "todo3", "emoji3", LocalDate.now(),false,null);
        Todo todo4 = new Todo(4L, 1L, "todo4", "emoji4", LocalDate.now(),false,null);
        List<Todo> todos = List.of(
                todo1, todo2, todo3, todo4
        );

        return todos;
    }
}