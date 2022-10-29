package com.example.testyourself.presentation.ui.fragments.user_fragments
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.testyourself.domain.models.ExamResult
import com.example.testyourself.domain.models.UserProfile
import com.example.testyourself.databinding.FragmentUserProfileBinding
import com.example.testyourself.presentation.viewmodels.UserProfileViewModel
import com.example.testyourself.utils.Constant
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileFragment : Fragment() {
    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserProfileViewModel by viewModels()
    lateinit var authFirebase : FirebaseAuth
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    var imageUri : Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authFirebase = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        registerActivityForResult()

        binding.manageUser.setOnClickListener {
            userDetailShow(true)
        }
        binding.userProfileDetailBtnCancel.setOnClickListener {
            userDetailShow(false)
        }
        binding.userProfileDetailBtn.setOnClickListener {
            userProfilePost()
            observeLiveData()
            userDetailShow(false)
        }
        binding.profileUserImage.setOnClickListener {
            chooseImage()
        }
        binding.swipeRefreshLayoutUserProfile.setOnRefreshListener {
            observeLiveData()
            binding.swipeRefreshLayoutUserProfile.isRefreshing = false
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun chooseImage(){
        if(ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)
        }else{
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            activityResultLauncher.launch(intent)
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            activityResultLauncher.launch(intent)
        }
    }

    private fun registerActivityForResult(){
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val resultCode = result.resultCode
            val imageData = result.data

            if (resultCode == RESULT_OK && imageData != null) {
                imageUri = imageData.data

                Glide.with(requireContext())
                    .load(imageUri)
                    .into(binding.profileUserImage)
            }
        }
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

    private fun userProfilePost(){
        var userLast = UserProfile()
        var userPATCH = UserProfile()
        viewModel.userProfile.observe(viewLifecycleOwner, Observer {
            userLast = UserProfile(
                id = it.id,
                student = it.student,
                firstName = it.firstName,
                lastName = it.lastName,
                image =  it.image.toString()
            )
        })

        userPATCH = UserProfile(
                id = userLast.id,
                student = Constant.STUDENT_ID,
                firstName = binding.userProfileFirstName.text.toString(),
                lastName = binding.userProfileLastName.text.toString(),
                age = binding.userEditTextAge.text.toString().toInt()
            )

        viewModel.userProfilePATCH(userPATCH,userLast.id!!.toInt())

    }

    @SuppressLint("SetTextI18n")
    private fun observeLiveData(){

        viewModel.getAboutUser(Constant.STUDENT_ID?:1)
        viewModel.userProfile.observe(viewLifecycleOwner) { a ->
            a?.let { userProfile ->
                if (!userProfile.lastName.isNullOrEmpty() and !userProfile.firstName.isNullOrEmpty()) {
                    binding.userProfileFullName.text =
                        userProfile.firstName + " " + userProfile.lastName
                }
                userProfile.age?.let {
                    binding.userProfileAge.text = userProfile.age.toString()
                    binding.userEditTextAge.setText(userProfile.age.toString())
                }
                userProfile.firstName?.let {
                    binding.userProfileFirstName.setText(userProfile.firstName.toString())
                }
                userProfile.lastName.let {
                    binding.userProfileLastName.setText(userProfile.lastName.toString())
                }
                userProfile.image?.let {
                    Glide.with(requireContext())
                        .load(userProfile.image)
                        .into(binding.profileUserImage)
                }

            }
        }

        viewModel.userAllAnswer.observe(viewLifecycleOwner) { a ->
            a?.let {
                binding.textViewAllTrue.text =
                    Constant.TRUE_ANSWER_COUNT + filteredAnswerList(it, true).toString()
                binding.textViewAllFalse.text =
                    Constant.FALSE_ANSWER_COUNT + filteredAnswerList(it, false).toString()
                binding.textViewAllNull.text =
                    Constant.NULL_ANSWER_COUNT + filteredAnswerList(it, null).toString()
            }
        }
    }

    private fun filteredAnswerList(list: List<ExamResult>, boolean:Boolean?):Int{
        var a = 0
        for(element in list){
            if (element.answerBoolean==boolean){
                a++
            }
        }
        return a
    }

}