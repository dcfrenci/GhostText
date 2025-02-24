package com.dcfrenci.ghosttext.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CreateScreenUI() {
    Box (
        modifier = Modifier.fillMaxSize(),
    ){
        Text(
            text = "CreateScreen"
        )
    }
}