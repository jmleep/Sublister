package dev.jordanleeper.mylists

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.google.android.material.color.DynamicColors
import dev.jordanleeper.mylists.data.MainActivityViewModel
import dev.jordanleeper.mylists.ui.main.MainActivityView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DynamicColors.applyToActivitiesIfAvailable(application);

        val viewModel = ViewModelProvider(this).get<MainActivityViewModel>()

        setContent {
            MainActivityView(viewModel)
        }
    }
}
