package com.valentinerutto.traveldiary.ui.registration

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.valentinerutto.traveldiary.R
import com.valentinerutto.traveldiary.databinding.FragmentRegisterBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEventListeners()
    }

    private fun setEventListeners() {
        binding.register.setOnClickListener {
            createUser()
        }
        binding.login.setOnClickListener {
            findNavController().navigate(R.id.action_RegisterFragment_to_LogInFragment)
        }
    }

    private fun createUser() {
        val email = binding.email.text.toString().trim()
        val password = binding.password.text.toString().trim()

        if (TextUtils.isEmpty(email)) {
            binding.email.error = getString(R.string.email_cannot_be_empty)
            binding.email.requestFocus()
        } else if (TextUtils.isEmpty(password)) {
            binding.password.error = getString(R.string.password_cannot_be_empty)
            binding.password.requestFocus()
        } else {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            requireActivity(),
                            getString(R.string.user_registered_successfully),
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigate(R.id.action_RegisterFragment_to_LogInFragment)
                    } else {
                        Toast.makeText(
                            requireActivity(),
                            getString(R.string.registration_error) + task.exception?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }).addOnFailureListener {
                    it.printStackTrace()
                }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            navigateToHome()
        }
    }

    private fun navigateToHome() {
//        startActivity(Intent(requireActivity(), TravelEntry::class.java))
//        activity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}