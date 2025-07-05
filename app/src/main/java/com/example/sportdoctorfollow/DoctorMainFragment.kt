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
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DoctorMainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DoctorMainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var userDoctor: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userDoctor = it.getString(ARG_USER_DOCTOR)
            param2 = it.getString(ARG_PARAM2)
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
                ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, textPacients)
            listView.adapter = adapter

            listView.setOnItemClickListener { _, _, position, _ ->
                val examCheckfragment = DoctorExamCheckFragment()
                val bundle = Bundle()
                bundle.putString("email", pacients[position].first)
                bundle.putString("name",pacients[position].second)
                bundle.putString("userDoctorTest",userDoctor)


                examCheckfragment.arguments = bundle

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, examCheckfragment)
                    .addToBackStack(null)
                    .commit()

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
        fun newInstance(userDoctor: String, param2: String) =
            DoctorMainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USER_DOCTOR, userDoctor)
                    putString(ARG_PARAM2, param2)
                }
            }


    }
}