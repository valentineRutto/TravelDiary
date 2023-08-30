package com.valentinerutto.traveldiary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.valentinerutto.traveldiary.databinding.ActivityMainBinding
import com.valentinerutto.traveldiary.ui.TravelViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val viewmodel: TravelViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val database = Firebase.database
        val myRef = database.getReference("travel")

        myRef.setValue("Hello, World!")

        //setSupportActionBar(binding.toolbar)


    }
}