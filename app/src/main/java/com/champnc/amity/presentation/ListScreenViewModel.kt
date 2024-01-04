package com.champnc.amity.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.champnc.amity.data.model.Status
import com.champnc.amity.data.model.Todo
import com.champnc.amity.domain.LoadTodoUseCase
import com.champnc.amity.domain.SaveTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val loadTodoUseCase: LoadTodoUseCase,
    private val saveTodoUseCase: SaveTodoUseCase
): ViewModel() {

    private var _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadTodo()
    }

    fun loadTodo() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            val data = loadTodoUseCase().first()
            if (data.isNotEmpty()) {
                _uiState.value = UiState.Success(data)
            }
        }
    }

    fun updateStatus(it: Todo) {
        viewModelScope.launch {
            saveTodoUseCase(Todo(
                id = it.id,
                title = it.title,
                status = Status.COMPLETE,
                userId = it.userId
            ))

            loadTodo()
        }

    }

    sealed class UiState {
        data class Success(val todoList: List<Todo>) : UiState()
        data object Loading: UiState()
        data object Error: UiState()
    }
}