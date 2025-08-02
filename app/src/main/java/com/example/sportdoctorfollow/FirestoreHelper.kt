package com.example.sportdoctorfollow

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class FirestoreHelper {
    private val db = Firebase.firestore


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

    fun testRequest(context: Context, request: testrequest ) {
        db.collection("testrequest")
            .whereEqualTo("email", request.email)
            .limit(1)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty){
                    db.collection("testrequest")
                        .add(request)
                        .addOnSuccessListener { documentReference ->
                            Log.d("FirestoreHelper", "Requested Test: ${request.name}")
                            Toast.makeText(context, "Requested Test: ${documentReference.id}", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Log.w("FirestoreHelper", "Error RequestedTest", e)
                        }

                }
                else {
                    val docId = documents.documents[0].id
                    val updateMap = mapOf("testrequest" to request.testrequest)

                    db.collection("testrequest").document(docId)
                        .update(updateMap)
                        .addOnSuccessListener {
                            Log.d("FirestoreHelper", "Requested Test updated for: ${request.email}")
                            Toast.makeText(context, "Requested Test updated", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Log.w("FirestoreHelper", "Error updating test request", e)
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("FirestoreHelper", "Error in check user", exception)
            }
    }

    fun getUsersone(context: Context,userMail:String, result: (users) -> Unit) {
        db.collection("users")
            .whereEqualTo("email", userMail)
            .limit(1)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val user = documents.first().toObject(users::class.java)
                    result(user)

                } else {
                    Toast.makeText(context, "There was aproblem please Check", Toast.LENGTH_SHORT).show()
                    result(users())
                }
            }
            .addOnFailureListener {exception ->

                Log.w("FirestoreHelper", "Error in read user", exception)
            }
    }

    fun insertKPI(context: Context, insert: InsertKpi ) {
         db.collection("InsertKPI")
             .add(insert)
             .addOnSuccessListener { documentReference ->
                 Log.d("FirestoreHelper", "Insert Data of: ${insert.name}")
                 Toast.makeText(context, "Insert Data of: ${insert.name} at ${insert.date}", Toast.LENGTH_SHORT).show()
             }
             .addOnFailureListener { e ->
                 Log.w("FirestoreHelper", "Error insert Data", e)
             }
    }

    fun getKpiUsers(context: Context,userMail:String,userKPI: (List<InsertKpi>) -> Unit) {
        db.collection("InsertKPI")
            .whereEqualTo("email", userMail)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val kpiList = mutableListOf<InsertKpi>()
                    for (doc in documents){
                        val kpi = doc.toObject(InsertKpi::class.java)
                        kpiList.add(kpi)
                    }
                    userKPI(kpiList)

                } else {
                    Toast.makeText(context, "There is not data for ${userMail}", Toast.LENGTH_SHORT).show()
                    userKPI(mutableListOf())
                }
            }
            .addOnFailureListener {exception ->

                Log.w("FirestoreHelper", "Error in read pacients", exception)
            }
    }

    fun uploadUserFile(context: Context, userMail: String, fileUri: Uri, onResult: (Boolean) -> Unit) {
        val storageRef = Firebase.storage.reference
        var name: String? = null
        val cursor = context.contentResolver.query(fileUri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val index = it.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
                if (index != -1) {
                    name = it.getString(index)
                }
            }
        }
        val fileName = "${userMail}_${name}_${System.currentTimeMillis()}"
        //val safeFileName = "${userMail.replace("[^a-zA-Z0-9]".toRegex(), "_")}_${System.currentTimeMillis()}"
        val fileRef = storageRef.child("user_files/$fileName")

        try {
            val stream = context.contentResolver.openInputStream(fileUri)
            if (stream == null) {
                Toast.makeText(context, "Cannot open selected file", Toast.LENGTH_LONG).show()
                onResult(false)
                return
            }
            fileRef.putStream(stream)
                .addOnSuccessListener {
                    stream.close()
                    fileRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                        val fileData = mapOf(
                            "email" to userMail,
                            "fileUrl" to downloadUrl.toString(),
                            "uploadedAt" to System.currentTimeMillis()
                        )
                        Firebase.firestore.collection("user_files")
                            .add(fileData)
                            .addOnSuccessListener {
                                Toast.makeText(context, "File uploaded successfully", Toast.LENGTH_SHORT).show()
                                onResult(true)
                            }
                            .addOnFailureListener { e ->
                                Log.e("FirestoreHelper", "Error saving file info", e)
                                onResult(false)
                            }
                    }.addOnFailureListener { e ->
                        Log.e("FirestoreHelper", "Error getting download URL", e)
                        onResult(false)
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("FirestoreHelper", "Error uploading file", e)
                    Toast.makeText(context, "Failed to upload file", Toast.LENGTH_SHORT).show()
                    onResult(false)
                }
        } catch (e: Exception) {
            Log.e("UPLOAD", "File not accessible: $fileUri", e)
            Toast.makeText(context, "Cannot access the selected file", Toast.LENGTH_LONG).show()
            onResult(false)
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