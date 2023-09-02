package com.kozan.utils

import androidx.navigation.NavOptions
import com.kozan.utils.R

object NavOptionsBuilder {
    fun getToRightAnimations(): NavOptions {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.from_right)
            .setExitAnim(R.anim.to_left)
            .setPopEnterAnim(R.anim.from_left)
            .setPopExitAnim(R.anim.to_right).build()
    }

    fun getFromLeftAnimations(): NavOptions {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.from_left)
            .setExitAnim(R.anim.to_right)
            .setPopEnterAnim(R.anim.from_right)
            .setPopExitAnim(R.anim.to_left).build()
    }

    fun getFromRightAnimations(): NavOptions {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.from_right)
            .setExitAnim(R.anim.to_left)
            .setPopEnterAnim(R.anim.from_left)
            .setPopExitAnim(R.anim.to_right).build()
    }


    fun getFromBottomAnimations(): NavOptions {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.from_top)
            .setExitAnim(R.anim.to_bottom)
            .setPopEnterAnim(R.anim.from_bottom)
            .setPopExitAnim(R.anim.to_top).build()
    }

    fun getOnlyBackNavOptions(): NavOptions {
        return NavOptions.Builder().setEnterAnim(R.anim.from_left)
            .setExitAnim(R.anim.to_right).build()
    }




}