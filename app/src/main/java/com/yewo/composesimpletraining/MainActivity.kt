package com.yewo.composesimpletraining

import android.os.Bundle
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.yewo.composesimpletraining.ui.FoodListViewModel
import com.yewo.composesimpletraining.ui.theme.ComposeSImpleTrainingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeSImpleTrainingTheme {
                // A surface container using the 'background' color from the theme
                val viewModel = FoodListViewModel()
                FoodListScreen(viewModel)
            }
        }
    }
}


