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
import java.util.Calendar

class AlarmReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onReceive(context: Context, intent: Intent) {

        AlarmUtil.notificationTitle?.let { notificationOlustur(context, intent)}
        val alarm = intent.getParcelableExtra(ALARM) as Alarm?
        alarm?.let {
            AlarmUtil.cancelAlarm(context, it)
             alarm.alarmInterval?.let {
                    val newAlarm = Alarm(alarm.alarmTime+it,it)
                    AlarmUtil.setAlarm(context, newAlarm)
                }
            }

        }



    fun notificationOlustur(context: Context, intent : Intent) {
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
            .setContentTitle(AlarmUtil.notificationTitle)
            // .setContentText(" ARAYAN NUMARA : $arayanNumara\n")
            //.setStyle(NotificationCompat.InboxStyle()
            //    .addLine(" ARAYAN NUMARA : $arayanNumara"))
            .setStyle(NotificationCompat.BigTextStyle().bigText(AlarmUtil.notificationText))

            // .setGroup("abc")
            //.setSubText(saat)
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
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(time, builder.build())

    }

}