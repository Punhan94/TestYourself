package com.example.testyourself.presentation.ui.fragments

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.testyourself.R
import com.example.testyourself.data.repository.ApiRepository
import com.example.testyourself.databinding.FragmentStudentHomeBinding
import com.example.testyourself.presentation.viewmodels.StudentHomeViewModel
import com.example.testyourself.presentation.viewmodels.StudentHomeViewModelProviderFactory
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_student_home.*
import kotlinx.android.synthetic.main.header_menu.view.*


class StudentHomeFragment : Fragment() {
    private var _binding: FragmentStudentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: StudentHomeViewModel
    lateinit var authFirebase : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authFirebase = FirebaseAuth.getInstance()
        val viewModelProviderFactory= StudentHomeViewModelProviderFactory(ApiRepository())
        viewModel = ViewModelProviders.of(this, viewModelProviderFactory ).get(StudentHomeViewModel::class.java)

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
        observeLiveData()

        navigationView.setNavigationItemSelectedListener {item->
            navigationSelect(item)
            true
        }
        navigation_view_toggle.setOnClickListener {
            studentDrawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    private fun observeLiveData(){

        viewModel.getAboutUser(1)
        viewModel.userProfile.observe(viewLifecycleOwner, Observer { a->
            a?.let {
                binding.studentDrawerLayout.studentFullName.text = it.firstName + " " + it.lastName
                Glide.with(requireContext())
                    .load(it.image)
                    .into(binding.studentDrawerLayout.studentImage)
                //Picasso.get().load(it.first().image).into(binding.studentDrawerLayout.studentImage)
            }
        })

    }

    private fun navigationSelect(item: MenuItem){
        when(item.itemId){
            R.id.userHomeFragment->{
                Navigation.findNavController(fragmentContainerView2).navigate(R.id.userHomeFragment)
            }
            R.id.user_profile->{
                Navigation.findNavController(fragmentContainerView2).navigate(R.id.userProfileFragment)
            }
            R.id.user_exit->{
                AlertDialog.Builder(requireContext())
                    .setTitle("Çıxış")
                    .setMessage("Çıxmaq istədiyinizdən əminsiniz?")
                    .setCancelable(true)
                    .setNegativeButton("yox", object : DialogInterface.OnClickListener{
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

}