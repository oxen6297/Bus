package sb.park.bus.feature.main.utils

import android.view.View
import android.widget.Toast
import androidx.databinding.BindingAdapter
import sb.park.bus.data.ApiResult

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("loading")
    fun bindIsLoading(view: View, apiResult: ApiResult<*>) {
        view.visibility = if (apiResult is ApiResult.Loading) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("toast")
    fun bindToast(view: View, throwable: Throwable?) {
        throwable?.message?.let { errorMessage ->
            Toast.makeText(view.context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}