package com.example.sportdoctorfollow

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class UsersActivity : AppCompatActivity() {
    private var user: users=users()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_users)
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val userNameTxtE:TextView=findViewById(R.id.UserNameTxtE)
        val userNameEmail:String = intent.getStringExtra("email").toString()
        val firestoreHelper = FirestoreHelper()

        firestoreHelper.getUsersone(this,userNameEmail) { result->
            user=result
            userNameTxtE.setText(user.name)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmetContainerUser, UserFragmentMain.newInstance(user,""))
                .commit()


        }

    }


    }
