package com.example.appgithubapi.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.appgithubapi.R
import com.example.appgithubapi.data.retrofit.ApiConfig
import com.example.appgithubapi.databinding.ActivityDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.bumptech.glide.Glide
import com.example.appgithubapi.adapter.SectionAdapter
import com.example.appgithubapi.data.response.DataDetailResponse
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    lateinit var dataUsername:String
    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val username = "user_name"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_satu,
            R.string.tab_dua
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        datausername()
        ambilDataDetail()
        setViewPager()
    }

    fun ambilDataDetail(){
        showLoading(true)
        val client = ApiConfig.getDetailService().getDetailUsers(dataUsername)
        client.enqueue(object: Callback<DataDetailResponse>{
            override fun onResponse(
                call: Call<DataDetailResponse>,
                response: Response<DataDetailResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful){
                    val responseBody = response.body()
                    var data = DataDetailResponse(
                        responseBody?.login,
                        responseBody?.followers,
                        responseBody?.avatarUrl,
                        responseBody?.following,
                        responseBody?.name
                    )
//                    Toast.makeText(this@DetailActivity, "${responseBody?.name}", Toast.LENGTH_SHORT).show()
                    setDetail(data)
                }else{
                    Log.e("DetailActivity", "onFailure: ${response.message()}")
                    Toast.makeText(this@DetailActivity, "${response.body()?.name}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DataDetailResponse>, t: Throwable) {
                showLoading(false)
                Log.e("DetailActivity", "onFailure: ${t.message}")
            }

        })
    }

    fun setDetail(data: DataDetailResponse){
//        Toast.makeText(this@DetailActivity, "${data.followers}", Toast.LENGTH_SHORT).show()
        binding.tvUsername.text = "${ceknull(data.login.toString())}"
        Glide.with(this).load(data.avatarUrl).into(binding.profileImage)
        binding.tvName.text = "${ceknull(data.name.toString())}"
        binding.tvFollowers.text = "followers: ${data.followers}"
        binding.tvFollowing.text = "following: ${data.following}"

    }

    private fun ceknull(data:String):String{
        return if (data == null) "unknown" else data
    }

    private fun datausername(){
        dataUsername = intent.getStringExtra(username).toString()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.tbFollow.visibility = View.GONE
            binding.vpFollow.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.tbFollow.visibility = View.VISIBLE
            binding.vpFollow.visibility = View.VISIBLE
        }
    }
    private fun setViewPager(){
        val sectionAdapter = SectionAdapter(this)
        val vpFollow: ViewPager2 = binding.vpFollow
        vpFollow.adapter = sectionAdapter
        val tabs: TabLayout = binding.tbFollow
        TabLayoutMediator(tabs, vpFollow) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        sectionAdapter.appName = dataUsername
    }




}


