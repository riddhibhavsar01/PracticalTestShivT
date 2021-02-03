package com.example.practicaltest.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [User :: class,GitUser :: class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao() : UserDao
    abstract fun getGitUserDao() : GitUserDao

    companion object {
        @Volatile
        private var instance : AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context : Context) = instance ?: synchronized(LOCK){
            instance?:buildInstance(context).also {
                instance = it
            }
        }

        private fun buildInstance(context: Context)  =
            Room.databaseBuilder(context.applicationContext,
            AppDatabase:: class.java,
        "MyDataBase.db"
                ).build()

    }
}