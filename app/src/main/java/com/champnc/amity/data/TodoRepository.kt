package com.champnc.amity.data

import com.champnc.amity.data.api.TodoService
import com.champnc.amity.data.db.TodoDao
import com.champnc.amity.data.model.Todo
import com.champnc.amity.data.model.TodoEntity
import com.champnc.amity.data.model.getBoolean
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface TodoRepository {
    fun saveTodo(todo: Todo): Flow<Unit>
    suspend fun loadTodos(): Flow<List<Todo>>
}

class TodoRepositoryImpl @Inject constructor(
    private val api: TodoService,
    private val db: TodoDao
) : TodoRepository {
    override fun saveTodo(todo: Todo): Flow<Unit> = flow {
        db.insertTodo(
            TodoEntity(
                id = todo.id,
                title = todo.title,
                completed = todo.status.getBoolean(),
                userId = todo.userId
            )
        )
    }

    override suspend fun loadTodos(): Flow<List<Todo>> = flow {
        db.getTodos().collect {
            if (it.isEmpty()) {
                val response = api.getTodoList().body()
                val mapped = response?.map {
                    it.toEntity()
                }.orEmpty()
                mapped.forEach {
                    db.insertTodo(it)
                }
                val data = db.getTodos().first().map { entity ->
                    entity.toModel()
                }
                emit(data)
            } else {
                val data = it.map { entity ->
                    entity.toModel()
                }
                emit(data)
            }
        }
    }

}