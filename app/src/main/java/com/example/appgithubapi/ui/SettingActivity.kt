package com.example.appgithubapi.ui

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.appgithubapi.data.local.datastore.SettingPreference
import com.example.appgithubapi.data.local.datastore.ViewModelFactory
import com.example.appgithubapi.data.local.datastore.dataStore
import com.example.appgithubapi.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreference.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )


        settingViewModel.getThemeSet().observe(this){ isDarkModeActive: Boolean ->

            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.swMode.text = "Night Mode"
                binding.swMode.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.swMode.text = "Light Mode"
                binding.swMode.isChecked = false
            }

        }

        binding.swMode.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
                settingViewModel.saveThemeSet((isChecked))
        }

    }


}