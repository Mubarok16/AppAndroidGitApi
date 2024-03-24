package com.example.appgithubapi.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appgithubapi.adapter.FollowAdapter
import com.example.appgithubapi.data.response.Follow
import com.example.appgithubapi.data.retrofit.ApiConfig
import com.example.appgithubapi.databinding.FragmentFollowersBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class followersFragment : Fragment() {

    lateinit private var _binding: FragmentFollowersBinding

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_NAME = "app_name"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return _binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = this.arguments?.getString(ARG_NAME)

        if (username != null) {
            findUser(username)
        }
    }

    fun findUser(cari: String){
        showLoading(true)
        val client = ApiConfig.getFollowerService().getFollowers(cari)
        client.enqueue(object : Callback<List<Follow>> {
            override fun onResponse(
                call: Call<List<Follow>>,
                response: Response<List<Follow>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setFollowersData(responseBody)
                    }
                } else {
                    Log.e("followFragment", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Follow>>, t: Throwable) {
                showLoading(false)
                Log.e("followFragment", "onFailure: ${t.message}")
            }
        })
    }

    fun setFollowersData(Data: List<Follow>) {
        _binding.rvFollowers.layoutManager = LinearLayoutManager(context)
        _binding.rvFollowers.adapter = FollowAdapter(Data)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            _binding.progressBar.visibility = View.VISIBLE
        } else {
            _binding.progressBar.visibility = View.GONE
        }
    }

}