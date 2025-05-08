package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class InicioSesionActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var buttonLogin: Button
    private lateinit var textRegister: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_sesion)

        auth = FirebaseAuth.getInstance()

        buttonLogin = findViewById(R.id.btnLogin)
        buttonLogin.setOnClickListener {
            val email = findViewById<EditText>(R.id.inputEmail).text.toString().trim()
            val password = findViewById<EditText>(R.id.inputContra).text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validar que sea un correo electrónico válido (cualquier dominio)
            val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
            if (!emailRegex.matches(email)) {
                Toast.makeText(this, "Debe ingresar un correo válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            login(email, password)
        }

        textRegister = findViewById(R.id.textCuentaNueva)
        textRegister.setOnClickListener { goToRegister() }
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MenuPrincipalActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun goToRegister() {
        val intent = Intent(this, RegistroActivity::class.java)
        startActivity(intent)
    }
}