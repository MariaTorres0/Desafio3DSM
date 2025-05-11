package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Instancia.ApiClient
import com.example.myapplication.modelo.Recurso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrarRecursosActivity : AppCompatActivity() {

    private lateinit var inputTitulo: EditText
    private lateinit var inputDescripcion: EditText
    private lateinit var inputTipo: EditText
    private lateinit var inputEnlace: EditText
    private lateinit var inputImagen: EditText
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_recursos)

        inputTitulo = findViewById(R.id.inputTitulo)
        inputDescripcion = findViewById(R.id.inputDescripcion)
        inputTipo = findViewById(R.id.inputTipo)
        inputEnlace = findViewById(R.id.inputEnlace)
        inputImagen = findViewById(R.id.inputImagen)
        btnGuardar = findViewById(R.id.btnGuardarRecurso)

        btnGuardar.setOnClickListener {
            guardarRecurso()
        }
    }

    private fun guardarRecurso() {
        val titulo = inputTitulo.text.toString().trim()
        val descripcion = inputDescripcion.text.toString().trim()
        val tipo = inputTipo.text.toString().trim()
        val enlace = inputEnlace.text.toString().trim()
        val imagen = inputImagen.text.toString().trim()

        if (titulo.isEmpty() || descripcion.isEmpty() || tipo.isEmpty() || enlace.isEmpty() || imagen.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val nuevoRecurso = Recurso(
            id = "", // el ID lo asigna el servidor
            titulo = titulo,
            descripcion = descripcion,
            tipo = tipo,
            enlace = enlace,
            imagen = imagen
        )

        ApiClient.apiService.postRecurso(nuevoRecurso).enqueue(object : Callback<Recurso> {
            override fun onResponse(call: Call<Recurso>, response: Response<Recurso>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@RegistrarRecursosActivity, "Recurso registrado correctamente", Toast.LENGTH_SHORT).show()
                    finish() // cerrar esta pantalla
                } else {
                    Toast.makeText(this@RegistrarRecursosActivity, "Error al registrar recurso", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Recurso>, t: Throwable) {
                Toast.makeText(this@RegistrarRecursosActivity, "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
