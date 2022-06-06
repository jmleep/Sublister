package dev.jordanleeper.mylists.ui.task

import android.content.res.ColorStateList
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.recyclerview.widget.RecyclerView
import dev.jordanleeper.mylists.R
import dev.jordanleeper.mylists.data.Item
import dev.jordanleeper.mylists.data.ParentListActivityViewModel
import dev.jordanleeper.mylists.data.SubList
import dev.jordanleeper.mylists.ui.theme.getColor
import java.util.*


class TaskAdapter
    (
    private var items: List<Item>,
    private val subList: SubList,
    private val viewModel: ParentListActivityViewModel
) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>(), TaskItemTouchCallback.ItemTouchHelperContract {

    var mItems = items

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var row = view
        var nameTextView = view.findViewById<TextView>(R.id.task_name)
        var checkBoxView = view.findViewById<CheckBox>(R.id.task_is_complete)
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

        if (item.isComplete) {
            holder.nameTextView.paintFlags =
                holder.nameTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.nameTextView.paintFlags = 0
        }

        holder.checkBoxView.buttonTintList =
            ColorStateList.valueOf(subList.color.getColor().toArgb())
        holder.checkBoxView.setOnCheckedChangeListener(null)
        holder.checkBoxView.isChecked = item.isComplete
        holder.checkBoxView.setOnCheckedChangeListener { buttonView, isChecked ->
            val updatedItem = item.copy(isComplete = isChecked)
            viewModel.updateItem(updatedItem)
        }
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

        val updatedItems = mItems.mapIndexed { index, item ->
            item.copy(position = index)
        }

        viewModel.updateItems(updatedItems)
    }

    override fun onRowDelete(viewHolder: ViewHolder) {
        viewModel.deleteItem(mItems[viewHolder.adapterPosition])
    }
}