package com.example.sqlite
/*
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.card.view.*

class Adaptador(context: Context, var listaNotas: ArrayList<Notas>): BaseAdapter(){
    var contexto = context

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val nota = listaNotas[position]
        val inflater = contexto!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val miVista = inflater.inflate(R.layout.card, null)
        miVista.titulo.text = nota.titulo
        miVista.descripcion.text = nota.descripcion

        miVista.eliminar.setOnClickListener {
            val dbManager = DBManager(this.contexto)
            val selectionArgs = arrayOf(nota.notaId.toString())

            dbManager.borrar("ID=?", selectionArgs)
            cargarQuery("%")
        }
        return  miVista
    }

    override fun getItem(position: Int): Any {
   return listaNotas[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listaNotas.size
    }

}*/