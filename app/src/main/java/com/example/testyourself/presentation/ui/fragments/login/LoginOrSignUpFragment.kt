package com.example.testyourself.presentation.ui.fragments.login
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.testyourself.R
import com.example.testyourself.data.repository.FirebaseInstanceRepository
import com.example.testyourself.databinding.FragmentLoginOrSignInBinding
import com.example.testyourself.domain.SignInLogic
import com.example.testyourself.domain.UserRegisterLogic
import com.example.testyourself.presentation.viewmodels.UserRegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase



class LoginOrSignUpFragment : Fragment() {
    private var _binding: FragmentLoginOrSignInBinding? = null
    private val binding get() = _binding!!

    //private lateinit var userRegisterViewModel: UserRegisterViewModel
    private lateinit var authFirebase : FirebaseAuth
    private lateinit var firebaseDatabase : FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var loginOrSignUpLogic :SignInLogic
    private lateinit var userRegisterLogic : UserRegisterLogic
    lateinit var viewModel: UserRegisterViewModel
    lateinit var firebaseRepository: FirebaseInstanceRepository
    private var loginName : String = ""
    private var passFirst:String = ""
    private var passSecond:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference()
        firebaseFirestore = FirebaseFirestore.getInstance()


        super.onCreate(savedInstanceState)
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
        firebaseRepository = FirebaseInstanceRepository(view)

        super.onViewCreated(view, savedInstanceState)
        loginOrSignUpLogic = SignInLogic(view)
        userRegisterLogic = UserRegisterLogic(view)

        //Register form function start
        binding.register.setOnClickListener {
            userRegisterLogic.registerFormShow()
        }

        //SignIn form function start
        binding.login.setOnClickListener {
            loginOrSignUpLogic.signInFormShow()
        }

        fun getForm(): HashMap<String, String> {
            loginName = binding.loginName.text.toString().trim()
            passFirst = binding.password.text.toString().trim()
            passSecond = binding.passwordRepeat.text.toString().trim()

            return hashMapOf<String,String>(
                "loginName" to loginName,
                "passFirst" to passFirst,
                "passSecond" to passSecond
            )
        }


        //Registration function started
        binding.registerButton.setOnClickListener {
            val form = getForm()
            userRegisterLogic.checkRegisterForm(
                form.get("passFirst") as String, form.get("passSecond") as String,form.get("loginName") as String
            )
        }

        //Login function started
        binding.signUpButton.setOnClickListener {
            val form = getForm()
            loginOrSignUpLogic.checkSignInForm(
                form.get("loginName") as String, form.get("passFirst") as String
            )
        }

        //RadioButton enabled function
        binding.loginOrSignUpRadioButton.setOnCheckedChangeListener { group, checkedId ->
            binding.registerButton.isEnabled = true
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

//    fun signUpLogicNavigate(job:String){
//        if (job=="teacher"){
//            findNavController().navigate(R.id.action_loginOrSignUpFragment_to_teacherHomeFragment)
//        }
//        else if(job=="student"){
//            findNavController().navigate(R.id.action_loginOrSignUpFragment_to_studentHomeFragment)
//        }
//    }
//
//    fun checkRegisterForm(firstPassword:String, secondPassword:String,email:String){
//
//        if( firstPassword.equals(secondPassword) and email.isNotEmpty() ){
//            firebaseRepository.createUserFirebase(email,firstPassword)
//        }
//        else if(email.isEmpty()){
//            Toast.makeText(context, "Emaili bos buraxmaq olmaz", Toast.LENGTH_SHORT).show()
//        }
//        else if(firstPassword.isEmpty()){
//            Toast.makeText(context, "Şifrəni bos buraxmaq olmaz", Toast.LENGTH_SHORT).show()
//        }
//        else if(secondPassword.isEmpty()){
//            Toast.makeText(context, "Şifrə tekrari bos buraxmaq olmaz", Toast.LENGTH_SHORT).show()
//        }
//        else{
//            Toast.makeText(context, "Formu duzgun doldurun", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    // Registraion Form Show function
//    fun registerFormShow(){
//        binding.register.setTextColor(Color.BLUE)
//        binding.login.setTextColor(Color.BLACK)
//        binding.passwordRepeat.visibility = View.VISIBLE
//        binding.registerButton.visibility = View.VISIBLE
//        binding.signUpButton.visibility = View.GONE
//        binding.forgotPassword.visibility = View.GONE
//        binding.loginOrSignUpRadioButton.visibility = View.VISIBLE
//    }
//
//    fun checkSignInForm(email: String, firstPassword: String) {
//
//        if (firstPassword.isNotEmpty() and email.isNotEmpty()) {
//            firebaseRepository.loginUserFirebase(email, firstPassword)
//        } else if (email.isEmpty()) {
//            Toast.makeText(context, "Emaili bos buraxmaq olmaz", Toast.LENGTH_SHORT).show()
//        } else if (firstPassword.isEmpty()) {
//            Toast.makeText(context, "Şifrəni bos buraxmaq olmaz", Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(context, "Xeta bas verdi", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//
//    //Sign up Form Show Function
//    fun signInFormShow() {
//        binding.login.setTextColor(Color.BLUE)
//        binding.register.setTextColor(Color.BLACK)
//        binding.passwordRepeat.visibility = View.GONE
//        binding.registerButton.visibility = View.GONE
//        binding.signUpButton.visibility = View.VISIBLE
//        binding.forgotPassword.visibility = View.VISIBLE
//        binding.signUpButton.isEnabled = true
//        binding.loginOrSignUpRadioButton.visibility = View.GONE
//    }


}