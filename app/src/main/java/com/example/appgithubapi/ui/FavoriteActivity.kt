package com.example.appgithubapi.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appgithubapi.adapter.FavoriteAdapter
import com.example.appgithubapi.data.local.database.Favorite
import com.example.appgithubapi.data.local.database.FavoriteRoomDatabase
import com.example.appgithubapi.databinding.ActivityFavoriteBinding
import com.example.appgithubapi.data.local.database.FavoriteViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val databse = FavoriteRoomDatabase.getDatabaseFav(this)
        val settingViewModel = ViewModelProvider(this, FavoriteViewModelFactory(databse))[FavoriteViewModel::class.java]
        settingViewModel.getFavorite().observeForever { fav: List<Favorite> ->
           setRecycleView(fav)
        }

    }

    private fun setRecycleView(User: List<Favorite>) {
        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFavorite.addItemDecoration(itemDecoration)

        val adapter = FavoriteAdapter(User)
        binding.rvFavorite.adapter = adapter
//        Toast.makeText(this@FavoriteActivity, "${User[0].name}", Toast.LENGTH_SHORT).show()

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallBack{
            override fun onItemClicked(data: Favorite) {
                val detailAct = Intent(this@FavoriteActivity, DetailActivity::class.java)
                detailAct.putExtra(DetailActivity.username,data.name.toString())
                startActivity(detailAct)
            }

        })
    }




}




