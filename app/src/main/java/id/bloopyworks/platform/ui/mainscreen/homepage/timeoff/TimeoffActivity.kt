package id.bloopyworks.platform.ui.mainscreen.homepage.timeoff

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.bloopyworks.platform.databinding.ActivityTimeoffBinding
import id.bloopyworks.platform.ui.mainscreen.homepage.HomepageFragment
import java.text.SimpleDateFormat
import java.util.*

class TimeoffActivity : AppCompatActivity(), View.OnClickListener {
    private var layoutManager : RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<RecyclerTimeoffAdapter.ViewHolder>? = null
    private lateinit var binding : ActivityTimeoffBinding
    private lateinit var recyclerView : RecyclerView
    private lateinit var btnReqTimeOff : Button
    private lateinit var btnMonth : Button
    private lateinit var btnBack : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimeoffBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViews()
        setListeners()

        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        adapter = RecyclerTimeoffAdapter()
        recyclerView.adapter = adapter

    }

    private fun setViews() {
        recyclerView = binding.searchRecyclerView
        btnReqTimeOff = binding.btnReqTimeOff
        btnMonth = binding.btnMonthDropdownTimeOff
        btnBack = binding.btnBackArrow5
    }

    private fun setListeners() {
        btnMonth.setOnClickListener(this)
        btnBack.setOnClickListener(this)
        btnReqTimeOff.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v) {
            btnBack -> {
                val intent = Intent(this@TimeoffActivity, HomepageFragment::class.java)
                startActivity(intent)
            }

            btnMonth -> {
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

            btnReqTimeOff -> {
                val intent = Intent(this@TimeoffActivity, ReqTimeoffActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun updateLable(myCalender: Calendar) {
        val myFormat = "MMMM yyyy"
        val sdf = SimpleDateFormat(myFormat)
        btnMonth.setText(sdf.format(myCalender.time))
    }
}