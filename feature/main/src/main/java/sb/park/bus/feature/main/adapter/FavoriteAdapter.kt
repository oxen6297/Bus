package sb.park.bus.feature.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import sb.park.bus.feature.main.databinding.ItemBusFavoriteBinding
import sb.park.bus.feature.main.databinding.ItemStationFavoriteBinding
import sb.park.model.response.bus.FavoriteEntity

class FavoriteAdapter(private val clickListener: (FavoriteEntity) -> Unit) :
    ListAdapter<FavoriteEntity, ViewHolder>(diffCallback) {

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            FavoriteEntity.Type.BUS.type -> {
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
            FavoriteEntity.Type.BUS.type -> {
                (holder as BusViewHolder).binding.apply {
                    favorite = getItem(position)
                    layoutFavorite.setOnClickListener {
                        clickListener(getItem(position))
                    }
                }
            }

            else -> {
                (holder as StationViewHolder).binding.apply {
                    favorite = getItem(position)
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
                return oldItem.busId == newItem.busId
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