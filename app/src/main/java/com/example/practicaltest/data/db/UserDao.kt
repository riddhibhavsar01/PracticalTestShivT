package com.example.practicaltest.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun upsert(user :User) : Long

    @Query("SELECT * FROM user WHERE uid = $CURRENT_USER_ID")
    fun getUser(): LiveData<User>

    @Query("SELECT COUNT(uid) FROM user WHERE email LIKE :userEmail  AND password LIKE :userPassword")
  suspend fun validateUser(userEmail : String,userPassword : String): Int
}