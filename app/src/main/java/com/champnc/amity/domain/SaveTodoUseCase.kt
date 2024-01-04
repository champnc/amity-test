package com.champnc.amity.domain

import com.champnc.amity.data.TodoRepository
import com.champnc.amity.data.model.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface SaveTodoUseCase {
    operator fun invoke(todo: Todo): Flow<Unit>
}

class SaveTodoUseCaseImpl @Inject constructor(
    private val repository: TodoRepository
): SaveTodoUseCase {
    override fun invoke(todo: Todo): Flow<Unit>  = flow {
        repository.saveTodo(todo)
    }
}