package org.example

import java.awt.Shape
import java.awt.geom.AffineTransform
import java.awt.geom.Area
import java.awt.geom.Ellipse2D
import java.awt.geom.Rectangle2D

class MotorDeFisica {

    //Detecta colisiones entre una bola y los elementos del nivel
    //Devuelve el primer elemento con el que colisiona, o null si no hay colisión.
    fun detectarColisiones(
        bola: Bola,
        elementos: List<ElementoDeJuego>,
        altoPantalla: Int
    ): ElementoDeJuego? {

        // Crear el área de la bola (coordenadas de pantalla)
        val bx = bola.area.posicion.x
        val by = bola.area.posicion.y
        val bw = bola.area.tamaño.x
        val bh = bola.area.tamaño.y

        // En coordenadas de pantalla (invirtiendo Y)
        val bolaScreenY = altoPantalla - (by + bh)
        val areaBola = Area(Ellipse2D.Double(bx, bolaScreenY, bw, bh))

        // Recorre los elementos
        for (elemento in elementos) {
            when (elemento) {
                is Obstaculo -> {
                    //el area del obstaculo, su posicion y tamaño
                    val ox = elemento.area.posicion.x
                    val oy = elemento.area.posicion.y
                    val ow = elemento.area.tamaño.x
                    val oh = elemento.area.tamaño.y

                    val rectScreenY = altoPantalla - (oy + oh)
                    val rect = Rectangle2D.Double(ox, rectScreenY, ow, oh)
                    //el comportamiento del area del obstaculo, en el caso de ser un obstaculo giratorio
                    val areaElemento = if (elemento.tipo == TipoObstaculo.GIRATORIO) {
                        val centroX = ox + ow / 2.0
                        val centroY = rectScreenY + oh / 2.0

                        val transform = AffineTransform()
                        transform.rotate(Math.toRadians(elemento.angulo), centroX, centroY)
                        Area(transform.createTransformedShape(rect))
                    } else {
                        Area(rect)
                    }

                    val inter = Area(areaBola) //Se crea un nuevo objeto Area a partir del área de la bola.
                    inter.intersect(areaElemento) //Esta línea modifica el área inter, quedándose solo con la parte que se superpone entre la bola (areaBola) y el obstáculo (areaObst).
                    if (!inter.isEmpty) return elemento //Si la intersección no está vacía, entonces hay una colisión y tambien nos retorna el elemento con el que colisionó
                }

                is Objetivo -> {
                    val ox = elemento.area.posicion.x
                    val oy = elemento.area.posicion.y
                    val ow = elemento.area.tamaño.x
                    val oh = elemento.area.tamaño.y

                    val rectScreenY = altoPantalla - (oy + oh)
                    val rect = Rectangle2D.Double(ox, rectScreenY, ow, oh)
                    val inter = Area(areaBola)
                    inter.intersect(Area(rect))
                    if (!inter.isEmpty) return elemento
                }
            }
        }

        return null
    }
}