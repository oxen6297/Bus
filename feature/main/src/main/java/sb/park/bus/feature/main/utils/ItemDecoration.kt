package sb.park.bus.feature.main.utils

import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import sb.park.bus.feature.main.R

class ItemDecoration : RecyclerView.ItemDecoration() {

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val paint = Paint().apply {
            color = parent.context.getColor(R.color.gray_c8c8c8)
        }

        parent.children.forEach {
            val params = it.layoutParams as RecyclerView.LayoutParams

            c.drawRect(
                parent.paddingStart.toFloat(),
                (it.bottom + params.bottomMargin).toFloat(),
                parent.width - parent.paddingEnd.toFloat(),
                (it.bottom + params.bottomMargin).toFloat() + 1,
                paint
            )
        }
    }
}

