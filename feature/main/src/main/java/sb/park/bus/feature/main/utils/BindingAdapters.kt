package sb.park.bus.feature.main.utils

import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import sb.park.bus.feature.main.R
import sb.park.bus.feature.main.extensions.hide
import sb.park.bus.feature.main.extensions.show
import sb.park.bus.feature.main.extensions.showToast
import sb.park.model.ApiResult
import sb.park.model.successOrNull

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("loading")
    fun bindIsLoading(view: View, apiResult: ApiResult<*>) {
        if (apiResult is ApiResult.Loading) view.show() else view.hide()
    }

    @JvmStatic
    @BindingAdapter("no_data")
    fun bindDataIsNullTextView(view: View, apiResult: ApiResult<*>) {
        view.isVisible = apiResult.successOrNull() == null && apiResult !is ApiResult.Loading
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
    @BindingAdapter("isFavorite")
    fun bindSetFavoriteImage(imageButton: ImageButton, isFavorite: Boolean) {
        val imageResource = if (isFavorite) R.drawable.star else R.drawable.white_star
        Glide.with(imageButton.context).load(imageResource).into(imageButton)
    }

    @JvmStatic
    @BindingAdapter("search")
    fun bindDoAfterTextChange(editText: EditText, action: AfterTextChangedListener) {
        editText.doAfterTextChanged {
            if (!it.isNullOrBlank()) {
                action.onAfterTextChanged(it.toString())
            }
        }
    }
}

interface AfterTextChangedListener {
    fun onAfterTextChanged(text: String)
}