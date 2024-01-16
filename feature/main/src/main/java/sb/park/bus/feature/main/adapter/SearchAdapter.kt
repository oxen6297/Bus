package sb.park.bus.feature.main.adapter

import android.graphics.Outline
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import sb.park.bus.data.BusType
import sb.park.bus.data.response.BusSearchResponse
import sb.park.bus.feature.main.R
import sb.park.bus.feature.main.databinding.ItemBusSearchBinding

class SearchAdapter : ListAdapter<BusSearchResponse, SearchAdapter.ViewHolder>(diffCallback) {

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
            val busTypeColor = when (bus?.routeType) {
                BusType.PORT.typeName -> R.color.sky_blue
                BusType.COUNTRY.typeName -> R.color.yellow
                BusType.GANSEON.typeName -> R.color.blue
                BusType.JISEON.typeName -> R.color.green
                BusType.CYCLE.typeName -> R.color.red
                BusType.WIDE.typeName -> R.color.purple
                BusType.INCHEON.typeName -> R.color.mint
                BusType.GYUNGGI.typeName -> R.color.black
                BusType.COMMON.typeName -> R.color.dark_gray
                else -> R.color.black
            }

            textBusType.apply {
                setBackgroundColor(root.context.getColor(busTypeColor))
                outlineProvider = object : ViewOutlineProvider() {
                    override fun getOutline(view: View?, outline: Outline?) {
                        view?.clipToOutline = true
                        outline?.setRoundRect(0, 0, view!!.width, view.height, 10f)
                    }
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