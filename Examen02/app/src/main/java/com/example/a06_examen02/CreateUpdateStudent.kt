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

class CreateUpdateComponente : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_update_componente)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.view_create_update_componente)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val name = findViewById<EditText>(R.id.input_componente_name)
        val description = findViewById<EditText>(R.id.input_componente_description)
        val computadorId = findViewById<EditText>(R.id.input_componente_computador_id)

        val selectedComponente = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                intent.getParcelableExtra("selectedComponente", ComponenteEntity::class.java)
            }
            else -> {
                intent.getParcelableExtra<ComponenteEntity>("selectedComponente")
            }
        }

        val selectedComputador = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                intent.getParcelableExtra("selectedComputador", ComputadorEntity::class.java)
            }
            else -> {
                intent.getParcelableExtra<ComputadorEntity>("selectedComputador")
            }
        }

        val create = intent.getBooleanExtra("create", true)

        selectedComputador?.let { computadorEntity ->
            if (create) {
                computadorId.setText(computadorEntity.id.toString())

                // Create a componente
                findViewById<Button>(R.id.btn_create_update_componente).setOnClickListener {
                    DataBase.tables?.createComponente(
                        name.text.toString(),
                        description.text.toString(),
                        computadorId.text.toString().toInt()
                    )
                    goToActivity(ComponentsList::class.java, computadorEntity)
                }
            } else {
                selectedComponente?.let { componente ->
                    name.setText(componente.nameComponente)
                    description.setText(componente.description)
                    computadorId.setText(componente.computador_id.toString())

                    // Update a componente
                    findViewById<Button>(R.id.btn_create_update_componente).setOnClickListener {
                        DataBase.tables?.updateComponente(
                            componente.id,
                            name.text.toString(),
                            description.text.toString(),
                            computadorId.text.toString().toInt()
                        )
                        goToActivity(ComponentsList::class.java, computadorEntity)
                    }
                }
            }
        }
    }

    private fun goToActivity(activityClass: Class<*>, dataToPass: ComputadorEntity) {
        val intent = Intent(this, activityClass).apply {
            putExtra("selectedComputador", dataToPass)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
        finish()
    }
}
