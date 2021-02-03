package com.example.practicaltest.ui.showUser

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.practicaltest.R
import com.example.practicaltest.data.db.GitUser
import kotlinx.android.synthetic.main.item_user_list.view.*

import kotlin.collections.ArrayList

class UserListAdapter(
    var context: Context,
    var users: ArrayList<GitUser>,
    internal var callback: Callback
) : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder{
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_list, parent, false)
        return UserViewHolder(itemView)
    }
    override fun getItemCount(): Int {
        return users.size
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
       // holder.bind(users[position]!!)
        holder.userName.text = users.get(position).login
        holder.cbSelection.isChecked = users.get(position).selection
        Glide.with(context)
            .load(users.get(position).avatar_url)
            .apply(RequestOptions.circleCropTransform())
            .error(R.drawable.ic_launcher_background)
            .into(holder.ivProfileimage)
    }

    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
         val userName : TextView = view.tvName
         val cbSelection : CheckBox = view.cbSelection
         val ivProfileimage : ImageView = view.ivProfileimage

        fun bind(user: GitUser) {


        }

        init {
            cbSelection.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
                users.get(adapterPosition)!!.selection= compoundButton.isChecked
                callback.onItemClick(users.get(adapterPosition)!!)
            })
        }
    }

    interface Callback {
        fun onItemClick(user: GitUser)
    }
}