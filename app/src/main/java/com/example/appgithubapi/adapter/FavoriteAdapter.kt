package com.example.appgithubapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appgithubapi.data.local.database.Favorite
import com.example.appgithubapi.data.response.User
import com.example.appgithubapi.databinding.RowDataApiBinding

class FavoriteAdapter(private val listUser:List<Favorite>): RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallBack
    inner class FavoriteHolder(private val binding: RowDataApiBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Favorite){
            Glide.with(itemView.context).load(data.avatarUrl).into(binding.profileImage)
            binding.tvItemName.text = data.name
        }
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: Favorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        return FavoriteHolder(RowDataApiBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        var user = listUser[position]
        holder.bind(user)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(user) }
    }
    fun setOnItemClickCallback(onItemClickCallback: FavoriteAdapter.OnItemClickCallBack) {
       this.onItemClickCallback = onItemClickCallback
    }

}




