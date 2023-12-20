package sb.park.bus.feature.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import sb.park.bus.data.response.BusStationResponse
import sb.park.bus.feature.main.databinding.ItemBusSearchBinding

class SearchAdapter : ListAdapter<BusStationResponse, SearchAdapter.ViewHolder>(DiffCallback()) {

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
            textStartStation.text = getItem(0).stationNm
            textEndStation.text = getItem(itemCount - 1).stationNm
        }
    }

    class ViewHolder(val binding: ItemBusSearchBinding) : RecyclerView.ViewHolder(binding.root)

    class DiffCallback : DiffUtil.ItemCallback<BusStationResponse>() {
        override fun areItemsTheSame(
            oldItem: BusStationResponse,
            newItem: BusStationResponse
        ): Boolean {
            return oldItem.busRouteNm == newItem.busRouteNm
        }

        override fun areContentsTheSame(
            oldItem: BusStationResponse,
            newItem: BusStationResponse
        ): Boolean {
            return oldItem == newItem
        }
    }
}