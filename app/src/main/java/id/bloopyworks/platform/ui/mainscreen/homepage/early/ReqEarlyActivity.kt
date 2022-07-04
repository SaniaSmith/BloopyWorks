package id.bloopyworks.platform.ui.mainscreen.homepage.early

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
import id.bloopyworks.platform.databinding.ActivityReqEarlyBinding
import id.bloopyworks.platform.ui.mainscreen.homepage.overtime.OvertimeActivity
import java.text.SimpleDateFormat
import java.util.*

class ReqEarlyActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btn_backEarly : Button
    private lateinit var btn_dateEarly : Button
    private lateinit var btn_reqEarly : Button
    private lateinit var btn_cancelEarly : Button
    private lateinit var expandLayout : LinearLayout
    private lateinit var expandCardView : CardView
    private lateinit var binding : ActivityReqEarlyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReqEarlyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViews()
        setListeners()
    }

    private fun setViews() {
        btn_backEarly = binding.btnBackArrow7
        btn_dateEarly = binding.btnDateDropdownEarly
        btn_reqEarly = binding.btnReqEarly
        btn_cancelEarly = binding.btnCancelEarly
        expandLayout = binding.expandableLayout
        expandCardView = binding.cardView
    }

    private fun setListeners() {
        btn_backEarly.setOnClickListener(this)
        btn_dateEarly.setOnClickListener(this)
        btn_reqEarly.setOnClickListener(this)
        btn_cancelEarly.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v) {
            btn_backEarly -> {
                val intent = Intent (this@ReqEarlyActivity, EarlyActivity::class.java)
                startActivity(intent)
            }

            btn_dateEarly -> {
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

            btn_reqEarly -> {

            }

            btn_cancelEarly -> {
                val intent = Intent(this@ReqEarlyActivity, OvertimeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun updateLable(myCalender: Calendar) {
        val myFormat = "d MMMM yyyy"
        val sdf = SimpleDateFormat(myFormat)
        btn_dateEarly.setText(sdf.format(myCalender.time))
    }
}