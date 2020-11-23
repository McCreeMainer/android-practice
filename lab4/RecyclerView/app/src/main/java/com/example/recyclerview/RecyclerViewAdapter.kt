package com.example.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.databinding.RecycleViewItemBinding
import kotlinx.android.synthetic.main.recycle_view_item.view.*
import name.ank.lab4.BibDatabase
import name.ank.lab4.Keys
import name.ank.lab4.Types.*
import java.io.InputStream
import java.io.InputStreamReader

class RecyclerViewAdapter(inputStream: InputStream) : RecyclerView.Adapter<ViewHolder>() {

    private lateinit var database: BibDatabase

    init {
        InputStreamReader(inputStream).use {
            database = BibDatabase(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecycleViewItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int = database.size()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = database.getEntry(position)
        val type = entry.type
        val color = when (type) {
            ARTICLE -> R.color.dark_gray
            BOOK -> R.color.gray
            BOOKLET -> R.color.crimson
            CONFERENCE -> R.color.pink
            INBOOK -> R.color.violet
            INCOLLECTION -> R.color.clay
            INPROCEEDINGS -> R.color.blue
            MANUAL -> R.color.dracula
            MASTERSTHESIS -> R.color.purple_200
            MISC -> R.color.black
            PHDTHESIS -> R.color.red
            PROCEEDINGS -> R.color.light_green
            TECHREPORT -> R.color.purple_500
            UNPUBLISHED -> R.color.orange
            else -> R.color.teal_200
        }
        val ctx = holder.view.rootView.context

        holder.view.layout.setBackgroundColor(ContextCompat.getColor(ctx, color))
        holder.view.format.text = type.name
        holder.view.title.text = entry.getField(Keys.TITLE)
        holder.view.author.text = entry.getField(Keys.AUTHOR)
    }
}