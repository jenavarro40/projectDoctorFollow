package com.example.sportdoctorfollow

class bitsToData {
    fun bitsToInfo(data:List<String>,value: Int): String {
        val sb = StringBuilder()

        for (i in data.indices) {
            if ((value and (1 shl i)) != 0) {
                sb.append(data[i]+"\n")
            }
        }
        return sb.toString()
    }
}