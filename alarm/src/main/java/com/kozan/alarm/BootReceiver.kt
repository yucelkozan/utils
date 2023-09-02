package com.kozan.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, p1: Intent?) {
        context?.let {
                AppDatabase.getInstance(context)?.let {
                    CoroutineScope(IO).launch {
                   val alarms = it.mainDao().getAlarms()
                   withContext(Main) {
                       alarms.value?.forEach {
                           AlarmUtil.setAlarm(context,it)
                       }
                   }
               }

            }
        }
    }
}