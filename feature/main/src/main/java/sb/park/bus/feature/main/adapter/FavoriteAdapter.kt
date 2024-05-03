package sb.park.bus.feature.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import sb.park.bus.feature.main.databinding.ItemBusFavoriteBinding
import sb.park.bus.feature.main.databinding.ItemStationFavoriteBinding
import sb.park.bus.feature.main.extensions.singleClickListener
import sb.park.model.response.bus.ArgumentData
import sb.park.model.response.bus.FavoriteEntity

class FavoriteAdapter(private val clickListener: (FavoriteEntity) -> Unit) :
    ListAdapter<FavoriteEntity, ViewHolder>(diffCallback) {

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ArgumentData.Type.BUS.type -> {
                BusViewHolder(
                    ItemBusFavoriteBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                ).apply(::setClickListener)
            }

            ArgumentData.Type.STATION.type -> {
                StationViewHolder(
                    ItemStationFavoriteBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                ).apply(::setClickListener)
            }

            else -> throw IllegalArgumentException("unDefined type")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItem(position).type) {
            ArgumentData.Type.BUS.type -> (holder as BusViewHolder).bind(getItem(position))
            ArgumentData.Type.STATION.type -> (holder as StationViewHolder).bind(getItem(position))
        }
    }

    class BusViewHolder(val binding: ItemBusFavoriteBinding) : ViewHolder(binding.root) {
        fun bind(item: FavoriteEntity) {
            binding.apply {
                busFavorite = item
                executePendingBindings()
            }
        }
    }

    class StationViewHolder(val binding: ItemStationFavoriteBinding) : ViewHolder(binding.root) {
        fun bind(item: FavoriteEntity) {
            binding.apply {
                stationFavorite = item
                executePendingBindings()
            }
        }
    }

    private fun setClickListener(viewHolder: ViewHolder) {
        viewHolder.apply {
            itemView.singleClickListener {
                val position = bindingAdapterPosition.takeIf {
                    it != RecyclerView.NO_POSITION
                } ?: return@singleClickListener
                clickListener(getItem(position))
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<FavoriteEntity>() {
            override fun areItemsTheSame(
                oldItem: FavoriteEntity,
                newItem: FavoriteEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: FavoriteEntity,
                newItem: FavoriteEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}