package com.valentinerutto.traveldiary.util

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import java.io.File


object ImageUtil {
     fun getImageFromUri(imageUri: Uri?,context: Context): File? {
        imageUri?.let { uri ->
            val mimeType = getMimeType(context, uri)
            mimeType?.let {
                val file = createTmpFileFromUri(context, imageUri, "temp_image")
                file?.let { Log.d("image Url = ", file.absolutePath) }
                return file
            }
        }
        return null
    }


    private fun getMimeType(context: Context, uri: Uri): String? {
        //Check uri format to avoid null
        val extension: String? = if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            //If scheme is a content
            val mime = MimeTypeMap.getSingleton()
            mime.getExtensionFromMimeType(context.contentResolver.getType(uri))
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(uri.path)).toString())
        }
        return extension
    }

    private fun createTmpFileFromUri(
        context: Context,
        uri: Uri,
        fileName: String): File? {
        return try {
            val stream = context.contentResolver.openInputStream(uri)
            val file = File.createTempFile(fileName, "mimeType")
           // FileUtils.copyInputStreamToFile(stream, file)
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    @SuppressLint("Range")
    fun getImageFilePath(context: Context, uri: Uri?): String? {
        var path: String? = null /*from  w ww  . j av  a2s  .c o m*/
        var cursor: Cursor? = null
        try {
            cursor = context.contentResolver.query(
                uri!!,
                arrayOf(MediaStore.Images.Media.DATA),
                null,
                null,
                null
            )
            cursor!!.moveToFirst()
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
        } finally {
            cursor!!.close()
        }
        return path
    }

}
