package com.example.sportdoctorfollow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_EMAIL= "email"
private const val ARG_NAME = "name"
private const val ARG_DOCTOR = "doctor"

/**
 * A simple [Fragment] subclass.
 * Use the [DoctorFoodFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DoctorFoodFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var email: String? = ""
    private var name: String? = ""
    private var doctor: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            email = it.getString(ARG_EMAIL)
            name= it.getString(ARG_NAME)
            doctor= it.getString(ARG_DOCTOR)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_food, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DoctorFoodFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(email: String, name: String, doctor: String) =
            DoctorFoodFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_EMAIL, email)
                    putString(ARG_NAME ,name)
                    putString(ARG_DOCTOR ,doctor)
                }
            }
    }
}