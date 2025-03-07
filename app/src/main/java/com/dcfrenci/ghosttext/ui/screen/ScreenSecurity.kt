package com.dcfrenci.ghosttext.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dcfrenci.ghosttext.ui.theme.SpacerElementSmall
import com.dcfrenci.ghosttext.ui.theme.SpacerEndScreen
import com.dcfrenci.ghosttext.ui.theme.SpacerStartScreen
import com.dcfrenci.ghosttext.viewmodel.ViewModelSecurity

@Composable
fun SecurityScreenUI(viewModelSecurity: ViewModelSecurity) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SpacerItemColumn(SpacerStartScreen)
        TextTitle("Security")
        SpacerItemColumn(SpacerElementSmall)
        // Sender public key
        MessageBox(viewModelSecurity = viewModelSecurity, readOnly = true)
        SpacerItemColumn(SpacerElementSmall)
        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(IntrinsicSize.Max)
        ){
            ElevatedButton(
                onClick = { viewModelSecurity.senderGenerate() }
            ) {
                IconTextButton(
                    arrangement = Arrangement.Center,
                    icon = Icons.Outlined.Update,
                    iconDescription = "icon_update",
                    text = "Generate"
                )
            }
            ButtonSpacerHorizontal()
            ElevatedButton(
                onClick = { viewModelSecurity.senderCopy() }
            ) {
                IconTextButton(
                    arrangement = Arrangement.Center,
                    icon = Icons.Outlined.ContentCopy,
                    iconDescription = "icon_copy",
                    text = "Copy"
                )
            }
        }
        SpacerItemColumn()
        // Receiver public key

        MessageBox(viewModelSecurity = viewModelSecurity, readOnly = false)
        SpacerItemColumn(SpacerElementSmall)
        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ){
            ElevatedButton(
                onClick = { viewModelSecurity.receiverPaste() }
            ) {
                IconTextButton(
                    arrangement = Arrangement.Center,
                    icon = Icons.Outlined.ContentPaste,
                    iconDescription = "icon_paste",
                    text = "Paste"
                )
            }
            ButtonSpacerHorizontal()
            ElevatedButton(
                onClick = { viewModelSecurity.receiverCopy() }
            ) {
                IconTextButton(
                    arrangement = Arrangement.Center,
                    icon = Icons.Outlined.ContentCopy,
                    iconDescription = "icon_copy",
                    text = "Copy"
                )
            }
        }
        SpacerItemColumn(SpacerEndScreen)
    }
}
