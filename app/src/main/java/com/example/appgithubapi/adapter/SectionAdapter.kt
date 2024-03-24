package com.example.appgithubapi.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.appgithubapi.ui.followersFragment
import com.example.appgithubapi.ui.followingFragment

class SectionAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var appName: String = ""

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        val fragmentfollowers = followersFragment()
        val fragmentfollowing = followingFragment()

        when (position) {
            0 -> {
                fragmentfollowers.arguments = Bundle().apply { putString(followersFragment.ARG_NAME,appName) }
                fragment = fragmentfollowers
            }
            1 -> {
                fragmentfollowing.arguments = Bundle().apply { putString(followingFragment.ARG_NAME,appName) }
                fragment = fragmentfollowing
            }
        }

        return fragment as Fragment

    }

}

