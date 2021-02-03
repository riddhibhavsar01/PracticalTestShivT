package com.example.practicaltest.ui.login

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.practicaltest.data.repository.UserRepository
import com.example.practicaltest.databinding.ActivityLoginBinding

class LoginViewModelFactory(
    private val repository: UserRepository,
    private val binding: ActivityLoginBinding
) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(repository,binding) as T
    }
}