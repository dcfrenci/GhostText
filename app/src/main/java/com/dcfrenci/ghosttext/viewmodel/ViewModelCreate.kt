package com.dcfrenci.ghosttext.viewmodel

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ViewModelCreate(/*application: Application*/) : ViewModel()/*AndroidViewModel(application)*/ {
//    private val contentResolver = application.contentResolver

    //application: Application
    var uri: Uri? by mutableStateOf(null)
    var upload: Boolean by mutableStateOf(false)

    fun updateUri(uri: Uri?) {
        this.uri = uri
    }

    fun updateUpload() {
        this.upload = !upload
    }

    //Button for loading the image
    fun loadGallery(galleryPicker: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>) {
        galleryPicker.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
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

data class Image(
    val id: Long,
    val uri: Uri
)