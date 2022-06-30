package id.bloopyworks.platform.ui.mainactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavInflater
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import id.bloopyworks.platform.R
import id.bloopyworks.platform.core.utlis.collectIt
import id.bloopyworks.platform.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private lateinit var navController: NavController

    private val viewModel by viewModel<MainActivityViewModel>()

    private var tokenIsAvailable: Boolean = false

    //From Stack Overflow
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var inflater: NavInflater
    private lateinit var graph: NavGraph

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        inflater = navHostFragment.navController.navInflater
        graph = inflater.inflate(R.navigation.activity_navigation)

        //get authentication token
        viewModel.getToken.collectIt(this) {
            tokenIsAvailable = it.isNotEmpty()
        }

//        navController = findNavController(R.id.nav_host_fragment)
//
//        navController.addOnDestinationChangedListener { _, destination, _ -> }

        if (tokenIsAvailable) {
            //if true navigate to Homepage layout
            graph.startDestination = R.id.homepageFragment
        } else {
            //if else navigate to GetStarted layout
            graph.startDestination = R.id.getStartedFragment
        }
    }
}