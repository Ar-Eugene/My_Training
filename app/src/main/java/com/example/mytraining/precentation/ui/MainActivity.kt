package com.example.mytraining.precentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mytraining.precentation.navigation.NavigationBarExample
import com.example.core.ui.theme.MyTrainingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyTrainingTheme {
                NavigationBarExample()
            }
        }
    }
}
