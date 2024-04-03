package com.example.appgithubapi.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appgithubapi.R
import com.example.appgithubapi.adapter.FavoriteAdapter
import com.example.appgithubapi.data.local.database.Favorite
import com.example.appgithubapi.data.local.database.FavoriteRoomDatabase
import com.example.appgithubapi.databinding.ActivityFavoriteBinding
import com.example.appgithubapi.databinding.ActivityMainBinding
import com.loopj.android.http.AsyncHttpClient.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteActivity : AppCompatActivity() {

    val db by lazy { FavoriteRoomDatabase.getDatabase(this) }
    private lateinit var binding: ActivityFavoriteBinding
    private val liveData = MutableLiveData<List<Favorite>>()
    private val observer = Observer<List<Favorite>> {
        getDataFavorite()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        liveData.observe(this,observer)
        getDataFavorite()

    }
    private fun getDataFavorite() {
        CoroutineScope(Dispatchers.IO).launch {
            val fav = db.favDao().getAllFavorite()
//            log.d("FavoriteActivity", "dbResponse: ${fav[0].name}")
            withContext(Dispatchers.Main) {
                setRecycleView(fav)
            }
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


