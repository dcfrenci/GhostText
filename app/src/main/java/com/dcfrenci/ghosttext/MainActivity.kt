package com.dcfrenci.ghosttext

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dcfrenci.ghosttext.ui.screen.NavigationBar
import com.dcfrenci.ghosttext.ui.screen.NavigationGraph
import com.dcfrenci.ghosttext.ui.theme.GhostTextTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GhostTextTheme {
                val navController: NavHostController = rememberNavController()
                var buttonVisible by remember { mutableStateOf(true)}
                Scaffold(
                    bottomBar = {
                        if (buttonVisible) {
                            NavigationBar(
                                navController = navController,
                                state = buttonVisible,
                                modifier = Modifier
                            )
                        }
                    }
                ){
                    paddingValues ->
                    Box(
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        NavigationGraph(navController = navController) {
                            isVisible ->
                                buttonVisible = isVisible
                        }
                    }

                }
            }
        }
    }
}

