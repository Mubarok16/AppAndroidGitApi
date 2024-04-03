package com.example.appgithubapi.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appgithubapi.R
import com.example.appgithubapi.adapter.DataUserAdapter
import com.example.appgithubapi.data.local.datastore.SettingPreference
import com.example.appgithubapi.data.local.datastore.ViewModelFactory
import com.example.appgithubapi.data.local.datastore.dataStore
import com.example.appgithubapi.data.retrofit.ApiConfig
import com.example.appgithubapi.data.response.User
import com.example.appgithubapi.data.response.DatagithubResponse
import com.example.appgithubapi.databinding.ActivityMainBinding
import com.example.appgithubapi.ui.DetailActivity.Companion.username
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val layoutManager = LinearLayoutManager(this)
        binding.rvDataApi.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvDataApi.addItemDecoration(itemDecoration)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text
                    searchView.hide()
                    findUser(searchView.text.toString())
                    false
                }
        }

        cekSetting()
        setAppbar()
        findUser(shuffle())
    }

    private fun cekSetting() {
        val pref = SettingPreference.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )

        settingViewModel.getThemeSet().observe(this){ isDarkModeActive: Boolean ->

            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

        }
    }

    private fun setAppbar() {
        binding.AppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.btn_favorite -> {
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.btn_setting -> {
                    val sIntent = Intent(this, SettingActivity::class.java)
                    startActivity(sIntent)
                    true
                }
                else -> false
            }
        }
    }

    fun findUser(cari: String){
        showLoading(true)
        val client = ApiConfig.getApiService().getUsers(cari)
        client.enqueue(object : Callback<DatagithubResponse> {
            override fun onResponse(
                call: Call<DatagithubResponse>,
                response: Response<DatagithubResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setUserData(responseBody.items)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    Toast.makeText(this@MainActivity, "gagal", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DatagithubResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun setUserData(listUser: ArrayList<User>) {
        val adapter = DataUserAdapter()
        adapter.submitList(listUser)
        binding.rvDataApi.adapter = adapter

        adapter.setOnItemClickCallback(object : DataUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                val detailAct = Intent(this@MainActivity, DetailActivity::class.java)
                detailAct.putExtra(username,data.login.toString())
                startActivity(detailAct)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun shuffle(): String{
        val arr = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJK".toCharArray()
        for (i in arr.size - 1 downTo 1) {
            val j = (Math.random() * (i + 1)).toInt()
            val temp = arr[i]
            arr[i] = arr[j]
            arr[j] = temp
        }
        return arr[3].toString()
    }

}




