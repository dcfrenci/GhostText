package com.dcfrenci.ghosttext.ui.screen

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dcfrenci.ghosttext.data.BottomNavigationItems

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavigationItems.CreateScreen.route) {
        composable(BottomNavigationItems.CreateScreen.route) { CreateScreenUI() }
        composable(BottomNavigationItems.AnalyzeScreen.route) { AnalyzeScreenUI() }
    }
}

@Composable
fun NavigationBar(
    items: List<BottomNavigationItems>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavigationItems) -> Unit
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    NavigationBar (
        modifier = modifier,
        //TODO - Change color
        containerColor = Color.DarkGray
    ){
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                label   = { Text(text = item.title) },
                icon    = { Icon(imageVector = item.icon, contentDescription = "") },
                onClick = { onItemClick(item) },
                selected = currentRoute == item.route,
                // TODO - Choose colors
                colors = NavigationBarItemColors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    selectedIndicatorColor = Color.Green,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    disabledIconColor = Color.Green,
                    disabledTextColor = Color.Green,
                ),
            )
        }
    }
}