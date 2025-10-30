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
        var jugando = true
        while (jugando && controlador.estaJugando()) {
            when (readLine()?.lowercase()) {
                "disparar" -> controlador.dispararBola()
                "salir" -> jugando = false
            }
            controlador.actualizar(0.1)
            canon.apuntar((canon.angulo + 5) % 360)
        }
        println("Fin del juego")
    }
}