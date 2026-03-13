package com.ulisesg.admingolazopro.core.hardware.data

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import com.ulisesg.admingolazopro.core.hardware.domain.ImagePickerRepository
import javax.inject.Inject

class AndroidImagePickerRepository @Inject constructor() : ImagePickerRepository {

    private var galleryLauncher: ActivityResultLauncher<String>? = null
    private var cameraLauncher: ActivityResultLauncher<Uri>? = null

    fun registerGalleryLauncher(
        launcher: ActivityResultLauncher<String>
    ) {
        galleryLauncher = launcher
    }

    fun registerCameraLauncher(
        launcher: ActivityResultLauncher<Uri>
    ) {
        cameraLauncher = launcher
    }

    override fun openGallery() {
        galleryLauncher?.launch("image/*")
    }

    override fun openCamera(uri: Uri) {
        cameraLauncher?.launch(uri)
    }
}