package org.example

import java.awt.Shape
import java.awt.geom.AffineTransform
import java.awt.geom.Area
import java.awt.geom.Rectangle2D
import java.awt.geom.Ellipse2D
import kotlin.math.max

class ControladorDeJuego(
    private val niveles: List<Nivel>,
    private val canon: Canon,
    private val renderer: Renderer,
    private val ancho: Int,   
    private val alto: Int     
) {
    private var indiceNivel = 0
    private var estado = EstadoJuego.JUGANDO
    private var bolasRestantes = niveles[indiceNivel].limiteDeBolas
    private val motorFisica = MotorDeFisica()
    private val bolasEnJuego = mutableListOf<Bola>()
    
    private val nivelActual: Nivel
    get() = if (indiceNivel in niveles.indices) niveles[indiceNivel]
    
        else niveles.last()

    fun estaJugando(): Boolean = estado == EstadoJuego.JUGANDO
    fun obtenerEstado(): EstadoJuego = estado

    // Dispara una nueva bola desde el cañón
    fun dispararBola() {
        if (bolasRestantes > 0 && estado == EstadoJuego.JUGANDO) {
            val bola = canon.disparar()
            bolasEnJuego.add(bola)
            bolasRestantes--
        } else {
            println(" No quedan más bolas o el juego no está activo.")
        }
    }

    // Bucle de actualización del juego
    fun actualizar(deltaTiempo: Double) {
        if (estado != EstadoJuego.JUGANDO) return

        // Actualizar posición de las bolas
        for (bola in bolasEnJuego) {
            bola.actualizar(deltaTiempo)
        }

        // Eliminar bolas fuera de pantalla
        bolasEnJuego.removeIf { bola ->
            bola.area.posicion.y < 0 || bola.area.posicion.x > 800
        }

        // Actualizar obstáculos giratorios
        for (obstaculo in nivelActual.obstaculos) {
            obstaculo.actualizar(deltaTiempo)
        }

        // DETECCIÓN DE COLISIONES (en coordenadas de pantalla)
        for (bola in bolasEnJuego.toList()) {
            val bx = bola.area.posicion.x
            val by = bola.area.posicion.y
            val bw = bola.area.tamaño.x
            val bh = bola.area.tamaño.y

            // Coordenadas en pantalla (mismo cálculo que en RendererSwing)
            val bolaScreenX = bx
            val bolaScreenY = alto - (by + bh)
            val bolaShape = Ellipse2D.Double(bolaScreenX, bolaScreenY, bw, bh)
            val areaBola = Area(bolaShape)

            var colisionDetectada = false

            // COLISIÓN CON OBJETIVOS 
            for (objetivo in nivelActual.objetivos.toList()) {
                val ox = objetivo.area.posicion.x
                val oy = objetivo.area.posicion.y
                val ow = objetivo.area.tamaño.x
                val oh = objetivo.area.tamaño.y

                val objScreenX = ox
                val objScreenY = alto - (oy + oh)
                val rectObj = Rectangle2D.Double(objScreenX, objScreenY, ow, oh)
                val areaObj = Area(rectObj)

                val inter1 = Area(areaBola)
                inter1.intersect(areaObj)
                if (!inter1.isEmpty) {
                    nivelActual.objetivos = nivelActual.objetivos - objetivo
                    bolasEnJuego.remove(bola)
                    colisionDetectada = true
                    if (nivelActual.objetivos.isEmpty()) {
                        pasarAlSiguienteNivel()
                    }
                    break
                }
            }

            //COLISIÓN CON OBSTÁCULOS 
            if (!colisionDetectada) {
                for (obstaculo in nivelActual.obstaculos) {
                    val ox = obstaculo.area.posicion.x
                    val oy = obstaculo.area.posicion.y
                    val ow = obstaculo.area.tamaño.x
                    val oh = obstaculo.area.tamaño.y

                    // Coordenadas en pantalla
                    val rectScreen = Rectangle2D.Double(
                        ox,
                        alto - (oy + oh),
                        ow,
                        oh
                    )

                    // Si el obstáculo es estático
                    if (obstaculo.tipo == TipoObstaculo.ESTATICO) {
                        val interObj = Area(areaBola)
                        interObj.intersect(Area(rectScreen))
                        if (!interObj.isEmpty) {
                            bolasEnJuego.remove(bola)
                            colisionDetectada = true
                            break
                        } else continue
                    }

                    // Si es giratorio
                    val centroX = ox + ow / 2.0
                    val centroY = alto - (oy + oh / 2.0)

                    val transform = AffineTransform()
                    transform.rotate(Math.toRadians(obstaculo.angulo), centroX, centroY)

                    val rectRotadoScreen: Shape = transform.createTransformedShape(rectScreen)
                    val areaObst = Area(rectRotadoScreen)

                    val inter = Area(areaBola)
                    inter.intersect(areaObst)
                    if (!inter.isEmpty) {
                        bolasEnJuego.remove(bola)
                        colisionDetectada = true
                        break
                    }
                }
            }
        }

        // --- DERROTA ---
        if (bolasRestantes == 0 && bolasEnJuego.isEmpty() && nivelActual.objetivos.isNotEmpty()) {
            estado = EstadoJuego.DERROTA
            println("Te has quedado sin bolas, fin del juego.")
        }

        // Dibujar
        renderer.dibujar(obtenerElementosParaRender())
    }

    // Cambiar de nivel
    private fun pasarAlSiguienteNivel() {
        indiceNivel++
        if (indiceNivel >= niveles.size) {
            estado = EstadoJuego.VICTORIA
            println("Has completado el juego.")
        } else {
            bolasRestantes = niveles[indiceNivel].limiteDeBolas
            println("🎉 Nivel ${indiceNivel + 1} iniciado.")
        }
    }

    // Elementos del nivel actual
    private fun obtenerElementosDelNivel(): List<ElementoDeJuego> {
        val elementos = mutableListOf<ElementoDeJuego>()
        elementos.addAll(nivelActual.obstaculos)
        elementos.addAll(nivelActual.objetivos)
        return elementos
    }

    // Elementos a renderizar
    fun obtenerElementosParaRender(): List<ElementoDeJuego> {
        val elementos = mutableListOf<ElementoDeJuego>()
        elementos.addAll(nivelActual.obstaculos)
        elementos.addAll(nivelActual.objetivos)
        elementos.addAll(bolasEnJuego)
        elementos.add(canon)
        return elementos
    }
}