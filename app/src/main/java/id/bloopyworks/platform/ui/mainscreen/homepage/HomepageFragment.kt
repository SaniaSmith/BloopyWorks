package id.bloopyworks.platform.ui.mainscreen.homepage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import id.bloopyworks.platform.R
import id.bloopyworks.platform.core.utlis.collectIt
import id.bloopyworks.platform.databinding.FragmentGetStartedBinding
import id.bloopyworks.platform.databinding.FragmentHomepageBinding
import id.bloopyworks.platform.ui.landing.getStarted.GetStartedFragmentDirections
import id.bloopyworks.platform.ui.landing.getStarted.GetStartedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomepageFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentHomepageBinding? = null
    private val binding get() = _binding
    private val viewModel by sharedViewModel<GetStartedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomepageBinding.inflate(inflater, container, false)
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
    }

    override fun onClick(view: View?) {
        when (view) {
            binding?.btnAttendance -> {
                parentFragment?.requireView()?.let {
                    //navigate to show attendance fragment
                    Navigation.findNavController(it).navigate(R.id.attendanceFragment)

                }
            }

            binding?.btnEarly -> {
                parentFragment?.requireView()?.let {
                    //navigate to show login
                    Navigation.findNavController(it).navigate(R.id.signUpFragment)
                }
            }
        }
    }
}