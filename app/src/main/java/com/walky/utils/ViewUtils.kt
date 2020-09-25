package com.walky.utils

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.GradientDrawable
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.ProgressBar
import android.widget.Toast
import java.io.File
import java.net.URISyntaxException


fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    visibility = View.GONE
}

fun Context.drawableBackColor(view: View, color: Int) {
    val bgShape: GradientDrawable = view.background as GradientDrawable
    bgShape.setColor(color)
}

fun Context.retrieveVideoFrameFromVideo(videoPath: String?): Bitmap? {
    var bitmap: Bitmap? = null
    var mediaMetadataRetriever: MediaMetadataRetriever? = null
    try {
        mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(videoPath, HashMap<String, String>())
        bitmap = mediaMetadataRetriever.frameAtTime
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        mediaMetadataRetriever?.release()
    }
    return bitmap
}

fun getMimeType(context: Context, uri: Uri): String? {
    val extension: String?

    //Check uri format to avoid null
    extension = if (uri.scheme.equals(ContentResolver.SCHEME_CONTENT)) {
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

@SuppressLint("NewApi")
@Throws(URISyntaxException::class)
fun getFilePath(context: Context, uri: Uri): String? {
    var uri = uri
    var selection: String? = null
    var selectionArgs: Array<String>? = null
    // Uri is different in versions after KITKAT (Android 4.4), we need to
    if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(
            context.applicationContext,
            uri
        )
    ) {
        if (isExternalStorageDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":".toRegex()).toTypedArray()
            return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
        } else if (isDownloadsDocument(uri)) {
            val id = DocumentsContract.getDocumentId(uri)
            uri = ContentUris.withAppendedId(
                Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)
            )
        } else if (isMediaDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":".toRegex()).toTypedArray()
            val type = split[0]
            if ("image" == type) {
                uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            } else if ("video" == type) {
                uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            } else if ("audio" == type) {
                uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }
            selection = "_id=?"
            selectionArgs = arrayOf(
                split[1]
            )
        }
    }
    if ("content".equals(uri.scheme, ignoreCase = true)) {
        if (isGooglePhotosUri(uri)) {
            return uri.lastPathSegment
        }
        val projection = arrayOf(
            MediaStore.Images.Media.DATA
        )
        var cursor: Cursor? = null
        try {
            cursor = context.contentResolver
                .query(uri, projection, selection, selectionArgs, null)
            val column_index: Int = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            if (cursor.moveToFirst()) {
                return cursor.getString(column_index)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    } else if ("file".equals(uri.scheme, ignoreCase = true)) {
        return uri.path
    }
    return null
}

fun isExternalStorageDocument(uri: Uri): Boolean {
    return "com.android.externalstorage.documents" == uri.authority
}

fun isDownloadsDocument(uri: Uri): Boolean {
    return "com.android.providers.downloads.documents" == uri.authority
}

fun isMediaDocument(uri: Uri): Boolean {
    return "com.android.providers.media.documents" == uri.authority
}

fun isGooglePhotosUri(uri: Uri): Boolean {
    return "com.google.android.apps.photos.content" == uri.authority
}

fun Context.shareIntent(body: String?, share_subject: String?) {
    /*Create an ACTION_SEND Intent*/
    val intent = Intent(Intent.ACTION_SEND)
    /*This will be the actual content you wish you share.*/
    val shareBody = "Here is the share content body"
    /*The type of the content is text, obviously.*/intent.type = "text/plain"
    /*Applying information Subject and Body.*/intent.putExtra(
        Intent.EXTRA_SUBJECT,
        share_subject
    )
    intent.putExtra(Intent.EXTRA_TEXT, shareBody)
    /*Fire!*/
    startActivity(Intent.createChooser(intent, share_subject))
}

fun Context.getRealPathFromURI(contentURI: Uri): String? {
    val result: String?
    val cursor: Cursor = contentResolver.query(contentURI, null, null, null, null)!!
    if (cursor == null) { // Source is Dropbox or other similar local file path
        result = contentURI.path
    } else {
        cursor.moveToFirst()
        val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        result = cursor.getString(idx)
        cursor.close()
    }
    return result
}