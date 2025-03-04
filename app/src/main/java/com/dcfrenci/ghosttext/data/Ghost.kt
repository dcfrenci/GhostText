package com.dcfrenci.ghosttext.data

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import java.io.File

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

    fun getGhostUriPdf(message: String): Uri? {
        try {
            val bitmap = createBitmap(getGhostUri(message) ?: return null) ?: return null
            val pdfDocument = PdfDocument()
            val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
            val page = pdfDocument.startPage(pageInfo)
            val canvas = page.canvas
            canvas.drawBitmap(bitmap, 0f, 0f, null)
            pdfDocument.finishPage(page)

            val fileUri = createUri("ghost_pdf_${System.currentTimeMillis()}", ".pdf")
            context.contentResolver.openOutputStream(fileUri)?.use { outputStream ->
                pdfDocument.writeTo(outputStream)
            }
            pdfDocument.close()

            return fileUri
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getMessage(): String {
        return searchMessage(uri)
    }

    fun getImageUri(): Uri? {
        val pageNumber = 0
        val fileDescriptor = context.contentResolver.openFileDescriptor(uri, "r") ?: return null
        val pdfRenderer = PdfRenderer(fileDescriptor)
        val page = pdfRenderer.openPage(pageNumber)
        val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        page.close()
        pdfRenderer.close()
        fileDescriptor.close()
        val fileUri = createUri("ghost_image_${System.currentTimeMillis()}", ".png")
        return createGhost(fileUri, bitmap)
    }

    // Private functions ----------------------------------

    private fun hideMessage(originalUri: Uri, message: String): Uri? {
        val originalBitmap = createBitmap(originalUri)
        originalBitmap?.let { bitmap ->
            val binaryMessage = createBinaryMessage(message + '\u0000')
            val ghostBitmap = createGhostBitmap(bitmap, binaryMessage)
            val ghostUri = createUri("ghost_${System.currentTimeMillis()}", ".png")
            return createGhost(ghostUri, ghostBitmap)
        }
        return null
    }

    private fun createBitmap(uri: Uri): Bitmap? {
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

    private fun createGhostBitmap(bitmap: Bitmap, message: List<Int>): Bitmap {
        var bitIndex = 0
        for (x in 0 until bitmap.width) {
            for (y in 0 until bitmap.height) {
                if (bitIndex >= message.size) {
                    return bitmap
                }
                val pixel = bitmap.getPixel(x, y)
                val newPixel = Color.rgb(pixel.red, pixel.green, (pixel.blue and 0b11111110) or message[bitIndex])
                bitmap.setPixel(x, y, newPixel)
                bitIndex++
            }
        }
        return bitmap
    }

    private fun createUri(fileName: String, format: String): Uri {
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(fileName, format, storageDir)
        return FileProvider.getUriForFile(context, context.packageName, file)
    }

    private fun createGhost(uri: Uri, bitmap: Bitmap): Uri? {
        try {
            context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return uri
    }

    private fun searchMessage(uri: Uri): String {
        val imageBitmap = createBitmap(uri)
        imageBitmap?.let { bitmap ->
            val bitList = searchBinaryMessage(bitmap)
            return decodeMessage(bitList)
        }
        return ""
    }

    private fun searchBinaryMessage(bitmap: Bitmap): String {
        val bit = mutableListOf<Int>()
        for (x in 0 until bitmap.width) {
            for (y in 0 until bitmap.height) {
                val pixel = bitmap.getPixel(x, y)
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
            .joinToString("") { it.toInt(2).toChar().toString() }
        return message
    }
}