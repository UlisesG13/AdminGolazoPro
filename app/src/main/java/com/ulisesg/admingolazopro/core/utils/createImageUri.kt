package com.ulisesg.admingolazopro.core.utils

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore

fun createImageUri(context: Context): Uri {

    val values = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "product_${System.currentTimeMillis()}.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    }

    return context.contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        values
    )!!
}
