package com.kozan.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

object AlarmUtil {
    const val REQUEST_CODE = "request_code"
    const val INTERVAL_TIME = "interval_time"
    const val ALARM_TIME = "alarm_time"
    const val ALARM = "alarm"






    fun getAlarms(context: Context): LiveData<List<Alarm>>? {
         val alarms = AppDatabase.getInstance(context)?.mainDao()?.getAlarms()
        return alarms
    }

    fun cancelAlarm(context: Context,alarm: Alarm) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(getPendingIntent(context,alarm))

        val dao = AppDatabase.getInstance(context)?.mainDao()
        dao?.let {
            CoroutineScope(IO).launch{
                val sdf = SimpleDateFormat("E,dd/MM/yyyy kk:mm:ss")
                val time =  sdf.format(alarm.time).toString()
                dao.delete(alarm.id,alarm.time)
            }
        }
    }


    fun setAlarm(context: Context, alarm: Alarm){
       // Notice that in the manifest, the boot receiver is set to android:enabled="false". This means that the receiver will not be called unless the application explicitly enables it. This prevents the boot receiver from being called unnecessarily. You can enable a receiver (for example, if the user sets an alarm) as follows:
//        val receiver = ComponentName(context, BootReceiver::class.java)
//        context.packageManager.setComponentEnabledSetting(
//            receiver,
//            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//            PackageManager.DONT_KILL_APP
//        )


        val sdf = SimpleDateFormat("E,dd/MM/yyyy kk:mm:ss")
        if (System.currentTimeMillis()>alarm.time){
              alarm.interval?.let {
                  alarm.time=alarm.time+alarm.interval
              } ?: kotlin.run {
                  alarm.time= alarm.time+AlarmManager.INTERVAL_DAY
              }
        }
        val time = sdf.format(alarm.time)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager


        // setExact perfect. on time. no delay.
       // .set nearly. 10-60 sn delay

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            alarm.time,
            getPendingIntent(context,alarm)

        )

        val dao = AppDatabase.getInstance(context)?.mainDao()
        dao?.let {
            CoroutineScope(IO).launch{
                dao.instertAlarm(alarm)
            }
        }

//            // repating alarms inexact
//        alarmManager.setRepeating(
//            AlarmManager.RTC_WAKEUP,
//            calendar.timeInMillis,
//            AlarmManager.INTERVAL_DAY*7,
//            pendingIntent
//        )
    }

    private fun getPendingIntent(context: Context, alarm: Alarm): PendingIntent {
       val intent =  Intent(context, AlarmReceiver::class.java)
           .putExtra(ALARM,alarm)
        return PendingIntent.getBroadcast(
            context,
            alarm.time.toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }
}