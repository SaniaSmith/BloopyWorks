package id.bloopyworks.platform.ui.mainscreen.homepage

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
import id.bloopyworks.platform.core.utlis.collectIt
import id.bloopyworks.platform.databinding.FragmentHomepageBinding
import id.bloopyworks.platform.ui.landing.emailVerif.EmailVerifFragmentDirections
import id.bloopyworks.platform.ui.landing.login.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomepageFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentHomepageBinding? = null
    private val binding get() = _binding
    private val viewModel by sharedViewModel<LoginViewModel>()
    private val viewModelLogout by sharedViewModel<HomepageViewModel>()

    private var token : String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomepageBinding.inflate(inflater, container, false)
        getUser()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnAttendance?.setOnClickListener(this)
        binding?.btnEarly?.setOnClickListener(this)
        binding?.btnOvertime?.setOnClickListener(this)
        binding?.btnLiveloc?.setOnClickListener(this)
        binding?.btnTimeOff?.setOnClickListener(this)
        binding?.btnPaySlip?.setOnClickListener(this)
        binding?.btnLogout?.setOnClickListener(this)

        //get token
        val sharedPrefToken = context?.getSharedPreferences("token", 0)
        token = sharedPrefToken?.getString("tokenBody", "").toString()
    }

    override fun onClick(view: View?) {
        when (view) {
            binding?.btnLogout -> {
                if (token.isNotEmpty()) {
                    logout()
                } else {
                    Toast.makeText(requireContext(), "Token tidak ada", Toast.LENGTH_SHORT).show()
                }
            }

            binding?.btnAttendance -> {
                parentFragment?.requireView()?.let {
                    //navigate to show attendance fragment
                    Navigation.findNavController(it).navigate(R.id.attendanceActivity)

                }
            }

            binding?.btnTimeOff -> {
                parentFragment?.requireView()?.let {
                    //navigate to show time off fragment
                    Navigation.findNavController(it).navigate(R.id.timeoffActivity)
                }
            }

            binding?.btnEarly -> {
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.homepageFragment, true).build()
                val action =
                    EmailVerifFragmentDirections.emailVerifFragmentToHomepageFragment()
                findNavController().navigate(action, navOptions)
            }

            binding?.btnOvertime -> {
                parentFragment?.requireView()?.let {
                    //navigate to show login
                    Navigation.findNavController(it).navigate(R.id.overtimeActivity)
                }
            }

            binding?.btnLiveloc -> {
                parentFragment?.requireView()?.let {
                    //navigate to show login
                    Navigation.findNavController(it).navigate(R.id.liveLocationActivity)
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

    private fun getUser() {
        val sharedPref = context?.getSharedPreferences("token", 0)
        val token = sharedPref?.getString("tokenBody", "").toString()
        viewModel.viewModelScope.launch {
            viewModel.authenticationUser(token)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.responseAuthenticationUser.collectLatest {
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
                            binding?.employeeName?.text = it.data?.body()?.data?.userName

                            if (it.data?.body()?.data == null) {
                                Toast.makeText(requireContext(), "Data tidak tersedia", Toast.LENGTH_SHORT).show()
                            } else {
//                                val sharedPref = context?.getSharedPreferences("cacheUser", 0)
//                                val editor: SharedPreferences.Editor = sharedPref!!.edit()
//                                editor.putString("user_name", it.data.body()?.data?.userName)
//                                editor.putString("email", it.data.body()?.data?.email)
//                                editor.putString("user_phone", it.data.body()?.data?.userPhone.toString())
//                                editor.putString("user_gender", it.data.body()?.data?.userGender.toString())
//                                editor.putString("user_role", it.data.body()?.data?.userRole)
//                                editor.putString("user_birthPlace", it.data.body()?.data?.userBirthPlace.toString())
//                                editor.putString("user_birthDate", it.data.body()?.data?.userBirthDate.toString())
//                                editor.putString("user_identityType", it.data.body()?.data?.userIdentityType.toString())
//                                editor.putString("user_identityNumber", it.data.body()?.data?.userIdentityNumber.toString())
//                                editor.putString("user_identityExpiryDate", it.data.body()?.data?.userIdentityExpiryDate.toString())
//                                editor.apply()
                            }
                        }
                    }
                }

            }
        }
    }

    private fun logout() {

        //send request logout user
        viewModelLogout.viewModelScope.launch {
            viewModelLogout.logoutUser("Bearer ".plus(token))
        }

        //get response logout user
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModelLogout.responseLogout.collectLatest {
                    when (it) {
                        //when response is error
                        is ResponseModel.Error -> {
                            Toast.makeText(requireContext(), "Gagal: ${it.message}", Toast.LENGTH_SHORT).show()
                        }
                        //when response is idle
                        is ResponseModel.Idle -> {
                            Toast.makeText(requireContext(), "Idle State", Toast.LENGTH_SHORT).show()
                        }
                        //when response is loading
                        is ResponseModel.Loading -> {
                            showDialog()
                        }
                        //when response is success
                        is ResponseModel.Success -> {
                            Toast.makeText(requireContext(), "Berhasil Logout", Toast.LENGTH_SHORT).show()

                            //delete stored token
                            viewModelLogout.viewModelScope.launch(Dispatchers.Main) {
                                viewModelLogout.deleteToken()
                            }

                            //navigate to HomeFragment layout
                            val navOptions = NavOptions.Builder()
                                .setPopUpTo(R.id.getStartedFragment, true)
                                .build()
                            val action = HomepageFragmentDirections.homepageFragmentToGetStartedFragment()
                            findNavController().navigate(action, navOptions)
                        }
                    }
                }
            }
        }
    }
}