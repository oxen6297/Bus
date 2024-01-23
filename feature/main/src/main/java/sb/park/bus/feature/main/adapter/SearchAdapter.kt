package sb.park.bus.feature.main.adapter

import android.graphics.Outline
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import sb.park.bus.data.BusType
import sb.park.bus.data.response.BusSearchResponse
import sb.park.bus.feature.main.databinding.ItemBusSearchBinding

class SearchAdapter(private val clickListener: (String) -> Unit) :
    ListAdapter<BusSearchResponse, SearchAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBusSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            bus = getItem(position)
            val busTypeColor = BusType.entries.find {
                it.typeName == bus?.routeType
            }?.color!!


            textBusType.apply {
                setBackgroundColor(root.context.getColor(busTypeColor))
                outlineProvider = object : ViewOutlineProvider() {
                    override fun getOutline(view: View?, outline: Outline?) {
                        view?.clipToOutline = true
                        outline?.setRoundRect(0, 0, view!!.width, view.height, 10f)
                    }
                }
            }

            layoutSearch.setOnClickListener {
                bus?.let {
                    clickListener(it.busId)
                }
            }
        }
    }

    class ViewHolder(val binding: ItemBusSearchBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<BusSearchResponse>() {
            override fun areItemsTheSame(
                oldItem: BusSearchResponse,
                newItem: BusSearchResponse
            ): Boolean {
                return oldItem.busRouteNm == newItem.busRouteNm
            }

            override fun areContentsTheSame(
                oldItem: BusSearchResponse,
                newItem: BusSearchResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}