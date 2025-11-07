package org.example

import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent

class JuegoSwing(
    private val ancho: Int = 800,
    private val alto: Int = 600
) {
    private val niveles = CargadorNiveles.cargarNiveles()
    private val canon = Canon(Rectangulo(Vector(30.0, 5.0), Vector(50.0, 20.0)), 45.0)
    private val renderer = RendererSwing(ancho, alto)
    private val indicador = IndicadorDeApuntado(canon)
    private val controlador = ControladorDeJuego(niveles, canon, renderer)

    fun ejecutar() {
        // Agregamos el listener de teclado aquí
        renderer.frame.addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent) {
                when (e.keyCode) {
                    KeyEvent.VK_SPACE -> controlador.dispararBola() // disparar
                    KeyEvent.VK_ESCAPE -> renderer.frame.dispose()  // cerrar ventana
                }
            }
        })

        // Bucle del juego en un hilo separado
        Thread {
            var tiempo = 0.0
            while (controlador.estaJugando() && tiempo < 30.0) {
                controlador.actualizar(0.1)
                Thread.sleep(50)
                canon.apuntar((canon.angulo + 5) % 360)
                tiempo += 0.05
            }
        }.start()
    }
}