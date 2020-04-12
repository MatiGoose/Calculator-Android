package com.example.calculatorandroid

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.net.URI
import android.provider.MediaStore
import java.time.format.ResolverStyle
import kotlin.coroutines.coroutineContext


class rvAdapter(var items: ArrayList<PostCard>, val callback: Callback) : RecyclerView.Adapter<rvAdapter.MainHolder>() {
    private val READ_REQUEST_CODE = 42
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


            val file = File(item.imageUri)
            if(file.exists())
            {

                val bitmap = BitmapFactory.decodeFile(file.absolutePath)

                image.setImageBitmap(bitmap)

            }

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