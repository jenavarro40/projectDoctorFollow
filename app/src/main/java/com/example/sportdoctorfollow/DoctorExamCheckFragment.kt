package com.example.sportdoctorfollow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_EMAIL= "email"
private const val ARG_NAME = "name"
private const val ARG_DOCTOR = "doctor"

/**
 * A simple [Fragment] subclass.
 * Use the [DoctorExamCheckFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DoctorExamCheckFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var email: String? = null
    private var name: String? = null
    private var doctor: String? = null

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
        return inflater.inflate(R.layout.fragment_doctor_exam_check, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firestoreHelper = FirestoreHelper()

        val testreqestBtn: Button = view.findViewById(R.id.SendDataBtn)
        val viewTest: Button=view.findViewById(R.id.ViewTestBTn)

        val recyclerView = view.findViewById<RecyclerView>(R.id.ExamsRecyclerView)

        val nameTxtView = view.findViewById<TextView>(R.id.nameDoctorCheckTitle)
        nameTxtView.setText(name)

        val examNames = arrayOf("Blood Test", "Urine Test", "Electrocardigram")
        val examImages = intArrayOf(
            R.drawable.bloodtest,
            R.drawable.urinetest,
            R.drawable.electrotest
        )

        val adapter = RecyclerAdapterDoctor(requireContext(), examImages, examNames)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())



        testreqestBtn.setOnClickListener {
            val selection = adapter.getSelectedItems()
            val testrequestdata = selectedPositionsToNumber(selection)
            val insertTest = testrequest(name!!,email!!,testrequestdata,doctor!!)
            firestoreHelper.testRequest(requireContext(), insertTest)

        }
        viewTest.setOnClickListener {
            FirestoreHelper().getUserFiles(requireContext(),email!!)

        }


    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DoctorExamCheckFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(email: String, name: String, doctor: String) =
            DoctorExamCheckFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_EMAIL, email)
                    putString(ARG_NAME ,name)
                    putString(ARG_DOCTOR ,doctor)
                }
            }
    }

    fun selectedPositionsToNumber(selectedPositions: Set<Int>): Int {
        var number = 0
        for (pos in selectedPositions) {
            number = number or (1 shl pos)
        }
        return number
    }
}