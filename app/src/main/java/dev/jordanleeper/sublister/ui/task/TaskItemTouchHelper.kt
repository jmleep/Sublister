package dev.jordanleeper.sublister.ui.task

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import dev.jordanleeper.sublister.R


class TaskItemTouchCallback(
    private val context: Context,
    private val adapter: ItemTouchHelperContract?
) : ItemTouchHelper.Callback() {
    private var icon: Drawable? = null
    private var background: ColorDrawable? = null

    init {
        icon = ContextCompat.getDrawable(
            context,
            R.drawable.ic_baseline_delete_24
        );
        background = ColorDrawable(Color.RED)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(
            c, recyclerView, viewHolder, dX,
            dY, actionState, isCurrentlyActive
        )
        val itemView: View = viewHolder.itemView
        val backgroundCornerOffset = 20

        val iconMargin = (itemView.height - icon!!.intrinsicHeight) / 2
        val iconTop = itemView.top + (itemView.height - icon!!.intrinsicHeight) / 2
        val iconBottom = iconTop + icon!!.intrinsicHeight

        if (dX > 0) { // Swiping to the right
            val iconLeft = itemView.left + iconMargin + icon!!.intrinsicWidth
            val iconRight = itemView.left + iconMargin
            icon!!.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            background!!.setBounds(
                itemView.left, itemView.top,
                itemView.left + dX.toInt() + backgroundCornerOffset,
                itemView.bottom
            )
        } else if (dX < 0) { // Swiping to the left
            val iconLeft = itemView.right - iconMargin - icon!!.intrinsicWidth
            val iconRight = itemView.right - iconMargin
            icon!!.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            background!!.setBounds(
                itemView.right + dX.toInt() - backgroundCornerOffset,
                itemView.top, itemView.right, itemView.bottom
            )
        } else { // view is unSwiped
            background!!.setBounds(0, 0, 0, 0)
        }

        background!!.draw(c)
        icon!!.draw(c)
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN

        return makeMovementFlags(dragFlags, ItemTouchHelper.LEFT)
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        println("on swip $direction")
        adapter?.onRowDelete(viewHolder as TaskAdapter.ViewHolder)
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
        adapter?.onRowMoved(fromPosition, toPosition);

        return true
    }

    interface ItemTouchHelperContract {
        fun onRowMoved(fromPosition: Int, toPosition: Int)
        fun onRowSelected(myViewHolder: TaskAdapter.ViewHolder?)
        fun onRowClear(myViewHolder: TaskAdapter.ViewHolder?)
        fun onRowDelete(viewHolder: TaskAdapter.ViewHolder)
    }
}

