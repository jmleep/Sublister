package dev.jordanleeper.sublister.ui.listdetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.color.DynamicColors
import dev.jordanleeper.sublister.data.ParentListActivityViewModel

class ListDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DynamicColors.applyToActivitiesIfAvailable(application);

        val viewModel = ViewModelProvider(this).get(ParentListActivityViewModel::class.java)
        val id: Int = intent.extras?.getInt("parentListId")!!

        setContent {
            ListDetailView(id, viewModel)
        }
    }
}