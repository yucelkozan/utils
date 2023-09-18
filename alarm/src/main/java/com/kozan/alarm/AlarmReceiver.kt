package com.kozan.alarm

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kozan.alarm.AlarmUtil.ALARM


class AlarmReceiver : BroadcastReceiver() {


    @SuppressLint("SuspiciousIndentation")
    override fun onReceive(context: Context, intent: Intent) {
        println("ALARM RECEİVER ÇALIŞTI")
        val alarm = intent.getParcelableExtra(ALARM) as Alarm?
        alarm?.let {
            AlarmUtil.cancelAlarm(context, it.copy())
             alarm.interval?.let {
                    //val newAlarm = Alarm(alarm.id,alarm.time+it,it,alarm.notificationTitle,alarm.notificationText)
                 alarm.time= alarm.time+it
                    AlarmUtil.setAlarm(context,alarm)
                }

            alarm.notificationTitle?.let {
                notificationOlustur(context, intent,alarm)
            }
            }

        }


    fun notificationOlustur(context: Context, intent : Intent,alarm: Alarm) {
        // declaring variables

        val notificationManager = NotificationManagerCompat.from(context)


        val channelId = "123"
        val description = "Hatırlatıcı"

        val time = System.currentTimeMillis().toInt()

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getActivity( context, time,intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        } else {
            PendingIntent.getActivity( context, time,intent,  PendingIntent.FLAG_UPDATE_CURRENT)
        }
            // ikisi de çalışıyor
        val soundUri = //Uri.parse("${ContentResolver.SCHEME_ANDROID_RESOURCE}://${context.packageName}/${R.raw.whistle3}")
            Uri.parse("${ContentResolver.SCHEME_ANDROID_RESOURCE}://${context.packageName}/raw/notif_sound")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                description,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)

           notificationChannel.setSound(soundUri, Notification.AUDIO_ATTRIBUTES_DEFAULT)
            

            notificationManager.createNotificationChannel(notificationChannel)
        }

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(alarm.notificationTitle)
            .setStyle(NotificationCompat.BigTextStyle().bigText(alarm.notificationText))
            .setSound(soundUri)
            .setFullScreenIntent(pendingIntent, true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setContentIntent(pendingIntent)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManager.notify(time, builder.build())

    }

}