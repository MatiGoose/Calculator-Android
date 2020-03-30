package com.example.calculatorandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class rvAdapter(var items: ArrayList<PostCard>, val callback: Callback) : RecyclerView.Adapter<rvAdapter.MainHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = MainHolder(LayoutInflater.from(parent.context).inflate(R.layout.post, parent, false))
    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(items[position])
    }
    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var image = itemView.findViewById<ImageView>(R.id.imagePost)
        private var title = itemView.findViewById<TextView>(R.id.titlePost)
        private var description = itemView.findViewById<TextView>(R.id.descriptionPost)
        fun bind(item: PostCard) {
            //image.setImageResource(item.imageUri)
            //image = item.imageUri
            image.setImageURI(item.imageUri)
            title.setText(item.title)
            description.setText(item.description)
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(items[adapterPosition], adapterPosition)
            }
        }
    }
    interface Callback {
        fun onItemClicked(item: PostCard, position: Int)
    }
}