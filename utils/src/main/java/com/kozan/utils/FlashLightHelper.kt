package com.kozan.utils

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.*

class FlashLightHelper(val context:Context) {
    private lateinit var job: Job
    val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    val cameraId = cameraManager.cameraIdList[0]
    val intervalTime = 300L

    fun checkEnable(): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun toggleLight(enable: Boolean) {
        cameraManager.setTorchMode(cameraId, enable)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun showFlashLightWarning() {
        if (!checkEnable()) return
        job = CoroutineScope(Dispatchers.Default).launch {
            while (!job.isCancelled) {
                Log.d("TAG", "showFlashLightWarning: works")
                toggleLight(true)
                delay(intervalTime)
                toggleLight(false)
            }
            if (job.isCancelled) {
                Log.d("TAG", "showFlashLightWarning: canceled")
                toggleLight(false)
            }
        }
    }

    fun stopFlashLightWarning() {
        job.cancel()
    }
}