package com.example.sportdoctorfollow

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


class RecyclerAdapterUser(
    private val context: Context,
    private val imageExams: IntArray,
    private val exams: Array<String>,
    private val selectedItem: (Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerAdapterUser.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.layout_recycler_user_main, viewGroup, false)
        val viewHolder: ViewHolder = ViewHolder(v)
        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.text_name.text = exams[position]
        viewHolder.image_item.setImageResource(imageExams[position])


       viewHolder.itemView.setOnClickListener {
           selectedItem(position)
           //Toast.makeText(context,selectedItem.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return exams.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image_item: ImageView =
            itemView.findViewById(R.id.item_image)
        var text_name: TextView =
            itemView.findViewById(R.id.item_name)
    }

    /*fun getSelectedItem(): Int {
        return selectedItem
    }*/
    /*
    }*/
}
