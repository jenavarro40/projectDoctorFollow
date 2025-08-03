package com.example.sportdoctorfollow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.ArrayAdapter
import android.widget.Toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_USER_DOCTOR = "userDoctor"
private const val ARG_TUSER = "typeOfUser"

/**
 * A simple [Fragment] subclass.
 * Use the [DoctorMainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DoctorMainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var userDoctor: String? = null
    private var typeOfUser: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userDoctor = it.getString(ARG_USER_DOCTOR)
            typeOfUser = it.getInt(ARG_TUSER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listView = view.findViewById<ListView>(R.id.listPacients)
        val firestoreHelper = FirestoreHelper()
        firestoreHelper.getUsersforDoctor(requireContext(), { pacients ->
            var textPacients: MutableList<String>
            textPacients = (mutableListOf())
            for (pac in pacients) {
                textPacients.add("${pac.second}")
            }
            val adapter =
                ArrayAdapter(requireContext(), R.layout.spinner_items, textPacients)
            listView.adapter = adapter

            listView.setOnItemClickListener { _, _, position, _ ->

                when(typeOfUser){
                    5->{
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerDoctor, DoctorExamCheckFragment.newInstance(
                                pacients[position].first,pacients[position].second,userDoctor!!))
                            .addToBackStack(null)
                            .commit()

                    }
                    4->{
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerDoctor, DoctorExorciseFragment.newInstance(
                                pacients[position].first,pacients[position].second,userDoctor!!
                            ))
                            .addToBackStack(null)
                            .commit()
                    }
                    3->{
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerDoctor, DoctorFoodFragment.newInstance(
                                pacients[position].first,pacients[position].second,userDoctor!!
                            ))
                            .addToBackStack(null)
                            .commit()
                    }
                }
            }

        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DoctorMainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(userDoctor: String, typeOfUser: Int) =
            DoctorMainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USER_DOCTOR, userDoctor)
                    putInt(ARG_TUSER, typeOfUser)
                }
            }


    }
}