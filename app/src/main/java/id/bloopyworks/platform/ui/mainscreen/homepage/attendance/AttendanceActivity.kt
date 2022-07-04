package id.bloopyworks.platform.ui.mainscreen.homepage.attendance

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.bloopyworks.platform.databinding.ActivityAttendanceBinding
import id.bloopyworks.platform.ui.mainscreen.homepage.HomepageFragment
import java.text.SimpleDateFormat
import java.util.*

class AttendanceActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var back : Button
    private lateinit var btnDatePicker : Button
    private lateinit var btnReqAttendance : Button
    private lateinit var binding : ActivityAttendanceBinding
    private var layoutManager : RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<RecyclerAttendanceAdapter.ViewHolder>? = null
    private lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAttendanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViews()
        setListeners()

        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        adapter = RecyclerAttendanceAdapter()
        recyclerView.adapter = adapter
    }

    private fun setViews() {
        back = binding.btnBackArrow
        btnDatePicker = binding.btnMonthDropdown
        btnReqAttendance = binding.btnReqEarly
        recyclerView = binding.attendanceRecyclerView
    }

    private fun setListeners() {
        back.setOnClickListener(this)
        btnDatePicker.setOnClickListener(this)
        btnReqAttendance.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v) {
            back -> {
                val intent = Intent(this@AttendanceActivity, HomepageFragment::class.java)
                startActivity(intent)
            }

            btnDatePicker -> {
                val myCalender = Calendar.getInstance()

                val datePicker = DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
                    myCalender.set(Calendar.YEAR, year)
                    myCalender.set(Calendar.MONDAY, month)
                    myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    updateLable(myCalender)
                }

                DatePickerDialog(this, datePicker, myCalender.get(Calendar.YEAR), myCalender.get(
                    Calendar.MONTH), myCalender.get(Calendar.DAY_OF_MONTH)).show()
            }

            btnReqAttendance -> {
                val intent = Intent(this@AttendanceActivity, RequestAttendanceActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun updateLable(myCalender: Calendar) {
        val myFormat = "MMMM yyyy"
        val sdf = SimpleDateFormat(myFormat)
        btnDatePicker.setText(sdf.format(myCalender.time))
    }
}