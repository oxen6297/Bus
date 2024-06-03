package sb.park.bus.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import sb.park.bus.presentation.databinding.ItemStationInfoBinding
import sb.park.model.BusType
import sb.park.model.response.bus.StationInfoResponse

class StationInfoAdapter :
    ListAdapter<StationInfoResponse, StationInfoAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemStationInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemStationInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: StationInfoResponse) {
            binding.apply {
                info = item
                type = BusType
                executePendingBindings()
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<StationInfoResponse>() {
            override fun areItemsTheSame(
                oldItem: StationInfoResponse,
                newItem: StationInfoResponse
            ): Boolean {
                return oldItem.busId == newItem.busId
            }

            override fun areContentsTheSame(
                oldItem: StationInfoResponse,
                newItem: StationInfoResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}