package com.champnc.amity.data.model

data class Todo(
    val id: Int,
    val title: String,
    val status: Status,
    val userId: Int
)

enum class Status {
    COMPLETE,
    NOT_COMPLETE
}

fun Status.getBoolean(): Boolean {
    return when(this) {
        Status.COMPLETE -> true
        Status.NOT_COMPLETE -> false
    }
}
