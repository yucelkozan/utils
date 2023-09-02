package com.kozan.utils

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding

object ExitDialog {
    fun show(context: Context, activity: AppCompatActivity,binding: ViewDataBinding,runnable: Runnable) {
        val binding = DialogLeaveBinding.inflate(LayoutInflater.from(context))
        val dialog = AlertDialog.Builder(context, R.style.myCustomDialog)
            .setView(binding.root)
            .show()


        NativeAdManager.loadNativeAd(binding.nativeContainer,EasyAdConstants.NATIVE_TYPE.NATIVE_LARGE)

        binding.yesButton.setOnClickListener {
            dialog.dismiss()
            activity.finish()

        }

        binding.cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setOnDismissListener {
            runnable.run()
        }

    }

}