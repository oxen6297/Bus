package sb.park.bus.presentation.utils

import android.os.SystemClock
import android.view.View

class SingleClick(private val onSingleClick: (View) -> Unit) :
    View.OnClickListener {

    private var lastClick = 0L

    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastClick > 1000L) {
            onSingleClick(v)
            lastClick = SystemClock.elapsedRealtime()
        }
    }
}