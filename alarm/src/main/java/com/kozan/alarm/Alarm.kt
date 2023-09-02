package com.kozan.alarm

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "alarms")
data class Alarm(
    val id :Int,
    var time : Long,
    val interval: Long?=null,
    val notificationTitle:String?=null,
    val notificationText: String?=null,
    @PrimaryKey(autoGenerate = true)
    val roomId :Int = 0
) : Parcelable
