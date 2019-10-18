package com.example.sqlite

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_agregar.*
import java.lang.Exception

class Agregar : AppCompatActivity() {
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar)

        try {
            val bundle: Bundle = intent.extras!!
            id = bundle.getInt("ID")
            if (id != 0) {
                edt_Titulo.setText(bundle.getString("Titulo"))
                edt_Contenido.setText(bundle.getString("Descripcion"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun btnAgregar(view: View) {

        val baseDatos = DBManager(this)

        val values = ContentValues()
        values.put("Titulo", edt_Titulo!!.text.toString())
        values.put("Descripcion", edt_Contenido!!.text.toString())


        if (id == 0) {

            val id = baseDatos.insert(values)
            if (id > 0) {
                Toast.makeText(this, "Agregado", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "No se agrego", Toast.LENGTH_SHORT).show()
            }
        } else {
            val selectionArgs = arrayOf(id.toString())

            val id = baseDatos.actualizar(values, "ID=?", selectionArgs)
            if (id > 0) {
                Toast.makeText(this, "Agregado", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "No se agrego", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
