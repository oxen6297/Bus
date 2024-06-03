package sb.park.bus.presentation.utils

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import sb.park.bus.presentation.R

@SuppressLint("InflateParams")
class CustomDialogBuilder(private val context: Context) {

    private val view by lazy {
        LayoutInflater.from(context).inflate(R.layout.custom_dialog, null)
    }
    private val dialog by lazy {
        AlertDialog.Builder(context).setView(view).create()
    }
    private lateinit var btnConfirm: Button
    private lateinit var btnCancel: Button
    private lateinit var textTitle: TextView

    init {
        dialog.apply {
            setCancelable(false)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    fun initView(confirmButtonId: Int, cancelBtnId: Int, textTitleId: Int) {
        btnConfirm = view.findViewById(confirmButtonId)
        btnCancel = view.findViewById(cancelBtnId)
        textTitle = view.findViewById(textTitleId)
    }

    fun setViewText(titleText: String, confirmBtnText: String, cancelBtnText: String) {
        textTitle.text = titleText
        btnConfirm.text = confirmBtnText
        btnCancel.text = cancelBtnText
    }

    fun clickConfirm(confirmClickListener: (View) -> Unit) {
        btnConfirm.setOnClickListener {
            confirmClickListener(it)
            dialog.dismiss()
        }
    }

    fun clickCancel(cancelClickListener: (View) -> Unit) {
        btnCancel.setOnClickListener {
            cancelClickListener(it)
            dialog.dismiss()
        }
    }

    fun showDialog() = dialog.show()
}