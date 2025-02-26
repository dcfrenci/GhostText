package com.dcfrenci.ghosttext.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ViewModelCreate: ViewModel() {
    var uri: Uri? by mutableStateOf(null)
    var upload: Boolean by mutableStateOf(false)

    fun updateUri(uri: Uri?) {
        this.uri = uri
    }

    fun updateUpload() {
        this.upload = !upload
    }

    //Button for loading the image
    fun loadGallery() {

    }

    fun loadCamera() {

    }

    fun loadStockPhoto() {

    }

    fun loadGenerate() {

    }

    //Export
    fun exportDownload() {

    }

    fun exportShare() {

    }
}