package com.example.a06_examen02

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ComponentesList : AppCompatActivity() {
    private var allComponentes: ArrayList<ComponenteEntity> = arrayListOf()
    private var adapter: ArrayAdapter<ComponenteEntity>? = null
    private var selectedRegisterPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_componente_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.view_componentes)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val selectedComputador = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("selectedComputador", ComputadorEntity::class.java)
        } else {
            intent.getParcelableExtra<ComputadorEntity>("selectedComputador")
        }

        val componentesClass = findViewById<TextView>(R.id.txt_view_computador_name)
        componentesClass.text = selectedComputador!!.name

        DataBase.tables = SqliteHelper(this)
        val componentesList = findViewById<ListView>(R.id.list_componentes)
        allComponentes = DataBase.tables.getComponentesByComputador(selectedComputador.id)
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            allComponentes
        )

        componentesList.adapter = adapter
        adapter!!.notifyDataSetChanged() // To update the UI

        val btnRedirectCreateComponente = findViewById<Button>(R.id.btn_redirect_create_componente)
        btnRedirectCreateComponente.setOnClickListener {
            goToActivity(CreateUpdateComponente::class.java, null, selectedComputador)
        }
        val btnBackToMain = findViewById<Button>(R.id.btn_back_to_main)
        btnBackToMain.setOnClickListener {
            goToActivity(MainActivity::class.java)
        }

        registerForContextMenu(componentesList)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        // Set options for the context menu
        val inflater = menuInflater
        inflater.inflate(R.menu.componente_menu, menu)

        // Get ID of the selected item of the list
        val register = menuInfo as AdapterView.AdapterContextMenuInfo
        selectedRegisterPosition = register.position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val selectedComputador = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("selectedComputador", ComputadorEntity::class.java)
        } else {
            intent.getParcelableExtra<ComputadorEntity>("selectedComputador")
        }

        return when (item.itemId) {
            R.id.mi_edit_componente -> {
                goToActivity(
                    CreateUpdateComponente::class.java,
                    allComponentes[selectedRegisterPosition],
                    selectedComputador!!,
                    false
                )
                true
            }

            R.id.mi_delete_componente -> {
                openDialog(allComponentes[selectedRegisterPosition].id)
                true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    private fun goToActivity(
        activityClass: Class<*>,
        dataToPass: ComponenteEntity? = null,
        dataToPass2: ComputadorEntity? = null,
        create: Boolean = true
    ) {
        val intent = Intent(this, activityClass)
        if (dataToPass != null) {
            intent.putExtra("selectedComponente", dataToPass)
        }
        if (dataToPass2 != null) {
            intent.putExtra("selectedComputador", dataToPass2)
        }
        intent.putExtra("create", create)
        startActivity(intent)
        finish()
    }

    private fun openDialog(registerIndex: Int) {
        val selectedComputador = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("selectedComputador", ComputadorEntity::class.java)
        } else {
            intent.getParcelableExtra<ComputadorEntity>("selectedComputador")
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Está seguro de eliminar el componente?")
        builder.setPositiveButton(
            "Eliminar"
        ) { _, _ ->
            DataBase.tables.deleteComponente(registerIndex)
            allComponentes.clear()
            allComponentes.addAll(DataBase.tables.getComponentesByComputador(selectedComputador!!.id))
            adapter!!.notifyDataSetChanged()
        }
        builder.setNegativeButton("Cancelar", null)

        builder.create().show()
    }
}
