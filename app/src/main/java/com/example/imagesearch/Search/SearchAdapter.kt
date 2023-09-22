package com.example.imagesearch.Search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearch.MainActivity
import com.example.imagesearch.databinding.SearchItemBinding
import com.example.imagesearch.model.SearchImage
import com.example.imagesearch.util.Utils.getDateFromTimestampWithFormat


class SearchAdapter(private val mContext: Context) : RecyclerView.Adapter<SearchAdapter.ItemViewHolder>() {

    var items = ArrayList<SearchImage>()


    fun clearItem() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = items[position]

        Glide.with(mContext)
            .load(currentItem.url)
            .into(holder.thumb_image)

        holder.iv_fav.visibility = if (currentItem.isLike) View.VISIBLE else View.INVISIBLE
        holder.tv_title.text = currentItem.title
        holder.tv_time.text = getDateFromTimestampWithFormat(
            currentItem.dateTime,
            "yyyy-MM-dd'T'HH:mm:ss.SSS+09:00",
            "yyyy-MM-dd HH:mm:ss"
        )
    }

    override fun getItemCount() = items.size

    inner class ItemViewHolder(binding: SearchItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        var thumb_image: ImageView = binding.thumbImage
        var iv_fav: ImageView = binding.ivFav
        var tv_title: TextView = binding.title
        var tv_time: TextView = binding.tvTime
        var cl_thumb_image: ConstraintLayout = binding.thumbnail

        init {
            iv_fav.visibility = View.GONE
            thumb_image.setOnClickListener(this)
            cl_thumb_image.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION } ?: return
            val item = items[position]

            item.isLike = !item.isLike

            if (item.isLike) {
                (mContext as MainActivity).addLikedItem(item)
            } else {
                (mContext as MainActivity).removeLikedItem(item)
            }

            notifyItemChanged(position)
        }
    }
}