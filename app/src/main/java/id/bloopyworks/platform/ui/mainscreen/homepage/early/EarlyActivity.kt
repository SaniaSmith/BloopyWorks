package id.bloopyworks.platform.ui.mainscreen.homepage.early

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import id.bloopyworks.platform.databinding.ActivityEarlyBinding
import id.bloopyworks.platform.ui.mainscreen.homepage.HomepageFragment
import java.text.SimpleDateFormat
import java.util.*

class EarlyActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btn_backEarly : Button
    private lateinit var btn_dateEarly : Button
    private lateinit var btn_reqEarly : Button
    private lateinit var binding: ActivityEarlyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEarlyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViews()
        setListeners()
    }

    private fun setViews() {
        btn_backEarly = binding.btnBackArrow8
        btn_dateEarly = binding.btnMonthDropdown
        btn_reqEarly = binding.btnReqEarly
    }

    private fun setListeners() {
        btn_backEarly.setOnClickListener(this)
        btn_dateEarly.setOnClickListener(this)
        btn_reqEarly.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v) {
            btn_backEarly -> {
                val intent = Intent(this@EarlyActivity, HomepageFragment::class.java)
                startActivity(intent)
            }

            btn_dateEarly -> {
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

            btn_reqEarly -> {
                val intent = Intent(this@EarlyActivity, ReqEarlyActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun updateLable(myCalender: Calendar) {
        val myFormat = "MMMM yyyy"
        val sdf = SimpleDateFormat(myFormat)
        btn_dateEarly.setText(sdf.format(myCalender.time))
    }
}