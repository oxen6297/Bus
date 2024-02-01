package sb.park.bus.feature.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import sb.park.bus.feature.main.databinding.ItemBusStationBinding
import sb.park.model.response.BusStationResponse

class StationAdapter : ListAdapter<BusStationResponse, StationAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBusStationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.station = getItem(position)
    }

    class ViewHolder(val binding: ItemBusStationBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<BusStationResponse>() {
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
}