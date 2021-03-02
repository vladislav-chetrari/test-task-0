package com.example.testtask0.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.example.testtask0.R

class ContainerActivity : AppCompatActivity(R.layout.activity_container) {

    override fun onStart() {
        super.onStart()
        setupActionBarWithNavController(this, findNavController(R.id.nav_host_fragment))
    }
}