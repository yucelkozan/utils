package com.kozan.utils;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;

public class NavigationUtils {
    public static void navigateSafely(@NonNull NavController navController,
                                      @IdRes int navigateActionId,
                                      @IdRes int destinationId){
        navigateSafely(navController, navigateActionId, destinationId, null, null);
    }

    public static void navigateSafely(@NonNull NavController navController,
                                      @IdRes int navigateActionId,
                                      @IdRes int destinationId,
                                      @Nullable Bundle bundle){
        navigateSafely(navController, navigateActionId, destinationId, bundle, null);
    }

    public static void navigateSafely(@NonNull NavController navController,
                                      @IdRes int navigateActionId,
                                      @IdRes int destinationId,
                                      @Nullable Bundle bundle,
                                      @Nullable NavOptions navOptions){
        if(navController.getCurrentDestination() == null || navController.getCurrentDestination().getId() != destinationId) return;

        if(navOptions != null) {
            navController.navigate(navigateActionId, bundle, navOptions);
        } else if(bundle != null ){
            navController.navigate(navigateActionId, bundle);
        } else {
            navController.navigate(navigateActionId);
        }
    }
}
