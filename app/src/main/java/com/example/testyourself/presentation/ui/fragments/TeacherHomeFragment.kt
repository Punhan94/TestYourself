package com.example.testyourself.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.withStateAtLeast
import androidx.navigation.fragment.findNavController
import com.example.testyourself.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_student_home.*
import kotlinx.android.synthetic.main.fragment_teacher_home.*

class TeacherHomeFragment : Fragment() {
    lateinit var authFirebase : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authFirebase = FirebaseAuth.getInstance()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teacher_signOut.setOnClickListener {
            val teacherName = authFirebase.currentUser?.email.toString()
            //teacher_name.text = teacherName
            authFirebase.signOut()
            findNavController().navigate(R.id.loginOrSignUpFragment)
        }
    }


}