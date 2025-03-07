package com.dcfrenci.ghosttext.ui.screen

import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.dcfrenci.ghosttext.viewmodel.ViewModelSecurity

@Composable
fun SecurityScreenUI(viewModelSecurity: ViewModelSecurity) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SpaceItemColumn()
        // Sender public key
        Text(
            text = "My public key"
        )
        Card(
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            border = BorderStroke(2.dp, Color.White),
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(0.8f)
        ) {
            TextField(
                value = viewModelSecurity.publicKey,
                onValueChange = {},
                readOnly = true
            )
        }
        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(IntrinsicSize.Max)
        ){
            ElevatedButton(
                onClick = { viewModelSecurity.senderGenerate() }
            ) {
                RowIconTextButton(
                    icon = Icons.Outlined.Update,
                    iconDescription = "icon_update",
                    text = "Generate"
                )
            }
            ElevatedButton(
                onClick = { viewModelSecurity.senderCopy() }
            ) {
                RowIconTextButton(
                    icon = Icons.Outlined.ContentCopy,
                    iconDescription = "icon_copy",
                    text = "Copy"
                )
            }
        }
        SpaceItemColumn()
        // Receiver public key
        Text(
            text = "Receiver public key"
        )
        Card(
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            border = BorderStroke(2.dp, Color.White),
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(0.8f)
        ) {
            TextField(
                value = viewModelSecurity.receiverPublicKey,
                onValueChange = { newReceiverPublicKey ->
                    viewModelSecurity.updateReceiverPublicKey(newReceiverPublicKey)
                }
            )
        }
        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ){
            ElevatedButton(
                onClick = { viewModelSecurity.receiverPaste() }
            ) {
                RowIconTextButton(
                    icon = Icons.Outlined.ContentPaste,
                    iconDescription = "icon_paste",
                    text = "Paste"
                )
            }
            ElevatedButton(
                onClick = { viewModelSecurity.receiverCopy() }
            ) {
                RowIconTextButton(
                    icon = Icons.Outlined.ContentCopy,
                    iconDescription = "icon_copy",
                    text = "Copy"
                )
            }
        }
        SpaceItemColumn(100)
    }
}

@Composable
fun RowIconTextButton(
    icon: ImageVector = Icons.Outlined.QuestionMark,
    iconDescription: String = "icon",
    text: String = "",
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.width(IntrinsicSize.Max)
    ){
        Icon(
            imageVector = icon,
            contentDescription = iconDescription,
        )
        Text(
            text = text
        )
    }
}