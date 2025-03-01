package com.dcfrenci.ghosttext.viewmodel

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import com.dcfrenci.ghosttext.data.Ghost

class ViewModelAnalyze(application: Application) : AndroidViewModel(application) {
    var uri: Uri? by mutableStateOf(null)
        private set
    var message: String by mutableStateOf("")
        private set

    fun updateUri(uri: Uri?) {
        this.uri = uri
    }

    private fun updateMessage(string: String) {
        this.message = string
    }

    fun getMessage() {
        uri?.let {
            val ghostMessage = Ghost(getApplication(), it).getMessage()
            updateMessage(ghostMessage.ifEmpty { "No message found" })
        }
    }
}