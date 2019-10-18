package com.example.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.CursorAdapter
import android.widget.Toast

class DBManager {

    //Atributos de la tabla
    val dbNombre = "MiDB"
    val dbTabla = "Notas"
    val columnaID = "ID"
    val columnaTitulo = "Titulo"
    val columnaDescripcion = "Descripcion"
    val dbVersion = 1

    /*CREATE TABLE IF NOT EXIST  "NOTAS", ID(INT), TITULO(VAR), DESCRIPCION (VAR) */
    val sqlCrearTabla =
        "CREATE TABLE IF NOT EXISTS " + dbTabla + " (" + columnaID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                columnaTitulo + " TEXT NOT NULL," + columnaDescripcion + " TEXT NOT NULL)"

    var sqlDB: SQLiteDatabase? = null

    constructor(context: Context) {
        val db = DBHelper(context)
        sqlDB = db.writableDatabase
    }

    inner class DBHelper(context: Context) : SQLiteOpenHelper(context, dbNombre, null, dbVersion) {

        val context: Context? = context

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(sqlCrearTabla)
            Toast.makeText(this.context, "Base de datos creada", Toast.LENGTH_LONG).show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("Drop table IF EXISTS" + dbTabla)
        }
    }

    fun insert(values: ContentValues): Long {
        val id = sqlDB!!.insert(dbTabla, "", values)
        return id
    }

    //Retorna un objeto de tipo cursor
    //Projection son las columnas
    //Selection es el cuerpo de la sentencia WHERE con las columnas que condicionamos
    //SelectionArgs Es una lista de valores que se usaran para seleccion
    //Order como queremos que se ordenes los datos de la base de datos
    fun query(
        projection: Array<String>,
        selection: String,
        selectionArgs: Array<String>,
        order: String
    ): Cursor {

        val consulta = SQLiteQueryBuilder()
        consulta.tables = dbTabla
        val cursor = consulta.query(sqlDB, projection, selection, selectionArgs, null, null, order)
        return cursor
    }

    fun borrar(selection: String, selectionArg: Array<String>): Int{
        val contador = sqlDB!!.delete(dbTabla, selection, selectionArg)
        return  contador
    }

    fun actualizar(values: ContentValues, selection: String, selectionArgs: Array<String>): Int{
        val contador = sqlDB!!.update(dbTabla, values, selection, selectionArgs)
        return contador
    }
}