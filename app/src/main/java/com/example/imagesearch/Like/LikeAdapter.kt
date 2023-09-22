package com.example.imagesearch.Like

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.example.imagesearch.Search.SearchAdapter
import com.example.imagesearch.databinding.SearchItemBinding
import com.example.imagesearch.model.SearchImage
import com.example.imagesearch.util.Utils.getDateFromTimestampWithFormat

class LikeAdapter(var mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var items = mutableListOf<SearchImage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Glide.with(mContext)
            .load(items[position].url)
            .into((holder as ItemViewHolder).thumb_image)
        holder.tv_title.text = items[position].title
        holder.iv_fav.visibility = View.GONE
        holder.tv_time.text =
            getDateFromTimestampWithFormat(
                items[position].dateTime,
                "yyyy-MM-dd'T'HH:mm:ss.SSS+09:00",
                "yyyy-MM-dd HH:mm:ss"
            )
    }


    override fun getItemCount() = items.size

    inner class ItemViewHolder(binding: SearchItemBinding) : RecyclerView.ViewHolder(binding.root) {

        var thumb_image: ImageView = binding.thumbImage
        var iv_fav: ImageView = binding.ivFav
        var tv_title: TextView = binding.title
        var tv_time: TextView = binding.tvTime
        var cl_thumb_image: ConstraintLayout = binding.thumbnail

        init {
            iv_fav.visibility = View.GONE
            cl_thumb_image.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    items.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }

    }

}
