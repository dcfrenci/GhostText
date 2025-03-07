package com.dcfrenci.ghosttext.ui.screen

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dcfrenci.ghosttext.data.BottomNavigationItems
import com.dcfrenci.ghosttext.ui.theme.*
import com.dcfrenci.ghosttext.viewmodel.ViewModelAnalyze
import com.dcfrenci.ghosttext.viewmodel.ViewModelCreate
import com.dcfrenci.ghosttext.viewmodel.ViewModelSecurity

@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: String,
    viewModelCreate: ViewModelCreate,
    viewModelAnalyze: ViewModelAnalyze,
    viewModelSecurity: ViewModelSecurity
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(BottomNavigationItems.CreateScreen.route) { CreateScreenUI(viewModelCreate) }
        composable(BottomNavigationItems.AnalyzeScreen.route) { AnalyzeScreenUI(viewModelAnalyze) }
        composable(BottomNavigationItems.SecurityScreen.route) { SecurityScreenUI(viewModelSecurity) }
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
    androidx.compose.material3.NavigationBar(
        modifier = modifier.clip(RoundedCornerShape(topStart = CornerRadius, topEnd = CornerRadius)),
    ) {
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                label = { Text(text = item.title) },
                icon = { Icon(imageVector = item.icon, contentDescription = "") },
                onClick = { onItemClick(item) },
                selected = currentRoute == item.route,
                colors = NavigationBarItemColors(
                    unselectedIconColor    = NightSilver,
                    unselectedTextColor    = NightSilver,
                    disabledIconColor      = NightSilver,
                    disabledTextColor      = NightSilver,
                    selectedIconColor      = NightWhite,
                    selectedTextColor      = NightWhite,
                    selectedIndicatorColor = NightGreen
                )
            )
        }
    }
}