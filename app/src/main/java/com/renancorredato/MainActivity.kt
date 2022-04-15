package com.renancorredato

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.renancorredato.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var activated: Boolean = false


    companion object {
        const val DB = "db"
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var preferences: SharedPreferences = getSharedPreferences(DB, MODE_PRIVATE)
        activated = preferences.getBoolean("activated", false)
        if (activated) {
            binding.let {
                with(it) {
                    activated = true
                    btNotify.text = getString(R.string.pause)
                    btNotify.setBackgroundColor(getColor(R.color.black))
                }
            }
            val interval = preferences.getInt("interval", 0)
            val hour = preferences.getInt("hour", binding.tpTime.hour)
            val minute = preferences.getInt("minute", binding.tpTime.minute)

            binding.etNumberInterval.text.let {
                interval
            }

            binding.tpTime.hour = hour
            binding.tpTime.minute = minute

            binding.tpTime.setIs24HourView(true)

            binding.btNotify.setOnClickListener {
                val sInterval = binding.etNumberInterval.text.toString()
                if (sInterval.isEmpty()) {
                    Toast.makeText(this, getString(R.string.error_interval), Toast.LENGTH_LONG)
                        .show()
                    return@setOnClickListener
                }
                val hour = binding.tpTime.hour
                val minute = binding.tpTime.minute
                val interval = sInterval.toInt()

                if (!activated) {
                    binding.let {
                        with(it) {
                            activated = true
                            btNotify.text = getString(R.string.pause)
                            btNotify.setBackgroundColor(getColor(R.color.black))
                        }
                    }

                    preferences.edit().apply {
                        putBoolean("activated", true)
                        putInt("interval", interval)
                        putInt("hour", hour)
                        putInt("minute", minute)
                        apply()
                    }

                } else {
                    binding.let {
                        with(it) {
                            activated = false
                            btNotify.text = getString(R.string.notify)
                            btNotify.setBackgroundColor(getColor(R.color.teal_200))
                        }
                    }

                    preferences.edit().apply {
                        putBoolean("activated", false)
                        remove("interval")
                        remove("hour")
                        remove("minute")
                        apply()
                    }
                }

                Log.d("teste", "hora:$hour minuto:$minute intervalo:$interval")
            }
        }
    }
}


