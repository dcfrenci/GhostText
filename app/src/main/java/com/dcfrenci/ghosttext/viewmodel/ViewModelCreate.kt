package com.dcfrenci.ghosttext.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import java.io.File
import java.io.FileOutputStream

class ViewModelCreate(application: Application) :/* ViewModel()*/AndroidViewModel(application) {


    var uri: Uri? by mutableStateOf(null)
        private set
    var upload: Boolean by mutableStateOf(false)

    var tempUri: Uri? by mutableStateOf(null)
        private set

    fun upTempUri(uri: Uri?) {
        this.tempUri = uri
    }

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

    fun loadCamera(bitmap: Bitmap) {
        val context = getApplication<Application>().applicationContext
        try {
            val file = File(context.cacheDir, "image_${System.currentTimeMillis()}.png")
            FileOutputStream(file).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.flush()
            }
            updateUri(Uri.fromFile(file))
        } catch (e: Exception) {
            e.printStackTrace()
        }
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


    fun bitmapToFileUri(bitmap: Bitmap) {
        val context = getApplication<Application>().applicationContext
        try {
            val file = File(context.cacheDir, "image_${System.currentTimeMillis()}.png")
            FileOutputStream(file).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.flush()
            }
            updateUri(Uri.fromFile(file))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
