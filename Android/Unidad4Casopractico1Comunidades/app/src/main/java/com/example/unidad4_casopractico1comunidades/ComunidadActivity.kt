package com.example.unidad4_casopractico1comunidades

import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class ComunidadActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var listView = findViewById<ListView>(R.id.listView)

        var arrComunidad: ArrayList<Comunidad> = ArrayList()
        arrComunidad.add(Comunidad("Andalucia", R.drawable.andalucia))
        arrComunidad.add(Comunidad("Aragon", R.drawable.aragon))
        arrComunidad.add(Comunidad("Asturias", R.drawable.asturias))
        arrComunidad.add(Comunidad("Baleares", R.drawable.baleares))
        arrComunidad.add(Comunidad("Canarias", R.drawable.canarias))
        arrComunidad.add(Comunidad("Cantabria", R.drawable.cantabria))
        arrComunidad.add(Comunidad("Castilla y Leon", R.drawable.castillaleon))
        arrComunidad.add(Comunidad("Castilla la Mancha", R.drawable.castillamancha))
        arrComunidad.add(Comunidad("Cataluña", R.drawable.catalunya))
        arrComunidad.add(Comunidad("Ceuta", R.drawable.ceuta))
        arrComunidad.add(Comunidad("Extremadura", R.drawable.extremadura))
        arrComunidad.add(Comunidad("Galicia", R.drawable.galicia))
        arrComunidad.add(Comunidad("La Rioja", R.drawable.larioja))
        arrComunidad.add(Comunidad("Madrid", R.drawable.madrid))
        arrComunidad.add(Comunidad("Melilla", R.drawable.melilla))
        arrComunidad.add(Comunidad("Murcia", R.drawable.murcia))
        arrComunidad.add(Comunidad("Navarra", R.drawable.navarra))
        arrComunidad.add(Comunidad("Pais Vasco", R.drawable.paisvasco))
        arrComunidad.add(Comunidad("Valencia", R.drawable.valencia))

        listView.adapter = CustomAdapter(applicationContext, arrComunidad)

        listView.setOnItemClickListener { parentAdapter, view, position, id ->
            Toast.makeText(applicationContext, "Yo soy de " + arrComunidad[position].nombre , Toast.LENGTH_LONG).show()
        }
    }

}

