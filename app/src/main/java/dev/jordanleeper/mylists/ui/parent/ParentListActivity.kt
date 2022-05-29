package dev.jordanleeper.mylists.ui.parent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.color.DynamicColors
import dev.jordanleeper.mylists.data.ParentListActivityViewModel

class ParentListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DynamicColors.applyToActivitiesIfAvailable(application);

        val viewModel = ViewModelProvider(this).get(ParentListActivityViewModel::class.java)
        val id: Int = intent.extras?.getInt("parentListId")!!

        setContent {
            ParentListActivityView(id, viewModel)
        }
    }
}