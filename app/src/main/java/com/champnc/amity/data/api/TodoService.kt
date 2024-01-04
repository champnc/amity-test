package com.champnc.amity.data.api

import com.champnc.amity.data.model.TodoResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET

interface TodoService {

    @GET("/todos")
    suspend fun getTodoList(): Response<List<TodoResponse>>
}