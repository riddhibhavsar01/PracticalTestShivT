package com.example.practicaltest.data.repository

import com.example.practicaltest.data.db.AppDatabase
import com.example.practicaltest.data.db.GitUser
import com.example.practicaltest.data.db.User
import com.example.practicaltest.data.network.RetrofitApiCall
import com.example.practicaltest.data.network.SafeApiRequest

class UserRepository(
    private val api : RetrofitApiCall,
    private val myDb : AppDatabase

 ) : SafeApiRequest(){

   suspend fun getUserList() : ArrayList<GitUser> {
        return  apiRequest { api.getUserList() }
    }

    suspend fun saveUser(user : User) = myDb.getUserDao().upsert(user)
    suspend fun saveGitUser(user : GitUser) = myDb.getGitUserDao().upsert(user)
    suspend fun getAllGitUser() = myDb.getGitUserDao().getAllUser()
    suspend fun getAllSelectedGitUser() = myDb.getGitUserDao().getAllSelectedUser()
   // suspend fun updateSelectionStatus(user: GitUser) = myDb.getGitUserDao().updateSelectionStatus(user)

     fun getUser() = myDb.getUserDao().getUser()

   suspend fun validateUser(email: String,password:String) = myDb.getUserDao().validateUser(email,password)

}