package com.renancorredato

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.edit
import androidx.core.view.get
import com.renancorredato.databinding.ActivityMainBinding
import kotlin.math.absoluteValue


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var activated: Boolean = false


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tpTime.setIs24HourView(true)
        val sharedPrefsPreferences = getSharedPreferences("db", Context.MODE_PRIVATE)
        activated = sharedPrefsPreferences.getBoolean("activated", false)

        with(binding) {
            if (activated) {
                btNotify.setText(R.string.pause) // Modifica o nome do botão
                btNotify.setBackgroundColor(getColor(R.color.black)) // Troca a cor do botão}

                val interval  = sharedPrefsPreferences.getString("interval" , "")
                val hour = sharedPrefsPreferences.getInt("hour",tpTime.hour)
                val minute = sharedPrefsPreferences.getInt("minute", tpTime.minute)

                etNumberInterval.setText(interval.toString())
                tpTime.hour = hour
                tpTime.minute = minute
            }

        }


        with(binding) {
            btNotify.setOnClickListener {
                val interval = etNumberInterval.text.toString()

                if (interval.isEmpty()) { // Verificando se o campo esta vazio
                    Toast.makeText(
                        applicationContext, "Digite o intervalo", Toast.LENGTH_LONG
                    )
                        .show()
                }

                val hour = tpTime.hour
                val minute = tpTime.minute

                if (!activated) {
                    btNotify.setText(R.string.pause) // Modifica o nome do botão
                    btNotify.setBackgroundColor(getColor(R.color.black)) // Troca a cor do botão}
                    activated = true

                    sharedPrefsPreferences.edit() {
                        putBoolean("activated", true)
                        putString("interval", interval)
                        putInt("hour", hour)
                        putInt("minute", minute)
                        apply()
                    }

                } else {
                    btNotify.setText(R.string.notify) // Modifica o nome do botão
                    btNotify.setBackgroundColor(getColor(R.color.teal_200))// Troca a cor do botão}
                    activated = false

                    sharedPrefsPreferences.edit() {
                        putBoolean("activated", false)
                        remove("interval")
                        remove("hour")
                        remove("minute")
                        apply()
                    }
                }




                Log.e("Teste", "Horas:- $hour:$minute - $interval")

            }
        }
    }
}


