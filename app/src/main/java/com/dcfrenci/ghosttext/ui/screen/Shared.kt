package com.dcfrenci.ghosttext.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.dcfrenci.ghosttext.ui.theme.*
import com.dcfrenci.ghosttext.viewmodel.ViewModelAnalyze
import com.dcfrenci.ghosttext.viewmodel.ViewModelCreate
import com.dcfrenci.ghosttext.viewmodel.ViewModelSecurity

@Composable
fun TextTitle(
    text: String = ""
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.fillMaxWidth(MessageBoxWidth)
    )
}

@Composable
fun Image(
    viewModelCreate: ViewModelCreate? = null,
    viewModelAnalyze: ViewModelAnalyze? = null,
) {
    Card(
        shape = RoundedCornerShape(CornerRadius),
        elevation = CardDefaults.cardElevation(defaultElevation = Elevation),
        modifier = Modifier.size(ImageDimension)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(color = NightBlack)
                .border(2.dp, color = NightSilver, shape = RoundedCornerShape(CornerRadius))
        ) {
            viewModelCreate?.let { viewModel ->
                viewModel.uri?.let {
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = "image",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                    )
                } ?: run {
                    Icon(
                        imageVector = Icons.Outlined.Image,
                        contentDescription = "image_default",
                        modifier = Modifier.fillMaxSize(ImageIconWidth)
                    )
                }
            }
            viewModelAnalyze?.let { viewModel ->
                viewModel.uri?.let {
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = "image",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                    )
                } ?: run {
                    Icon(
                        imageVector = Icons.Outlined.Image,
                        contentDescription = "image_default",
                        modifier = Modifier.fillMaxSize(ImageIconWidth)
                    )
                }
            }
        }
    }
}

@Composable
fun MessageBox(
    viewModelCreate: ViewModelCreate? = null,
    viewModelAnalyze: ViewModelAnalyze? = null,
    viewModelSecurity: ViewModelSecurity? = null,
    readOnly: Boolean = false
) {
    viewModelCreate?.let { viewModel ->
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(MessageBoxWidth),
            value = viewModel.message,
            onValueChange = { viewModel.updateMessage(it) },
            label = { Text(text = "Message") },
            readOnly = false
        )
    }
    viewModelAnalyze?.let { viewModel ->
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(MessageBoxWidth),
            value = viewModel.message,
            onValueChange = {},
            label = { Text(text = "Message") },
            readOnly = true
        )
    }
    viewModelSecurity?.let { viewModel ->
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(MessageBoxWidth),
            value = if (readOnly) viewModel.publicKey else viewModel.receiverPublicKey,
            onValueChange = { if (!readOnly) viewModel.updateReceiverPublicKey(it) },
            label = { Text(text = if (readOnly) "My public key" else "Receiver public key") },
            readOnly = readOnly
        )
    }
}

@Composable
fun SpacerItemColumn(
    height: Int = 50
) {
    Spacer(
        modifier = Modifier.height(height.dp)
    )
}

@Composable
fun ButtonSpacerHorizontal() {
    Spacer(
        modifier = Modifier.width(SpacerButtonHorizontal)
    )
}

@Composable
fun IconTextButton(
    arrangement: Arrangement.Horizontal,
    icon: ImageVector = Icons.Outlined.QuestionMark,
    iconDescription: String = "icon",
    text: String = "",
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = arrangement,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = iconDescription,
        )
        if (text.isNotEmpty()) {
            ButtonSpacerHorizontal()
            Text(
                text = text
            )
        }
    }
}