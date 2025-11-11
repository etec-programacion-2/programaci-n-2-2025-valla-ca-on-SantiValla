package org.example

import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent

class JuegoSwing(
    private val ancho: Int = 800,
    private val alto: Int = 600
) {
    private var niveles = CargadorNiveles.cargarNiveles()
    private var canon = Canon(Rectangulo(Vector(30.0, 50.0), Vector(50.0, 20.0)), 45.0)
    private var renderer = RendererSwing(ancho, alto)
    private var controlador = ControladorDeJuego(niveles, canon, renderer, ancho, alto)
    private var direccionAngulo: Int = 1
    private var pantallaFinal: PantallaFinal? = null
    fun ejecutar() {
        renderer.frame.addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent) {
                if (controlador.estaJugando()) {
                    when (e.keyCode) {
                        KeyEvent.VK_SPACE -> controlador.dispararBola() 
                        KeyEvent.VK_ESCAPE -> cerrar()
                    }
                } else {
                    pantallaFinal?.manejarEntrada(e.keyCode, this@JuegoSwing)
                }
            }
        })
        //hilo de simulacion del juego
        Thread {
            var corriendo = true
            while (corriendo) {
                if (controlador.estaJugando()) {
                    controlador.actualizar(0.1)
                    canon.apuntar(canon.angulo + direccionAngulo * 2)

                    if (canon.angulo >= 90) direccionAngulo = -1
                    if (canon.angulo <= 0) direccionAngulo = 1
                } else {
                    // 🔹 Mostrar pantalla final solo una vez al finalizar el juego
                    when (controlador.obtenerEstado()) {
                        EstadoJuego.VICTORIA -> {
                            if (pantallaFinal == null) pantallaFinal = PantallaVictoria()
                            renderer.dibujarPantallaFinal(pantallaFinal!!)
                        }
                        EstadoJuego.DERROTA -> {
                            if (pantallaFinal == null) pantallaFinal = PantallaDerrota()
                            renderer.dibujarPantallaFinal(pantallaFinal!!)
                        }
                        else -> {}
                    }

                    // Esperamos un instante y luego salimos del bucle
                    Thread.sleep(2000)
                    corriendo = false
                }

                Thread.sleep(50)
            }
        }.start()
    }

    fun cerrar() {
        renderer.frame.dispose()
    }
}