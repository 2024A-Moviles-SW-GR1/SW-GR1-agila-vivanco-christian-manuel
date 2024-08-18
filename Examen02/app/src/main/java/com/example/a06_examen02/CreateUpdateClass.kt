package com.example.a06_examen02

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.os.Build
import android.widget.Button
import android.widget.EditText

class CreateUpdateComputador : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_update_computador) // Asegúrate de que el layout esté actualizado
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.view_create_update_computador)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val name = findViewById<EditText>(R.id.input_computador_name)
        val brand = findViewById<EditText>(R.id.input_computador_brand) // Cambiado de semester a brand
        val model = findViewById<EditText>(R.id.input_computador_model) // Cambiado de groupClass a model
        val latitude = findViewById<EditText>(R.id.latitude)
        val longitude = findViewById<EditText>(R.id.longitude)

        val selectedComputador = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                intent.getParcelableExtra("selectedComputador", ComputadorEntity::class.java)
            }
            else -> {
                intent.getParcelableExtra<ComputadorEntity>("selectedComputador")
            }
        }

        val btnCreateUpdateComputador = findViewById<Button>(R.id.btn_create_update_computador)
        selectedComputador?.let { computadorEntity ->
            name.setText(computadorEntity.name)
            brand.setText(computadorEntity.brand)
            model.setText(computadorEntity.model)
            latitude.setText(computadorEntity.latitude)
            longitude.setText(computadorEntity.longitude)

            btnCreateUpdateComputador.setOnClickListener {
                DataBase.tables?.updateComputador(
                    computadorEntity.id,
                    name.text.toString(),
                    brand.text.toString(),
                    model.text.toString(),
                    latitude.text.toString(),
                    longitude.text.toString()
                )
                goToActivity(MainActivity::class.java)
            }
        } ?: run {
            btnCreateUpdateComputador.setOnClickListener {
                DataBase.tables?.createComputador(
                    name.text.toString(),
                    brand.text.toString(),
                    model.text.toString(),
                    latitude.text.toString(),
                    longitude.text.toString()
                )
                goToActivity(MainActivity::class.java)
            }
        }
    }

    private fun goToActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}
