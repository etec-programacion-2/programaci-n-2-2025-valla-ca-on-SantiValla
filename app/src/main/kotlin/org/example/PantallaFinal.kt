package org.example 
import java.awt.Graphics2D
import java.awt.event.KeyEvent

interface PantallaFinal {
    fun dibujar(g: Graphics2D)
    fun manejarEntrada(tecla: Int, juego: JuegoSwing)
}