package sb.park.bus.presentation.extensions

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import sb.park.bus.presentation.R
import sb.park.bus.presentation.utils.CustomDialogBuilder
import sb.park.bus.presentation.utils.SingleClick

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


/**
 * NaverMap Marker Extension
 */
fun NaverMap.setMarker(gpsY: Double, gpsX: Double, title: String) {
    Marker().apply {
        position = LatLng(gpsY, gpsX)
        icon = OverlayImage.fromResource(R.drawable.marker_station)
        width = 75
        height = 75
        captionText = title
        captionOffset = 10
        captionRequestedWidth = 120
        map = this@setMarker
    }
}