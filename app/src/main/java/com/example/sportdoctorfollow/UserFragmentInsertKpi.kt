package com.example.sportdoctorfollow

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val USER = "user"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserFragmentInsertKpi.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFragmentInsertKpi : Fragment() {
    // TODO: Rename and change types of parameters
    private var user: users? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(USER, users::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.getParcelable(USER)
            }
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_insert_kpi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var pacientData: InsertKpi = InsertKpi()
        val bloodPreasureSisTxt: EditText = view.findViewById(R.id.sistolicTxtE)
        val bloodPreasureDiasTxt: EditText = view.findViewById(R.id.DiastolicTxtE)
        val weightTxt: EditText = view.findViewById(R.id.WeightTxtE)
        val fatTxt: EditText = view.findViewById(R.id.FatTxtE)
        val heartRateTxt: EditText = view.findViewById(R.id.HeartRateTxtE)
        val caloriesTxt: EditText = view.findViewById(R.id.caloriesTxtE)
        val sendDataBtn: Button = view.findViewById(R.id.SendDataBtn)

        sendDataBtn.setOnClickListener {
            when {
                bloodPreasureSisTxt.text.toString().toInt() <= 0 || bloodPreasureSisTxt.text.toString()
                    .toInt() > 500 || bloodPreasureSisTxt.text.toString()
                    .isBlank() || bloodPreasureDiasTxt.text.toString()
                    .toInt() <= 0 || bloodPreasureDiasTxt.text.toString()
                    .toInt() > 500 || bloodPreasureDiasTxt.text.toString().isBlank()->
                    showToast("Please enter a correct name")


                 weightTxt.text.toString().toDouble() <= 0 || weightTxt.text.toString()
                    .toDouble() > 500 || weightTxt.text.toString()
                    .isBlank() ->
                    showToast("Please enter a correct Weight")

                fatTxt.text.toString().toDouble() <= 0 || fatTxt.text.toString()
                    .toDouble() > 100 || fatTxt.text.toString()
                    .isBlank() ->
                    showToast("Please enter a correct (%) between 0 to 100")

                heartRateTxt.text.toString().toInt() <= 0 || heartRateTxt.text.toString()
                    .toInt() > 300 || heartRateTxt.text.toString()
                    .isBlank() ->
                    showToast("Please enter a Heart Rate")

                caloriesTxt.text.toString().toInt() <= 0 || caloriesTxt.text.toString()
                    .toInt() > 10000 || caloriesTxt.text.toString()
                    .isBlank() ->
                    showToast("Please enter a correct Value of Calories")

                else -> {
                    pacientData=InsertKpi(user?.name.toString(),user?.email.toString(),bloodPreasureSisTxt.text.toString().toInt(),bloodPreasureDiasTxt.text.toString().toInt(),
                        heartRateTxt.text.toString().toInt(),fatTxt.text.toString().toDouble(),weightTxt.text.toString().toDouble(), caloriesTxt.text.toString().toInt())

                    val firestoreHelper=FirestoreHelper()
                    firestoreHelper.insertKPI(requireContext(),pacientData)
                    user?.let {
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragmetContainerUser, UserFragmentMain.newInstance(it, ""))
                            .commit()
                    } ?: showToast("User data is missing")

                }


            }


        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserFragmentInsertKpi.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(user: users, param2: String) =
            UserFragmentInsertKpi().apply {
                arguments = Bundle().apply {
                    putParcelable(USER, user)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}