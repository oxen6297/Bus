package sb.park.bus.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import sb.park.bus.presentation.databinding.ItemBusFavoriteBinding
import sb.park.bus.presentation.databinding.ItemStationFavoriteBinding
import sb.park.bus.presentation.extensions.singleClickListener
import sb.park.model.response.bus.ArgumentData
import sb.park.model.response.bus.FavoriteEntity

class FavoriteAdapter(
    private val clickListener: (FavoriteEntity) -> Unit
) : ListAdapter<FavoriteEntity, FavoriteAdapter.MultiViewHolder>(diffCallback) {

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultiViewHolder {
        return when (viewType) {
            ArgumentData.Type.BUS.type -> {
                MultiViewHolder.BusViewHolder(
                    ItemBusFavoriteBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                ).apply(::setClickListener)
            }

            ArgumentData.Type.STATION.type -> {
                MultiViewHolder.StationViewHolder(
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

    override fun onBindViewHolder(holder: MultiViewHolder, position: Int) {
        holder.bind(getItem(position))
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

    sealed class MultiViewHolder(binding: ViewDataBinding) : ViewHolder(binding.root) {

        abstract fun bind(item: FavoriteEntity)

        class BusViewHolder(private val binding: ItemBusFavoriteBinding) :
            MultiViewHolder(binding) {
            override fun bind(item: FavoriteEntity) {
                binding.apply {
                    busFavorite = item
                    executePendingBindings()
                }
            }
        }

        class StationViewHolder(private val binding: ItemStationFavoriteBinding) :
            MultiViewHolder(binding) {
            override fun bind(item: FavoriteEntity) {
                binding.apply {
                    stationFavorite = item
                    executePendingBindings()
                }
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<FavoriteEntity>() {
            override fun areItemsTheSame(
                oldItem: FavoriteEntity,
                newItem: FavoriteEntity
            ): Boolean = oldItem.id == newItem.id


            override fun areContentsTheSame(
                oldItem: FavoriteEntity,
                newItem: FavoriteEntity
            ): Boolean = oldItem == newItem
        }
    }
}