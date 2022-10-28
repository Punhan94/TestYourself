package com.example.testyourself.presentation.ui.fragments

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.testyourself.R
import com.example.testyourself.domain.models.Student
import com.example.testyourself.domain.models.UserProfile
import com.example.testyourself.databinding.FragmentStudentHomeBinding
import com.example.testyourself.presentation.viewmodels.StudentHomeViewModel
import com.example.testyourself.utils.LoadingDialog
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_student_home.*
import kotlinx.android.synthetic.main.header_menu.view.*

@AndroidEntryPoint
class StudentHomeFragment : Fragment() {
    private var _binding: FragmentStudentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StudentHomeViewModel by viewModels()
    lateinit var authFirebase: FirebaseAuth
    private var myStudent = Student()
    private var myUserProfile = UserProfile()
    private val loading = LoadingDialog(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        loading.startLoading()
        super.onCreate(savedInstanceState)
        authFirebase = FirebaseAuth.getInstance()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAllStudent()
        loading.isDisMiss()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object: OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    exituser()
                }
            } )

        binding.navigationView.setNavigationItemSelectedListener { item ->
            navigationSelect(item)
            true
        }
        binding.navigationViewToggle.setOnClickListener {
            binding.studentDrawerLayout.openDrawer(GravityCompat.START)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getAllStudent() {
        viewModel.allStudent.observe(viewLifecycleOwner, Observer { students ->
            students?.let {
                for (i in it) {
                    if (i.studentName == authFirebase.currentUser?.email.toString()) {
                        myStudent = i
                    }
                }
                if (myStudent.studentName == null) {
                    newStudent()
                }
                myStudent.id?.let { it1 -> it1.toInt()?.let { it2 -> findAllUserProfile(it2) } }
            }
        })
    }

    private fun newStudent() {
        viewModel.userStudent(
            Student(
                studentName = authFirebase.currentUser?.email.toString()
            )
        )
    }

    private fun findAllUserProfile(studId: Int){
        viewModel.usersProfile.observe(viewLifecycleOwner, Observer { users->
            var bool = false
            for (user in users){
                if (user.student == studId){
                    bool = true
                }
            }
            if (bool){
                myFun(studId)
            }else{
                newUser(studId)
            }

        })
    }


    @SuppressLint("SetTextI18n")
    private fun myFun(studId: Int) {
        viewModel.getAboutUser(studId)
        viewModel.userProfile.observe(viewLifecycleOwner, Observer { a ->
            a?.let {
                myUserProfile = a
                if (!it.lastName.isNullOrEmpty() and !it.firstName.isNullOrEmpty()){
                    binding.studentDrawerLayout.studentFullName.text =
                        it.firstName + " " + it.lastName
                }
                it.image?.let { image ->
                    Glide.with(requireContext())
                        .load(image)
                        .into(binding.studentDrawerLayout.studentImage)
                }
                //Picasso.get().load(it.first().image).into(binding.studentDrawerLayout.studentImage)
            }
        })
    }


    private fun newUser(studId: Int){
        viewModel.newUserProfile(studId)
    }

    private fun navigationSelect(item: MenuItem) {
        when (item.itemId) {
            R.id.userHomeFragment -> {
                Navigation.findNavController(fragmentContainerView2).navigate(R.id.userHomeFragment)
            }
            R.id.user_profile -> {
                Navigation.findNavController(fragmentContainerView2)
                    .navigate(R.id.userProfileFragment)
            }
            R.id.user_exit -> {
                exituser()
            }

        }
    }

    fun exituser(){
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


