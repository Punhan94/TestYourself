package com.example.testyourself.presentation.ui.fragments.login
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.testyourself.R
import com.example.testyourself.data.repository.FirebaseInstanceRepository
import com.example.testyourself.databinding.FragmentLoginOrSignInBinding
import com.example.testyourself.presentation.app.MainActivity
import com.example.testyourself.presentation.viewmodels.UserRegisterViewModel
import com.example.testyourself.utils.LoadingDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import javax.xml.transform.Result


class LoginOrSignUpFragment : Fragment() {
    private var _binding: FragmentLoginOrSignInBinding? = null
    private val binding get() = _binding!!
    //private lateinit var userRegisterViewModel: UserRegisterViewModel
    private lateinit var authFirebase : FirebaseAuth
    private lateinit var firebaseDatabase : FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseFirestore: FirebaseFirestore
//    private lateinit var loginOrSignUpLogic :SignInLogic
//    private lateinit var userRegisterLogic : UserRegisterLogic
    lateinit var viewModel: UserRegisterViewModel
    lateinit var firebaseRepository: FirebaseInstanceRepository
    private var loginName : String = ""
    private var passFirst:String = ""
    private var passSecond:String = ""
    var jobId :Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference()
        firebaseFirestore = FirebaseFirestore.getInstance()
        viewModel = ViewModelProviders.of(this).get(UserRegisterViewModel::class.java)
        authFirebase = FirebaseAuth.getInstance()

    }

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
        firebaseRepository = FirebaseInstanceRepository(view, this)

        super.onViewCreated(view, savedInstanceState)
//        loginOrSignUpLogic = SignInLogic(view)
//        userRegisterLogic = UserRegisterLogic(view)


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
            jobId = checkedId
        }

        // Forgot password page Navigation
        binding.forgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginOrSignUpFragment_to_forgotPasswordFragment)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun checkRegisterForm(email:String,firstPassword:String, secondPassword:String){
        if( firstPassword.equals(secondPassword) and email.isNotEmpty() ){
            firebaseRepository.createUserFirebase(email,firstPassword,jobId)
            binding.registerButton.isClickable = false
        }
        else if(email.isEmpty()){
            Toast.makeText(context, "Emaili bos buraxmaq olmaz", Toast.LENGTH_SHORT).show()
        }
        else if(firstPassword.isEmpty()){
            Toast.makeText(context, "Şifrəni bos buraxmaq olmaz", Toast.LENGTH_SHORT).show()
        }
        else if(secondPassword.isEmpty()){
            Toast.makeText(context, "Şifrə tekrari bos buraxmaq olmaz", Toast.LENGTH_SHORT).show()
        }
        else if(firstPassword.toInt() != secondPassword.toInt()){
            Toast.makeText(context, "Şifrə ilə təkrar şifrə uyğun deyil", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context, "Formu duzgun doldurun", Toast.LENGTH_SHORT).show()
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

        if (firstPassword.isNotEmpty() and email.isNotEmpty()) {
            binding.signInButton.isClickable = false
            firebaseRepository.loginUserFirebase(email, firstPassword)
        } else if (email.isEmpty()) {
            Toast.makeText(context, "Emaili bos buraxmaq olmaz", Toast.LENGTH_SHORT).show()
        } else if (firstPassword.isEmpty()) {
            Toast.makeText(context, "Şifrəni bos buraxmaq olmaz", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Xeta bas verdi", Toast.LENGTH_SHORT).show()
        }
    }


    //Sign up Form Show Function
    fun signInFormShow() {
        binding.login.setTextColor(Color.WHITE)
        binding.register.setTextColor(Color.BLACK)
        binding.registerLayoutShow.visibility = View.GONE
        binding.signInLayoutShow.visibility = View.VISIBLE
    }


}