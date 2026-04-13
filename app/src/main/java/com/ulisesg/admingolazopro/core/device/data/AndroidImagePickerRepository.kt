package com.ulisesg.admingolazopro.core.device.data

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import com.ulisesg.admingolazopro.core.device.domain.ImagePickerRepository
import javax.inject.Inject

class AndroidImagePickerRepository @Inject constructor() : ImagePickerRepository {

    private var galleryLauncher: ActivityResultLauncher<String>? = null
    private var cameraLauncher: ActivityResultLauncher<Uri>? = null
    private var currentCameraUri: Uri? = null
    private var onImageSelected: ((Uri) -> Unit)? = null

    fun registerLaunchers(
        gallery: ActivityResultLauncher<String>,
        camera: ActivityResultLauncher<Uri>,
        callback: (Uri) -> Unit
    ) {
        galleryLauncher = gallery
        cameraLauncher = camera
        onImageSelected = callback
    }

    fun handleGalleryResult(uri: Uri) {
        onImageSelected?.invoke(uri)
    }

    fun handleCameraResult(success: Boolean) {
        if (success) {
            currentCameraUri?.let { onImageSelected?.invoke(it) }
        }
    }

    override fun openGallery() {
        galleryLauncher?.launch("image/*")
    }

    override fun openCamera(uri: Uri) {
        currentCameraUri = uri
        cameraLauncher?.launch(uri)
    }
}

