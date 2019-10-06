package com.fullmvvmsample.baiju.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fullmvvmsample.baiju.data.db.entities.User
import com.fullmvvmsample.baiju.data.db.entities.UserDao

@Database(entities = [User::class], version = 1)

abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object {

        @Volatile // Volatile means, this variable is easily available to all the other threads
        private var instance: AppDatabase? = null
        private val LOCK = Any() // LOCK help us create a single instance only of DB

        // Create a instance of DB
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        // Create DB
        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "MyDatabase.db"
        ).build()
    }

}