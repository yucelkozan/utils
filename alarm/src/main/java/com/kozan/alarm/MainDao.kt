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

    @Query("SELECT * FROM alarms" )
    fun getAlarms2(): List<Alarm>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun instertAlarm(alarm: Alarm)


  @Query("DELETE FROM alarms WHERE time=:alarmTime and id= :alarmId")
  suspend fun delete(alarmId:Int,alarmTime: Long)


//    @Delete
//    suspend fun delete(alarm: Alarm)


}