package com.dcfrenci.ghosttext

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dcfrenci.ghosttext.data.BottomNavigationItems
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
                Scaffold(
                    bottomBar = {
                        NavigationBar(
                            items = listOf(
                                BottomNavigationItems.CreateScreen,
                                BottomNavigationItems.AnalyzeScreen
                            ),
                            navController = navController,
                            modifier = Modifier,
                            onItemClick = {
                                navController.navigate(it.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                ){
                    NavigationGraph(navController = navController)
                }
            }
        }
    }
}

