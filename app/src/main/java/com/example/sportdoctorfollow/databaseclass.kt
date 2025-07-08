package com.example.sportdoctorfollow

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

//import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.logging.Log

@Parcelize
data class users (
    val name:String = "",
    val address:String = "",
    val phone:String = "",
    val email: String = "",
    val password:String = "",
    val usertype:Int = 0
): Parcelable

@Parcelize
data class testrequest (
    val name:String = "",
    val email: String = "",
    val testrequest:Int = 0,
    val doctor:String = ""
): Parcelable

@Parcelize
data class InsertKpi (
    val name:String = "",
    val email: String = "",
    val bloodPreasureSis:Int = 0,
    val bloodPreasureDia:Int = 0,
    val heartRate:Int =0,
    val fatRate:Double=0.0,
    val weight:Double=0.0,
    val calories :Int=0,
    val workout:String="",
    val date:LocalDateTime=LocalDateTime.now()
): Parcelable




