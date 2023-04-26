package com.example.roomword

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
   entities = [Estudiante::class],
    version = 1
)
 abstract class dbEstudiante:RoomDatabase() {
  abstract fun daoEstudiante():DaoEstudiante
}