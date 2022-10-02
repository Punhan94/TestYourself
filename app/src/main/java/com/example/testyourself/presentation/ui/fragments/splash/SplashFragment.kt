package com.example.testyourself.presentation.ui.fragments.splash

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.testyourself.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class SplashFragment : Fragment() {
    private lateinit var authFirebase : FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore
    var jobb = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authFirebase = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_slapsh, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed({
            if (authFirebase.currentUser != null){
                authUser()
            }
            else{
                findNavController().navigate(R.id.action_splashFragment_to_onBoardingFragment2)
            }
        },2000)

    }

    fun authUser(){
        authFirebase.currentUser?.let {
            firebaseFirestore.collection("users")
                .get()
                .addOnSuccessListener { result ->

                    for (document in result) {
                        if (document.data.get(it.email.toString()) != null){
                            jobb = document.data.get(it.email.toString()).toString()
                            Log.e("job",jobb)
                            signUpLogicNavigate(jobb)
                            break
                        }
                    }

                }
                .addOnFailureListener { exception ->
                    Log.e("exception",exception.toString())
                }
        }
    }

    fun signUpLogicNavigate(job:String){
        if (job=="teacher"){
            findNavController().navigate(R.id.teacherHomeFragment)
        }
        else if(job=="student"){
            findNavController().navigate(R.id.studentHomeFragment)
            Log.e("navcontroller","isledi")
        }
    }

}