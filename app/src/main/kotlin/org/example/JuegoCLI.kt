package org.example

class JuegoCLI(
    private val ancho: Int = 60,
    private val alto: Int = 20
) {
    private val niveles = CargadorNiveles.cargarNiveles()
    private val canon = Canon(Rectangulo(Vector(0.0, 0.0), Vector(2.0, 2.0)), 45.0)
    private val renderer = RendererCLI(ancho, alto)
    private val controlador = ControladorDeJuego(niveles, canon, renderer)
    fun ejecutar() {
    println("=== Juego iniciado ===")
    println("Comandos disponibles: 'disparar' para lanzar, 'salir' para terminar.\n")

    var jugando = true
    while (jugando && controlador.estaJugando()) {
        // Dibujar el estado actual antes de pedir entrada
        renderer.dibujar(controlador.obtenerElementosParaRender())

        // Actualizar el juego
        controlador.actualizar(0.1)

        // Revisar si el juego terminó
        if (!controlador.estaJugando()) {
            break // termina el bucle si ya no está jugando
        }

        // Solicitar input del usuario
        print("> ")
        val comando = readLine()?.lowercase()

        when (comando) {
            "disparar" -> {
                controlador.dispararBola()
                canon.apuntar((canon.angulo + 5) % 360)
            }
            "salir" -> jugando = false
            else -> println("Comando no reconocido. Usa 'disparar' o 'salir'.")
        }
    }

    println("Fin del juego.")
}
}