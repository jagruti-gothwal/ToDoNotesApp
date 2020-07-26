package com.jagruti.todonotesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jagruti.todonotesapp.R
import com.jagruti.todonotesapp.clicklistner.ItemClickListener
import com.jagruti.todonotesapp.db.Notes

class NotesAdapter(val list : List<Notes>, val itemClickListener: ItemClickListener) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapter.ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_adapter_layout,parent,false)
    return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val notes = list[position]
            val title = notes.title
            val description =notes.description
            holder.textViewTitle.text=title
            holder.textViewDescription.text=description
            holder.checkboxMarkStatus.isChecked=notes.isTaskCompleted
            Glide.with(holder.itemView).load(notes.imgPath).into(holder.imageView)
            holder.itemView.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: View?) {
                    itemClickListener.onClick(notes)
                }

            })
        holder.checkboxMarkStatus.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                    notes.isTaskCompleted=isChecked
                    itemClickListener.onUpdate(notes)
            }

        })
    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val textViewTitle : TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewDescription : TextView = itemView.findViewById(R.id.textViewDescription)
        val checkboxMarkStatus : CheckBox = itemView.findViewById(R.id.checkboxMarkStatus)
        val imageView : ImageView = itemView.findViewById(R.id.imageView)
    }
}