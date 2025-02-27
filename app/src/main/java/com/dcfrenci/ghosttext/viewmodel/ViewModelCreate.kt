package com.dcfrenci.ghosttext.viewmodel

import android.app.Application
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.AndroidViewModel
import java.io.File
import java.io.FileOutputStream

class ViewModelCreate(application: Application) :/* ViewModel()*/AndroidViewModel(application) {

    var uri: Uri? by mutableStateOf(null)
        private set
    var photoUri: Uri? by mutableStateOf(null)
        private set
    var upload: Boolean by mutableStateOf(false)
        private set

    fun updateUri(uri: Uri?) {
        this.uri = uri
    }
    private fun updatePhotoUri(uri: Uri?) {
        this.photoUri = uri
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

    fun loadCamera(): Uri? {
        val context = getApplication<Application>().applicationContext
        val fileName = "capture_${System.currentTimeMillis()}"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(fileName, ".jpg", storageDir)
        val fileUri = FileProvider.getUriForFile(context, context.packageName, file)
        updatePhotoUri(fileUri)
        return fileUri
    }

    fun loadStockPhoto() {
    }

    fun loadGenerate() {
    }

    //Export
    fun exportDownload() {

    }

    fun exportShare() {
        val context = getApplication<Application>().applicationContext
        uri?.let {
            val intent = Intent(Intent.ACTION_SEND)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setType(context.contentResolver.getType(it))
            intent.putExtra(Intent.EXTRA_STREAM, it)

            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            }
        }
    }
}
