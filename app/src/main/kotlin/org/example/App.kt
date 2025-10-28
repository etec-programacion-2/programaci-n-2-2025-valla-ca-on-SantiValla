package org.example
fun main() {
    val niveles = CargadorNiveles.cargarNiveles()
    val canon = Canon(Rectangulo(Vector(0.0, 0.0), Vector(10.0, 20.0)), 45.0)
    val controlador = ControladorDeJuego(niveles, canon)

    controlador.dispararBola()

    var tiempoAnterior = System.nanoTime()
    while (controlador.estaJugando()) {
        val tiempoActual = System.nanoTime()
        val deltaTiempo = (tiempoActual - tiempoAnterior) / 1_000_000_000.0
        tiempoAnterior = tiempoActual

        controlador.actualizar(deltaTiempo)
        Thread.sleep(16)
    }

    println("Fin del juego. Estado: ${controlador}")
}