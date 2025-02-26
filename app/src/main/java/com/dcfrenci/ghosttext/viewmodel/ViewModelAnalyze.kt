package com.dcfrenci.ghosttext.viewmodel

import android.media.Image
import android.net.Uri
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class ViewModelAnalyze : ViewModel() {
    var uri: Uri? by mutableStateOf(null)

    fun updateUri(uri: Uri?) {
        this.uri = uri
    }

    fun searchMessage() {

    }

    fun getMessage(): String {
        //TODO("Provide the return value")
        return "no function"
    }
}