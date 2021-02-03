package com.example.practicaltest.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GitUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun upsert(user :GitUser) : Long

    @Query("SELECT * FROM gituser")
   suspend fun getAllUser(): List<GitUser>

    @Query("SELECT * FROM gituser where selection = 1")
    suspend fun getAllSelectedUser(): List<GitUser>

 /* @Update
   suspend fun updateSelectionStatus(gitUser: GitUser)*/
}