package org.example

import java.time.*
import java.time.format.DateTimeFormatter

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    //tipo variables

    val inmutable = "Christian";

    var mutable = "Agila"
    mutable = "Agila";





    val pathComponente = "C:\\Users\\Christian\\SW-GR1-agila-vivanco-christian-manuel\\02-Deberes\\DeberIB-AppsMoviles-ChristianAgila-GR1SW\\DeberIB\\src\\main\\data\\componente.txt"
    val pathComputador = "C:\\Users\\Christian\\SW-GR1-agila-vivanco-christian-manuel\\02-Deberes\\DeberIB-AppsMoviles-ChristianAgila-GR1SW\\DeberIB\\src\\main\\data\\computador.txt"
    while (true) {
        println("\nSeleccione una opción:")
        println("1. Mostrar todos los computadores")
        println("2. Agregar un nuevo computador")
        println("3. Eliminar un computador")
        println("4. Actualizar un computador")
        println("5. Mostrar todos los componentes")
        println("6. Agregar un nuevo componente")
        println("7. Eliminar un componente")
        println("8. Actualizar un componente")
       // println("9. Agregar un componente a un computador")
        println("10. Salir")

        when (readLine()?.toIntOrNull()) {
            1 -> {
                val computadores = Computador.cargarComputadores(pathComputador)
                if (computadores.isNotEmpty()) {
                    println("Computadores registrados:")
                    computadores.forEach { computador ->
                        println("ID: ${computador.id}, Marca: ${computador.marca}, Modelo: ${computador.modelo}, Año: ${computador.anioLanzamiento}, Precio: ${computador.precio}, Componentes: ${computador.componentes.map { it.id }}")
                    }
                } else {
                    println("No hay computadores registrados.")
                }
            }
            2 -> {
                println("Ingrese la marca del computador:")
                val marca = readLine().toString()

                println("Ingrese el modelo del computador:")
                val modelo = readLine().toString()

                println("Ingrese el año de lanzamiento del computador:")
                val anioLanzamiento = readLine()?.toIntOrNull() ?: 0

                println("Ingrese el precio del computador:")
                val precio = readLine()?.toDoubleOrNull() ?: 0.0

                Computador.crearYGuardarComputador(marca, modelo, anioLanzamiento, precio, pathComputador)
                println("¡Computador agregado con éxito!")
            }
            3 -> {
                println("Ingrese la marca del computador a eliminar:")
                val marca = readLine().toString()

                println("Ingrese el modelo del computador a eliminar:")
                val modelo = readLine().toString()

                if (Computador.eliminarComputador(marca, modelo, pathComputador)) {
                    println("¡Computador eliminado con éxito!")
                } else {
                    println("No se encontró un computador con la marca y modelo especificados.")
                }
            }
            4 -> {
                println("Ingrese la marca del computador a actualizar:")
                val marca = readLine().toString()

                println("Ingrese el modelo del computador a actualizar:")
                val modelo = readLine().toString()

                println("Ingrese el nuevo año de lanzamiento del computador:")
                val anioLanzamiento = readLine()?.toIntOrNull() ?: 0

                println("Ingrese el nuevo precio del computador:")
                val precio = readLine()?.toDoubleOrNull() ?: 0.0

                val computadores = Computador.cargarComputadores(pathComputador)
                val computadorActualizado = computadores.find { it.marca == marca && it.modelo == modelo }
                if (computadorActualizado != null) {
                    val actualizado = Computador(
                        computadorActualizado.id, marca, modelo, anioLanzamiento, precio, computadorActualizado.componentes
                    )
                    if (Computador.actualizarComputador(actualizado, pathComputador)) {
                        println("¡Computador actualizado con éxito!")
                    }
                } else {
                    println("No se encontró un computador con la marca y modelo especificados.")
                }
            }
            5 -> {
                val componentes = Componente.cargarComponentes(pathComponente)
                if (componentes.isNotEmpty()) {
                    println("Componentes registrados:")
                    componentes.forEach { componente ->
                        println("ID: ${componente.id}, Nombre: ${componente.nombre}, Versión: ${componente.version}, Fecha de Actualización: ${componente.fechaActualizacion}")
                    }
                } else {
                    println("No hay componentes registrados.")
                }
            }
            6 -> {
                println("Ingrese el nombre del componente:")
                val nombre = readLine().toString()

                println("Ingrese la versión del componente:")
                val version = readLine().toString()

                println("Ingrese la fecha de actualización del componente (YYYY-MM-DD):")
                val fechaActualizacion = LocalDate.parse(readLine(), DateTimeFormatter.ISO_DATE)

                Componente.crearYGuardarComponente(nombre, version, fechaActualizacion, pathComponente)
                println("¡Componente agregado con éxito!")
            }
            7 -> {
                println("Ingrese el nombre del componente a eliminar:")
                val nombre = readLine().toString()

                if (Componente.eliminarComponente(nombre, pathComponente)) {
                    println("¡Componente eliminado con éxito!")
                } else {
                    println("No se encontró un componente con el nombre especificado.")
                }
            }
            8 -> {
                println("Ingrese el nombre del componente a actualizar:")
                val nombre = readLine().toString()

                println("Ingrese la nueva versión del componente:")
                val version = readLine().toString()

                println("Ingrese la nueva fecha de actualización del componente (YYYY-MM-DD):")
                val fechaActualizacion = LocalDate.parse(readLine(), DateTimeFormatter.ISO_DATE)

                if (Componente.actualizarComponente(nombre, version, fechaActualizacion, pathComponente)) {
                    println("¡Componente actualizado con éxito!")
                } else {
                    println("No se encontró un componente con el nombre especificado.")
                }
            }
            9 -> {
                println("Ingrese el ID del computador al que desea agregar un componente:")
                val idComputador = readLine()?.toIntOrNull()
                println("Ingrese el ID del componente que desea agregar:")
                val idComponente = readLine()?.toIntOrNull()

                if (idComputador != null && idComponente != null) {
                    val computadores = Computador.cargarComputadores(pathComputador).toMutableList()
                    val componentes = Componente.cargarComponentes(pathComponente).toMutableList()

                    val computador = computadores.find { it.id == idComputador }
                    val componente = componentes.find { it.id == idComponente }

                    if (computador != null && componente != null) {
                        computador.componentes.add(componente)
                        Computador.actualizarComputador(computador, pathComputador)
                        println("¡Componente agregado con éxito al computador!")
                    } else {
                        println("No se encontró el computador o componente con el ID especificado.")
                    }
                } else {
                    println("IDs inválidos. Por favor, ingrese IDs válidos.")
                }
            }
            10 -> {
                println("Proceso terminado")
                return
            }
            else -> {
                println("Opción inválida. Por favor, seleccione una opción válida.")
            }
        }
    }
}



