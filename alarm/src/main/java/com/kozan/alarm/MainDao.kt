package com.kozan.alarm

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query



@Dao
interface MainDao {

    @Query("SELECT * FROM alarms" )
    fun getAlarms(): LiveData<List<Alarm>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun instertAlarm(alarm: Alarm)


    @Query("DELETE FROM alarms WHERE alarmTime=:alarmTime")
    suspend fun delete(alarmTime: Long)







}