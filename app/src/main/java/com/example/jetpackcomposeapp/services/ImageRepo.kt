package com.example.jetpackcomposeapp.services

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Size
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream

class ImageRepo {
    lateinit var uri : Uri

    private var photo_storage = SHARED_STORAGE_ID
    private var sortyBy = "${MediaStore.Images.Media.DATE_ADDED}"

    fun setStorage(storage: Int) : Boolean {
        if (storage != SHARED_STORAGE_ID && storage != APP_STORAGE_ID)
            return false
        else photo_storage = storage
        return true
    }

    fun getStorage(): Int = photo_storage

    fun getSharedList(byDescending: Boolean = false) : MutableList<FileItem>? {
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        sharedStoreList?.clear()

        val contentResolver: ContentResolver = ctx.contentResolver
        val cursor = contentResolver.query(uri, null, null, null, sortyBy + " " + if (byDescending) "DESC" else "ASC")
        if (cursor != null && cursor.moveToFirst()) {
            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
            do {
                val thisId = cursor.getLong(idColumn)
                val thisName = cursor.getString(nameColumn)
                val thisContentUri = ContentUris.withAppendedId(uri, thisId)
                val thisUriPath = thisContentUri.toString()
                sharedStoreList?.add(FileItem(thisName, thisUriPath, "No path yet", thisContentUri))
            } while (cursor.moveToNext())
        }

        return sharedStoreList
    }
    fun getAppList(byDescending: Boolean = false): MutableList<FileItem>? {
        val dir: File? = ctx.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        appStoreList?.clear()

        if (dir?.isDirectory == true) {
            val fileList = dir.listFiles()
            if (byDescending)
                fileList?.sortByDescending { it.lastModified() }
            if (fileList != null) {
                for (file in fileList) {
                    var fileName = file.name
                    if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")
                        || fileName.endsWith(".png") || fileName.endsWith(".gif")) {
                        val tmpUri = FileProvider.getUriForFile(ctx, ctx.packageName + ".provider", file)
                        appStoreList?.add(FileItem(fileName, file.toURI().path, file.absolutePath, tmpUri))
                    }
                }
            }
        }
        return appStoreList
    }

    fun getFilesList(dateDescending: Boolean = false) : MutableList<FileItem>? {
        return when(getStorage()) {
            SHARED_STORAGE_ID -> getSharedList(dateDescending)
            else -> getAppList(dateDescending)
        }
    }

    fun getFileBitmap(fileUri: Uri, width: Int = 72, height: Int = 72) : Bitmap? {
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            ctx.contentResolver.loadThumbnail(fileUri, Size(width, height), null)
        } else getBitmapFromUri(ctx, fileUri)
    }

    private fun getBitmapFromUri(appContext: Context, contentUri: Uri?): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val image_stream: InputStream
            try {
                image_stream = contentUri?.let {
                    appContext.contentResolver.openInputStream(it)
                }!!
                bitmap = BitmapFactory.decodeStream(image_stream)
            } catch(e: FileNotFoundException) {
                e.printStackTrace()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }

    companion object {
        private var INSTANCE: ImageRepo? = null
        private lateinit var ctx : Context
        var sharedStoreList: MutableList<FileItem>? = null
        var appStoreList: MutableList<FileItem>? = null

        val SHARED_STORAGE_ID = 0
        val APP_STORAGE_ID = 1

        fun getInstance(ctx: Context) : ImageRepo {
            if (INSTANCE == null) {
                INSTANCE = ImageRepo()
                sharedStoreList = mutableListOf()
                appStoreList = mutableListOf()
                Companion.ctx = ctx
            }
            return INSTANCE as ImageRepo
        }
    }
}