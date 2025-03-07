package com.dcfrenci.ghosttext.viewmodel

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.FileProvider
import androidx.lifecycle.AndroidViewModel
import com.dcfrenci.ghosttext.data.Ghost
import com.dcfrenci.ghosttext.data.RSAEncryption
import java.io.File

class ViewModelCreate(application: Application) : AndroidViewModel(application) {
    private var viewModelSecurity: ViewModelSecurity? = null

    var uri: Uri? by mutableStateOf(null)
        private set
    var photoUri: Uri? by mutableStateOf(null)
        private set
    var upload: Boolean by mutableStateOf(false)
        private set
    var message: String by mutableStateOf("")
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

    fun updateMessage(string: String) {
        this.message = string
    }

    fun updateViewModelSecurity(viewModelSecurity: ViewModelSecurity) {
        this.viewModelSecurity = viewModelSecurity
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
        val encryptedMessage = viewModelSecurity!!.getEncryptedMessage(message)
        uri?.let { image ->
            val ghostUri = Ghost(getApplication(), image).getGhostUri(encryptedMessage)
            ghostUri?.let { ghost ->
                val context = getApplication<Application>().applicationContext
                val intent = Intent(Intent.ACTION_SEND)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.setType(context.contentResolver.getType(ghost))
                intent.putExtra(Intent.EXTRA_STREAM, ghost)

                if (intent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(intent)
                }
            }
//            else {
//                //TODO - Add error during creating ghost image
//            }
        }
    }

    fun exportShare() {
        val encryptedMessage = viewModelSecurity!!.getEncryptedMessage(message)
        uri?.let { image ->
            val ghostUri = Ghost(getApplication(), image).getGhostUriPdf(encryptedMessage)
            if (ghostUri != null) {
                val context = getApplication<Application>().applicationContext
                val intent = Intent(Intent.ACTION_SEND)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.setType("application/pdf")
                intent.putExtra(Intent.EXTRA_STREAM, ghostUri)

                if (intent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(intent)
                }
            } else {
                //TODO - Add error during creating ghost image
            }
        }
    }
}
