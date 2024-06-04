package sb.park.bus.presentation.utils

import android.graphics.Color
import android.graphics.Paint
import android.view.View
import android.widget.ToggleButton
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import sb.park.bus.presentation.R
import sb.park.bus.presentation.extensions.showToast
import sb.park.bus.presentation.extensions.singleClickListener
import sb.park.model.ApiResult
import sb.park.model.successOrNull

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("loading")
    fun bindIsLoading(view: View, apiResult: ApiResult<*>) {
        view.isVisible = apiResult is ApiResult.Loading
    }

    @JvmStatic
    @BindingAdapter("no_data")
    fun bindDataIsNullTextView(view: View, apiResult: ApiResult<*>) {
        view.isVisible =
            (apiResult.successOrNull() as List<*>?).isNullOrEmpty() && apiResult !is ApiResult.Loading
    }

    @JvmStatic
    @BindingAdapter("toast")
    fun bindToast(view: View, throwable: Throwable?) {
        throwable?.message?.let { errorMessage ->
            view.context.showToast(errorMessage)
        }
    }

    @JvmStatic
    @BindingAdapter("adapter")
    fun bindAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        recyclerView.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("submitList")
    @Suppress("UNCHECKED_CAST")
    fun bindSubmitList(view: RecyclerView, itemList: List<Any>?) {
        (view.adapter as ListAdapter<Any, *>).submitList(itemList)
    }

    @JvmStatic
    @BindingAdapter("decoration")
    fun bindItemDecoration(view: RecyclerView, itemDecoration: ItemDecoration) {
        view.addItemDecoration(itemDecoration)
    }

    @JvmStatic
    @BindingAdapter("backPressed")
    fun bindBackPressed(view: View, backPressed: Boolean) {
        if (backPressed) {
            view.singleClickListener {
                it.findNavController().popBackStack()
            }
        }
    }

    @JvmStatic
    @BindingAdapter("chart")
    fun bindSetChart(chart: CandleStickChart, itemList: List<CandleEntry>) {
        itemList.ifEmpty { return }

        val dataSet = CandleDataSet(itemList, "").apply {
            shadowColor = Color.LTGRAY
            shadowWidth = 1F
            decreasingColor = Color.BLUE
            decreasingPaintStyle = Paint.Style.FILL
            increasingColor = Color.RED
            increasingPaintStyle = Paint.Style.FILL
            neutralColor = Color.DKGRAY
            highLightColor = Color.TRANSPARENT
            setDrawValues(false)
        }

        chart.apply {
            data = CandleData(dataSet)
            description.isEnabled = false
            isHighlightPerDragEnabled = true
            requestDisallowInterceptTouchEvent(true)
            invalidate()
        }
    }

    @JvmStatic
    @BindingAdapter("clickFavorite")
    fun bindCheckedChangedListener(
        view: ToggleButton,
        action: () -> Unit
    ) {
        view.setOnClickListener {
            action()
            it.context.apply {
                val message = if (view.isChecked) {
                    getString(R.string.toast_add_favorite)
                } else {
                    getString(R.string.toast_delete_favorite)
                }
                showToast(message)
            }
        }
    }
}