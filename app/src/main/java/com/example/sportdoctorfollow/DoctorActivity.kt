package com.example.sportdoctorfollow

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
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
        val spinnerPacients: Spinner=findViewById(R.id.pacientSpin)

        val firestoreHelper= FirestoneHelper()
        firestoreHelper.getUsersforDoctor(this, { pacients ->
            var textPacients:MutableList<String>
            textPacients=(mutableListOf())
            for (pac in pacients){
                textPacients.add("${pac.second}")
            }
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                textPacients
            )

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            // Asignar al Spinner
            spinnerPacients.adapter = adapter



        })
    }
}