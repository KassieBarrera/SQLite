package com.example.sqlite

import java.io.FileDescriptor

class Notas{
    var notaId: Int? = null
    var titulo: String? = null
    var descripcion: String? = null

    constructor(notaId: Int, titulo: String, descripcion: String){
        this.notaId = notaId
        this.titulo = titulo
        this.descripcion = descripcion
    }

}