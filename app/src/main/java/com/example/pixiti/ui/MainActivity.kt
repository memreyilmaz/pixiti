package com.example.pixiti.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pixiti.R
import com.example.pixiti.databinding.ActivityMainBinding
import com.example.pixiti.utils.KEY_DAY_NIGHT
import com.example.pixiti.utils.PREFS_FILE

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = findNavController(R.id.main_fragment_nav_host)
        binding.mainBottomNavigationView.setupWithNavController(navController)

        setDayNightModeTheme()
    }

    fun setDayNightModePreferences(enableDarkMode: Boolean) {
        val preferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean(KEY_DAY_NIGHT, enableDarkMode)
        editor.apply()

        setDayNightModeTheme()
    }

    private fun setDayNightModeTheme() {
        val preferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)

        if (preferences.getBoolean(KEY_DAY_NIGHT, true)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}