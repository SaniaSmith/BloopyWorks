package id.bloopyworks.platform.ui.mainscreen.homepage.overtime

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import id.bloopyworks.platform.databinding.ActivityReqOvertimeBinding
import java.text.SimpleDateFormat
import java.util.*

class ReqOvertimeActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btn_backOvertime : Button
    private lateinit var btn_dateOvertime : Button
    private lateinit var btn_reqOvertime : Button
    private lateinit var btn_cancelOvertime : Button
    private lateinit var expandLayout : LinearLayout
    private lateinit var expandCardView : CardView
    private lateinit var binding : ActivityReqOvertimeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReqOvertimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViews()
        setListeners()
    }

    private fun setViews() {
        btn_backOvertime = binding.btnBackArrow7
        btn_dateOvertime = binding.btnDateDropdownOvertime
        btn_reqOvertime = binding.btnReqOvertime
        btn_cancelOvertime = binding.btnCancelOvertime
        expandLayout = binding.expandableLayout
        expandCardView = binding.cardView
    }

    private fun setListeners() {
        btn_backOvertime.setOnClickListener(this)
        btn_dateOvertime.setOnClickListener(this)
        btn_reqOvertime.setOnClickListener(this)
        btn_cancelOvertime.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v) {
            btn_backOvertime -> {
                val intent = Intent (this@ReqOvertimeActivity, OvertimeActivity::class.java)
                startActivity(intent)
            }

            btn_dateOvertime -> {
                val myCalender = Calendar.getInstance()

                val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    myCalender.set(Calendar.YEAR, year)
                    myCalender.set(Calendar.MONDAY, month)
                    myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    updateLable(myCalender)
                }

                DatePickerDialog(this, datePicker, myCalender.get(Calendar.YEAR), myCalender.get(
                    Calendar.MONTH), myCalender.get(
                    Calendar.DAY_OF_MONTH)).show()

                if(expandLayout.visibility == View.GONE)  {
                    TransitionManager.beginDelayedTransition(expandCardView, AutoTransition())
                    expandLayout.visibility = View.VISIBLE
                }

            }

            btn_reqOvertime -> {

            }

            btn_cancelOvertime -> {
                val intent = Intent(this@ReqOvertimeActivity, OvertimeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun updateLable(myCalender: Calendar) {
        val myFormat = "d MMMM yyyy"
        val sdf = SimpleDateFormat(myFormat)
        btn_dateOvertime.setText(sdf.format(myCalender.time))
    }
}