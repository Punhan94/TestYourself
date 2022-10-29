package com.example.testyourself.presentation.ui.fragments.login
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.testyourself.R
import com.example.testyourself.data.repository.FirebaseInstanceRepository
import com.example.testyourself.databinding.FragmentLoginOrSignInBinding
import com.example.testyourself.presentation.viewmodels.ObservableData.createUserJobLiveData
import com.example.testyourself.presentation.viewmodels.ObservableData.createUserLiveData
import com.example.testyourself.presentation.viewmodels.ObservableData.loginUserJobCheckLiveData
import com.example.testyourself.presentation.viewmodels.ObservableData.loginUserLiveData
import com.example.testyourself.presentation.viewmodels.UserRegisterViewModel
import com.example.testyourself.utils.Constant
import com.example.testyourself.utils.LoadingDialog
import com.example.testyourself.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern


class LoginOrSignUpFragment : Fragment() {
    private var _binding: FragmentLoginOrSignInBinding? = null
    private val binding get() = _binding!!
    val viewModel: UserRegisterViewModel by viewModels()
    private var loginName : String = ""
    private var passFirst:String = ""
    private var passSecond:String = ""
    var jobId :Int = 0
    var loading= LoadingDialog(this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginOrSignInBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Register form function start
        binding.register.setOnClickListener {
            registerFormShow()
        }

        //SignIn form function start
        binding.login.setOnClickListener {
            signInFormShow()
        }

        //Registration function started
        binding.registerButton.setOnClickListener {
            loginName = binding.loginName.text.toString().trim()
            passFirst = binding.password.text.toString().trim()
            passSecond = binding.passwordRepeat.text.toString().trim()
            checkRegisterForm(
                loginName,passFirst, passSecond
            )
        }

        //Login function started
        binding.signInButton.setOnClickListener {
            loginName = binding.loginName.text.toString().trim()
            passFirst = binding.password.text.toString().trim()
            checkSignInForm(
                loginName, passFirst
            )
        }

        //RadioButton enabled function
        binding.loginOrSignUpRadioButton.setOnCheckedChangeListener { group, checkedId ->
            binding.registerButton.isEnabled = true
            if (checkedId == R.id.login_or_signUp_first_radio_btn) {
                jobId = 1
            }
        }

        // Forgot password page Navigation
        binding.forgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginOrSignUpFragment_to_forgotPasswordFragment)
        }
        observeFirebaseResult()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun checkRegisterForm(email:String,firstPassword:String, secondPassword:String){
        val emailBool = emailValidation(email)
        val passwordBool = passwordSize(firstPassword)

        if(passwordBool and firstPassword.equals(secondPassword) and emailBool){
            viewModel.createUser(email,firstPassword,jobId)
            binding.registerButton.isClickable = false
        }
        else if(!emailBool){
            Toast.makeText(context, R.string.email_validate, Toast.LENGTH_SHORT).show()
        }
        else if (!passwordBool) {
            Toast.makeText(context, R.string.password_validate, Toast.LENGTH_SHORT).show()
        }
        else if(firstPassword != secondPassword){
            Toast.makeText(context, R.string.password_repeat_problem, Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context, R.string.form_problem, Toast.LENGTH_SHORT).show()
        }
    }

    // Registraion Form Show function
    fun registerFormShow(){
        binding.register.setTextColor(Color.WHITE)
        binding.login.setTextColor(Color.BLACK)
        binding.registerLayoutShow.visibility = View.VISIBLE
        binding.signInLayoutShow.visibility = View.GONE
    }

    fun checkSignInForm(email: String, firstPassword: String) {
        val emailBool = emailValidation(email)
        val passwordBool = passwordSize(firstPassword)

        if (passwordBool and emailBool) {
            binding.signInButton.isClickable = false
            viewModel.loginUserFirebase(email, firstPassword)
        } else if(!emailBool){
            Toast.makeText(context,R.string.email_validate , Toast.LENGTH_SHORT).show()
        } else if (!passwordBool) {
            Toast.makeText(context, R.string.password_validate, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, R.string.problem, Toast.LENGTH_SHORT).show()
        }
    }


    fun emailValidation(email: String): Boolean {
        return Pattern.compile(Constant.EMAIL_PATTERN).matcher(email).matches()
    }

    fun passwordSize(password:String):Boolean{
        var size = 0
        for (i in password){
            size++
        }
        return size>6
    }

    //Sign up Form Show Function
    fun signInFormShow() {
        binding.login.setTextColor(Color.WHITE)
        binding.register.setTextColor(Color.BLACK)
        binding.registerLayoutShow.visibility = View.GONE
        binding.signInLayoutShow.visibility = View.VISIBLE
    }


   private fun observeFirebaseResult(){
       createUserLiveData.observe(viewLifecycleOwner){
           when(it){
               is Resource.Error->{
                   binding.registerButton.isClickable=true
                   Toast.makeText(requireContext(), R.string.problem, Toast.LENGTH_SHORT).show()
               }
               else->{

               }
           }
       }
       createUserJobLiveData?.observe(viewLifecycleOwner) {
           when (it) {
               is Resource.Loading -> {
                   loading.startLoading()
               }
               is Resource.Success -> {
                   if (loading.isDialog.isShowing)
                       loading.dismiss()
                   val job= it.data
                   job?.let { it1 -> signUpLogicNavigate(it1) }
               }
               else -> {
                   if (loading.isDialog.isShowing)
                   loading.dismiss()
                   binding.registerButton.isClickable=true
                   Toast.makeText(requireContext(), R.string.problem, Toast.LENGTH_SHORT).show()
               }
           }
       }

       loginUserLiveData.observe(viewLifecycleOwner){
           when(it){
               is Resource.Error->{
                   binding.registerButton.isClickable=true
                   Toast.makeText(requireContext(), R.string.problem, Toast.LENGTH_SHORT).show()
               }
               else->{

               }
           }
       }

       loginUserJobCheckLiveData?.observe(viewLifecycleOwner) {
           when (it) {
               is Resource.Loading -> {
                   loading.startLoading()
               }
               is Resource.Success -> {
                   if (loading.isDialog.isShowing)
                       loading.dismiss()
                   val job= it.data
                   job?.let { it1 -> signUpLogicNavigate(it1) }
               }
               else -> {
                   if (loading.isDialog.isShowing)
                       loading.dismiss()
                   binding.signInButton.isClickable=true
                   Toast.makeText(requireContext(), R.string.problem, Toast.LENGTH_SHORT).show()

               }
           }
       }
   }

    fun signUpLogicNavigate(job: String) {
        if (job == "teacher") {
       findNavController()
                .navigate(R.id.action_loginOrSignUpFragment_to_teacherHomeFragment)
        } else if (job == "student") {
            findNavController()
                .navigate(R.id.action_loginOrSignUpFragment_to_studentHomeFragment)
        }
    }


}