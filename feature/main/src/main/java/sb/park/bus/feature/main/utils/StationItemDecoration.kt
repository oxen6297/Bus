package sb.park.bus.feature.main.utils

import android.graphics.Canvas
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import sb.park.bus.feature.main.R

class StationItemDecoration : RecyclerView.ItemDecoration() {

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val paint = Paint().apply {
            color = parent.context.getColor(R.color.gray)
        }

        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams

            c.drawRect(
                parent.paddingStart.toFloat(),
                (child.bottom + params.bottomMargin).toFloat(),
                parent.width - parent.paddingEnd.toFloat(),
                (child.bottom + params.bottomMargin).toFloat() + 1,
                paint
            )
        }
    }
}

