package com.example.sportdoctorfollow

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
//import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val registerBtn: Button = findViewById(R.id.registerBtn)
        val signInBtn: Button = findViewById(R.id.signInBtn)
        val userNameTxtE: EditText = findViewById(R.id.userNameTxtE)
        val passwordTxtE: EditText = findViewById(R.id.PasswordTxtE)


        registerBtn.setOnClickListener() {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        signInBtn.setOnClickListener() {
            val firestoreHelper = FirestoreHelper()
            firestoreHelper.loginUser(
                this, userNameTxtE.text.toString(), passwordTxtE.text.toString())
            { resultValid,typeofuser ->
                if (resultValid) {
                    when (typeofuser){
                        1 ->{
                            val intent = Intent(this, UsersActivity::class.java)
                            intent.putExtra("email", userNameTxtE.text.toString())
                            startActivity(intent)
                        }
                        2 ->{
                            val intent = Intent(this, UsersActivity::class.java)
                            intent.putExtra("email", userNameTxtE.text.toString())
                            startActivity(intent)
                        }
                        3 ->{
                            val intent = Intent(this, FoodActivity::class.java)
                            intent.putExtra("email", userNameTxtE.text.toString())
                            startActivity(intent)
                        }
                        4 ->{
                            val intent = Intent(this, ExorciseActivity::class.java)
                            intent.putExtra("email", userNameTxtE.text.toString())
                            startActivity(intent)
                        }
                        5 ->{
                            val intent = Intent(this, DoctorActivity::class.java)
                            intent.putExtra("email", userNameTxtE.text.toString())
                            startActivity(intent)
                        }
                    }

                } else {
                    Toast.makeText(this, "is {$resultValid}", Toast.LENGTH_SHORT).show()
                }
            }

                //val a=firestoreHelper.hashPassword("1234567")
                //val resultSignin=firestoreHelper.verifyPassword("1234567",a)

        }
    }
}