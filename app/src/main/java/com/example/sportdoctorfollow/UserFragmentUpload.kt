package com.example.sportdoctorfollow

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val EMAIL = "email"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserFragmentUpload.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFragmentUpload : Fragment() {
    // TODO: Rename and change types of parameters
    private var email: String? = null
    private var param2: String? = null

    private lateinit var firestoreHelper: FirestoreHelper


    private val filePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val fileUri: Uri? = data?.data
            if (fileUri != null && email != null) {
                val takeFlags = data.flags and (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                requireContext().contentResolver.takePersistableUriPermission(fileUri, takeFlags)
                firestoreHelper.uploadUserFile(requireContext(), email!!, fileUri) { success ->
                    if (success) {
                        Toast.makeText(requireContext(), "Success upload", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Error to upload", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            email = it.getString(EMAIL)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_user_upload, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firestoreHelper = FirestoreHelper()

        val btnUploadFile = view.findViewById<Button>(R.id.uploadBtn)

        btnUploadFile.setOnClickListener {
            selectFile()
        }
    }

    private fun selectFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
        }
        filePickerLauncher.launch(intent)
    }

    companion object {
        @JvmStatic
        fun newInstance(email: String, param2: String) =
            UserFragmentUpload().apply {
                arguments = Bundle().apply {
                    putString(EMAIL, email)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
