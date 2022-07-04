package id.bloopyworks.platform.ui.landing.login

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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
import id.bloopyworks.platform.databinding.FragmentLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoginFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding

    private val viewModel by sharedViewModel<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnLogin?.setOnClickListener(this)

        binding?.btnSignUp?.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view) {
            binding?.btnLogin -> {
                login()

            }

            binding?.btnSignUp -> {
                parentFragment?.requireView()?.let {
                    //navigate to show dialog sign up
                    Navigation.findNavController(it).navigate(R.id.signUpFragment)
                }
            }
        }
    }

    private fun login() {
        val email = binding?.edtEmail?.text
        val password = binding?.edtPass?.text

        val request = LoginAPIRequest(email.toString(), password.toString())

        //send request login user
        viewModel.viewModelScope.launch {
            viewModel.login(request)
        }

        //get response login user
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.data.collectLatest {
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

                            if (it.data?.body()?.status == null) {
                                Toast.makeText(requireContext(), "Data tidak tersedia", Toast.LENGTH_SHORT).show()
                            } else {
                                val token = it.data.body()?.data?.token
                                val email = binding?.edtEmail?.text.toString()
                                val isVerif = it.data.body()?.data?.isVerified
                                if (token != null) {
                                    //stored token
                                    saveLocalToken(token)
                                    saveEmail(email)
                                    viewModel.saveTokenKey(token)
                                    if (isVerif != null) {
                                        saveIsVerified(isVerif)
                                    }

                                    if (isVerif == true) {
                                        Toast.makeText(requireContext(), "Login Berhasil", Toast.LENGTH_SHORT).show()
                                        // Navigate to Homepage
                                        val navOptions = NavOptions.Builder().setPopUpTo(R.id.homepageFragment, true).build()
                                        val action = LoginFragmentDirections.loginToHomepage()
                                        findNavController().navigate(action, navOptions)
                                    } else if (isVerif == false) {
                                        Toast.makeText(requireContext(), "Tolong Verif Email Terlebih Dahulu", Toast.LENGTH_SHORT).show()
                                        //Navigate to email verif
                                        val navOptions = NavOptions.Builder().setPopUpTo(R.id.emailVerifFragment, true).build()
                                        val action = LoginFragmentDirections.loginFragmentToEmailVerifFragment()
                                        findNavController().navigate(action, navOptions)
                                    }

                                } else {
                                    Toast.makeText(requireContext(), "Token Tidak Tersedia", Toast.LENGTH_SHORT).show()
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

    private fun saveLocalToken(token: String) {
        val sharedPref = context?.getSharedPreferences("token", 0)
        val editor: SharedPreferences.Editor = sharedPref!!.edit()
        editor.putString("tokenBody", token)
        editor.apply()
    }

    private fun saveIsVerified(verif: Boolean) {
        val sharedPref = context?.getSharedPreferences("verifed", 0)
        val editor: SharedPreferences.Editor = sharedPref!!.edit()
        editor.putBoolean("verifiedBody", verif)
        editor.commit()
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