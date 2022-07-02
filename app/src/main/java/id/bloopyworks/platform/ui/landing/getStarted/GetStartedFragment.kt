package id.bloopyworks.platform.ui.landing.getStarted

import android.os.Bundle
import android.util.Log
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
import id.bloopyworks.platform.databinding.FragmentGetStartedBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class GetStartedFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentGetStartedBinding? = null
    private val binding get() = _binding

    private val viewModel by sharedViewModel<GetStartedViewModel>()

    private var token : String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGetStartedBinding.inflate(inflater, container, false)
        val sharedPref = context?.getSharedPreferences("token", 0)
        token = sharedPref?.getString("tokenBody", "").toString()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnGetStarted?.setOnClickListener(this)

        binding?.btnContinue?.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding?.btnGetStarted -> {
                parentFragment?.requireView()?.let {
                    //navigate to show sign up
                    Navigation.findNavController(it).navigate(R.id.signUpFragment)

                }
            }

            binding?.btnContinue -> {
                if(token != "") {
                    parentFragment?.requireView()?.let {
                        //navigate to show sign up
                        Navigation.findNavController(it).navigate(R.id.homepageFragment)
                    }
                } else {
                    parentFragment?.requireView()?.let {
                        //navigate to show sign up
                        Navigation.findNavController(it).navigate(R.id.loginFragment)
                    }
                }
            }
        }
    }
}