package com.example.testyourself.presentation.ui.fragments.teacher_fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.testyourself.databinding.FragmentChangeOrNewTestBinding
import com.example.testyourself.domain.models.Test
import com.example.testyourself.presentation.viewmodels.ChangeOrNewTestViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeOrNewTestFragment : Fragment() {
    var showTest = 0
    var exam = 0
    lateinit var test: Test
    var myList = mutableListOf<Test>()
    private var _binding: FragmentChangeOrNewTestBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChangeOrNewTestViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val argument = arguments?.get("examID")
        viewModel.getTest(argument as Int)
        exam = argument
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChangeOrNewTestBinding.inflate(inflater,container,false)
        val view = binding.root
        return view    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        binding.textConfirm.setOnClickListener {
            changeOrNewTest()
        }

    }

    private fun changeOrNewTest() {
        Log.e("examGorundu",exam.toString())
        Log.e("myListsize",myList.size.toString())

        val thisTest = Test(
            testQuestion = binding.testQuestionEditText.text.toString(),
            testTrueAnswer = binding.testTrueAnswer.text.toString(),
            testFalseAnswer1 = binding.testFalseAnswer1.text.toString(),
            testFalseAnswer2 = binding.testFalseAnswer2.text.toString(),
            testFalseAnswer3 = binding.testFalseAnswer3.text.toString(),
            oneTestSecond = binding.oneTestSecond.text.toString().toInt(),
            exam = exam
        )
        if (myList.size>showTest){
            test.testId?.let { viewModel.patchOrPost(showTest, it,thisTest) }
        }else{
            viewModel.patchOrPost(-1,0,thisTest)
        }
    }

    private fun observeLiveData(){
        viewModel.tests.observe(viewLifecycleOwner, Observer { a->

            a?.let {tests->
                myList.addAll(tests)
                showTestFun()
            }
        })
    }

    fun showTestFun(){
        test = myList[showTest]

        if (myList.size>showTest){
            binding.testQuestionEditText.setText(test.testQuestion)
            binding.testTrueAnswer.setText(test.testTrueAnswer)
            binding.testFalseAnswer1.setText(test.testFalseAnswer1)
            binding.testFalseAnswer2.setText(test.testFalseAnswer2)
            binding.testFalseAnswer3.setText(test.testFalseAnswer3)
            binding.oneTestSecond.setText(test.oneTestSecond.toString())
            binding.thisNewTest.visibility = View.GONE
        }else{
            binding.thisNewTest.visibility = View.VISIBLE
        }

    }


}