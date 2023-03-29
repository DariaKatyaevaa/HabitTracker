package com.example.habittrackerapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.habittrackerapp.R
import com.example.habittrackerapp.databinding.ActivityHabitTrackerBinding

class HabitTrackerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHabitTrackerBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHabitTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val navController = binding.navHostFragment.getFragment<NavHostFragment>().navController
        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> {
                    navController.navigate(R.id.HomeFragment)
                    true
                }
                R.id.action_app_info -> {
                    navController.navigate(R.id.AboutAppFragment)
                    true
                }
                else -> false
            }.also { binding.drawerLayout.close() }
        }
    }

    override fun onSupportNavigateUp() =
        findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
}