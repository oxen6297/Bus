package sb.park.bus.feature.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import sb.park.bus.feature.main.databinding.ItemBusFavoriteBinding
import sb.park.bus.feature.main.databinding.ItemStationFavoriteBinding
import sb.park.bus.feature.main.extensions.singleClickListener
import sb.park.model.response.bus.DeliveryData
import sb.park.model.response.bus.FavoriteEntity

class FavoriteAdapter(private val clickListener: (FavoriteEntity) -> Unit) :
    ListAdapter<FavoriteEntity, ViewHolder>(diffCallback) {

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            DeliveryData.Type.BUS.type -> {
                BusViewHolder(
                    ItemBusFavoriteBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else -> {
                StationViewHolder(
                    ItemStationFavoriteBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        when (getItem(position).type) {
            DeliveryData.Type.BUS.type -> {
                (holder as BusViewHolder).binding.apply {
                    busFavorite = getItem(position)
                    layoutFavorite.singleClickListener {
                        clickListener(busFavorite!!)
                    }
                }
            }

            else -> {
                (holder as StationViewHolder).binding.apply {
                    stationFavorite = getItem(position)
                    layoutFavorite.singleClickListener {
                        clickListener(stationFavorite!!)
                    }
                }
            }
        }
    }

    class BusViewHolder(val binding: ItemBusFavoriteBinding) : ViewHolder(binding.root)

    class StationViewHolder(val binding: ItemStationFavoriteBinding) : ViewHolder(binding.root)

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