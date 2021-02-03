package com.example.practicaltest.ui.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.practicaltest.R
import com.example.practicaltest.Utils.snackbar
import com.example.practicaltest.ui.login.LoginActivity
import com.example.practicaltest.ui.showUser.ShowUserActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvLogout.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            //set title for alert dialog
            builder.setTitle("")
            //set message for alert dialog
            builder.setMessage("Are you sure want to logout?")
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            //performing positive action
            builder.setPositiveButton("Yes") { dialogInterface, which ->
                val sharedPreferences: SharedPreferences = this.getSharedPreferences(
                    "sharedPrefFile",
                    Context.MODE_PRIVATE
                )
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putInt("log", 0)
                editor.apply()
                editor.commit()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }

            //performing negative action
            builder.setNegativeButton("No") { dialogInterface, which ->

            }
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
        }



        btnAllUser.setOnClickListener { ShowUserActivity.startActivity(this,"All") }
        btnSelectedUser.setOnClickListener { ShowUserActivity.startActivity(this,"Selected") }
    }
}