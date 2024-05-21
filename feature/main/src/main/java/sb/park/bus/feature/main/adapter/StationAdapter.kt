package sb.park.bus.feature.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import sb.park.bus.feature.main.databinding.ItemBusStationBinding
import sb.park.bus.feature.main.extensions.singleClickListener
import sb.park.model.response.bus.BusStationResponse
import sb.park.model.response.bus.LocationModel

class StationAdapter(private val clickListener: (BusStationResponse) -> Unit) :
    ListAdapter<BusStationResponse, StationAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBusStationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            itemView.singleClickListener {
                val position = bindingAdapterPosition.takeIf {
                    it != RecyclerView.NO_POSITION
                } ?: return@singleClickListener
                clickListener(getItem(position))
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(val binding: ItemBusStationBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BusStationResponse) {
            binding.apply {
                station = item
                executePendingBindings()
            }
        }
    }

    fun updateLocation(data: LocationModel, setScroll: (Int) -> Unit) {
        data.position?.let {
            getItem(it).isLocated = true
            getItem(it).arriveTime = data.arriveTime
            notifyItemChanged(it)
            setScroll(it)
        }
    }

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