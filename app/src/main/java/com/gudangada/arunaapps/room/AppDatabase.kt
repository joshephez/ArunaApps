package com.gudangada.arunaapps.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gudangada.arunaapps.entity.Model


@Database(entities = [Model::class], version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun postsDao(): DAO

}