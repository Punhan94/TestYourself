package com.example.testyourself.presentation.ui.fragments.user_fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testyourself.R
import com.example.testyourself.databinding.FragmentResultBinding
import com.example.testyourself.databinding.FragmentUserProfileBinding
import com.example.testyourself.utils.Constant
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.fragment_result.*


class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val argument = arguments?.get("testResult") as HashMap<Int,Boolean?>
        showPieChart(argument)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showPieChart(argument:HashMap<Int,Boolean?>) {
        val pieEntries: ArrayList<PieEntry> = ArrayList()
        val label = "type"

        var truAnswers = 0
        var falseAnswers = 0
        var nullAnswers = 0
        argument.forEach { t, u ->
            if (u==true){
                truAnswers++
            }
            else if(u==false){
                falseAnswers++
            }
            else{
                nullAnswers++
            }
        }


        //initializing data
        val typeAmountMap: MutableMap<String, Int> = HashMap()
        typeAmountMap["Dogru"] = truAnswers
        typeAmountMap["Sehv"] = falseAnswers
        typeAmountMap["Bos"] = nullAnswers

        //initializing colors for the entries
        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.parseColor("#F02B79"))
        colors.add(Color.parseColor("#a7a29a"))
        colors.add(Color.parseColor("#138808"))



        //input data and fit data into pie chart entry
        for (type in typeAmountMap.keys) {
            pieEntries.add(PieEntry(typeAmountMap[type]!!.toFloat(), type))
        }

        //collecting the entries with label name
        val pieDataSet = PieDataSet(pieEntries, label)
        //setting text size of the value
        pieDataSet.valueTextSize = 12f
        //providing color list for coloring different entries
        pieDataSet.colors = colors
        //grouping the data set from entry to chart
        val pieData = PieData(pieDataSet)
        //showing the value of the entries, default true if not set
        pieData.setDrawValues(true)
        binding.pieChartView.setData(pieData)
        binding.pieChartView.invalidate()
    }


}