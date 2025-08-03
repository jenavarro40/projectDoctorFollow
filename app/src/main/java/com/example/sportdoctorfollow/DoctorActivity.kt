package com.example.sportdoctorfollow

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DoctorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_doctor)
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val userNameDoctor:String = intent.getStringExtra("email").toString()
        val typeOfUser=intent.getIntExtra("typeOfUser",0)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerDoctor, DoctorMainFragment.newInstance(userNameDoctor, typeOfUser))
            .commit()





    }
}