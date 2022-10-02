package com.example.testyourself.presentation.ui.fragments.user_fragments.exam_test

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.testyourself.R
import com.example.testyourself.data.models.ExamResult
import com.example.testyourself.data.models.Test
import com.example.testyourself.data.repository.ApiRepository
import com.example.testyourself.domain.ExamTestLogic
import com.example.testyourself.domain.UserRegisterLogic
import com.example.testyourself.presentation.viewmodels.ExamTestModelProviderFactory
import com.example.testyourself.presentation.viewmodels.ExamTestViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_exam_test.*
import kotlinx.android.synthetic.main.fragment_student_home.*


class ExamTestFragment : Fragment() {
    private lateinit var examTestLogic : ExamTestLogic
    private lateinit var viewModel: ExamTestViewModel
    var myList = mutableListOf<Test>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exam_test, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        examTestLogic = ExamTestLogic(view)

        val viewModelProviderFactory= ExamTestModelProviderFactory(ApiRepository())
        viewModel = ViewModelProviders.of(this, viewModelProviderFactory ).get(ExamTestViewModel::class.java)
        observeLiveData()
        super.onViewCreated(view, savedInstanceState)

    }

    private fun observeLiveData(){
        val argument = arguments?.get("examId")
        viewModel.getTest(argument as Int)
        examTestLogic.exam = argument
        viewModel.tests.observe(viewLifecycleOwner, Observer { a->

            a?.let {tests->
                myList.addAll(tests)
                var showTest = 0

                examTestLogic.showTestFun(showTest,myList)
                answer_btn.setOnClickListener {
                    viewModel.postResult(examTestLogic.testAnswer(myList))
                }

            }
        })

    }


}