package id.bloopyworks.platform.core.utlis

import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// Short way to collect StateFlow on coroutine when Activity Started.
// Useful when you need to collect one StateFlow, but could be used for many.
fun <T> StateFlow<T>.collectIt(lifecycleOwner: LifecycleOwner, function: (T) -> Unit) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collect() {
                function.invoke(it)
            }
        }
    }
}

// Set on click listener
fun View.OnClickListener.setOnClickListeners(vararg views: View?) {
    views.forEach { it?.setOnClickListener(this) }
}

//show toast
fun Fragment.showToast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

//glide
fun ImageView.loadImageUrl(url: String) {
    Glide.with(this.context)
        .load(url)
        .into(this)
}
fun ImageView.loadImageId(id: Int) {
    Glide.with(this.context)
        .load(id)
        .into(this)
}