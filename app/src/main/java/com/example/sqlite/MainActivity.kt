package com.example.sqlite

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.BaseAdapter
import android.widget.SearchView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.card.*
import kotlinx.android.synthetic.main.card.view.*
import kotlinx.android.synthetic.main.card.view.editar

class MainActivity(var adaptador: Adaptador? = null) : AppCompatActivity() {

    var listaNotas = ArrayList<Notas>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* listaNotas.add(Notas(1, "Un Titulo", "Una descripcion"))
         listaNotas.add(Notas(1, "Segundo Titulo", "Segunda descripcion"))
         listaNotas.add(Notas(1, "Tercer Titulo", "Tercera descripcion"))
         listaNotas.add(Notas(1, "Cuarto Titulo", "Cuarta descripcion"))*/


        cargarQuery("%")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val buscar = menu!!.findItem(R.id.app_bar_search).actionView as SearchView
        val manejador = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        buscar.setSearchableInfo(manejador.getSearchableInfo(componentName))
        buscar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(applicationContext, query, Toast.LENGTH_SHORT).show()
                cargarQuery("%" + query + "%")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_agregar -> {
                var intent = Intent(this, Agregar::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //pasando ID, Titulo, Descripcion de notas  cuando el titulo sea el ingresado ordenar por titulo
    fun cargarQuery(title: String) {

        var baseDatos = DBManager(this)
        val columnas = arrayOf("ID", "Titulo", "Descripcion")
        val selectArgs = arrayOf(title)
        val cursor = baseDatos.query(columnas, "Titulo like ?", selectArgs, "Titulo")
        listaNotas.clear()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("ID"))
                val titulo = cursor.getString(cursor.getColumnIndex("Titulo"))
                val descripcion = cursor.getString(cursor.getColumnIndex("Descripcion"))

                listaNotas.add(Notas(id, titulo, descripcion))
            } while (cursor.moveToNext())
        }
        adaptador = Adaptador(this, listaNotas)
        lstView.adapter = adaptador

    }

    inner class Adaptador(context: Context, var listaNotas: ArrayList<Notas>) : BaseAdapter() {
        var contexto = context

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val nota = listaNotas[position]
            val inflater =
                contexto!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val miVista = inflater.inflate(R.layout.card, null)
            miVista.titulo.text = nota.titulo
            miVista.descripcion.text = nota.descripcion

            miVista.eliminar.setOnClickListener {
                val dbManager = DBManager(this.contexto)
                val selectionArgs = arrayOf(nota.notaId.toString())

                dbManager.borrar("ID=?", selectionArgs)
                cargarQuery("%")

            }
            miVista.editar.setOnClickListener{
                val intent = Intent(this.contexto, Agregar::class.java)
                intent.putExtra("ID", nota.notaId)
                intent.putExtra("Titulo", nota.descripcion)
                intent.putExtra("Descripcion", nota.descripcion)

                startActivity(intent)
            }
            return miVista

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
    }
}