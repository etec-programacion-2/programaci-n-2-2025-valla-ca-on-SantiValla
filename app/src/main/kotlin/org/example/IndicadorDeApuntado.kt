package org.example

import java.awt.Color
import java.awt.Graphics2D
import kotlin.math.cos
import kotlin.math.sin

class IndicadorDeApuntado(
    private val canon: Canon,
    private val radio: Double = 50.0
) {

    fun dibujar(g: Graphics2D, alto: Int) {
        val anguloRad = Math.toRadians(canon.angulo)

        // Calcular posición del punto (invirtiendo eje Y como en RendererSwing)
        val x = canon.area.posicion.x + cos(anguloRad) * radio
        val y = alto - (canon.area.posicion.y + sin(anguloRad) * radio)

        g.color = Color.WHITE
        g.fillOval(x.toInt() - 3, y.toInt() - 3, 6, 6)
    }
}
