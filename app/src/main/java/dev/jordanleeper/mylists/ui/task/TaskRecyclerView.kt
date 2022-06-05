package dev.jordanleeper.mylists.ui.task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.jordanleeper.mylists.R
import dev.jordanleeper.mylists.data.Item

@Composable
fun TaskRecyclerView(items: List<Item>) {
    AndroidView(factory = {
        val view = LayoutInflater.from(it)
            .inflate(R.layout.task_recycler_view, null, false)

        val recyclerView =
            view.findViewById<RecyclerView>(R.id.task_recycler_view)

        if (recyclerView.parent != null) {
            (recyclerView.parent as ViewGroup).removeView(recyclerView)
        }

        recyclerView.adapter = TaskAdapter(items)
        recyclerView.layoutManager = LinearLayoutManager(it)
        recyclerView
    }, update = {
        it.adapter = TaskAdapter(items)
    })
}