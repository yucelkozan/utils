package com.kozan.alarm

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

object AlarmUtil {

    object PermissionManager {
        private lateinit var checked: (Boolean) -> Unit
        private var activityResultLauncher: ActivityResultLauncher<Array<String>>? = null

        fun register(appCompatActivity: AppCompatActivity) {
            activityResultLauncher = appCompatActivity.registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()){
                checked.invoke(!it.values.contains(false))
            }
        }


      private fun checkPermissions(permissions: Array<String>, checked: (Boolean) -> Unit) {
            PermissionManager.checked = checked
            activityResultLauncher?.let {
                it.launch(permissions) }
        }

        fun checkPermissions(
            activity: AppCompatActivity,
            checked: (Boolean) -> Unit
        ) {

           checkPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS)) {
                if (it) {
                    val alarmManager = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    val isPermissionGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        alarmManager.canScheduleExactAlarms()
                    } else {
                        true
                    }
                    checked.invoke(isPermissionGranted)
                    if (!isPermissionGranted) activity.startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
                }
                else checked.invoke(false)


            }
        }
    }

    const val ALARM = "alarm"

    fun getAlarms(context: Context): LiveData<List<Alarm>>? {
        val alarms = AppDatabase.getInstance(context)?.mainDao()?.getAlarms()
        return alarms
    }

    fun cancelAlarm(context: Context, alarm: Alarm) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(getPendingIntent(context, alarm))

        val dao = AppDatabase.getInstance(context)?.mainDao()
        dao?.let {
            CoroutineScope(IO).launch {
                val sdf = SimpleDateFormat("E,dd/MM/yyyy kk:mm:ss")
                val time = sdf.format(alarm.time).toString()
                dao.delete(alarm.id, alarm.time)
            }
        }
    }


    fun setAlarm(context: Context, alarm: Alarm,activity: AppCompatActivity?=null) {
        activity?.let {
            PermissionManager.checkPermissions(activity){

            }
        }


        val sdf = SimpleDateFormat("E,dd/MM/yyyy kk:mm:ss")
        if (System.currentTimeMillis() > alarm.time) {
            alarm.interval?.let {
                alarm.time = alarm.time + alarm.interval
            } ?: kotlin.run {
                alarm.time = alarm.time + AlarmManager.INTERVAL_DAY
            }
        }
        val time = sdf.format(alarm.time)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // setExact perfect. on time. no delay.
        // .set nearly. 10-60 sn delay

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            alarm.time,
            getPendingIntent(context, alarm)

        )

        val dao = AppDatabase.getInstance(context)?.mainDao()
        dao?.let {
            CoroutineScope(IO).launch {
                dao.instertAlarm(alarm)
            }
        }

    }

    private fun getPendingIntent(context: Context, alarm: Alarm): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java)
            .putExtra(ALARM, alarm)
        return PendingIntent.getBroadcast(
            context,
            alarm.time.toInt(),
            intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)  PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            else  PendingIntent.FLAG_UPDATE_CURRENT
        )
    }



    }


