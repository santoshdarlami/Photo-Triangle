package com.santosh.phototraingle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ImageRvAdapter(private val imageItems: Array<ImageItem?>?) : RecyclerView.Adapter<ImageRvAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_iv)
        val textView: TextView = itemView.findViewById(R.id.index_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageItem = imageItems!![position]
        holder.imageView.setImageURI(imageItem?.imageUri)
        holder.textView.text = imageItem?.text
    }

    override fun getItemCount(): Int {
        return imageItems?.size ?: 0
    }
}