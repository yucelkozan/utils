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
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.timepicker.MaterialTimePicker
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
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)


        adapter = AlarmAdapter()
        binding.recylerView.apply {
        layoutManager= LinearLayoutManager(this@MainActivity)
            adapter= this@MainActivity.adapter

        }



        AlarmUtil.getAlarms(this)?.observe(this){
            it?.let {
                val list = it.filter { it.id==5 }
                adapter.update(list)
            }
        }

        setPermissions()

        val setAlarmBtn = findViewById<Button>(R.id.setAlarm)

        setAlarmBtn.setOnClickListener {
            showTimerPickerFragment()
        }


    }


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
        val materialTimePicker: MaterialTimePicker = MaterialTimePicker.Builder().build()
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
            val alarm = Alarm(5,calendar.timeInMillis,AlarmManager.INTERVAL_DAY,"Parol ", "1 Tablet")
           AlarmUtil.setAlarm(this,alarm,)
        }
    }

    override fun onBackPressed() {
        finish()
    }

}