package com.kozan.alarmmanager

import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat.CLOCK_24H
import com.kozan.alarm.Alarm
import com.kozan.alarm.AlarmUtil

import com.kozan.alarmmanager.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: AlarmAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        adapter = AlarmAdapter()
        binding.recylerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter

        }

        AlarmUtil.PermissionManager.register(this)


        AlarmUtil.getAlarms(this)?.observe(this) {
            it?.let {
                val list = it.filter { it.id == 5 }
                adapter.update(list)
            }
        }


        val setAlarmBtn = findViewById<Button>(R.id.setAlarm)

        setAlarmBtn.setOnClickListener {view->
            AlarmUtil.PermissionManager.checkPermissions(this) {
                if (!it) {
                   view.showSnackbar(view,"Devam etmek için gerekli izinleri sağlamanız gerekmektedir.")
                    return@checkPermissions
                }
                showTimerPickerFragment()
            }


        }
    }
    fun View.showSnackbar(
        view: View,
        msg: String,
    ) {
        val snackbar = Snackbar.make(view, msg,Snackbar.LENGTH_INDEFINITE,)
            snackbar.setAction("TAMAM") {
               snackbar.dismiss()
            }.show()

    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun setPermissions() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val isPermissionGranted =  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
        if (!isPermissionGranted) startActivity(Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM))

        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
    }

    /**
     * On Click Button call for Time Set
     */
    fun showTimerPickerFragment() {
        val materialTimePicker: MaterialTimePicker = MaterialTimePicker.Builder()
            .setTimeFormat(CLOCK_24H)
            .build()
        materialTimePicker.show(supportFragmentManager, "MainActivity")

        materialTimePicker.addOnPositiveButtonClickListener {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, materialTimePicker.hour)
            calendar.set(Calendar.MINUTE, materialTimePicker.minute)
            calendar.set(Calendar.SECOND, 0)
            val sdf = SimpleDateFormat("E,dd/MM/yyyy kk:mm:ss")
            Log.d(
                "main",
                "reminderTime: ${sdf.format(calendar.timeInMillis).toString()}"
            )
            val alarm = Alarm(5,calendar.timeInMillis,AlarmManager.INTERVAL_FIFTEEN_MINUTES/5,"Parol ", "1 Tablet")
           AlarmUtil.setAlarm(this,alarm,)
        }
    }

    override fun onBackPressed() {
        finish()
    }

}