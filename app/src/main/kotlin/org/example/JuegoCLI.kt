package org.example

class JuegoCLI(
    private val ancho: Int = 60,
    private val alto: Int = 20
) {
    private val niveles = CargadorNiveles.cargarNiveles()
    private val canon = Canon(Rectangulo(Vector(10.0, 5.0), Vector(2.0, 2.0)), 45.0)
    private val renderer = RendererCLI(ancho, alto)
    private val controlador = ControladorDeJuego(niveles, canon, renderer)
    fun ejecutar() {
    println("=== Juego iniciado ===")
    println("Comandos disponibles: 'disparar' para lanzar, 'salir' para terminar.\n")

    var jugando = true
    while (jugando && controlador.estaJugando()) {
        print("> ")
        val comando = readLine()

        // Si no hay entrada (por ejemplo, cuando se ejecuta desde Gradle Run)
        if (comando == null) {
            println("\nNo se puede leer entrada interactiva. Terminando el juego...")
            break
        }

        when (comando.lowercase()) {
            "disparar" -> {
                controlador.dispararBola()
                controlador.actualizar(0.1)
                canon.apuntar((canon.angulo + 5) % 360)
            }
            "salir" -> jugando = false
            else -> println("Comando no reconocido. Usa 'disparar' o 'salir'.")
        }

        renderer.dibujar(controlador.obtenerElementosParaRender())
    }

    println("Fin del juego.")
}
}