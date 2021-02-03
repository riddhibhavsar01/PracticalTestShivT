package com.example.practicaltest.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.learnmvvm.Utils.Coroutines

import com.example.practicaltest.R
import com.example.practicaltest.Utils.snackbar
import com.example.practicaltest.data.db.User
import com.example.practicaltest.data.repository.UserRepository
import com.example.practicaltest.databinding.ActivityLoginBinding
import com.example.practicaltest.ui.home.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class LoginActivity : AppCompatActivity(),LoginListener,KodeinAware{


    override val kodein by kodein()
    val userRepository : UserRepository by instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences("sharedPrefFile",
            Context.MODE_PRIVATE)
        val binding : ActivityLoginBinding =  DataBindingUtil.setContentView(this, R.layout.activity_login);
        val factory = LoginViewModelFactory(userRepository,binding)

        val viewModel = ViewModelProvider(this,factory).get(LoginViewModel::class.java)
        binding.logindata = viewModel
        viewModel.loginListener = this

        val sharedIdValue = sharedPreferences.getInt("init",0)
        val loggedIn = sharedPreferences.getInt("log",0)
        Coroutines.main {

            if (sharedIdValue.equals(0)) {
                val init: Int = 1
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putInt("init", init)
                editor.apply()
                editor.commit()
                userRepository.saveUser(User("abc@gmail.com", "Abc@1234"))

            }
        }
        viewModel.getUserLoggedIn().observe(this, Observer {
            if(it != null && loggedIn!=0 ){
                Intent(this, MainActivity ::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }

            }
        })
    }

    override fun onStarted() {

    }

    override fun onSuccess(msg: String) {
      root_layout.snackbar(msg)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences("sharedPrefFile",
            Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt("log", 1)
        editor.apply()
        editor.commit()
        Intent(this, MainActivity ::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }
    }

    override fun onFailure(msg: String) {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences("sharedPrefFile",
            Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        root_layout.snackbar("Login fail : $msg")
        editor.putInt("log", 0)
        editor.apply()
        editor.commit()
    }
}