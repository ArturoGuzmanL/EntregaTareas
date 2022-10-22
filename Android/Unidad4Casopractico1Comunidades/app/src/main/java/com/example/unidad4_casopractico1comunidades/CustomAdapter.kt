package com.example.unidad4_casopractico1comunidades

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

class CustomAdapter(var context: Context, var comunidad: ArrayList<Comunidad>): BaseAdapter() {

    private class ViewHolder(row: View?) {
        var txtName: TextView
        var ivImage: ImageView

        init {
            this.txtName = row?.findViewById(R.id.txtName) as TextView
            this.ivImage = row.findViewById(R.id.ivComunidad) as ImageView
        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View?
        var viewHolder: ViewHolder

        if (convertView == null) {
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.comunidad_item_list, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var comunidad: Comunidad = getItem(position) as Comunidad
        viewHolder.txtName.text = comunidad.nombre
        viewHolder.ivImage.setImageResource(comunidad.imagen)

        return view as View
    }

    override fun getItem(position: Int): Any {
        return comunidad[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return comunidad.count()
    }

}