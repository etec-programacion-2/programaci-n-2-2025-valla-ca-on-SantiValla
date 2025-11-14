package org.example

import java.awt.*
import java.awt.event.KeyEvent

class PantallaVictoria : PantallaFinal {
    override fun dibujar(g: Graphics2D) {
        g.color = Color.GREEN
        g.font = Font("Arial", Font.BOLD, 48)
        g.drawString("¡VICTORIA!", 250, 250)

        g.font = Font("Arial", Font.PLAIN, 24)
        g.drawString("Presiona ESC para salir", 260, 360)
    }

    override fun manejarEntrada(tecla: Int, juego: JuegoSwing) {
        when (tecla) {
            KeyEvent.VK_ESCAPE -> juego.cerrar()
        }
    }
}