package com.eddnav.adyensquare.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.eddnav.adyensquare.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val tx = supportFragmentManager.beginTransaction()
            tx.add(R.id.container, ExploreFragment())
            tx.commit()
        }
    }
}