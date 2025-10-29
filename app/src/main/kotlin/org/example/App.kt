package org.example

fun main() {
    val niveles = CargadorNiveles.cargarNiveles()
    val canon = Canon(Rectangulo(Vector(10.0, 5.0), Vector(2.0, 2.0)), 45.0)
    val renderer = RendererCLI(60, 20)
    val controlador = ControladorDeJuego(niveles, canon, renderer)

    controlador.dispararBola()

    // Simulación del bucle del juego
    var tiempo = 0.0
    while (controlador.estaJugando() && tiempo < 10.0) {
        controlador.actualizar(0.1)
        Thread.sleep(100)
        tiempo += 0.1
    }

    println("Fin del juego")
}