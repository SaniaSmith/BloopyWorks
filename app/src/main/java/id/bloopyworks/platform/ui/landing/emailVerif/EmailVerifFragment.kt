package id.bloopyworks.platform.ui.landing.emailVerif

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import id.bloopyworks.platform.R
import id.bloopyworks.platform.core.data.source.remote.network.ResponseModel
import id.bloopyworks.platform.core.data.source.remote.request.EmailVerificationApiRequest
import id.bloopyworks.platform.core.data.source.remote.request.ResendCodeApiRequest
import id.bloopyworks.platform.databinding.FragmentEmailVerifBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EmailVerifFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentEmailVerifBinding? = null
    private val binding get() = _binding

    private val viewModel by sharedViewModel<EmailVerificationViewModel>()

    private var email : String = ""
    private var userId : String = ""
    private var tokenVerif : String = ""
    private var token : String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEmailVerifBinding.inflate(inflater, container, false)

        //get email user
        val sharedPref = context?.getSharedPreferences("email", 0)
        email = sharedPref?.getString("emailUser", "").toString()

        //get user id
        val sharedPrefUserId = context?.getSharedPreferences("user_id", 0)
        userId = sharedPrefUserId?.getString("UserIdBody", "").toString()

        //get token verif
        val sharedPrefTokenVerif = context?.getSharedPreferences("verification_token", 0)
        tokenVerif = sharedPrefTokenVerif?.getString("verifTokenBody", "").toString()

        //get token
//        val sharedPrefToken = context?.getSharedPreferences("token", 0)
//        token = sharedPrefToken?.getString("tokenBody", "").toString()

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.emailForVerification?.text = email

        //set Listeners
        binding?.btnLogin?.setOnClickListener(this)
        binding?.linearLayoutRoot?.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v) {
            binding?.btnResend -> {
                val request = ResendCodeApiRequest(token, email)
                //send request login user
                viewModel.viewModelScope.launch {
                    viewModel.resendCodeViewModel(request)
                }

                //get response login user
                viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.code.collect {
                            when (it) {
                                //when response is error
                                is ResponseModel.Error -> {
                                    Toast.makeText(
                                        requireContext(),
                                        "Gagal: ${it.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                //when response is Idle
                                is ResponseModel.Idle -> {
                                    Toast.makeText(
                                        requireContext(),
                                        "Idle State",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                //when response is loading
                                is ResponseModel.Loading -> {
                                    showDialog()
                                }
                                //when response is success
                                is ResponseModel.Success -> {
                                    dismissDialog()

                                    if (it.data?.body()?.status == "success") {
                                        Toast.makeText(
                                            requireContext(),
                                            "Resend Code Berhasil",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    } else {
                                        Toast.makeText(
                                            requireContext(),
                                            "Verified Gagal",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        }
                    }
                }
            }

            binding?.btnLogin -> {
                val code1 = binding?.codeNumber1?.text.toString()
                val code2 = binding?.codeNumber2?.text.toString()
                val code3 = binding?.codeNumber3?.text.toString()
                val code4 = binding?.codeNumber4?.text.toString()


                val otpCode = "${code1}${code2}${code3}${code4}"
                val request = EmailVerificationApiRequest(userId.toInt(), tokenVerif, otpCode)

                //send request login user
                viewModel.viewModelScope.launch {
                    viewModel.verifEmailUserViewModel(request)
                }

                //get response login user
                viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.data.collect {
                            when (it) {
                                //when response is error
                                is ResponseModel.Error -> {
                                    Toast.makeText(
                                        requireContext(),
                                        "Gagal: ${it.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                //when response is Idle
                                is ResponseModel.Idle -> {
                                    Toast.makeText(
                                        requireContext(),
                                        "Idle State",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                //when response is loading
                                is ResponseModel.Loading -> {
                                    showDialog()
                                }
                                //when response is success
                                is ResponseModel.Success -> {
                                    dismissDialog()

                                    if (it.data?.body()?.status == "success") {
                                        Toast.makeText(
                                            requireContext(),
                                            "Verified Berhasil",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        // Navigate to Homepage
                                        val navOptions = NavOptions.Builder()
                                            .setPopUpTo(R.id.homepageFragment, true).build()
                                        val action =
                                            EmailVerifFragmentDirections.emailVerifFragmentToHomepageFragment()
                                        findNavController().navigate(action, navOptions)
                                    } else {
                                        Toast.makeText(
                                            requireContext(),
                                            "Verified Gagal",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
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