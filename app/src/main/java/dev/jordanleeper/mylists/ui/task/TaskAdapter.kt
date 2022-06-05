package dev.jordanleeper.mylists.ui.task

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.recyclerview.widget.RecyclerView
import dev.jordanleeper.mylists.R
import dev.jordanleeper.mylists.data.Item
import dev.jordanleeper.mylists.data.ParentListActivityViewModel
import java.util.*


class TaskAdapter
    (private val items: List<Item>, private val viewModel: ParentListActivityViewModel) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>(), TaskItemTouchCallback.ItemTouchHelperContract {

    var mItems = items

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var row = view
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
        val item: Item = mItems[position]
        // Set item views based on your views and data model
        holder.nameTextView.text = item.name
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(mItems, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(mItems, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onRowSelected(myViewHolder: ViewHolder?) {
        myViewHolder?.row?.setBackgroundColor(Color.Gray.toArgb());
    }

    override fun onRowClear(myViewHolder: ViewHolder?) {
        myViewHolder?.row?.setBackgroundColor(Color.White.toArgb());

        viewModel.updateItems(mItems)
    }
}