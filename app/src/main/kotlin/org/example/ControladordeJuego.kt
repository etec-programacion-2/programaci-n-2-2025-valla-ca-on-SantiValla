package org.example

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

    fun dispararBola() {
        if (bolasRestantes > 0 && estado == EstadoJuego.JUGANDO) {
            val bola = canon.disparar()
            bolasEnJuego.add(bola)
            bolasRestantes--
        } else if (bolasRestantes == 0) {
            println("No quedan más bolas.")
        }
    }

    fun actualizar(deltaTiempo: Double) {
        if (estado != EstadoJuego.JUGANDO) return

        // Actualizar bolas
        for (bola in bolasEnJuego.toList()) {
            bola.actualizar(deltaTiempo)

            val colision = motorFisica.detectarColisiones(bola, nivelActual.obstaculos + nivelActual.objetivos, alto)
            if (colision != null) {
                when (colision) {
                    is Objetivo -> {
                        nivelActual.objetivos = nivelActual.objetivos - colision
                        bolasEnJuego.remove(bola)
                        if (nivelActual.objetivos.isEmpty()) pasarAlSiguienteNivel()
                    }

                    is Obstaculo -> {
                        bolasEnJuego.remove(bola)
                    }
                }
            }
        }

        // Quitar bolas fuera de la pantalla
        bolasEnJuego.removeIf { it.area.posicion.y < 0 || it.area.posicion.x > ancho }

        // Actualizar obstáculos giratorios
        for (obstaculo in nivelActual.obstaculos) {
            obstaculo.actualizar(deltaTiempo)
        }

        // Condición de derrota
        if (bolasRestantes == 0 && bolasEnJuego.isEmpty() && nivelActual.objetivos.isNotEmpty()) {
            estado = EstadoJuego.DERROTA
        }

        renderer.dibujar(obtenerElementosParaRender())
    }
    //Método para pasar al siguiente nivel
    private fun pasarAlSiguienteNivel() {
        indiceNivel++
        if (indiceNivel >= niveles.size) {
            estado = EstadoJuego.VICTORIA
        } else {
            bolasRestantes = niveles[indiceNivel].limiteDeBolas
            println("Pasando al nivel ${indiceNivel + 1}")
        }
    }

    fun obtenerElementosParaRender(): List<ElementoDeJuego> {
        val elementos = mutableListOf<ElementoDeJuego>()
        elementos.addAll(nivelActual.obstaculos)
        elementos.addAll(nivelActual.objetivos)
        elementos.addAll(bolasEnJuego)
        elementos.add(canon)
        return elementos
    }
}