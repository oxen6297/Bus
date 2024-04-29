package sb.park.bus.feature.main.extensions

import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.isVisible
import sb.park.bus.feature.main.R
import sb.park.bus.feature.main.utils.CustomDialogBuilder
import sb.park.bus.feature.main.utils.SingleClick

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun ObjectAnimator.setAnimation(view: View) {
    if (view.isVisible) {
        doOnEnd { view.hide() }
    } else {
        doOnStart { view.show() }
    }
    start()
}

fun View.singleClickListener(click: (View) -> Unit) {
    setOnClickListener(SingleClick(click))
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