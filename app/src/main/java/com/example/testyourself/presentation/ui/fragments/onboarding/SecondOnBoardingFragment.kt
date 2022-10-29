package com.example.testyourself.presentation.ui.fragments.onboarding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.testyourself.R
import kotlinx.android.synthetic.main.fragment_second_onboarding.*


class SecondOnBoardingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second_onboarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        finish_OnBoarding_btn.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingFragment2_to_loginOrSignUpFragment)
        }

    }


}