package com.example.testyourself.presentation.ui.fragments.login
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.testyourself.R
import com.example.testyourself.databinding.FragmentForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth


class ForgotPasswordFragment : Fragment() {
    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!
    private lateinit var authFirebase : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authFirebase = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container,
            false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.resetButton.setOnClickListener {
            val userEmail = binding.resetEmail.text.toString()

            authFirebase.sendPasswordResetEmail(userEmail).addOnCompleteListener { task->
                if (task.isSuccessful){
                    Toast.makeText(requireContext(),
                        R.string.send_new_password,
                        Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }else{
                    Toast.makeText(requireContext(),
                        task.exception?.localizedMessage,
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}