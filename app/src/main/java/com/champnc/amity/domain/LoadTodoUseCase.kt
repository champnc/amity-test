package com.champnc.amity.domain

import com.champnc.amity.data.TodoRepository
import com.champnc.amity.data.model.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface LoadTodoUseCase {
    suspend operator fun invoke(): Flow<List<Todo>>
}

class LoadTodoUseCaseImpl @Inject constructor(
    private val repository: TodoRepository
): LoadTodoUseCase {
    override suspend fun invoke(): Flow<List<Todo>> = flow {
        emit(repository.loadTodos().first())
    }
}