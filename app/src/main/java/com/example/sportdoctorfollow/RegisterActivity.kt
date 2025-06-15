package com.example.sportdoctorfollow

import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.RadioGroup
import android.widget.Toast


class RegisterActivity : AppCompatActivity() {

    private var typeOfUser: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {

        val firestoreHelper = FirestoneHelper()
        val userTypeRadGrp: RadioGroup
        val registerBtn: Button
        val name: EditText
        val address: EditText
        val phone: EditText
        val email: EditText
        val password: EditText
        //var flagIssue: Boolean


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userTypeRadGrp = findViewById(R.id.typeosuserRdnBTN)
        registerBtn = findViewById(R.id.RegisterSendBtn)
        name = findViewById(R.id.NameTxtE)
        address = findViewById(R.id.addressTxtE)
        phone = findViewById(R.id.PhoneTxtE)
        email = findViewById(R.id.emailTxtE)
        password = findViewById(R.id.paswRegTxtE)
        //flagIssue = false



        userTypeRadGrp.setOnCheckedChangeListener { _, i ->

            when (i) {
                R.id.BeginnerRadBtn -> {
                    typeOfUser = 1
                }

                R.id.sportPersonRadBtn -> {
                    typeOfUser = 2
                }

                R.id.nutrisionistRadBtn -> {
                    typeOfUser = 3
                }

                R.id.trainerRadBtn -> {
                    typeOfUser = 4
                }

                R.id.doctorRadBtn -> {
                    typeOfUser = 5
                }

            }

        }

        registerBtn.setOnClickListener()
        {

            if (name.text.toString().equals("") || name.text.toString().length < 5 ) {
                Toast.makeText(this, "Please enter a correct name", Toast.LENGTH_SHORT).show()
            }
            else if (address.text.toString().equals("") || address.text.toString().length < 6 ) {
                Toast.makeText(this, "Please enter a correct address", Toast.LENGTH_SHORT).show()
            }
            else if (phone.text.toString().equals("") || !Patterns.PHONE.matcher(phone.toString()).matches() || phone.toString().length < 10) {
                Toast.makeText(this, "Please enter a correct phone number", Toast.LENGTH_SHORT).show()
            }
            else if (email.text.toString().equals("") || !Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches())  {
                Toast.makeText(this, "Please enter a correct e-mail address", Toast.LENGTH_SHORT).show()
            }
            else if (password.text.toString().equals("") || password.text.toString().length < 6) {
                Toast.makeText(this, "Please enter a correct password", Toast.LENGTH_SHORT).show()
            }
            else if (typeOfUser == 0) {
                Toast.makeText(this, "Please select one type of user ", Toast.LENGTH_SHORT).show()

            }
            else {
                val insertUser = users(name.text.toString(),address.text.toString(),phone.text.toString(),email.text.toString(),password.text.toString(),typeOfUser)
                firestoreHelper.addUser(this,insertUser)

            }


        }


        //

    }
}