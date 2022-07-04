package id.bloopyworks.platform.ui.mainscreen.homepage.timeoff

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import id.bloopyworks.platform.R
import id.bloopyworks.platform.databinding.ActivityReqTimeoffBinding
import java.text.SimpleDateFormat
import java.util.*

class ReqTimeoffActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityReqTimeoffBinding
    private lateinit var back : Button
    private lateinit var req : Button
    private lateinit var cancel : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReqTimeoffBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setviews()

        typeDropDownList()
        reqDropDownList()
        startDate()
        endDate()

        setListeners()

    }

    private fun setviews() {
        back = binding.btnBackArrow5
        req = binding.btnReqTimeOff
        cancel = binding.btnCancelTimeoff
    }

    private fun setListeners() {
        back.setOnClickListener(this)
        req.setOnClickListener(this)
        cancel.setOnClickListener(this)
    }

    private fun typeDropDownList() {
        val timeoff = resources.getStringArray(R.array.time_off)
        val reasonAdapter = ArrayAdapter(this, R.layout.dropdown_time_off, timeoff)
        binding.acTimeOff.setAdapter(reasonAdapter)
    }

    private fun reqDropDownList() {
        val reqtimeoff = resources.getStringArray(R.array.req_time_off)
        val reasonAdapter = ArrayAdapter(this, R.layout.dropdown_time_off, reqtimeoff)
        binding.acReqTimeOff.setAdapter(reasonAdapter)
    }

    private fun startDate() {
        val myCalender = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
            myCalender.set(Calendar.YEAR, year)
            myCalender.set(Calendar.MONDAY, month)
            myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLableStart(myCalender)
        }

        DatePickerDialog(this, datePicker, myCalender.get(Calendar.YEAR), myCalender.get(Calendar.MONTH), myCalender.get(
            Calendar.DAY_OF_MONTH)).show()
    }

    private fun updateLableStart(myCalender: Calendar) {
        val myFormat = "MMMM yyyy"
        val sdf = SimpleDateFormat(myFormat)
        binding.acStartTimeOff.setText(sdf.format(myCalender.time))
    }

    private fun endDate() {
        val myCalender = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
            myCalender.set(Calendar.YEAR, year)
            myCalender.set(Calendar.MONDAY, month)
            myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLableEnd(myCalender)
        }

        DatePickerDialog(this, datePicker, myCalender.get(Calendar.YEAR), myCalender.get(Calendar.MONTH), myCalender.get(
            Calendar.DAY_OF_MONTH)).show()
    }

    private fun updateLableEnd(myCalender: Calendar) {
        val myFormat = "MMMM yyyy"
        val sdf = SimpleDateFormat(myFormat)
        binding.acEndTimeOff.setText(sdf.format(myCalender.time))
    }

    override fun onClick(v: View?) {
        when(v) {
            back -> {
                val intent = Intent(this@ReqTimeoffActivity, TimeoffActivity::class.java)
                startActivity(intent)
                finish()
            }

            req -> {
                //Input data ke database (get)

            }

            cancel -> {
                val intent = Intent(this@ReqTimeoffActivity, TimeoffActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}