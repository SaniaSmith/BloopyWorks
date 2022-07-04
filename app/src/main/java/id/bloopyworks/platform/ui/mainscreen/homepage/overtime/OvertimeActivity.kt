package id.bloopyworks.platform.ui.mainscreen.homepage.overtime

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import id.bloopyworks.platform.databinding.ActivityOvertimeBinding
import id.bloopyworks.platform.ui.mainscreen.homepage.HomepageFragment
import java.text.SimpleDateFormat
import java.util.*

class OvertimeActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btn_backOver : Button
    private lateinit var btn_dateOver : Button
    private lateinit var btn_reqOver : Button
    private lateinit var binding : ActivityOvertimeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOvertimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViews()
        setListeners()
    }

    private fun setViews() {
        btn_backOver = binding.btnBackArrow6
        btn_dateOver = binding.btnMonthDropdown
        btn_reqOver = binding.btnReqOvertime
    }

    private fun setListeners() {
        btn_backOver.setOnClickListener(this)
        btn_dateOver.setOnClickListener(this)
        btn_reqOver.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v) {
            btn_backOver -> {
                val intent = Intent(this@OvertimeActivity, HomepageFragment::class.java)
                startActivity(intent)
            }

            btn_dateOver -> {
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

            btn_reqOver -> {
                val intent = Intent(this@OvertimeActivity, ReqOvertimeActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun updateLable(myCalender: Calendar) {
        val myFormat = "MMMM yyyy"
        val sdf = SimpleDateFormat(myFormat)
        btn_dateOver.setText(sdf.format(myCalender.time))
    }
}