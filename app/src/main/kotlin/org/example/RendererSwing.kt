package org.example

import java.awt.Color
import java.awt.Graphics
import javax.swing.JFrame
import javax.swing.JPanel

// Clase que implementa Renderer usando Swing
class RendererSwing(
    private val ancho: Int,  // ancho de la ventana
    private val alto: Int, // alto de la ventana
) : Renderer {

    // ventana principal del juego
    val frame = JFrame("Juego - Swing") //la hacemos publica para usarla desde JuegoSwing

    // dibujo de los elementos del juego
    private val panel = object : JPanel() {
        var elementos: List<ElementoDeJuego> = emptyList()  // elementos actuales a dibujar

        // Método llamado automáticamente por Swing para dibujar el panel
        override fun paintComponent(g: Graphics) {
            super.paintComponent(g)
            g.color = Color.BLACK
            g.fillRect(0, 0, ancho, alto)  // fondo negro

            // Dibujar cada elemento
            for (e in elementos) {
                val x = e.area.posicion.x.toInt()
                val y = alto - e.area.posicion.y.toInt() // invertir eje y para que (0,0) quede abajo
                val w = e.area.tamaño.x.toInt()
                val h = e.area.tamaño.y.toInt()

                // Elegir color según tipo de elemento
                g.color = when (e) {
                    is Canon -> Color.RED
                    is Bola -> Color.YELLOW
                    is Objetivo -> Color.GREEN
                    is Obstaculo -> Color.GRAY
                    else -> Color.WHITE
                }

                // Dibujar un rectángulo que representa el elemento
                g.fillRect(x, y - h, w, h) // restamos h porque coordenada y es arriba del rectángulo
            }
        }
    }
    
    init {
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.setSize(ancho, alto)
    frame.add(panel)
    frame.isVisible = true

}

    // Este método será llamado desde ControladorDeJuego cada ciclo
    override fun dibujar(elementos: List<ElementoDeJuego>) {
        panel.elementos = elementos
        panel.repaint() // pide a Swing que redibuje el panel con los nuevos elementos
    }
}
