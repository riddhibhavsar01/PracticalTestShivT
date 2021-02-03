package com.example.practicaltest.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import retrofit2.http.Field

const val CURRENT_USER_ID =0
@Entity
data class User(
   var email: String? = null,
    var password : String? = null,


){
    @PrimaryKey(autoGenerate = false)
    var uid :Int = CURRENT_USER_ID
}