package dev.jordanleeper.mylists.ui.task

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class TaskItemTouchCallback(private val adapter: ItemTouchHelperContract?) :
    ItemTouchHelper.Callback() {

    override fun isItemViewSwipeEnabled(): Boolean {
        return false
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        return makeMovementFlags(dragFlags, 0)
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        println("test")
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState !== ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder is TaskAdapter.ViewHolder) {
                val myViewHolder: TaskAdapter.ViewHolder = viewHolder
                adapter?.onRowSelected(myViewHolder)
            }
        }

        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)

        if (viewHolder is TaskAdapter.ViewHolder) {
            val myViewHolder: TaskAdapter.ViewHolder = viewHolder
            adapter?.onRowClear(myViewHolder)
        }
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val fromPosition = viewHolder.adapterPosition
        val toPosition = target.adapterPosition
        adapter?.onRowMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
//        println("ON MOVE")
//        println(fromPosition)
//        println(toPosition)
//
//        Collections.swap(items, fromPosition, toPosition)
//        viewModel.updateItems(items)
//        recyclerView.adapter?.notifyItemMoved(fromPosition, toPosition)

//                mAdapter.notifyItemMoved(fromPosition, toPosition)
        return true

    }

    interface ItemTouchHelperContract {
        fun onRowMoved(fromPosition: Int, toPosition: Int)
        fun onRowSelected(myViewHolder: TaskAdapter.ViewHolder?)
        fun onRowClear(myViewHolder: TaskAdapter.ViewHolder?)
    }
}

