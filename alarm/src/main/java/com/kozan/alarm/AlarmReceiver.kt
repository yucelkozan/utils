package com.kozan.alarm

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
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

        // RemoteViews are used to use the content of
        // some different layout apart from the current activity layout
        //val contentView = RemoteViews("com.kozan.haliyikamauygulamasi",R.layout.dialog_musteri_ekle)

        // checking if android version is greater than oreo(API 26) or not
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                description,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)

            notificationManager.createNotificationChannel(notificationChannel)
        }

        val builder = NotificationCompat.Builder(context, channelId)
            // .setContent(contentView)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            // .setLargeIcon(BitmapFactory.decodeResource(context.resources,R.drawable.firca_icon))
            .setContentTitle(alarm.notificationTitle)
            // .setContentText(" ARAYAN NUMARA : $arayanNumara\n")
            .setStyle(NotificationCompat.BigTextStyle().bigText(alarm.notificationText))
           // .addAction(android.R.drawable.ic_input_add, "SİPARİŞ EKLE", pendingIntent)
            .setFullScreenIntent(pendingIntent, true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setContentIntent(pendingIntent)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
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