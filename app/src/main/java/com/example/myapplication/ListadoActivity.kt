package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.RecursoAdapter
import com.example.myapplication.Instancia.ApiClient
import com.example.myapplication.modelo.Recurso
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListadoActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecursoAdapter
    private lateinit var btnAgregar: FloatingActionButton
    private lateinit var inputBuscarId: EditText
    private lateinit var btnBuscarId: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado)
        inputBuscarId = findViewById(R.id.inputBuscarId)
        btnBuscarId = findViewById(R.id.btnBuscarId)

        btnBuscarId.setOnClickListener {
            val id = inputBuscarId.text.toString().trim()
            if (id.isEmpty()) {
                obtenerRecursos()
            } else {
                buscarRecursoPorId(id)
            }
        }

        recyclerView = findViewById(R.id.recyclerViewRecursos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        btnAgregar = findViewById(R.id.btnAgregarRecurso)
        btnAgregar.setOnClickListener {
            val intent = Intent(this, RegistrarRecursosActivity::class.java)
            startActivity(intent)
        }

        obtenerRecursos()
    }

    private fun obtenerRecursos() {
        ApiClient.apiService.getRecursos().enqueue(object : Callback<List<Recurso>> {
            override fun onResponse(call: Call<List<Recurso>>, response: Response<List<Recurso>>) {
                if (response.isSuccessful) {
                    val recursos = response.body() ?: emptyList()
                    adapter = RecursoAdapter(recursos) { recursoSeleccionado ->
                        val intent = Intent(this@ListadoActivity, EditarRecursoActivity::class.java)
                        intent.putExtra("recursoId", recursoSeleccionado.id)
                        startActivity(intent)
                    }
                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(this@ListadoActivity, "Error al cargar recursos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Recurso>>, t: Throwable) {
                Toast.makeText(this@ListadoActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun buscarRecursoPorId(id: String) {
        ApiClient.apiService.getRecursoPorId(id).enqueue(object : Callback<Recurso> {
            override fun onResponse(call: Call<Recurso>, response: Response<Recurso>) {
                if (response.isSuccessful && response.body() != null) {
                    val recurso = response.body()!!
                    adapter = RecursoAdapter(listOf(recurso)) { recursoSeleccionado ->
                        val intent = Intent(this@ListadoActivity, EditarRecursoActivity::class.java)
                        intent.putExtra("recursoId", recursoSeleccionado.id)
                        startActivity(intent)
                    }
                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(
                        this@ListadoActivity,
                        "No se encontró el recurso con ID $id",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            override fun onFailure(call: Call<Recurso>, t: Throwable) {
                Toast.makeText(
                    this@ListadoActivity,
                    "Error de red: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

    }

    override fun onResume() {
        super.onResume()
        obtenerRecursos()
    }

    }
