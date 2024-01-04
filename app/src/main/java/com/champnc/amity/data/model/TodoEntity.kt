package com.champnc.amity.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "completed")
    val completed: Boolean,
    @ColumnInfo(name = "userId")
    val userId: Int
) {
    fun toModel(): Todo {
        return Todo(
            id = this.id,
            title = this.title,
            status = if (completed) Status.COMPLETE else Status.NOT_COMPLETE,
            userId = this.userId
        )
    }
}