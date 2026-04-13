package com.ulisesg.admingolazopro.core.device.presentation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.ulisesg.admingolazopro.core.device.data.AndroidImagePickerRepository

@Composable
fun ImagePickerLauncher(
    picker: AndroidImagePickerRepository,
    onImageSelected: (Uri) -> Unit
) {
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { picker.handleGalleryResult(it) }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        picker.handleCameraResult(success)
    }

    LaunchedEffect(picker, onImageSelected) {
        picker.registerLaunchers(galleryLauncher, cameraLauncher, onImageSelected)
    }
}
