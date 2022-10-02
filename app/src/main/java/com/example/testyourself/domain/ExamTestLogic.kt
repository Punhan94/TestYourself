package com.example.testyourself.domain

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.graphics.Color
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.testyourself.R
import com.example.testyourself.data.models.ExamResult
import com.example.testyourself.data.models.Test
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_exam_test.view.*

class ExamTestLogic(val view: View) {
    lateinit var result : ExamResult
    var showTest = 0
    val userAnswerList = hashMapOf<Int,Boolean?>()
    var resultAnswer = ""
    var answerBoolean : Boolean? = false
    var student = 1
    var exam = 0
    var testNumber = 0
    var lastRadioButtonItem : RadioButton?=null
    var countDownTimer: CountDownTimer? = null

    //test show function
    fun showTestFun(showTest: Int, mylist: MutableList<Test>) {
        clearCheck()

        val test = mylist[showTest]
        val answers = arrayListOf(
            test.testTrueAnswer, test.testFalseAnswer1,
            test.testFalseAnswer2, test.testFalseAnswer3
        )
        answers.shuffle()


        test.testImage?.let {
            Picasso.get().load(it).into(view.exam_test_img)
        }
        view.exam_test_num.text = (showTest + 1).toString()
        view.exam_test_question.text = test.testQuestion
        view.radioGroup2_1.text = answers[0]
        view.radioGroup2_2.text = answers[1]
        view.radioGroup2_3.text = answers[2]
        view.radioGroup2_4.text = answers[3]
        timer(mylist, showTest)
        testNumber = showTest + 1

        view.radioGroup2.setOnCheckedChangeListener{ group, checkedId ->
            val radioButton: RadioButton = group.findViewById(checkedId) as RadioButton
            if (lastRadioButtonItem !=radioButton){
                radioButton.setBackgroundColor(Color.GREEN)
                lastRadioButtonItem?.let {
                    group.findViewById<RadioButton>(it.id).background = ContextCompat.getDrawable(view.context, R.color.purple_700)
                }
                lastRadioButtonItem=radioButton
            }

        }

    }

    //test timer
    fun timer(mylist: MutableList<Test>, thisShowTest: Int) {
        val test = mylist[showTest]
        val time: Long = test.oneTestSecond?.toLong()?.times(1000) ?: 30000
        testTime(mylist, thisShowTest, time)
    }

    //test time start
    private fun testTime(mylist: MutableList<Test>, thisShowTest: Int, thisTime: Long) {

       countDownTimer = object : CountDownTimer(thisTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                view.exam_test_time.text = (millisUntilFinished / 1000).toString()
            }
            override fun onFinish() {
                    testAnswer(mylist)
            }
        }.start()
    }

    //test answer function
    fun testAnswer(myList: MutableList<Test>):ExamResult {

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

        if (answerBoolean != null) {
            result = ExamResult(
                answer = resultAnswer,
                answerBoolean = answerBoolean,
                student = student,
                exam = exam,
                testNumer = testNumber
            )
        }else{
            result = ExamResult(
                answer = resultAnswer,
                student = student,
                answerBoolean = null,
                exam = exam,
                testNumer = testNumber
            )
        }

        if (showTest < myList.size) {
            clearCheck()
            showTestFun(showTest, myList)
        }else{
            testExitedFun(userAnswerList)
        }
        return result
    }

    fun testExitedFun(userAnswerList: HashMap<Int, Boolean?> /* = java.util.HashMap<kotlin.Int, kotlin.Boolean?> */){
        exitTest(userAnswerList)
    }

    //user test finished
    fun exitTest(userAnswerList:HashMap<Int,Boolean?>) {

        AlertDialog.Builder(view.context)
            .setTitle("Imtahan bitdi")
            .setMessage("Nəticələri görməyə hazırsınız?")
            .setCancelable(false)
            .setPositiveButton("he", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    val bundle = bundleOf(
                        "testResult" to userAnswerList
                    )
                    findNavController(view.findFragment()).navigate(R.id.action_examTestFragment_to_resultFragment,
                    bundle)
                }
            }).show()
    }


    fun clearCheck(){
        var id: Int = view.radioGroup2.checkedRadioButtonId
        if (id!=-1){
            val radio:RadioButton = view.findViewById(id)
            radio.background = ContextCompat.getDrawable(view.context, R.color.purple_700)
            radio.isChecked = false
            lastRadioButtonItem = null
        }
    }


}