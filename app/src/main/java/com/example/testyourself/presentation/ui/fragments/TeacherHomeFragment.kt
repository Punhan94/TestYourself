package com.example.testyourself.presentation.ui.fragments

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.lifecycle.withStateAtLeast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.testyourself.R
import com.example.testyourself.databinding.FragmentTeacherHomeBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_student_home.*
import kotlinx.android.synthetic.main.fragment_teacher_home.*

class TeacherHomeFragment : Fragment() {
    lateinit var authFirebase : FirebaseAuth
    private var _binding:FragmentTeacherHomeBinding?=null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authFirebase = FirebaseAuth.getInstance()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTeacherHomeBinding.inflate(inflater,container,false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navigationView.setNavigationItemSelectedListener { item ->

            navigationSelect(item)
            true
        }

        binding.navigationViewToggle.setOnClickListener {
            binding.teacherDrawerLayout.openDrawer(GravityCompat.START)
        }

    }

    private fun navigationSelect(item: MenuItem) {
        when (item.itemId) {
            R.id.homeLayoutTeacherFragment -> {
                findNavController().navigate(R.id.homeLayoutTeacherFragment)
            }
            R.id.user_profile -> {
                Navigation.findNavController(fragmentContainerView2)
                    .navigate(R.id.userProfileFragment)
            }
            R.id.teacher_signOut -> {
                AlertDialog.Builder(requireContext())
                    .setTitle("Çıxış")
                    .setMessage("Çıxmaq istədiyinizdən əminsiniz?")
                    .setCancelable(true)
                    .setNegativeButton("yox", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                        }
                    })
                    .setPositiveButton("he", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            authFirebase.signOut()
                            findNavController().navigate(R.id.loginOrSignUpFragment)
                        }
                    }).show()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}