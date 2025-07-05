package com.example.sportdoctorfollow

//import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.logging.Log


data class users (
    val name:String = "",
    val address:String = "",
    val phone:String = "",
    val email: String = "",
    val password:String = "",
    val usertype:Int = 0
)

data class testrequest (
    val name:String = "",
    val email: String = "",
    val testrequest:Int = 0,
    val doctor:String = ""
)




