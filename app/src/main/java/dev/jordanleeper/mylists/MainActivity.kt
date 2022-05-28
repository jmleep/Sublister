package dev.jordanleeper.mylists

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.color.DynamicColors
import dev.jordanleeper.mylists.data.ListViewModel
import dev.jordanleeper.mylists.ui.MainActivityView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DynamicColors.applyToActivitiesIfAvailable(application);

        val viewModel = ViewModelProvider(this).get(ListViewModel::class.java)

        setContent {
            MainActivityView(viewModel)
        }
    }
}
