package dev.jordanleeper.mylists.ui.task

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.jordanleeper.mylists.R
import dev.jordanleeper.mylists.data.Item

class TaskAdapter
    (private val items: List<Item>) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nameTextView = view.findViewById<TextView>(R.id.task_name)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout

        val itemView = inflater.inflate(R.layout.task_recycler_view_item, parent, false)
        // Return a new holder instance
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: TaskAdapter.ViewHolder,
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