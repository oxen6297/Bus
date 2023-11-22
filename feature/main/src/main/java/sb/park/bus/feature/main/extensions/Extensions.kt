package sb.park.bus.feature.main.extensions

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import sb.park.bus.feature.main.utils.SingleClick

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.isShow(): Boolean {
    return visibility == View.VISIBLE
}

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

inline fun <reified T> JsonElement.toList(): List<T> {
    return Gson().fromJson(this, object : TypeToken<List<T>>() {}.type)
}

fun View.singleClickListener(click: (View) -> Unit) {
    setOnClickListener(SingleClick(click))
}

fun LifecycleOwner.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED, block)
    }
}