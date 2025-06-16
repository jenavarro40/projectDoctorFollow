package com.example.sportdoctorfollow

import android.content.Intent
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
        val firestoreHelper = FirestoneHelper()

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

            val nameStr = name.text.toString()
            val addressStr = address.text.toString()
            val phoneStr = phone.text.toString()
            val emailStr = email.text.toString()
            val passStr = password.text.toString()

            when {
                nameStr.isBlank() || nameStr.length < 5 ->
                    showToast("Please enter a correct name")

                addressStr.isBlank() || addressStr.length < 6 ->
                    showToast("Please enter a correct address")

                phoneStr.isBlank() || !Patterns.PHONE.matcher(phoneStr).matches()  ->
                    showToast("Please enter a correct phone number")

                emailStr.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(emailStr).matches() ->
                    showToast("Please enter a correct e-mail address")

                passStr.isBlank() || passStr.length < 6 ->
                    showToast("Please enter a correct password")

                typeOfUser == 0 ->
                    showToast("Please select one type of user")

                else -> {
                    val insertUser = users(nameStr, addressStr, phoneStr, emailStr, passStr, typeOfUser)
                    firestoreHelper.addUser(this, insertUser)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }

            }
        }


        //

    }
    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}