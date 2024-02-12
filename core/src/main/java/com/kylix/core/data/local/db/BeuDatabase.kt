package com.kylix.core.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kylix.core.data.local.db.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class BeuDatabase: RoomDatabase() {

    abstract fun beuDao(): BeuDao
}