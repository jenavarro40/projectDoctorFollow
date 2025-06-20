package com.example.sportdoctorfollow

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoneHelper {
    private val db = Firebase.firestore

    // Función para agregar un usuario a Firestore
    fun addUser(context: Context, user: users) {
        db.collection("users")
            .whereEqualTo("email", user.email)
            .limit(1)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty){
                    val userHashPw = user.copy(password = hashPassword(user.password))

                    db.collection("users")
                        .add(userHashPw)
                        .addOnSuccessListener { documentReference ->
                            Log.d("FirestoreHelper", "Add User con ID: ${user.email}")
                            Toast.makeText(context, "User Created: ${documentReference.id}", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Log.w("FirestoreHelper", "Error Add User", e)
                        }

                }
                else {
                    Toast.makeText(context, "this user exist", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Log.w("FirestoreHelper", "Error in check user", exception)
            }




    }

    fun loginUser(context: Context,username: String, plainPassword: String, onResult: (Boolean,Int) -> Unit) {
        db.collection("users")
            .whereEqualTo("email", username)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val user = documents.first().toObject(users::class.java)
                    val isValid = verifyPassword(plainPassword, user.password)
                    onResult(isValid,user.usertype)
                } else {
                    Toast.makeText(context, "Error in email or password", Toast.LENGTH_SHORT).show()
                    onResult(false,-1)
                }
            }
            .addOnFailureListener {exception ->

                Log.w("FirestoreHelper", "Error in check user", exception)
            }
    }

    fun getUsersforDoctor(context: Context,pacients: (List<Pair<String,String>>) -> Unit) {
        db.collection("users")
            .whereEqualTo("usertype", 1)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val pacientList = mutableListOf<Pair<String, String>>()
                    for (doc in documents){
                        val user = doc.toObject(users::class.java)
                        pacientList.add(Pair(user.email,user.name))
                    }
                    pacients(pacientList)

                } else {
                    Toast.makeText(context, "There is not Pacient", Toast.LENGTH_SHORT).show()
                    pacients(mutableListOf())
                }
            }
            .addOnFailureListener {exception ->

                Log.w("FirestoreHelper", "Error in read pacients", exception)
            }
    }


    private fun hashPassword(password: String): String {
        return at.favre.lib.crypto.bcrypt.BCrypt.withDefaults()
            .hashToString(12, password.toCharArray())
    }

    private fun verifyPassword(plainPassword: String, hashedPassword: String): Boolean {
        val result = at.favre.lib.crypto.bcrypt.BCrypt.verifyer()
            .verify(plainPassword.toCharArray(), hashedPassword)
        return result.verified
    }
}