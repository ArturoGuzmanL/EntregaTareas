package com.example.unidad3_casopractico1login

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val boton = findViewById<Button>(R.id.enviarB)
        val usuario = findViewById<TextInputEditText>(R.id.Usuario)
        val contra = findViewById<TextInputEditText>(R.id.Contra)

        boton.setOnClickListener {
            if (usuario.text.isNullOrBlank() || contra.text.isNullOrBlank()) {
                Toast.makeText(this, "Error. Rellene los campos.", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(this, "Se ha logueado satisfactoriamente.", Toast.LENGTH_SHORT).show()
                usuario.setText("")
                contra.setText("")
            }
        }

    }
}