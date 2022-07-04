package com.yellowai.git_pull_requests

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.yellowai.git_pull_requests.databinding.ActivityMainBinding
import com.yellowai.git_pull_requests.utils.navigateToHomeScreen

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.logo.visibility = View.GONE
        navigateToHomeScreen(supportFragmentManager, "home_fragment")
    }

}