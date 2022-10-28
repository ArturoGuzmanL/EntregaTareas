package com.example.unidad5_casopractico1fecha

import android.app.DatePickerDialog.OnDateSetListener
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val botonFecha:Button = findViewById(R.id.botonFecha)
        val fechatxt:TextView = findViewById(R.id.fechaActual)
            

        val date = System.currentTimeMillis()
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val dateString: String = sdf.format(date)
        val temp: String = "Estamos a $dateString"
        fechatxt.setText(temp)
    }

    private fun InitDatePicker() {
        val dateSetListener = OnDateSetListener { datePicker, year, month, day ->
                var month = month
                month = month + 1
                val date: String = makeDataString(day, month, year)
            }
    }

}



    private String