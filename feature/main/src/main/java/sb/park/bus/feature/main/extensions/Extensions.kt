package sb.park.bus.feature.main.extensions

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import sb.park.bus.feature.main.R
import sb.park.bus.feature.main.utils.CustomDialogBuilder
import sb.park.bus.feature.main.utils.SingleClick

/**
 * View Extension
 */
fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.singleClickListener(click: (View) -> Unit) {
    setOnClickListener(SingleClick(click))
}


/**
 * Context Extension
 */
fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.customDialog(
    titleText: String,
    confirmBtnText: String = getString(R.string.confirm),
    cancelBtnText: String = getString(R.string.cancel),
    clickCancel: (View) -> Unit = {},
    clickConfirm: (View) -> Unit
): CustomDialogBuilder = CustomDialogBuilder(this).apply {
    initView(R.id.btn_confirm, R.id.btn_cancel, R.id.text_title)
    setViewText(titleText, confirmBtnText, cancelBtnText)
    clickCancel(clickCancel)
    clickConfirm(clickConfirm)
    showDialog()
}


/**
 * BottomSheet Extension
 */
inline fun <T : View> BottomSheetBehavior<T>.setOnSlide(
    crossinline onSlide: (View, Float) -> Unit
) = addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
    override fun onStateChanged(p0: View, p1: Int) {}

    override fun onSlide(p0: View, p1: Float) {
        onSlide(p0, p1)
    }
})