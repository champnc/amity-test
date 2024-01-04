package com.champnc.amity.data.model

import com.google.gson.annotations.SerializedName

data class TodoResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("completed")
    val completed: Boolean,
    @SerializedName("userId")
    val userId: Int
) {
    fun toEntity(): TodoEntity {
        return TodoEntity(
            id = this.id,
            title = this.title,
            completed = this.completed,
            userId = this.userId
        )
    }
}