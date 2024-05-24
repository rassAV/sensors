package com.example.sensors

import android.hardware.Sensor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SensorAdapter : RecyclerView.Adapter<SensorAdapter.SensorViewHolder>() {

    private var sensorList: List<Sensor> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sensor, parent, false)
        return SensorViewHolder(view)
    }

    override fun onBindViewHolder(holder: SensorViewHolder, position: Int) {
        val sensor = sensorList[position]
        holder.sensorName.text = sensor.name
    }

    override fun getItemCount(): Int {
        return sensorList.size
    }

    fun updateSensors(sensors: List<Sensor>) {
        sensorList = sensors
        notifyDataSetChanged()
    }

    class SensorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sensorName: TextView = itemView.findViewById(R.id.sensorName)
    }
}