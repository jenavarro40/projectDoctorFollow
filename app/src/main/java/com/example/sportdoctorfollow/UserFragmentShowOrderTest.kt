package com.example.sportdoctorfollow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val EMAIL= "email"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserFragmentShowOrderTest.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFragmentShowOrderTest : Fragment() {
    // TODO: Rename and change types of parameters
    private var email: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            email = it.getString(EMAIL)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_show_order_test, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var testRequest: testrequest=testrequest()
        val firestoreHelper = FirestoreHelper()
        val testRequired:TextView=view.findViewById(R.id.testRequired)

        firestoreHelper.getTestRequest(requireContext(),email!!) { result->
            testRequest=result
            val test = listOf("Blood Test", "Urine Test", "Electrocardigram")
            val bittodata=bitsToData()
            testRequired.setText(bittodata.bitsToInfo(test,testRequest.testrequest))
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserFragmentShowOrderTest.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(email: String, param2: String) =
            UserFragmentShowOrderTest().apply {
                arguments = Bundle().apply {
                    putString(EMAIL, email)
                    putString(ARG_PARAM2, param2)
                }
            }
 /*       fun bitsToTest(value: Int): String {
            val test = listOf("Blood Test", "Urine Test", "Electrocardigram")
            val sb = StringBuilder()

            for (i in test.indices) {
                if ((value and (1 shl i)) != 0) {
                    sb.append(test[i]+"\n")
                }
            }
            return sb.toString()
        }*/
    }
}