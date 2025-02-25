package com.dcfrenci.ghosttext.ui.screen

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dcfrenci.ghosttext.data.BottomNavigationItems

@Composable
fun NavigationGraph(navController: NavHostController, startDestination: String) {
    NavHost(navController = navController, startDestination = startDestination) {
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
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    NavigationBar(
        modifier = modifier,
        //TODO - Change color
        containerColor = Color.DarkGray
    ) {
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                label = { Text(text = item.title) },
                icon = { Icon(imageVector = item.icon, contentDescription = "") },
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

@Composable
fun ImageCard(
    painter: Painter,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Box(
            modifier = modifier.height(200.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = contentDescription, contentScale = ContentScale.Crop
            )
        }
    }
}