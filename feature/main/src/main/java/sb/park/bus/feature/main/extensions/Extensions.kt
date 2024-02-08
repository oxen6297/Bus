package sb.park.bus.feature.main.extensions

import android.content.Context
import android.view.View
import android.widget.Toast
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

fun View.singleClickListener(click: (View) -> Unit) {
    setOnClickListener(SingleClick(click))
}

fun Context.customDialog(
    titleText: String,
    confirmBtnText: String = "확인",
    cancelBtnText: String = "취소",
    clickCancel: (View) -> Unit = {},
    clickConfirm: (View) -> Unit
): CustomDialogBuilder = CustomDialogBuilder(this).apply {
    initView(R.id.btn_confirm, R.id.btn_cancel, R.id.text_title)
    setViewText(titleText, confirmBtnText, cancelBtnText)
    clickCancel(clickCancel)
    clickConfirm(clickConfirm)
    showDialog()
}