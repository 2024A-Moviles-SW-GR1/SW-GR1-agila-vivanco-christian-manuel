package org.example

import java.io.File
import java.nio.charset.Charset
import java.time.LocalDate
import java.util.*

class Componente (
    val id: Int,
    val nombre: String,
    val version: String,
    val fechaActualizacion: LocalDate
) {
    companion object {
        private var nextId: Int = 1

        fun guardarComponente(componente: Componente, filePath: String) {
            val datosComponente = "${componente.id},${componente.nombre},${componente.version},${componente.fechaActualizacion}\n"
            File(filePath).appendText(datosComponente, Charsets.UTF_8)
        }

        fun cargarComponentes(filePath: String): List<Componente> {
            val componentesData = File(filePath).readLines()
            val componentes = mutableListOf<Componente>()
            for (componenteData in componentesData) {
                val parts = componenteData.split(",")
                if (parts.size >= 4) {
                    val id = parts[0].toInt()
                    val nombre = parts[1]
                    val version = parts[2]
                    val fechaActualizacion = LocalDate.parse(parts[3])
                    componentes.add(Componente(id, nombre, version, fechaActualizacion))
                    if (id >= nextId) {
                        nextId = id + 1
                    }
                } else {
                    println("Error: Formato de datos incorrecto en la línea: $componenteData")
                }
            }
            return componentes
        }

        fun eliminarComponente(nombre: String, filePath: String): Boolean {
            val componentes = cargarComponentes(filePath).toMutableList()
            val iterator = componentes.iterator()
            var eliminado = false
            while (iterator.hasNext()) {
                val componente = iterator.next()
                if (componente.nombre == nombre) {
                    iterator.remove()
                    eliminado = true
                }
            }
            if (eliminado) {
                File(filePath).writeText("") // Borra el archivo actual
                componentes.forEach { guardarComponente(it, filePath) }
            }
            return eliminado
        }

        fun actualizarComponente(nombre: String, version: String, fechaActualizacion: LocalDate, filePath: String): Boolean {
            val componentes = cargarComponentes(filePath).toMutableList()
            var actualizado = false
            for (i in componentes.indices) {
                val componente = componentes[i]
                if (componente.nombre == nombre) {
                    componentes[i] = Componente(componente.id, nombre, version, fechaActualizacion)
                    actualizado = true
                    break
                }
            }
            if (actualizado) {
                File(filePath).writeText("") // Borra el archivo actual
                componentes.forEach { guardarComponente(it, filePath) }
            }
            return actualizado
        }

        fun crearYGuardarComponente(nombre: String, version: String, fechaActualizacion: LocalDate, filePath: String) {
            val componente = Componente(nextId++, nombre, version, fechaActualizacion)
            guardarComponente(componente, filePath)
        }
    }
}
