package id.bloopyworks.platform.ui.landing.login

import android.os.Bundle
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
                    Navigation.findNavController(it).navigate(R.id.signUpDialogFragment)
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

                            if (it.data?.body()?.data == null) {
                                Toast.makeText(requireContext(), "Data tidak tersedia", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(requireContext(), "Login berhasil", Toast.LENGTH_SHORT).show()
                                val token = it.data.body()?.data?.token
                                if (token != null) {
                                    //stored token
                                    viewModel.saveTokenKey(token)

                                    //navigate to HomepageFragment layout
                                    val navOptions = NavOptions.Builder()
                                        .setPopUpTo(R.id.homepageFragment, true)
                                        .build()
                                    val action = LoginFragmentDirections.loginToHomepage()
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