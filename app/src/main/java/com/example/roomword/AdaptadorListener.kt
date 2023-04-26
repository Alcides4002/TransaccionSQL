package com.example.roomword

interface AdaptadorListener{
    fun  onEditItemClick(estudiante: Estudiante)
    fun onDeleteItemClick(estudiante: Estudiante)
    fun onUpdateItem(estudiantes: Estudiante)
}


