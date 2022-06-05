package dev.jordanleeper.mylists.ui.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.jordanleeper.mylists.R
import dev.jordanleeper.mylists.data.Item

class ItemAdapter
    (private val items: List<Item>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var nameTextView = view.findViewById<TextView>(R.id.item_name)

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout

        val itemView = inflater.inflate(R.layout.item_recycler_view_item, parent, false)
        // Return a new holder instance
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ItemAdapter.ViewHolder,
        position: Int
    ) {
        // Get the data model based on position
        val item: Item = items[position]
        // Set item views based on your views and data model
        val textView = holder.nameTextView
        textView.text = item.name
        
    }

    override fun getItemCount(): Int {
        return items.size
    }

}