package com.example.sportdoctorfollow

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val EMAIL = "eMail"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserFragmentTrend.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFragmentTrend : Fragment() {
    // TODO: Rename and change types of parameters
    private var eMail: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            eMail = it.getString(EMAIL)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_trend, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title:TextView=view.findViewById(R.id.graphTitleTxt)
        val lineChart: LineChart = view.findViewById(R.id.lineChart)

        /*val fechas = listOf("2025-07-01", "2025-07-02", "2025-07-03", "2025-07-04")
        val valores1 = listOf(10f, 15f, 8f, 20f)
        val valores2 = listOf(5f, 18f, 12f, 22f)*/

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewHorizontal)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val imageList = listOf(
            R.drawable.bloodpreasure,
            R.drawable.weight,
            R.drawable.calories,
            R.drawable.fat,
            R.drawable.heartrate
        )

        recyclerView.adapter = RecyclerAdapterRoundButton(imageList) { position ->
            val firestoreHelper = FirestoreHelper()
            when (position) {
                0 -> {
                    title.setText("Blood Preasure")
                    firestoreHelper.getKpiUsers(requireContext(), eMail.toString(), { userKpi ->

                        val graphCons = graphKPI()
                        graphCons.graphKPI(
                            requireContext(),
                            lineChart,
                            userKpi,
                            "Diastole",
                            "Sistole"
                        )
                    })
                }

                1 -> {
                    firestoreHelper.getKpiUsers(requireContext(), eMail.toString(), { userKpi ->
                        title.setText("Weight")
                        val graphCons = graphKPI()
                        graphCons.graphKPI(
                            requireContext(),
                            lineChart,
                            userKpi,
                            "Weight",
                            ""
                        )
                    })
                }
                2-> {
                    firestoreHelper.getKpiUsers(requireContext(), eMail.toString(), { userKpi ->
                        title.setText("Calories")
                        val graphCons = graphKPI()
                        graphCons.graphKPI(
                            requireContext(),
                            lineChart,
                            userKpi,
                            "Calories",
                            ""
                        )
                    })
                }
                3-> {
                    firestoreHelper.getKpiUsers(requireContext(), eMail.toString(), { userKpi ->
                        title.setText("Fat(%)")
                        val graphCons = graphKPI()
                        graphCons.graphKPI(
                            requireContext(),
                            lineChart,
                            userKpi,
                            "Fat(%)",
                            ""
                        )
                    })
                }
                4-> {
                    firestoreHelper.getKpiUsers(requireContext(), eMail.toString(), { userKpi ->
                        title.setText("Heart Rate")
                        val graphCons = graphKPI()
                        graphCons.graphKPI(
                            requireContext(),
                            lineChart,
                            userKpi,
                            "Heart Rate",
                            ""
                        )
                    })
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
         * @return A new instance of fragment UserFragmentTrend.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(eMail: String, param2: String) =
            UserFragmentTrend().apply {
                arguments = Bundle().apply {
                    putString(EMAIL, eMail)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}