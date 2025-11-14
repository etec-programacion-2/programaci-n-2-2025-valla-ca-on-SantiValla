package org.example

import java.io.IOException

class RendererCLI(
    private val ancho: Int = 60,
    private val alto: Int = 20
) : Renderer {

    override fun dibujar(elementos: List<ElementoDeJuego>) {
        //limpiar consola
        print("\u001b[H\u001b[2J")
        System.out.flush()   

        val grilla = Array(alto) { CharArray(ancho) { ' ' } } //la pantalla es una matriz de caracteres que representan espacios vacíos

        for (e in elementos) {
            val pos = e.area.posicion
            val x = pos.x.toInt().coerceIn(0, ancho - 1) //la grilla usa índices enteros asi que pasamos esos valores decimales a enteros
            val y = (alto - 1 - pos.y.toInt()).coerceIn(0, alto - 1) //hacemos lo mismo pero tambien reflejamos la coordenada y para que 0 se encuentre en la parte inferior de la pantalla, y no en la superior

            val simbolo = when (e) {
                is Canon -> 'C'
                is Bola -> 'o'
                is Objetivo -> 'X'
                is Obstaculo -> '#'
                else -> '?'
            }

            grilla[y][x] = simbolo
        }

        for (fila in grilla) {
            println(fila.concatToString())
        }
    }
}