package dev.jordanleeper.sublister.ui.task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.jordanleeper.sublister.R
import dev.jordanleeper.sublister.data.Item


@Composable
fun TaskRecyclerView(
    items: List<Item>,
    taskAdapter: TaskAdapter
) {
    AndroidView(factory = {
        val view = LayoutInflater.from(it)
            .inflate(R.layout.task_recycler_view, null, false)

        val recyclerView =
            view.findViewById<RecyclerView>(R.id.task_recycler_view)

        if (recyclerView.parent != null) {
            (recyclerView.parent as ViewGroup).removeView(recyclerView)
        }

        recyclerView.adapter = taskAdapter
        recyclerView.layoutManager = LinearLayoutManager(it)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )
        val touchCallback = TaskItemTouchCallback(it, taskAdapter)
        val itemTouchHelper = ItemTouchHelper(touchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        recyclerView
    }, update = {
        val adapter = it.adapter as TaskAdapter
        adapter.mItems = items
        adapter.notifyDataSetChanged()
    })
}