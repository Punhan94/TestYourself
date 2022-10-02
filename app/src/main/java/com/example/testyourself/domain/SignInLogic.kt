package com.example.testyourself.domain

import android.graphics.Color
import android.view.View
import android.widget.Toast
import com.example.testyourself.data.repository.FirebaseInstanceRepository
import kotlinx.android.synthetic.main.fragment_login_or_sign_in.view.*

class SignInLogic(val view:View) {
    lateinit var firebaseRepository: FirebaseInstanceRepository

    //Sign up Form Logic
    fun checkSignInForm(email: String, firstPassword: String) {
        firebaseRepository = FirebaseInstanceRepository(view)

        if (firstPassword.isNotEmpty() and email.isNotEmpty()) {
            firebaseRepository.loginUserFirebase(email, firstPassword)
        } else if (email.isEmpty()) {
            view.login_name.error = "bos buraxila bilmez"
            view.login_name.isFocused
        } else if (firstPassword.isEmpty()) {
            view.password.error = "bos buraxila bilmez"
            view.login_name.isFocused
        } else {
            Toast.makeText(view.context, "Xeta bas verdi", Toast.LENGTH_SHORT).show()
        }
    }


    //Sign up Form Show Function
    fun signInFormShow() {
        view.login.setTextColor(Color.BLUE)
        view.register.setTextColor(Color.BLACK)
        view.password_repeat.visibility = View.GONE
        view.register_button.visibility = View.GONE
        view.signUp_button.visibility = View.VISIBLE
        view.forgot_password.visibility = View.VISIBLE
        view.signUp_button.isEnabled = true
        view.login_or_signUp_radioButton.visibility = View.GONE
    }
}
























//    //Create New User Function
//    fun createUserFirebase(nav:NavController){
//        radioBtnID = view.login_or_signUp_radioButton.checkedRadioButtonId
//        loginName = view.login_name.text.toString()
//        loginPassword = view.password.text.toString()
//
//        authFirebase.createUserWithEmailAndPassword(
//            loginName,loginPassword
//        )
//                .addOnFailureListener {
//                    Toast.makeText(view.context, "Düzgün email adresi daxil edin", Toast.LENGTH_SHORT).show()
//                }
//                .addOnSuccessListener {
//                    val myData = mutableMapOf<String,String>()
//                    when(radioBtnID){
//                        R.id.login_or_signUp_first_radio_btn->{
//                            myData[loginName]="teacher"
//                            firebaseFirestore.collection("users").add(myData).addOnSuccessListener {
//                                nav.navigate(R.id.action_loginOrSignUpFragment_to_teacherHomeFragment )
//                            }
//                                .addOnFailureListener {
//
//                                }
//
//                        }
//                        R.id.login_or_signUp_second_radio_btn->
//                        {
//                            myData[loginName]="student"
//                            firebaseFirestore.collection("users").add(myData).addOnSuccessListener {
//                                nav.navigate(R.id.action_loginOrSignUpFragment_to_studentHomeFragment )
//                            }
//                                .addOnFailureListener {
//
//                                }
//                        }
//
//
//                    }
//
//
//                }
//    }



//    //Login User Function
//    fun loginUserFirebase(nav:NavController){
//        radioBtnID = view.login_or_signUp_radioButton.checkedRadioButtonId
//        loginName = view.login_name.text.toString()
//        loginPassword = view.password.text.toString()
//
//        authFirebase.signInWithEmailAndPassword(
//            loginName,loginPassword
//        )
//            .addOnFailureListener {
//
//        }
//            .addOnSuccessListener {
//
//
//            }
//    }
