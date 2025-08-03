package com.example.sportdoctorfollow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "email"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserFragmentWorkout.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFragmentWorkout : Fragment() {
    // TODO: Rename and change types of parameters
    private var email: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            email = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_workout, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var trainRequest:TrainRequest=TrainRequest()
        val firestoreHelper = FirestoreHelper()
        val trainRequired: TextView =view.findViewById(R.id.workOutTrainTxt)

        firestoreHelper.getTrainRequest(requireContext(),email!!) { result->
            trainRequest=result
            val train = listOf("Cardio", "Chest", "Leg","Shoulder","back","Arms")
            val bittodata=bitsToData()
            trainRequired.setText(bittodata.bitsToInfo(train,trainRequest.trainRequest))
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserFragmentWorkout.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(email: String, param2: String) =
            UserFragmentWorkout().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, email)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}