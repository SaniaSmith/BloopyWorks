package id.bloopyworks.platform.ui.mainscreen.homepage.livelocation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import id.bloopyworks.platform.databinding.ActivityLiveLocationBinding
import id.bloopyworks.platform.ui.mainscreen.homepage.HomepageFragment
import java.text.SimpleDateFormat
import java.util.*

class LiveLocationActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var backArrow : Button
    private lateinit var clockIn : Button
    private lateinit var clockOut : Button
    private lateinit var viewLog : Button
    private lateinit var binding : ActivityLiveLocationBinding
    private lateinit var time : TextView
    private lateinit var date : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLiveLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViews()
        setListeners()

        var myCalender = Calendar.getInstance()

        val myFormat = "EEEE, d MMMM yyyy"
        val sdf = SimpleDateFormat(myFormat)
        date.setText(sdf.format(myCalender.time))


        val myTimeFormat = "HH.mm"
        val dtf = SimpleDateFormat(myTimeFormat)
        time.setText(dtf.format(myCalender.time))

    }


    private fun setViews() {
        backArrow = binding.btnBackArrow10
        clockIn = binding.btnClockIn
        clockOut = binding.btnClockOut
        time = binding.tvTime
        date = binding.tvTimeDate
    }

    private fun setListeners() {
        backArrow.setOnClickListener(this)
        clockIn.setOnClickListener(this)
        clockOut.setOnClickListener(this)
        viewLog.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v)  {
            backArrow -> {
                val intent = Intent(this@LiveLocationActivity, HomepageFragment::class.java)
                startActivity(intent)
            }

            clockIn -> {

            }

            clockOut -> {

            }

            viewLog -> {

            }
        }
    }
}