package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class MenuPrincipalActivity : AppCompatActivity() {
    private lateinit var btnMostrarRecursos: Button
    private lateinit var btnCerrarSesion: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        auth = FirebaseAuth.getInstance()
        btnMostrarRecursos = findViewById(R.id.btnMostrarRecursos)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)

        btnMostrarRecursos.setOnClickListener {
            startActivity(Intent(this, ListadoActivity::class.java))
        }

        btnCerrarSesion.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, InicioSesionActivity::class.java))
            finish()
        }
    }
    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser == null) {
            startActivity(Intent(this, InicioSesionActivity::class.java))
            finish()
        }
    }

}