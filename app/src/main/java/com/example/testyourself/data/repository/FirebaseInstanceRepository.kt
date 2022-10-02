package com.example.testyourself.data.repository

import android.app.Activity
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.testyourself.R
import com.example.testyourself.domain.SignInLogic
import com.example.testyourself.presentation.app.MainActivity
import com.example.testyourself.presentation.ui.fragments.login.LoginOrSignUpFragment
import com.example.testyourself.utils.Constant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login_or_sign_in.view.*

class FirebaseInstanceRepository(val view:View):FirebaseInstanceRepositoryInterface {
    lateinit var authFirebase : FirebaseAuth
    private var firebaseFirestore = FirebaseFirestore.getInstance()

    override fun createUserFirebase(email: String, password: String) {
        //loginOrSignUpLogic = SignInLogic(view)
        authFirebase = FirebaseAuth.getInstance()
        //radioBtnID = view.login_or_signUp_radioButton.checkedRadioButtonId

        authFirebase.createUserWithEmailAndPassword(email,password)
            .addOnFailureListener {
                //Toast.makeText(view.context, "Düzgün email adresi daxil edin", Toast.LENGTH_SHORT).show()
            }
            .addOnSuccessListener {
                when(view.login_or_signUp_radioButton.checkedRadioButtonId){
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
        //loginOrSignUpLogic = SignInLogic(view)
        authFirebase = FirebaseAuth.getInstance()

        authFirebase.signInWithEmailAndPassword(email,password)
            .addOnFailureListener {
                Log.e("exception",it.toString())
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

            }
    }

    fun signUpLogicNavigate(job:String){
        if (job=="teacher"){
            view.findNavController()
                .navigate(R.id.action_loginOrSignUpFragment_to_teacherHomeFragment)
        }
        else if(job=="student"){
            view.findNavController()
                .navigate(R.id.action_loginOrSignUpFragment_to_studentHomeFragment)
        }
    }

}