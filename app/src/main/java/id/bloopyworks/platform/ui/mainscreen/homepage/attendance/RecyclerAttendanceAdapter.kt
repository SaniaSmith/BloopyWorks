package id.bloopyworks.platform.ui.mainscreen.homepage.attendance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.bloopyworks.platform.R

class RecyclerAttendanceAdapter : RecyclerView.Adapter<RecyclerAttendanceAdapter.ViewHolder>() {
    private var date = arrayOf("1 May 2022", "2 May 2022", "3 May 2022", "4 May 2022", "5 May 2022", "6 May 2022", "7 May 2022", "8 May 2022", "9 May 2022")
    private var clockIn = arrayOf("08.55", "09.02", "-", "08.52", "08.58", "09.15", "-", "08.50", "09.05")
    private var clockOut = arrayOf("17.02", "17.15", "-", "16.58", "17.09", "17.02", "-", "17.05", "16.58")
    private val btnPlus = intArrayOf(R.drawable.ic_plus_square, R.drawable.ic_plus_square, R.drawable.ic_plus_square, R.drawable.ic_plus_square,
        R.drawable.ic_plus_square, R.drawable.ic_plus_square, R.drawable.ic_plus_square, R.drawable.ic_plus_square, R.drawable.ic_plus_square)

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var dateAttendance : TextView
        var clockInAttendance : TextView
        var clockOutAttendance : TextView
        var btnPlusAttendance : ImageButton

        init {
            dateAttendance = itemView.findViewById(R.id.tv_input_date1)
            clockInAttendance = itemView.findViewById(R.id.tv_clockIn_time_1)
            clockOutAttendance = itemView.findViewById(R.id.tv_clockOut_time_1)
            btnPlusAttendance = itemView.findViewById(R.id.btn_plus_attendance)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAttendanceAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.attendance_recycler_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAttendanceAdapter.ViewHolder, position: Int) {
        holder.dateAttendance.text = date[position]
        holder.clockInAttendance.text = clockIn[position]
        holder.clockOutAttendance.text = clockOut[position]
        holder.btnPlusAttendance.setImageResource(btnPlus[position])
    }

    override fun getItemCount(): Int {
        return date.size
    }
}