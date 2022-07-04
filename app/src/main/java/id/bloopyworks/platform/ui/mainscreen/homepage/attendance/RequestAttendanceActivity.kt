package id.bloopyworks.platform.ui.mainscreen.homepage.attendance

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import id.bloopyworks.platform.core.data.source.remote.request.ReqAttendanceAPIRequest
import id.bloopyworks.platform.databinding.ActivityRequestAttendanceBinding
import java.text.SimpleDateFormat
import java.util.*

class RequestAttendanceActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnDatePicker: Button
    private lateinit var edtClockIn : EditText
    private lateinit var edtClockOut : EditText
    private lateinit var edtDesc : EditText
    private lateinit var btnReq : Button
    private lateinit var btnCancel : Button
    private lateinit var binding : ActivityRequestAttendanceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestAttendanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViews()
        setListeners()
    }

    private fun setViews() {
        btnDatePicker = binding.btnDateDropdown
        edtClockIn = binding.edtClockIn
        edtClockOut = binding.edtClockOut
        edtDesc = binding.edtDesc
        btnReq = binding.btnReqAttendance
        btnCancel = binding.btnCancel
    }

    private fun setListeners() {
        btnDatePicker.setOnClickListener(this)
        edtClockIn.setOnClickListener(this)
        edtClockOut.setOnClickListener(this)
        edtDesc.setOnClickListener(this)
        btnReq.setOnClickListener(this)
        btnCancel.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v) {
            btnReq -> {
                val myCalender = Calendar.getInstance()

                val datePicker =
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        myCalender.set(Calendar.YEAR, year)
                        myCalender.set(Calendar.MONDAY, month)
                        myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        updateLable(myCalender)
                    }

                DatePickerDialog(
                    this,
                    datePicker,
                    myCalender.get(Calendar.YEAR),
                    myCalender.get(Calendar.MONTH),
                    myCalender.get(
                        Calendar.DAY_OF_MONTH
                    )
                ).show()

                val date = btnDatePicker.text
                val clockIn = edtClockIn.text
                val clockOut = edtClockOut.text
                val description = edtDesc.text

                val request = ReqAttendanceAPIRequest(date.toString(), clockIn.toString(), clockOut.toString(), description.toString())

            }
        }
    }

    private fun updateLable(myCalender: Calendar) {
        val myFormat = "d MMMM yyyy"
        val sdf = SimpleDateFormat(myFormat)
        btnDatePicker.setText(sdf.format(myCalender.time))

    }
}