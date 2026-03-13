package com.ulisesg.admingolazopro.features.products.presentation.hardware

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.ulisesg.admingolazopro.core.hardware.data.AndroidImagePickerRepository

@Composable
fun ImagePickerLauncher(
    picker: AndroidImagePickerRepository,
    onImageSelected: (Uri) -> Unit
) {

    val galleryLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->

            uri?.let { onImageSelected(it) }
        }

    val cameraLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.TakePicture()
        ) { }

    LaunchedEffect(Unit) {

        picker.registerGalleryLauncher(galleryLauncher)
        picker.registerCameraLauncher(cameraLauncher)
    }
}