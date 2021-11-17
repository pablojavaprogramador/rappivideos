package com.rappi.adminsion.data.local;

import androidx.room.Database
import androidx.room.RoomDatabase
const val DB_VERSION = 1

@Database(entities = [FavoriteMovie::class], version = DB_VERSION, exportSchema = false)
abstract class MovieDb : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}