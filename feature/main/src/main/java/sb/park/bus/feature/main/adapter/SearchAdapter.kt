package sb.park.bus.feature.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import sb.park.bus.data.response.BusIdResponse
import sb.park.bus.feature.main.databinding.ItemBusSearchBinding

class SearchAdapter : ListAdapter<BusIdResponse, SearchAdapter.ViewHolder>(diffCallback) {

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
        }
    }

    class ViewHolder(val binding: ItemBusSearchBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<BusIdResponse>() {
            override fun areItemsTheSame(
                oldItem: BusIdResponse,
                newItem: BusIdResponse
            ): Boolean {
                return oldItem.routeName == newItem.routeName
            }

            override fun areContentsTheSame(
                oldItem: BusIdResponse,
                newItem: BusIdResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}