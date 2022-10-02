package com.example.testyourself.presentation.ui.fragments.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testyourself.R
import com.example.testyourself.presentation.adapters.MainViewPagerAdapter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_on_boarding.view.*

class OnBoardingFragment : Fragment() {
    lateinit var authFirebase : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authFirebase = FirebaseAuth.getInstance()
        var user = authFirebase.currentUser

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_on_boarding, container, false)
        val fragmentList = listOf( FirstOnBoardingFragment(), SecondOnBoardingFragment() )
        val adapter = MainViewPagerAdapter(parentFragmentManager,lifecycle,fragmentList)
        view.mainViewPager.adapter = adapter
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }




}