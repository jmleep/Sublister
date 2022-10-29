package dev.jordanleeper.sublister.ui.task

import android.content.res.ColorStateList
import android.graphics.Paint
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.CheckBox
import android.widget.EditText
import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.toArgb
import androidx.recyclerview.widget.RecyclerView
import dev.jordanleeper.sublister.R
import dev.jordanleeper.sublister.data.Item
import dev.jordanleeper.sublister.data.ParentListActivityViewModel
import dev.jordanleeper.sublister.data.Sublist
import dev.jordanleeper.sublister.ui.theme.getColor
import java.util.*


class TaskAdapter
    (
    private var items: List<Item>,
    private val subList: Sublist,
    private val viewModel: ParentListActivityViewModel,
    private val colorScheme: ColorScheme
) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>(), TaskItemTouchCallback.ItemTouchHelperContract {

    var mItems = items

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var row = view
        var taskEditText = view.findViewById<EditText>(R.id.task_name)
        var checkBoxView = view.findViewById<CheckBox>(R.id.task_is_complete)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val itemView = inflater.inflate(R.layout.task_recycler_view_item, parent, false)

        // Return a new holder instance
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: TaskAdapter.ViewHolder,
        position: Int
    ) {
        holder.itemView.setBackgroundColor(colorScheme.surface.toArgb())
        // Get the data model based on position
        val item: Item = mItems[position]
        // Set item views based on your views and data model
        holder.taskEditText.setText(item.name)
        holder.taskEditText.setTextColor(colorScheme.onSurface.toArgb())
        holder.taskEditText.imeOptions = EditorInfo.IME_ACTION_DONE;
        holder.taskEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        holder.taskEditText.setOnEditorActionListener { v, actionId, event ->
            val newTaskText = v.text.toString()
            if (actionId == EditorInfo.IME_ACTION_DONE && newTaskText.isNotBlank()) {
                val updatedItem = item.copy(name = newTaskText)
                viewModel.updateItem(updatedItem)
                v.clearFocus()
                true
            } else {
                v.error = "Please set a value"
                false
            }
        }

        if (item.isComplete) {
            holder.taskEditText.paintFlags =
                holder.taskEditText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.taskEditText.paintFlags = 0
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
        myViewHolder?.row?.setBackgroundColor(colorScheme.surfaceVariant.toArgb());
    }

    override fun onRowClear(myViewHolder: ViewHolder?) {
        myViewHolder?.row?.setBackgroundColor(colorScheme.surface.toArgb());

        val updatedItems = mItems.mapIndexed { index, item ->
            item.copy(position = index)
        }

        viewModel.updateItems(updatedItems)
    }

    override fun onRowDelete(viewHolder: ViewHolder) {
        viewModel.deleteItem(mItems[viewHolder.adapterPosition])
    }
}