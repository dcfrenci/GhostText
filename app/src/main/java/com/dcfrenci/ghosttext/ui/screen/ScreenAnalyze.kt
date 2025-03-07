package com.dcfrenci.ghosttext.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dcfrenci.ghosttext.ui.theme.SpacerElementSmall
import com.dcfrenci.ghosttext.ui.theme.SpacerEndScreen
import com.dcfrenci.ghosttext.ui.theme.SpacerStartScreen
import com.dcfrenci.ghosttext.viewmodel.ViewModelAnalyze

@Composable
fun AnalyzeScreenUI(viewModelAnalyze: ViewModelAnalyze) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SpacerItemColumn(SpacerStartScreen)
        TextTitle("Analyze")
        SpacerItemColumn(SpacerElementSmall)
        //Image
        Image(viewModelAnalyze = viewModelAnalyze)
        SpacerItemColumn()
        //Search Button
        ElevatedButton(
            onClick = { viewModelAnalyze.getMessage() }
        ) {
            IconTextButton(
                arrangement = Arrangement.Center,
                icon = Icons.Outlined.Search,
                iconDescription = "icon_search",
                text = "Search image"
            )
        }
        SpacerItemColumn()
        //Text found
        MessageBox(viewModelAnalyze = viewModelAnalyze)
        SpacerItemColumn(SpacerEndScreen)
    }
}

//@Composable
//fun SearchButton(
//    viewModelAnalyze: ViewModelAnalyze,
//) {
//    Box {
//        ElevatedButton(
//            modifier = Modifier.width(IntrinsicSize.Max),
//            onClick = { viewModelAnalyze.getMessage() }
//        ) {
//            IconTextButton(
//                arrangement = Arrangement.Center,
//                icon = Icons.Outlined.Search,
//                iconDescription = "icon_search",
//                text = "Search text",
//            )
//        }
//    }
//}

