package com.dcfrenci.ghosttext.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Construction
import androidx.compose.material.icons.outlined.ImageSearch
import androidx.compose.material.icons.outlined.Key
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomNavigationItems(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object CreateScreen: BottomNavigationItems(
        route = "create_screen",
        title = "Create",
        icon = Icons.Outlined.Construction
    )

    data object AnalyzeScreen: BottomNavigationItems(
        route = "analyze_screen",
        title = "Analyze",
        icon = Icons.Outlined.ImageSearch
    )

    data object SecurityScreen: BottomNavigationItems(
        route = "security_screen",
        title = "Security",
        icon = Icons.Outlined.Key
    )
}