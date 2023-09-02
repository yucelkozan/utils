package com.kozan.utils

import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

class ImagePicker(private val activity: AppCompatActivity, private val onImageReady: (Uri?) -> Unit) {

    private val TAG = "ImagePicker"
    private var uri: Uri? = null
    private var pickImageLauncher: ActivityResultLauncher<String>? = null
    private var takePhotoLauncher: ActivityResultLauncher<Uri>? = null

    fun init() {
        pickImageLauncher = activity.registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) {
            onImageReady.invoke(it)
        }

        takePhotoLauncher = activity.registerForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it) {
                onImageReady.invoke(uri)
            } else {
                Log.e(TAG, "init: ", Throwable("take picture failed"))
                onImageReady.invoke(null)
            }
        }
    }

    fun pickImage() {
        pickImageLauncher!!.launch("image/*")
    }

    private fun createFile(): Uri? {
        val timeStamp: String = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
        val file = File(
            activity.filesDir,
            "image.jpg"
            //"$timeStamp.jpg"
        )
        return FileProvider.getUriForFile(
            activity,
            activity.packageName + ".provider", file
        )
    }

    fun takePhoto() {
        uri = createFile()
        takePhotoLauncher?.launch(uri)
    }
}