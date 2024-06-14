package org.example

import java.io.File
import java.nio.charset.Charset

class Computador(
    val id: Int,
    val marca: String,
    val modelo: String,
    val anioLanzamiento: Int,
    val precio: Double,
    val componentes: MutableList<Componente> = mutableListOf()
) {
    companion object {
        private var nextId: Int = 1

        fun guardarComputador(computador: Computador, filePath: String) {
            val datosComputador = "${computador.id},${computador.marca},${computador.modelo},${computador.anioLanzamiento},${computador.precio},${computador.componentes.toString()}\n"
            File(filePath).appendText(datosComputador, Charset.defaultCharset())
        }

        fun cargarComputadores(filePath: String): List<Computador> {
            val computadoresData = File(filePath).readLines()
            val computadores = mutableListOf<Computador>()
            for (computadorData in computadoresData) {
                val parts = computadorData.split(",")
                if (parts.size >= 5) {
                    val id = parts[0].toInt()
                    val marca = parts[1]
                    val modelo = parts[2]
                    val anioLanzamiento = parts[3].toInt()
                    val precio = parts[4].toDouble()
                    computadores.add(Computador(id, marca, modelo, anioLanzamiento, precio))
                    if (id >= nextId) {
                        nextId = id + 1
                    }
                } else {
                    println("Error: Formato de datos incorrecto en la línea: $computadorData")
                }
            }
            return computadores
        }

        fun eliminarComputador(marca: String, modelo: String, filePath: String): Boolean {
            val computadores = cargarComputadores(filePath).toMutableList()
            val iterator = computadores.iterator()
            var eliminado = false
            while (iterator.hasNext()) {
                val computador = iterator.next()
                if (computador.marca == marca && computador.modelo == modelo) {
                    iterator.remove()
                    eliminado = true
                }
            }
            if (eliminado) {
                File(filePath).writeText("") // Borra el archivo actual
                computadores.forEach { guardarComputador(it, filePath) }
            }
            return eliminado
        }

        fun actualizarComputador(computadorActualizado: Computador, filePath: String): Boolean {
            val computadores = cargarComputadores(filePath).toMutableList()
            var actualizado = false
            for (i in computadores.indices) {
                if (computadores[i].id == computadorActualizado.id) {
                    computadores[i] = Computador(
                        computadores[i].id,
                        computadorActualizado.marca,
                        computadorActualizado.modelo,
                        computadorActualizado.anioLanzamiento,
                        computadorActualizado.precio,
                        computadorActualizado.componentes
                    )
                    actualizado = true
                    break
                }
            }
            if (actualizado) {
                File(filePath).writeText("") // Borra el archivo actual
                computadores.forEach { guardarComputador(it, filePath) }
            }
            return actualizado
        }

        fun crearYGuardarComputador(marca: String, modelo: String, anioLanzamiento: Int, precio: Double, filePath: String) {
            val computador = Computador(nextId++, marca, modelo, anioLanzamiento, precio)
            guardarComputador(computador, filePath)
        }
    }
}


