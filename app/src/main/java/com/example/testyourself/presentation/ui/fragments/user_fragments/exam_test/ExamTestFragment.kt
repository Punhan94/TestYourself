package com.example.testyourself.presentation.ui.fragments.user_fragments.exam_test

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.*
import android.widget.RadioButton
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.testyourself.R
import com.example.testyourself.data.models.ExamResult
import com.example.testyourself.data.models.Test
import com.example.testyourself.data.repository.ApiRepository
import com.example.testyourself.databinding.FragmentExamTestBinding
import com.example.testyourself.databinding.FragmentStudentHomeBinding
import com.example.testyourself.domain.ExamTestLogic
import com.example.testyourself.domain.UserRegisterLogic
import com.example.testyourself.presentation.viewmodels.ExamTestModelProviderFactory
import com.example.testyourself.presentation.viewmodels.ExamTestViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_exam_test.*
import kotlinx.android.synthetic.main.fragment_exam_test.view.*
import kotlinx.android.synthetic.main.fragment_student_home.*


class ExamTestFragment : Fragment() {
    private var _binding: FragmentExamTestBinding? = null
    private val binding get() = _binding!!
    private lateinit var examTestLogic : ExamTestLogic
    private lateinit var viewModel: ExamTestViewModel
    var myList = mutableListOf<Test>()
    lateinit var result : ExamResult
    var showTest = 0
    private val userAnswerList = hashMapOf<Int,Boolean?>()
    var resultAnswer = ""
    private var answerBoolean : Boolean? = null
    var student = 3454
    var exam = 0
    var testNumber = 0
    var lastRadioButtonItem : RadioButton?=null
    var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentExamTestBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        examTestLogic = ExamTestLogic(view)

        val viewModelProviderFactory= ExamTestModelProviderFactory(ApiRepository())
        viewModel = ViewModelProviders.of(this, viewModelProviderFactory ).get(ExamTestViewModel::class.java)
        observeLiveData()
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
        object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                exitTest(userAnswerList)
            }
        } )



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeLiveData(){
        val argument = arguments?.get("examId")
        viewModel.getTest(argument as Int)
        exam = argument
        viewModel.tests.observe(viewLifecycleOwner, Observer { a->

            a?.let {tests->
                myList.addAll(tests)
                showTestFun()
                answer_btn.setOnClickListener {
                    testAnswer(myList)
                }

            }
        })

    }

    fun postResultTest(examResult: ExamResult){
        viewModel.postResult(examResult)
    }

    //test show function
    fun showTestFun() {
        clearCheck()

        val test = myList[showTest]
        val answers = arrayListOf(
            test.testTrueAnswer, test.testFalseAnswer1,
            test.testFalseAnswer2, test.testFalseAnswer3
        )
        answers.shuffle()


        test.testImage?.let {
            Picasso.get().load(it).into(binding.examTestImg)
        }
        binding.examTestNum.text = (showTest + 1).toString()
        binding.examTestQuestion.text = test.testQuestion
        binding.radioGroup21.text = answers[0]
        binding.radioGroup22.text = answers[1]
        binding.radioGroup23.text = answers[2]
        binding.radioGroup24.text = answers[3]
        timer()
        testNumber = showTest + 1

        binding.radioGroup2.setOnCheckedChangeListener{ group, checkedId ->
            val radioButton: RadioButton = group.findViewById(checkedId) as RadioButton
            if (lastRadioButtonItem !=radioButton){
                radioButton.setBackgroundColor(Color.GREEN)
                lastRadioButtonItem?.let {
                    group.findViewById<RadioButton>(it.id).background = ContextCompat.getDrawable(requireContext(), R.color.purple_700)
                }
                lastRadioButtonItem=radioButton
            }

        }

    }

    //test timer
    fun timer() {
        val test = myList[showTest]
        val time: Long = test.oneTestSecond?.toLong()?.times(1000) ?: 30000
        testTime( time)
    }

    //test time start
    private fun testTime(thisTime: Long) {

        countDownTimer = object : CountDownTimer(thisTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.examTestTime.text = (millisUntilFinished / 1000).toString()
            }
            override fun onFinish() {
                testAnswer(myList)
            }
        }.start()
    }

    //test answer function
    fun testAnswer(myList: MutableList<Test>) {

        try {
            if (lastRadioButtonItem != null) {
                resultAnswer = lastRadioButtonItem?.text.toString()
                userAnswerList[showTest+1] = lastRadioButtonItem?.text.toString() == myList[showTest].testTrueAnswer
            }
            else{
                userAnswerList[showTest+1]=null
            }
        } catch (e:Exception){
            userAnswerList[showTest+1]=null
        }

        answerBoolean = userAnswerList[showTest+1]
        Log.e("asnwers",userAnswerList.toString())
        Log.e("asnwers1",answerBoolean.toString())
        showTest++
        countDownTimer?.cancel()

        result = ExamResult(
            answer = resultAnswer,
            answerBoolean = answerBoolean,
            student = student,
            exam = exam,
            testNumer = testNumber
        )
        postResultTest(result)

        if (showTest < myList.size) {
            clearCheck()
            showTestFun()
        }else{
            testExitedFun(userAnswerList)
        }
    }

    fun testExitedFun(userAnswerList: HashMap<Int, Boolean?> /* = java.util.HashMap<kotlin.Int, kotlin.Boolean?> */){
        exitTest(userAnswerList)
    }

    //user test finished
    fun exitTest(userAnswerList:HashMap<Int,Boolean?>) {

        if (userAnswerList.isEmpty()){
            userAnswerList[1]=null
        }
        countDownTimer?.cancel()
        this.context?.let {
            AlertDialog.Builder(it)
                .setTitle("Imtahan bitdi")
                .setMessage("Nəticələri görməyə hazırsınız?")
                .setCancelable(false)
                .setPositiveButton("he"
                ) { dialog, which ->
                    val bundle = bundleOf(
                        "testResult" to userAnswerList
                    )
                    findNavController().navigate(
                        R.id.action_examTestFragment_to_resultFragment,
                        bundle
                    )
                }.show()
        }
    }


    fun clearCheck(){
        var id: Int = binding.radioGroup2.checkedRadioButtonId
        if (id!=-1){
            val radio:RadioButton = requireView().findViewById(id)
            radio.background = this?.let { it.context?.let { it1 -> ContextCompat.getDrawable(it1, R.color.purple_700) } }
            radio.isChecked = false
            lastRadioButtonItem = null
        }
    }


}