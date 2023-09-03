package com.kozan.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        println("BOOT RECEİVER ÇALIŞTI")
        context?.let {
                AppDatabase.getInstance(context)?.let {
                    CoroutineScope(IO).launch {
                   val alarms = it.mainDao().getAlarms2()
                        val sdf = SimpleDateFormat("E,dd/MM/yyyy kk:mm:ss")
                        println("ALARMS Sayısı: ${alarms.size}")
                        println("ALARMS : ${alarms?.map { sdf.format(it.time) }}")
                   withContext(Main) {
                       alarms.forEach {
                           AlarmUtil.setAlarm(context,it)
                       }
                   }
               }

            }
        }
    }
}