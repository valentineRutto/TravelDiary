package com.valentinerutto.traveldiary.ui.registration

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.valentinerutto.traveldiary.R
import com.valentinerutto.traveldiary.ui.TravelEntry
import com.valentinerutto.traveldiary.databinding.FragmentSignInBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEventClickListeners()
    }

    private fun setEventClickListeners() {

        binding.login.setOnClickListener {
       loginUser()
        }
    }

    private fun loginUser() {

        val email = binding.email.text.toString().trim()
        val password = binding.password.text.toString().trim()

        if (TextUtils.isEmpty(email)) {
            binding.email.error = getString(R.string.email_cannot_be_empty)
            binding.email.requestFocus()
        } else if (TextUtils.isEmpty(password)) {
            binding.password.error = getString(R.string.password_cannot_be_empty)
            binding.password.requestFocus()
        } else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            requireActivity(),
                            getString(R.string.user_logged_in_successfully),
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(requireActivity(), TravelEntry::class.java))
                        activity?.finish()

                    } else {
                        Toast.makeText(
                            requireActivity(),
                            getString(R.string.log_in_error) + task.exception?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }).addOnFailureListener{
                    it.printStackTrace()
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}