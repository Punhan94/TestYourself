package com.example.testyourself.data.repository

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.testyourself.R
import com.example.testyourself.domain.SignInLogic
import com.example.testyourself.presentation.app.MainActivity
import com.example.testyourself.presentation.ui.fragments.login.LoginOrSignUpFragment
import com.example.testyourself.utils.Constant
import com.example.testyourself.utils.LoadingDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login_or_sign_in.view.*

class FirebaseInstanceRepository(var view: View, var fragment: Fragment):FirebaseInstanceRepositoryInterface {
    lateinit var authFirebase : FirebaseAuth
    private var firebaseFirestore = FirebaseFirestore.getInstance()
    private val loading = LoadingDialog(fragment)


    override fun createUserFirebase(email: String, password: String, jobId: Int) {
        loading.startLoading()
        //loginOrSignUpLogic = SignInLogic(view)
        authFirebase = FirebaseAuth.getInstance()
        //radioBtnID = view.login_or_signUp_radioButton.checkedRadioButtonId

        authFirebase.createUserWithEmailAndPassword(email,password)
            .addOnFailureListener {
                Toast.makeText(fragment.context, "Xeta bas verdi", Toast.LENGTH_SHORT).show()
                view.register_button.isClickable = true
                loading.isDisMiss()
            }
            .addOnSuccessListener {
                when(jobId){
                    R.id.login_or_signUp_first_radio_btn->{
                        createUserJob(email,"teacher")
                    }
                    R.id.login_or_signUp_second_radio_btn->{
                        createUserJob(email,"student")
                    }
                }

            }
    }

    override fun loginUserFirebase(email: String, password: String) {
        loading.startLoading()
        //loginOrSignUpLogic = SignInLogic(view)
        authFirebase = FirebaseAuth.getInstance()

        authFirebase.signInWithEmailAndPassword(email,password)
            .addOnFailureListener {
                Toast.makeText(fragment.context, "Xeta bas verdi", Toast.LENGTH_SHORT).show()
                view.signIn_button.isClickable = true
                loading.isDisMiss()
            }
            .addOnSuccessListener {
                firebaseFirestoreJobCheck(email)
            }
    }

    fun firebaseFirestoreJobCheck(email: String){

        firebaseFirestore.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val job = document.data.get(email).toString()
                    signUpLogicNavigate(job)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("exception",exception.toString())
            }
    }

    fun createUserJob(email:String,job: String){
        firebaseFirestore = FirebaseFirestore.getInstance()

        val myData = mutableMapOf<String,String>()
        myData[email]=job
        firebaseFirestore.collection("users").add(myData).addOnSuccessListener {
            signUpLogicNavigate(job)
        }
            .addOnFailureListener {
                loading.isDisMiss()
            }
    }

    fun signUpLogicNavigate(job:String){
        if (job=="teacher"){
            loading.isDisMiss()
            view.findNavController()
                .navigate(R.id.action_loginOrSignUpFragment_to_teacherHomeFragment)
        }
        else if(job=="student"){
            loading.isDisMiss()
            view.findNavController()
                .navigate(R.id.action_loginOrSignUpFragment_to_studentHomeFragment)
        }
    }

}