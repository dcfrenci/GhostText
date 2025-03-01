package com.dcfrenci.ghosttext.ui.screen

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Android
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberAsyncImagePainter
import com.dcfrenci.ghosttext.data.BottomNavigationItems
import com.dcfrenci.ghosttext.viewmodel.ViewModelAnalyze
import com.dcfrenci.ghosttext.viewmodel.ViewModelCreate

@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: String,
    viewModelCreate: ViewModelCreate,
    viewModelAnalyze: ViewModelAnalyze
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(BottomNavigationItems.CreateScreen.route) { CreateScreenUI(viewModelCreate) }
        composable(BottomNavigationItems.AnalyzeScreen.route) { AnalyzeScreenUI(viewModelAnalyze) }
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
    uri: Uri,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        modifier = modifier.size(200.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = contentDescription,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun ImageCardLost(
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        modifier = modifier
            .size(200.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Outlined.Image,
                contentDescription = "no_image_found",
                modifier = modifier.fillMaxSize(0.3f)
            )
        }
    }
}

@Composable
fun MessageBox(
    viewModelCreate: ViewModelCreate? = null,
    viewModelAnalyze: ViewModelAnalyze? = null,
    input: Boolean = false
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        border = BorderStroke(2.dp, Color.White),
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(0.8f)
    ) {
        if (input) {
            viewModelCreate?.let { viewModel ->
                TextField(
                    value = viewModel.message,
                    onValueChange = { updatedMessage ->
                        viewModel.updateMessage(updatedMessage)
                    }
                )
            }
        } else {
            viewModelAnalyze?.let { viewModel ->
                TextField(
                    value = viewModel.message,
                    onValueChange = {},
                    readOnly = true
                )
            }
        }
    }
}

@Composable
fun SpaceItemColumn(
    height: Int = 50
) {
    Spacer(
        modifier = Modifier.height(height.dp)
    )
}

@Composable
fun IconTextButton(
    arrangement: Arrangement.Horizontal = Arrangement.SpaceEvenly,
    imageVector: ImageVector = Icons.Outlined.Android,
    contentDescription: String = "icon_image",
    text: String = "Text",
    color: Color = Color.White,
    distance: Int = 10
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = arrangement,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = color
        )
        if (text != "") {
            Spacer(
                modifier = Modifier.width(distance.dp)
            )
            Text(
                text,
                color = color,
            )
        }
    }
}