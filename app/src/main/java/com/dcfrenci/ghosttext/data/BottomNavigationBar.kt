package com.dcfrenci.ghosttext.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Construction
import androidx.compose.material.icons.outlined.ImageSearch
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomNavigationItems(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object CreateScreen: BottomNavigationItems(
        route = "create_screen",
        title = "CreateScreen",
        icon = Icons.Outlined.Construction
    )

    data object AnalyzeScreen: BottomNavigationItems(
        route = "analyze_screen",
        title = "AnalyzeScreen",
        icon = Icons.Outlined.ImageSearch
    )
}