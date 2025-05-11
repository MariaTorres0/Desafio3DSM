package com.example.myapplication

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.modelo.Recurso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.myapplication.Instancia.ApiClient

class EditarRecursoActivity : AppCompatActivity() {

    private lateinit var inputTitulo: EditText
    private lateinit var inputDescripcion: EditText
    private lateinit var inputTipo: EditText
    private lateinit var inputEnlace: EditText
    private lateinit var inputImagen: EditText
    private lateinit var btnActualizar: Button
    private lateinit var btnEliminar: Button

    private var recursoId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_recurso)

        inputTitulo = findViewById(R.id.inputTituloEditar)
        inputDescripcion = findViewById(R.id.inputDescripcionEditar)
        inputTipo = findViewById(R.id.inputTipoEditar)
        inputEnlace = findViewById(R.id.inputEnlaceEditar)
        inputImagen = findViewById(R.id.inputImagenEditar)
        btnActualizar = findViewById(R.id.btnActualizar)
        btnEliminar = findViewById(R.id.btnEliminar)

        recursoId = intent.getStringExtra("recursoId")

        if (recursoId != null) {
            cargarRecurso(recursoId!!)
        }

        btnActualizar.setOnClickListener {
            actualizarRecurso()
        }

        btnEliminar.setOnClickListener {
            eliminarRecurso()
        }
    }

    private fun cargarRecurso(id: String) {
        ApiClient.apiService.getRecursoPorId(id).enqueue(object : Callback<Recurso> {
            override fun onResponse(call: Call<Recurso>, response: Response<Recurso>) {
                if (response.isSuccessful) {
                    val recurso = response.body()
                    if (recurso != null) {
                        inputTitulo.setText(recurso.titulo)
                        inputDescripcion.setText(recurso.descripcion)
                        inputTipo.setText(recurso.tipo)
                        inputEnlace.setText(recurso.enlace)
                        inputImagen.setText(recurso.imagen)
                    }
                }
            }

            override fun onFailure(call: Call<Recurso>, t: Throwable) {
                Toast.makeText(this@EditarRecursoActivity, "Error al cargar", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun actualizarRecurso() {
        val recurso = Recurso(
            id = recursoId ?: "",
            titulo = inputTitulo.text.toString(),
            descripcion = inputDescripcion.text.toString(),
            tipo = inputTipo.text.toString(),
            enlace = inputEnlace.text.toString(),
            imagen = inputImagen.text.toString()
        )

        ApiClient.apiService.putRecurso(recurso.id, recurso).enqueue(object : Callback<Recurso> {
            override fun onResponse(call: Call<Recurso>, response: Response<Recurso>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditarRecursoActivity, "Recurso ctualizado", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@EditarRecursoActivity, "Error al actualizar", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Recurso>, t: Throwable) {
                Toast.makeText(this@EditarRecursoActivity, "Error de red", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun eliminarRecurso() {
        val id = recursoId ?: return

        ApiClient.apiService.deleteRecurso(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditarRecursoActivity, "Recurso eliminado", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@EditarRecursoActivity, "Error al eliminar", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@EditarRecursoActivity, "Error de red", Toast.LENGTH_SHORT).show()
            }
        })
    }
}