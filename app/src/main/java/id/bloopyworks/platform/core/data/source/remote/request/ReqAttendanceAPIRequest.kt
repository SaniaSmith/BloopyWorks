package id.bloopyworks.platform.core.data.source.remote.request

import android.widget.DatePicker
import java.util.*

data class ReqAttendanceAPIRequest(
    val date: String,
    val clockIn: String,
    val clockOut: String,
    val desc: String
)
