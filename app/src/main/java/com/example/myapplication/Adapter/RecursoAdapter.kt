package com.example.myapplication.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.modelo.Recurso
import android.text.Html

class RecursoAdapter(
    private val recursos: List<Recurso>,
    private val onItemClick: (Recurso) -> Unit
) : RecyclerView.Adapter<RecursoAdapter.RecursoViewHolder>() {

    class RecursoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id: TextView = view.findViewById(R.id.txtId)
        val titulo: TextView = view.findViewById(R.id.txtTitulo)
        val descripcion: TextView = view.findViewById(R.id.txtDescripcion)
        val tipo: TextView = view.findViewById(R.id.txtTipo)
        val enlace: TextView = view.findViewById(R.id.txtEnlace)
        val imagen: ImageView = view.findViewById(R.id.imgRecurso)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecursoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recurso, parent, false)
        return RecursoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecursoViewHolder, position: Int) {
        val recurso = recursos[position]
        holder.id.text = Html.fromHtml("<b>ID:</b> ${recurso.id}")
        holder.titulo.text = Html.fromHtml("<b>Título:</b> ${recurso.titulo}")
        holder.descripcion.text = Html.fromHtml("<b>Descripción:</b> ${recurso.descripcion}")
        holder.tipo.text = Html.fromHtml("<b>Tipo:</b> ${recurso.tipo}")
        holder.enlace.text = Html.fromHtml("<b>Enlace:</b> ${recurso.enlace}")
        Glide.with(holder.itemView.context).load(recurso.imagen).into(holder.imagen)

        holder.itemView.setOnClickListener {
            onItemClick(recurso)
        }
    }

    override fun getItemCount(): Int = recursos.size
}
