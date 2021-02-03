package com.example.practicaltest.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import retrofit2.http.Field


@Entity
data class GitUser(
    var login: String? = null,
    var id : Int? = Int.MIN_VALUE,
    @ColumnInfo(name = "url")
    var avatar_url : String? = null,
    var selection : Boolean = false


    ){
    @PrimaryKey(autoGenerate = true)
    var uid :Int =0
}