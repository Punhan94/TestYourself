package com.example.testyourself.domain

import android.graphics.Color
import android.view.View
import android.widget.Toast
import com.example.testyourself.data.repository.FirebaseInstanceRepository
import kotlinx.android.synthetic.main.fragment_login_or_sign_in.view.*

class UserRegisterLogic(val view: View) {

    lateinit var firebaseRepository:FirebaseInstanceRepository


    fun checkRegisterForm(firstPassword:String, secondPassword:String,email:String){
        firebaseRepository = FirebaseInstanceRepository(view)

        if( firstPassword.equals(secondPassword) and email.isNotEmpty() ){
            firebaseRepository.createUserFirebase(email,firstPassword)
        }
        else if(email.isEmpty()){
            Toast.makeText(view.context, "Emaili bos buraxmaq olmaz", Toast.LENGTH_SHORT).show()
        }
        else if(firstPassword.isEmpty()){
            Toast.makeText(view.context, "Şifrəni bos buraxmaq olmaz", Toast.LENGTH_SHORT).show()
        }
        else if(secondPassword.isEmpty()){
            Toast.makeText(view.context, "Şifrə tekrari bos buraxmaq olmaz", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(view.context, "Formu duzgun doldurun", Toast.LENGTH_SHORT).show()
        }
    }

    // Registraion Form Show function
    fun registerFormShow(){
        view.register.setTextColor(Color.BLUE)
        view.login.setTextColor(Color.BLACK)
        view.password_repeat.visibility = View.VISIBLE
        view.register_button.visibility = View.VISIBLE
        view.signUp_button.visibility = View.GONE
        view.forgot_password.visibility = View.GONE
        view.login_or_signUp_radioButton.visibility = View.VISIBLE
    }

}