package com.ulisesg.admingolazopro.core.device.domain

import android.net.Uri

interface ImagePickerRepository {

    fun openGallery()

    fun openCamera(uri: Uri)

}
