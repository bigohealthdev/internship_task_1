package com.bigohealth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val doctors= Doctors()
        val cities= Cities(doctors.getListener())
         supportFragmentManager.beginTransaction().add(R.id.frame1_main, cities).commit()
         supportFragmentManager.beginTransaction().add(R.id.frame2_main, doctors).commit()
    }
}