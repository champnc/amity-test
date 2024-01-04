package com.champnc.amity.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.champnc.amity.data.model.TodoEntity

@Database(entities = [TodoEntity::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao
}