package com.ulisesg.admingolazopro.core.hardware.domain

import android.net.Uri

interface ImagePickerRepository {

    fun openGallery()

    fun openCamera(uri: Uri)

}