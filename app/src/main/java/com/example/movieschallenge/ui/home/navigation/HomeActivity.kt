package com.example.movieschallenge.ui.home.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.movieschallenge.ui.theme.KueskiChagengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KueskiChagengeTheme {
                val navHostController = rememberNavController()
                JarsNavHost(navHostController = navHostController)
            }

        }
    }
}
