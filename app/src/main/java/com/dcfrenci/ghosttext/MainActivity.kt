package com.dcfrenci.ghosttext

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dcfrenci.ghosttext.data.BottomNavigationItems
import com.dcfrenci.ghosttext.ui.screen.NavigationBar
import com.dcfrenci.ghosttext.ui.screen.NavigationGraph
import com.dcfrenci.ghosttext.ui.theme.GhostTextTheme
import com.dcfrenci.ghosttext.viewmodel.ViewModelAnalyze

class MainActivity : ComponentActivity() {

    private val viewModelAnalyze by viewModels<ViewModelAnalyze>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startDestination = if (intent?.action == Intent.ACTION_MAIN) {
            BottomNavigationItems.CreateScreen.route
        } else {
            BottomNavigationItems.AnalyzeScreen.route
        }
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
                    NavigationGraph(navController = navController, startDestination)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
        } else {
            intent?.getParcelableExtra(Intent.EXTRA_STREAM)
        }
        viewModelAnalyze.updateUri(uri)
    }
}

