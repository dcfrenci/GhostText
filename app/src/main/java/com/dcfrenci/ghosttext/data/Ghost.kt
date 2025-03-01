package com.dcfrenci.ghosttext.data

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.Image
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import java.io.File
import java.util.*

class Ghost(
    application: Application,
    private val uri: Uri
) {
    private val context = application.applicationContext

    fun getGhostUri(message: String): Uri? {
        hideMessage(uri, message + '\u0000')?.let {
            return it
        }
        return null
    }

    fun getMessage(): String {
        return searchMessage(uri)
    }

    fun analyze() {

    }


    private fun hideMessage(originalUri: Uri, message: String): Uri? {
        val originalBitmap = createBitMap(originalUri)
        originalBitmap?.let { bitMap ->
            val binaryMessage = createBinaryMessage(message + '\u0000')
            val ghostBitMap = createGhostBitMap(bitMap, binaryMessage)
            val ghostUri = createUri()
            return createGhost(ghostUri, ghostBitMap)
        }
        return null
    }

    private fun createBitMap(uri: Uri): Bitmap? {
        return try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val bitmap = BitmapFactory.decodeStream(inputStream)
                bitmap?.copy(Bitmap.Config.ARGB_8888, true)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun createBinaryMessage(message: String): List<Int> {
        val builder = StringBuilder()
        for (char in message.toCharArray()) {
            builder.append(char.code.toString(2).padStart(8, '0'))
        }
        return builder.toString().map { it.digitToInt() }
    }

    private fun createGhostBitMap(bitMap: Bitmap, message: List<Int>): Bitmap {
        var bitIndex = 0
        for (x in 0 until bitMap.width) {
            for (y in 0 until bitMap.height) {
                if (bitIndex >= message.size) {
                    return bitMap
                }
                val pixel = bitMap.getPixel(x, y)
                val newPixel = Color.rgb(pixel.red, pixel.green, pixel.blue or message[bitIndex])
                bitMap.setPixel(x, y, newPixel)
                bitIndex++
            }
        }
        return bitMap
    }

    private fun createUri(): Uri {
        val fileName = "ghost_${System.currentTimeMillis()}"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(fileName, ".png", storageDir)
        return FileProvider.getUriForFile(context, context.packageName, file)
    }

    private fun createGhost(uri: Uri, bitMap: Bitmap): Uri? {
        try {
            context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                bitMap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return uri
    }

    private fun searchMessage(uri: Uri): String {
        val imageBitMap = createBitMap(uri)
        imageBitMap?.let { bitMap ->
            val bitList = searchBinaryMessage(bitMap)
            return decodeMessage(bitList)
        }
        return ""
    }

    private fun searchBinaryMessage(bitMap: Bitmap): String {
        val bit = mutableListOf<Int>()
        for (x in 0 until bitMap.width) {
            for (y in 0 until bitMap.height) {
                val pixel = bitMap.getPixel(x, y)
                bit.add(pixel.blue and 1)
            }
        }
        return bit.joinToString("")
    }

    private fun decodeMessage(bits: String): String {
        val dimension = 8
        val charBinaryList = bits
            .chunked(dimension)
            .takeWhile { it != "00000000" }
        val message = charBinaryList
            .takeWhile { it != "00000000" }
            .joinToString(""){ it.toInt(2).toChar().toString() }
        return message
    }
}