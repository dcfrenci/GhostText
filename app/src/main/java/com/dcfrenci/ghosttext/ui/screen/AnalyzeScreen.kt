package com.dcfrenci.ghosttext.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dcfrenci.ghosttext.viewmodel.ViewModelAnalyze

@Composable
fun AnalyzeScreenUI(viewModelAnalyze: ViewModelAnalyze) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SpaceItemColumn()
        //Image
        if (viewModelAnalyze.uri != null) {
            viewModelAnalyze.uri?.let {
                ImageCard(
                    uri = it,
                    contentDescription = "shared_image",
                )
            }
        } else {
            ImageCardLost(
                modifier = Modifier
            )
        }
        SpaceItemColumn()
        //Search Button
        SearchButton(viewModelAnalyze)
        SpaceItemColumn()
        //Text found
        MessageBox(modifier = Modifier, viewModelAnalyze.getMessage())
    }
}

@Composable
fun SearchButton(
    viewModelAnalyze: ViewModelAnalyze,
) {
    Box {
        ElevatedButton(
            modifier = Modifier.width(IntrinsicSize.Max),
            onClick = { viewModelAnalyze.searchMessage() }
        ) {
            IconTextButton(
                imageVector = Icons.Outlined.Search,
                contentDescription = "icon_search",
                text = "Search text",
            )
        }
    }
}

