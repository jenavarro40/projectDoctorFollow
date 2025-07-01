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
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DoctorExamCheckFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DoctorExamCheckFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
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

        val testreqestBtn: Button = view.findViewById(R.id.OrderTestBtn)

        val recyclerView = view.findViewById<RecyclerView>(R.id.ExamsRecyclerView)

        val email:String = arguments?.getString("email").toString()
        val name:String = arguments?.getString("name").toString()

        val nameTxtView = view.findViewById<TextView>(R.id.nameDoctorCheckTitle)
        nameTxtView.setText(name)

        val examNames = arrayOf("Blood Test", "Urine Test", "Electrocardigram")
        val examImages = intArrayOf(
            R.drawable.bloodtest,
            R.drawable.urinetest,
            R.drawable.electrotest
        )

        val adapter = RecyclerAdapter(requireContext(), examImages, examNames)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())



        testreqestBtn.setOnClickListener {
            val selection = adapter.getSelectedItems()
            val testrequestdata = selectedPositionsToNumber(selection)
            val insertTest = testrequest(name,email,testrequestdata)
            firestoreHelper.testRequest(requireContext(), insertTest)

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
        fun newInstance(param1: String, param2: String) =
            DoctorExamCheckFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
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