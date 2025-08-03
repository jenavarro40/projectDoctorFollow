package com.example.sportdoctorfollow

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val USER = "user"
private const val EMAIL = "email"

/**
 * A simple [Fragment] subclass.
 * Use the [UserFragmentMain.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFragmentMain : Fragment() {
    // TODO: Rename and change types of parameters
    private var user: users? = null
    private var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(USER, users::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.getParcelable(USER)
            }
            email = it.getString(EMAIL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.UserRecyclerView)


        val userActivitiesNames =
            arrayOf("Upload Test","Insert Activity", "Test Order", "View Workout","Trend")
        val userActivitiesImages = intArrayOf(
            R.drawable.upload,
            R.drawable.activity,
            R.drawable.bloodtest,
            R.drawable.workout,
            R.drawable.trend
        )

        val adapter = RecyclerAdapterUser(
            requireContext(),
            userActivitiesImages,
            userActivitiesNames
        ) { position ->

        when (position) {
            0 -> {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmetContainerUser, UserFragmentUpload.newInstance(email!!,""))
                    .addToBackStack(null)
                    .commit()

            }

            1 -> {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmetContainerUser, UserFragmentInsertKpi.newInstance(user!!, ""))
                    .addToBackStack(null)
                    .commit()

            }
            2 -> {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmetContainerUser, UserFragmentShowOrderTest.newInstance(email!!,""))
                    .addToBackStack(null)
                    .commit()

            }
            3 -> {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmetContainerUser, UserFragmentWorkout())
                    .addToBackStack(null)
                    .commit()

            }
            4 -> {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmetContainerUser, UserFragmentTrend.newInstance(email!!,""))
                    .addToBackStack(null)
                    .commit()

            }
        }
    }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

    }
    companion object {
        /**
         * Use this factory method to = create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserFragmentMain.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(user: users, email: String) =
            UserFragmentMain().apply {
                arguments = Bundle().apply {
                    putParcelable(USER, user)
                    putString(EMAIL, email)
                }
            }
    }
}