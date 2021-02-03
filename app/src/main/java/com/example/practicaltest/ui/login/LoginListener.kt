package com.example.practicaltest.ui.login

import androidx.lifecycle.LiveData
import com.example.practicaltest.data.db.User

interface LoginListener {

    fun onStarted()
    fun onSuccess(msg: String)
    fun onFailure(msg : String)
}