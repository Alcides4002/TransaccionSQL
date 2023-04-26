package com.example.roomword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomword.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), AdaptadorListener {
    lateinit var binding: ActivityMainBinding
    lateinit var adatador: AdaptadorEstudiante
    lateinit var estudiante:Estudiante
    lateinit var room: dbEstudiante
    var listaEstudiante: MutableList<Estudiante> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvEstudiante.layoutManager = LinearLayoutManager(this)
        room = Room.databaseBuilder(this, dbEstudiante::class.java, "dbEstudiante").build()
        obtenerEstudiante(room)

        with(binding){
            binding.floatingActionButton.setOnClickListener {
                if (etNombre.text.toString() == "" || etApellidos.text.toString() == "" || etCarrera.text.toString() == "" || etCorreo.text.toString() == ""){
                    Toast.makeText(this@MainActivity, "Todos los campos son requeridos", Toast.LENGTH_LONG).show()
                }else{

                    estudiante = Estudiante(
                        etNombre.text.toString().trim(),
                        etApellidos.text.toString().trim(),
                        etCarrera.text.toString().trim(),
                        etCorreo.text.toString().trim()
                    )

                    agregarEstudiante(room, estudiante)

                }
            }

        }


    }
    fun obtenerEstudiante(room:dbEstudiante){
        //utilizamos corrutinas para lo que vamos a recibir de la bd si hay usuario cuando
        //abramos la app

        lifecycleScope.launch{
            listaEstudiante = room.daoEstudiante().obtenerEstudiante()
            adatador = AdaptadorEstudiante(listaEstudiante,this@MainActivity)
            binding.rvEstudiante.adapter = adatador


        }
    }
     fun agregarEstudiante(room: dbEstudiante,estudiantes: Estudiante){
         lifecycleScope.launch{
             room.daoEstudiante().agrgarEstudiante(estudiantes)
             obtenerEstudiante(room)
             clean()

         }
     }

   private fun clean(){
      with(binding){
          etNombre.setText("")
          etApellidos.setText("")
          etCarrera.setText("")
          etCorreo.setText("")
      }


  }
    fun actualizarEstudiante(room: dbEstudiante, estudiante: Estudiante) {
        lifecycleScope.launch {
            room.daoEstudiante().actualizarEstudiante(estudiante.id, estudiante.nombre, estudiante.apellidos, estudiante.carrera, estudiante.correo)
            obtenerEstudiante(room)
            clean()
        }
    }
    override fun onUpdateItem(estudiantes: Estudiante) {

        with(binding) {
            if (etNombre.text.toString() == "" || etApellidos.text.toString() == "" || etCarrera.text.toString() == "" || etCorreo.text.toString() == ""){
                Toast.makeText(this@MainActivity, "Todos los campos son requeridos", Toast.LENGTH_LONG).show()
            }else{

                estudiante.nombre = etNombre.text.toString().trim()
                estudiante.apellidos = etApellidos.text.toString().trim()
                estudiante.carrera = etCarrera.text.toString().trim()
                estudiante.correo = etCorreo.text.toString().trim()



                actualizarEstudiante(room, estudiante)
                clean()

            }
        }

    }

    override fun onEditItemClick(estudiante: Estudiante) {
        this.estudiante = estudiante
        var id = this.estudiante.id
        binding.etNombre.setText(this.estudiante.nombre)
        binding.etApellidos.setText(this.estudiante.apellidos)
        binding.etCarrera.setText(this.estudiante.carrera)
        binding.etCorreo.setText(this.estudiante.correo)
    }

    override fun onDeleteItemClick(estudiante: Estudiante) {
       with(binding){
           lifecycleScope.launch{
               room.daoEstudiante().deleteEstudiante(estudiante.id)
               adatador.notifyDataSetChanged()
               obtenerEstudiante(room)
               clean()
           }
       }
    }


}