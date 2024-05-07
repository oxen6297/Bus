package sb.park.bus.feature.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import sb.park.bus.feature.main.databinding.ItemBusSearchBinding
import sb.park.bus.feature.main.extensions.singleClickListener
import sb.park.model.BusType
import sb.park.model.response.bus.BusSearchResponse

class SearchAdapter(private val clickListener: (BusSearchResponse) -> Unit) :
    ListAdapter<BusSearchResponse, SearchAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBusSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            itemView.singleClickListener {
                val position = bindingAdapterPosition.takeIf {
                    it != NO_POSITION
                } ?: return@singleClickListener
                clickListener(getItem(position))
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(val binding: ItemBusSearchBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BusSearchResponse) {
            binding.apply {
                bus = item
                type = BusType
                executePendingBindings()
            }
        }
    }

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