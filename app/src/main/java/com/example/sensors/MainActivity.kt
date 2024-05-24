package com.example.sensors

import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager
    private lateinit var sensorAdapter: SensorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        val categories = listOf(
            getString(R.string.environmental_sensors),
            getString(R.string.position_sensors),
            getString(R.string.human_sensors)
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val sensorCategorySpinner = findViewById<Spinner>(R.id.sensorCategorySpinner)
        sensorCategorySpinner.adapter = adapter

        sensorAdapter = SensorAdapter()
        val sensorRecyclerView = findViewById<RecyclerView>(R.id.sensorRecyclerView)
        sensorRecyclerView.layoutManager = LinearLayoutManager(this)
        sensorRecyclerView.adapter = sensorAdapter

        sensorCategorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCategory = categories[position]
                displaySensors(selectedCategory)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Nothing to do
            }
        }

        // Display initial category
        displaySensors(categories[0])
    }

    private fun displaySensors(category: String) {
        val sensors = when (category) {
            getString(R.string.environmental_sensors) -> getEnvironmentalSensors()
            getString(R.string.position_sensors) -> getPositionSensors()
            getString(R.string.human_sensors) -> getHumanSensors()
            else -> emptyList()
        }
        sensorAdapter.updateSensors(sensors)
    }

    private fun getEnvironmentalSensors(): List<Sensor> {
        return listOfNotNull(
            sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE),
            sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
            sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),
            sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)
        )
    }

    private fun getPositionSensors(): List<Sensor> {
        return listOfNotNull(
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
            sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
            sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)
        )
    }

    private fun getHumanSensors(): List<Sensor> {
        return listOfNotNull(
            sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE),
            sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),
            sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        )
    }
}