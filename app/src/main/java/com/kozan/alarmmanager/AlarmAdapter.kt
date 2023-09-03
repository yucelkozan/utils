package com.kozan.alarmmanager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kozan.alarm.Alarm
import com.kozan.alarm.AlarmUtil
import com.kozan.alarmmanager.databinding.ItemAlarmBinding

import java.text.SimpleDateFormat

class AlarmAdapter : RecyclerView.Adapter<AlarmAdapter.MyViewHolder>() {
    var itemList = mutableListOf<Alarm>()


        val sdf = SimpleDateFormat("E,dd/MM/yyyy kk:mm:ss")


    class MyViewHolder(val binding: ItemAlarmBinding): RecyclerView.ViewHolder(binding.root)

    fun update(list: List<Alarm>){
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemAlarmBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val alarm =  itemList[position]

        holder.binding.apply {
            alarmTimeText.text = sdf.format(alarm.time)

            deleteAlarm.setOnClickListener {
                AlarmUtil.cancelAlarm(it.context, alarm)
                itemSil(position)

            }

        }

    }

    fun itemSil(position: Int){
        itemList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position,itemList.size)
    }
}