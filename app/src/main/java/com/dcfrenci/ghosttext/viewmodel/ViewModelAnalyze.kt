package com.dcfrenci.ghosttext.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ViewModelAnalyze : ViewModel() {
    private var uri: Uri? by mutableStateOf(null)

    fun updateUri(uri: Uri?) {
        this.uri = uri
    }
}