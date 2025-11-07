package org.example

import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JFrame
import javax.swing.JPanel

// Clase que implementa Renderer usando Swing
class RendererSwing(
    private val ancho: Int,  // ancho de la ventana
    private val alto: Int,   // alto de la ventana
    private val indicador: IndicadorDeApuntado? = null // indicador opcional
) : Renderer {

    // Ventana principal del juego (pública para acceder desde JuegoSwing)
    val frame = JFrame("Juego - Swing")

    // Panel encargado de dibujar los elementos
    private val panel = object : JPanel() {
        var elementos: List<ElementoDeJuego> = emptyList()  // elementos actuales a dibujar

        // Método de Swing para redibujar el panel
        override fun paintComponent(g: Graphics) {
            super.paintComponent(g)
            val g2 = g as Graphics2D

            // Fondo negro
            g2.color = Color.BLACK
            g2.fillRect(0, 0, ancho, alto)

            // Dibujar cada elemento del juego
            for (e in elementos) {
                val x = e.area.posicion.x.toInt()
                val y = alto - e.area.posicion.y.toInt() // invertir eje Y
                val w = e.area.tamaño.x.toInt()
                val h = e.area.tamaño.y.toInt()

                // Elegir color según el tipo de elemento
                g2.color = when (e) {
                    is Canon -> Color.RED
                    is Bola -> Color.YELLOW
                    is Objetivo -> Color.GREEN
                    is Obstaculo -> Color.GRAY
                    else -> Color.WHITE
                }

                // Dibujar el elemento
                g2.fillRect(x, y - h, w, h)
            }

            // Dibujar el indicador de apuntado (si existe)
            indicador?.dibujar(g2, alto)
        }
    }

    init {
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(ancho, alto)
        frame.add(panel)
        frame.isVisible = true
    }

    // Método llamado desde el juego cada ciclo de dibujo
    override fun dibujar(elementos: List<ElementoDeJuego>) {
        panel.elementos = elementos
        panel.repaint()
    }
}