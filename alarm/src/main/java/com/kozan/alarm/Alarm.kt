package com.kozan.alarm

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "alarms")
data class Alarm(
    val alarmTime : Long,
    val alarmInterval: Long?=null,
    @PrimaryKey(autoGenerate = true)
    val roomId :Int = 0
) : Parcelable
