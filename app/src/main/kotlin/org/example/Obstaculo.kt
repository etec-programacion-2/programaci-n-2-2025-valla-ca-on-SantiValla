package org.example

import java.awt.Shape
import java.awt.geom.AffineTransform
import java.awt.geom.Rectangle2D

class Obstaculo(
    area: Rectangulo,
    private var _tipo: TipoObstaculo
) : ElementoDeJuego(area) {
    val tipo = _tipo
    var angulo: Double = 0.0  // ángulo de rotación actual, lo hacemos publico para su uso en controlador de juego

    fun actualizar(deltaTiempo: Double) {
        when (tipo) {
            TipoObstaculo.ESTATICO -> {
                // No hace nada
            }
            TipoObstaculo.GIRATORIO -> {
                angulo = (angulo + 45 * deltaTiempo) % 360 
            }
        }
    }

    fun obtenerAngulo(): Double = angulo
    fun obtenerFormaRotada(): Shape {
    val rect = java.awt.geom.Rectangle2D.Double(
        area.posicion.x,
        area.posicion.y,
        area.tamaño.x,
        area.tamaño.y
    )

    val transform = java.awt.geom.AffineTransform()
    transform.rotate(
        Math.toRadians(angulo),
        area.posicion.x + area.tamaño.x / 2,
        area.posicion.y + area.tamaño.y / 2
    )

    return transform.createTransformedShape(rect)
}
}
