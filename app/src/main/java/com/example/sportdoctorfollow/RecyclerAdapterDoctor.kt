package com.example.sportdoctorfollow

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class RecyclerAdapterDoctor(
    private val context: Context,
    private val imageExams: IntArray,
    private val exams: Array<String>,
    private val selectedItems: MutableSet<Int> = mutableSetOf<Int>()
) :
    RecyclerView.Adapter<RecyclerAdapterDoctor.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.layout_recycler_exams, viewGroup, false)
        val viewHolder: ViewHolder = ViewHolder(v)
        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.text_name.text = exams[position]
        viewHolder.image_item.setImageResource(imageExams[position])



        viewHolder.itemView.setOnClickListener {
            if (selectedItems.contains(position)) {
                selectedItems.remove(position)
                viewHolder.itemView.alpha = 1.0f
            } else {
                selectedItems.add(position)
                viewHolder.itemView.alpha = 0.5f
            }
            viewHolder.itemView.alpha = if (selectedItems.contains(position)) 0.5f else 1.0f
            //val examsRequest=selectedPositionsToNumber(selectedItems)


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

    fun getSelectedItems(): Set<Int> {
        return selectedItems.toSet()
    }
    /*
    }*/
}
