package com.valentinerutto.traveldiary

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.accessibility.AccessibilityEventCompat.setAction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.valentinerutto.traveldiary.databinding.ActivityTravelEntryBinding
import com.valentinerutto.traveldiary.ui.registration.RegisterLoginActivity


class TravelEntry : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityTravelEntryBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTravelEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        auth = FirebaseAuth.getInstance()

        val navController = findNavController(R.id.nav_host_fragment_content_travel_entry)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "${auth.currentUser}", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser

        if (user == null) {
            startActivity(Intent(this@TravelEntry, RegisterLoginActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_travel_entry)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}