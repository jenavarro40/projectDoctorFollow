package com.example.sportdoctorfollow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapterRoundButton(private val images: List<Int>, private val selectedItem: (Int) -> Unit) :
    RecyclerView.Adapter<RecyclerAdapterRoundButton.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.imageItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_recicler_round_button, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = images.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.image.setImageResource(images[position])

        viewHolder.itemView.setOnClickListener {
            selectedItem(position)
            //Toast.makeText(context,selectedItem.toString(),Toast.LENGTH_SHORT).show()
        }
    }
}
