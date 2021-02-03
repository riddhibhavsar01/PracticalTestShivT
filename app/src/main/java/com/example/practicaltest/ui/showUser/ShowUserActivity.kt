package com.example.practicaltest.ui.showUser

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnmvvm.Utils.Coroutines
import com.example.practicaltest.R
import com.example.practicaltest.Utils.ApiException
import com.example.practicaltest.Utils.NoInternetException
import com.example.practicaltest.Utils.toast
import com.example.practicaltest.data.db.GitUser
import com.example.practicaltest.data.repository.UserRepository
import kotlinx.android.synthetic.main.activity_show_user.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ShowUserActivity : AppCompatActivity() , KodeinAware ,UserListAdapter.Callback{
    override val kodein by kodein()
    val userRepository : UserRepository by instance()
    lateinit var rvLayout :LinearLayoutManager
    var mType : String = ""
private lateinit var userListAdapter: UserListAdapter
    var userList = ArrayList<GitUser>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_user)

     mType = intent.extras!!.getString("type").toString()

        rvLayout = LinearLayoutManager(this)
        rvUser.setLayoutManager(rvLayout)
        userListAdapter = UserListAdapter(this,userList,this)
        rvUser.adapter = userListAdapter



        getUserData()


    }

    private fun getUserData() {

        val sharedPreferences: SharedPreferences = this.getSharedPreferences("sharedPrefFile",
            Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        val dataInserted = sharedPreferences.getInt("insert",0)

        if(dataInserted == 0 && !mType.equals("Selected")) {
            editor.putInt("insert", 1)
            editor.apply()
            editor.commit()
            Coroutines.main {
                try {
                    var response = userRepository.getUserList()
                    toast(response.toString())
                    response.forEach {
                        userRepository.saveGitUser(
                           it
                        )
                    }
                    if(mType.equals("All")) {
                        userList.addAll(userRepository.getAllGitUser() as ArrayList<GitUser>)
                    }else{
                        userList.addAll(userRepository.getAllSelectedGitUser() as ArrayList<GitUser>)
                    }
                    userListAdapter.notifyDataSetChanged()

                } catch (e: ApiException) {
                    toast("error code : ${e.message}")
                } catch (e: NoInternetException) {
                    toast("${e.message}")
                }
            }
        }

        Coroutines.main {
            if(mType.equals("All")) {
                userList.addAll(userRepository.getAllGitUser() as ArrayList<GitUser>)
            }else{
                userList.addAll(userRepository.getAllSelectedGitUser() as ArrayList<GitUser>)
            }
            userListAdapter.notifyDataSetChanged()
        }


    }

    override fun onItemClick(user: GitUser) {
        Coroutines.main {
            userRepository.saveGitUser(user)
        }
    }

    companion object {

        fun startActivity(mContext: Context,type : String) {
            val intent = Intent(mContext, ShowUserActivity::class.java)
            intent.putExtra("type",type)
            mContext.startActivity(intent)
        }
    }
}