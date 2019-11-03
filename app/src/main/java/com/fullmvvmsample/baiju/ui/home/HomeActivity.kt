package com.fullmvvmsample.baiju.ui.home

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.fullmvvmsample.baiju.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)

        navController = Navigation.findNavController(
            this,
            R.id.fragment
        )
        // setup left drawer with Nav
        NavigationUI.setupWithNavController(nav_view_drawer, navController)
        // To set different text in action bar
        NavigationUI.setupActionBarWithNavController(this, navController, drawer_layout)

    }

    // To navigate back arrow
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.fragment), drawer_layout
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.nav_menu, menu)
        return true
    }
}
