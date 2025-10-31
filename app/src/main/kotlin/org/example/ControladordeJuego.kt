package org.example

class ControladorDeJuego(
    private val niveles: List<Nivel>,
    private val canon: Canon,
    private val renderer: Renderer   
) {
    private var indiceNivel = 0
    private var estado = EstadoJuego.JUGANDO
    private var bolasRestantes = niveles[indiceNivel].limiteDeBolas
    private val motorFisica = MotorDeFisica()
    private val bolasEnJuego = mutableListOf<Bola>()

    private val nivelActual: Nivel
        get() = niveles[indiceNivel]

    fun estaJugando(): Boolean = estado == EstadoJuego.JUGANDO

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

        // Actualizar posición de todas las bolas
        for (bola in bolasEnJuego) {
            bola.actualizar(deltaTiempo)
        }

        // Eliminar bolas que salieron del escenario
        bolasEnJuego.removeIf { bola ->
            bola.area.posicion.y < 0
        }

        //  Detectar colisiones
        for (bola in bolasEnJuego.toList()) { // usar copia para evitar modificar lista mientras se recorre
            val colision = motorFisica.detectarColisiones(bola, obtenerElementosDelNivel())
            if (colision != null) {
                when (colision) {
                    is Objetivo -> {
                        nivelActual.objetivos = nivelActual.objetivos - colision
                        bolasEnJuego.remove(bola)

                        if (nivelActual.objetivos.isEmpty()) {
                            pasarAlSiguienteNivel()
                        }
                    }
                    is Obstaculo -> {
                        bolasEnJuego.remove(bola)
                    }
                }
            }
        }

        // Comprobar derrota
        if (bolasRestantes == 0 && bolasEnJuego.isEmpty() && nivelActual.objetivos.isNotEmpty()) {
            estado = EstadoJuego.DERROTA
            println("Te has quedado sin bolas, fin del juego")
        }

        // Dibujar el estado del juego 
        renderer.dibujar(obtenerElementosParaRender())
    }

    // Cambiar de nivel
    private fun pasarAlSiguienteNivel() {
        indiceNivel++
        if (indiceNivel >= niveles.size) {
            estado = EstadoJuego.VICTORIA
            println("Has completado el juego")
        } else {
            bolasRestantes = niveles[indiceNivel].limiteDeBolas
            println("🎉 Nivel ${indiceNivel + 1} iniciado.")
        }
    }

    // Reunir todos los elementos del nivel actual
    private fun obtenerElementosDelNivel(): List<ElementoDeJuego> {
        val elementos = mutableListOf<ElementoDeJuego>()
        elementos.addAll(nivelActual.obstaculos)
        elementos.addAll(nivelActual.objetivos)
        return elementos
    }

    // Reunir todos los elementos que se deben renderizar
    fun obtenerElementosParaRender(): List<ElementoDeJuego> { 
        val elementos = mutableListOf<ElementoDeJuego>()
        elementos.addAll(nivelActual.obstaculos)
        elementos.addAll(nivelActual.objetivos)
        elementos.addAll(bolasEnJuego)
        elementos.add(canon)
        return elementos
    }
}