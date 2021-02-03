package com.example.practicaltest.ui.login

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.SharedPreferences
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModel
import com.example.learnmvvm.Utils.Coroutines
import com.example.practicaltest.Utils.isValidEmail
import com.example.practicaltest.Utils.isValidPassword
import com.example.practicaltest.data.repository.UserRepository
import com.example.practicaltest.databinding.ActivityLoginBinding


class LoginViewModel(
    private val userRepository: UserRepository,
    private val binding: ActivityLoginBinding
) : ViewModel() {

    var email :String? = null
    var password :String? = null
    var loginListener : LoginListener?= null

    fun getUserLoggedIn() = userRepository.getUser()

    fun loginButtonClicked(view: View){
        loginListener?.onStarted()
        if(email.isNullOrEmpty()){
            binding.etEmail.error= "enter email"
            return
        }
        if(!binding.etEmail.isValidEmail(email)){
            binding.etEmail.error= "enter valid email"
            return
        }
        if(password.isNullOrEmpty()){
            binding.etPassword.error= "enter password"
            return
        }
        if(!binding.etPassword.isValidPassword(password)){
            binding.etPassword.error= "enter valid password"
            return
        }
        Coroutines.main {

            val validUser= userRepository.validateUser(email!!, password!!)
            if(validUser == 1){
                loginListener?.onSuccess("user Logged in success")


                return@main
            }
            loginListener?.onFailure("error  login failure")

            binding.etPassword.onEditorAction(EditorInfo.IME_ACTION_DONE);
            binding.etEmail.onEditorAction(EditorInfo.IME_ACTION_DONE);
               }



    }

}