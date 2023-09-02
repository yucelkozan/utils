package com.kozan.utils

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

object PermissionManager {
    private lateinit var checked: (Boolean) -> Unit
    private var activityResultLauncher: ActivityResultLauncher<Array<String>>? = null

    fun register(appCompatActivity: AppCompatActivity) {
         activityResultLauncher =
            appCompatActivity.registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()){
                checked.invoke(!it.values.contains(false))

                }
            }


    fun checkPermissions(permissions: Array<String>, checked: (Boolean) -> Unit) {
        PermissionManager.checked = checked
        activityResultLauncher?.let { it.launch(permissions) }
    }
}