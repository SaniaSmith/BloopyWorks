package id.bloopyworks.platform.ui.landing.signUp

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import id.bloopyworks.platform.R
import id.bloopyworks.platform.core.data.source.remote.network.ResponseModel
import id.bloopyworks.platform.core.data.source.remote.request.LoginAPIRequest
import id.bloopyworks.platform.core.data.source.remote.request.SignUpAPIRequest
import id.bloopyworks.platform.databinding.FragmentLoginBinding
import id.bloopyworks.platform.databinding.FragmentSignUpBinding
import id.bloopyworks.platform.ui.landing.login.LoginFragmentDirections
import id.bloopyworks.platform.ui.landing.login.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SignUpFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding

    private val viewModel by sharedViewModel<SignUpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnLogin?.setOnClickListener(this)

        binding?.btnRegis?.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view) {
            binding?.btnLogin -> {
                parentFragment?.requireView()?.let {
                    //navigate to show dialog sign up
                    Navigation.findNavController(it).navigate(R.id.loginFragment)
                }
            }
            binding?.btnRegis -> {
                    signUp()
                }
            }
        }

    private fun signUp() {
        val name = binding?.inputUser?.text.toString()
        val email = binding?.inputEmail?.text.toString()
        val password = binding?.inputPass?.text.toString()
        val confirmPass = binding?.confirmPass?.text.toString()

        val request = SignUpAPIRequest(name, email, password)

        if (password != confirmPass){
            Toast.makeText(requireContext(), "Pass dan Confirm Pass harus sama", Toast.LENGTH_SHORT).show()
        } else {
            //send request sign up user
            viewModel.viewModelScope.launch {
                viewModel.signUp(request)
            }

            //get response sign up user
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.data.collect() {
                        when (it) {
                            //when response is error
                            is ResponseModel.Error -> {
                                Toast.makeText(requireContext(), "Gagal: ${it.message}", Toast.LENGTH_SHORT).show()
                            }
                            //when response is Idle
                            is ResponseModel.Idle -> {
                                Toast.makeText(requireContext(), "Idle State", Toast.LENGTH_SHORT).show()
                            }
                            //when response is loading
                            is ResponseModel.Loading -> {
                                showDialog()
                            }
                            //when response is success
                            is ResponseModel.Success -> {
                                dismissDialog()

                                if (it.data?.body()?.data == null) {
                                    Toast.makeText(requireContext(), "Data tidak tersedia", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(requireContext(), "Sign Up berhasil", Toast.LENGTH_SHORT).show()
                                    val token = it.data.body()?.data?.token
                                    val tokenVerif = it.data.body()?.data?.user?.verificationToken
                                    val email = binding?.inputEmail?.text.toString()
                                    val userId = it.data.body()?.data?.user?.userId.toString()
                                    if (token != null) {
                                        //stored token
                                        saveLocalToken(token)
                                        saveEmail(email)

                                        //get user id and token email
                                        val sharedPref = context?.getSharedPreferences("cacheUserAndVerification", 0)
                                        val editor: SharedPreferences.Editor = sharedPref!!.edit()
                                        editor.putString("user_id", it.data.body()?.data?.user?.userId.toString())
                                        editor.putString("verification_token", it.data.body()?.data?.user?.verificationToken)
                                        editor.apply()

                                        if (userId != null) {
                                            //stored user id
                                            saveLocalUserId(userId)
//                                        viewModel.saveUserID(userId)

                                            if (tokenVerif != null){
                                                //stored token email
                                                saveLocalTokenVerif(tokenVerif)
//                                            viewModel.saveTokenVerif(tokenVerif)
                                            }
                                        }

                                        //navigate to Email Verif layout
                                        val navOptions = NavOptions.Builder()
                                            .setPopUpTo(R.id.emailVerifFragment, true)
                                            .build()
                                        val action = SignUpFragmentDirections.signUpFragmentToEmailVerifFragment()
                                        findNavController().navigate(action, navOptions)


                                    } else {
                                        Toast.makeText(requireContext(), "Token tidak tersedia", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun saveEmail(email: String) {
        val sharedPref = context?.getSharedPreferences("email", 0)
        val editor: SharedPreferences.Editor = sharedPref!!.edit()
        editor.putString("emailUser", email)
        editor.apply()
    }

    private fun saveLocalToken(token: String){
        val sharedPref = context?.getSharedPreferences("token", 0)
        val editor: SharedPreferences.Editor = sharedPref!!.edit()
        editor.putString("tokenBody", token)
        editor.apply()
    }

    private fun saveLocalTokenVerif(tokenVerif: String) {
        val sharedPref = context?.getSharedPreferences("verification_token", 0)
        val editor: SharedPreferences.Editor = sharedPref!!.edit()
        editor.putString("verifTokenBody", tokenVerif)
        editor.apply()
    }

    private fun saveLocalUserId(userId: String) {
        val sharedPref = context?.getSharedPreferences("user_id", 0)
        val editor: SharedPreferences.Editor = sharedPref!!.edit()
        editor.putString("UserIdBody", userId)
        editor.apply()
    }

    private fun showDialog() {
        viewModel.viewModelScope.launch {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Loading...", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun dismissDialog() {
        viewModel.viewModelScope.launch {
            withContext(Dispatchers.Main) {

            }
        }
    }
}