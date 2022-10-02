package com.example.testyourself.presentation.ui.fragments.user_fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.testyourself.R
import com.example.testyourself.data.models.ExamResult
import com.example.testyourself.data.models.UserProfile
import com.example.testyourself.data.repository.ApiRepository
import com.example.testyourself.databinding.FragmentUserProfileBinding
import com.example.testyourself.presentation.viewmodels.UserProfileViewModel
import com.example.testyourself.presentation.viewmodels.UserProfileViewModelProviderFactory
import com.example.testyourself.utils.Constant
import com.google.firebase.auth.FirebaseAuth

class UserProfileFragment : Fragment() {
    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: UserProfileViewModel
    lateinit var authFirebase : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authFirebase = FirebaseAuth.getInstance()
        val viewModelProviderFactory= UserProfileViewModelProviderFactory(ApiRepository())
        viewModel = ViewModelProviders.of(this, viewModelProviderFactory ).get(UserProfileViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        binding.manageUser.setOnClickListener {
            userDetailShow(true)
        }
        binding.userProfileDetailBtnCancel.setOnClickListener {
            userDetailShow(false)
        }
        binding.userProfileDetailBtn.setOnClickListener {
            userProfilePost()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun userDetailShow(myBoolean: Boolean){
        if (myBoolean) {
            binding.userProfileLayout.visibility =  View.GONE
            binding.userProfileCreateOrUpdate.visibility = View.VISIBLE
            binding.manageUser.visibility = View.GONE
        }else{
            binding.userProfileLayout.visibility =  View.VISIBLE
            binding.userProfileCreateOrUpdate.visibility = View.GONE
            binding.manageUser.visibility = View.VISIBLE
        }
    }

    fun userProfilePost(){
        var userLast = UserProfile()
        var userNew = UserProfile()
        viewModel.getAboutUser(authFirebase.currentUser?.email.toString(),1)

        viewModel.userProfile.observe(viewLifecycleOwner, Observer {
            userLast = UserProfile(
                id = it.id,
                student = it.student,
                firstName = it.firstName,
                lastName = it.lastName,
                image =  it.image.toString()
            )
        })

        userNew = UserProfile(
                id = 1,
                image =  userLast.image.toString(),
                student = 1,
                firstName = binding.userProfileFirstName.text.toString(),
                lastName = binding.userProfileLastName.text.toString(),
                age = binding.userEditTextAge.text.toString().toInt()
            )

        Log.e("postUser", userNew.toString())
        viewModel.userProfilePost(userNew,userLast.id!!.toInt())

    }

    @SuppressLint("SetTextI18n")
    private fun observeLiveData(){
        viewModel.getAboutUser(authFirebase.currentUser?.email.toString(),1)
        viewModel.userProfile.observe(viewLifecycleOwner, Observer { a->
            a?.let { userProfile->
                binding.userProfileFullName.text = userProfile.firstName +" "+ userProfile.lastName
                binding.userProfileAge.text = userProfile.age.toString()
                binding.userProfileFirstName.setText(userProfile.firstName.toString())
                binding.userProfileLastName.setText(userProfile.lastName.toString())
                binding.userEditTextAge.setText(userProfile.age.toString())
                Glide.with(requireContext())
                    .load(userProfile.image)
                    .into(binding.profileUserImage)
               // Picasso.get().load(it.first().image).into(binding.profileUserImage)

            }
        })

        viewModel.userAllAnswer.observe(viewLifecycleOwner, Observer { a->
            a?.let {
                binding.textViewAllTrue.text = Constant.TRUE_ANSWER_COUNT + filteredAnswerList(it,true).toString()
                binding.textViewAllFalse.text = Constant.FALSE_ANSWER_COUNT + filteredAnswerList(it,false).toString()
                binding.textViewAllNull.text = Constant.NULL_ANSWER_COUNT + filteredAnswerList(it,null).toString()
            }
        })
    }

    private fun filteredAnswerList(list: List<ExamResult>,boolean:Boolean?):Int{
        var a = 0
        for(element in list){
            if (element.answerBoolean==boolean){
                a++
            }
        }
        return a
    }

}