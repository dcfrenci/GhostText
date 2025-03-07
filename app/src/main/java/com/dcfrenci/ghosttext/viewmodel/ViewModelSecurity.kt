package com.dcfrenci.ghosttext.viewmodel

import android.app.Application
import android.content.Context
import android.content.ClipboardManager
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.dcfrenci.ghosttext.data.RSAEncryption
import java.security.PrivateKey
import java.security.PublicKey

class ViewModelSecurity(application: Application) : AndroidViewModel(application) {
    var publicKey: String by mutableStateOf("")
        private set
    private var privateKey: String by mutableStateOf("")
    var receiverPublicKey: String by mutableStateOf("")
        private set

    init {
        senderGenerate()
    }

    private fun updatePublicKey(string: String) {
        this.publicKey = string
    }

    private fun updatePrivateKey(string: String) {
        this.privateKey = string
    }

    fun updateReceiverPublicKey(string: String) {
        this.receiverPublicKey = string
    }

    fun getEncryptedMessage(messageToEncrypt: String): String {
        val receiverPublicKey = RSAEncryption.getPublicKeyFromString(receiverPublicKey)
        return receiverPublicKey?.let { RSAEncryption.encryptText(messageToEncrypt, it)?: "" }?: ""
    }

    fun getDecryptedMessage(messageToDecrypt: String): String {
        val senderPrivateKey = RSAEncryption.getPrivateKeyFromString(privateKey)
        return senderPrivateKey?.let { RSAEncryption.decryptText(messageToDecrypt, it)?: "" }?: ""
    }

    // Buttons
    fun senderGenerate() {
        val keyPair = RSAEncryption.generateKeyPair()
        updatePublicKey(keyPair.first)
        updatePrivateKey(keyPair.second)
    }

    fun senderCopy() {
        copy(publicKey)
    }

    fun receiverPaste() {
        val context = getApplication<Application>().applicationContext
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.primaryClip?.let { clip ->
            val copyPublicKey = if (clip.itemCount > 0) {
                clip.getItemAt(0).text.toString()
            } else {
                null
            }
            copyPublicKey?.let {
                updateReceiverPublicKey(it)
            }
        }
    }

    fun receiverCopy() {
        copy(receiverPublicKey)
    }

    private fun copy(string: String) {
        val context = getApplication<Application>().applicationContext
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = android.content.ClipData.newPlainText("copied_text", string)
        clipboardManager.setPrimaryClip(clip)
        Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show()
    }

}