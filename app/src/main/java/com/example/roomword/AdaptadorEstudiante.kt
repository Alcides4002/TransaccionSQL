package com.example.roomword

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.EventListener

class AdaptadorEstudiante(
    val listaEstudiante: MutableList<Estudiante>,
    val listener: AdaptadorListener
):RecyclerView.Adapter<AdaptadorEstudiante.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.rcestudiante, parent, false)
        return ViewHolder(vista)
    }


    override fun onBindViewHolder(holder: AdaptadorEstudiante.ViewHolder, position: Int) {
      val estudiante = listaEstudiante[position]
        holder.etNombre.text = estudiante.nombre
        holder.etApellido.text = estudiante.apellidos
        holder.etCarrera.text = estudiante.carrera
        holder.etCorreo.text = estudiante.correo

        holder.constrain.setOnClickListener {
            listener.onEditItemClick(estudiante)
        }

        holder.btnEliminar.setOnClickListener {
            listener.onDeleteItemClick(estudiante)
        }
        holder.btnActualizar.setOnClickListener {
            listener.onUpdateItem(estudiante)
        }




    }

    override fun getItemCount(): Int {
       return listaEstudiante.size
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
     var etNombre = itemView.findViewById<TextView>(R.id.etNombre)
        var etApellido = itemView.findViewById<TextView>(R.id.etApellidos)
        var etCarrera = itemView.findViewById<TextView>(R.id.etCarrera)
        var etCorreo = itemView.findViewById<TextView>(R.id.etCorreo)
        var btnActualizar = itemView.findViewById<FloatingActionButton>(R.id.floatingActionButton2)
        var btnEliminar = itemView.findViewById<FloatingActionButton>(R.id.floatingActionButton3)
        var constrain = itemView.findViewById<ConstraintLayout>(R.id.ctRecycler)

    }
}