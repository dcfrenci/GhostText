package com.dcfrenci.ghosttext.viewmodel

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import com.dcfrenci.ghosttext.data.Ghost

class ViewModelAnalyze(application: Application) : AndroidViewModel(application) {
    private var viewModelSecurity: ViewModelSecurity? = null

    var uri: Uri? by mutableStateOf(null)
        private set
    var message: String by mutableStateOf("")
        private set

    fun updateUri(uri: Uri?) {
        val context = getApplication<Application>().applicationContext
        uri?.let {
            if (context.contentResolver.getType(it) == "application/pdf") {
                this.uri = Ghost(getApplication(), it).getImageUri()
                return
            }
        }
        this.uri = uri
    }

    fun updateViewModelSecurity(viewModelSecurity: ViewModelSecurity) {
        this.viewModelSecurity = viewModelSecurity
    }

    private fun updateMessage(string: String) {
        this.message = string
    }

    fun getMessage() {
        uri?.let {
            val encryptedMessage = Ghost(getApplication(), it).getMessage()
            val ghostMessage = viewModelSecurity!!.getDecryptedMessage(encryptedMessage)
            updateMessage(ghostMessage.ifEmpty { "No message found" })
        }
    }
}