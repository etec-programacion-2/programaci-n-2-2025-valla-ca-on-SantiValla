package org.example

class ControladorDeJuego(
    private val niveles: List<Nivel>,
    private val canon: Canon
) {
    private var indiceNivel = 0
    private var estado = EstadoJuego.JUGANDO
    private var bolasRestantes = niveles[indiceNivel].limiteDeBolas
    private val motorFisica = MotorDeFisica()

    // lista con bolas en juego
    private val bolasEnJuego = mutableListOf<Bola>()

    private val nivelActual: Nivel
        get() = niveles[indiceNivel]

    fun estaJugando(): Boolean = estado == EstadoJuego.JUGANDO

    // Disparar una bola desde el cañón
    fun dispararBola() {
        if (bolasRestantes > 0 && estado == EstadoJuego.JUGANDO) {
            val bola = canon.disparar()
            bolasEnJuego.add(bola)
            bolasRestantes-- 
        } else {
            println("No quedan más bolas o el juego no está activo.")
        }
    }

    // Bucle de actualización (física, colisiones, estados)
    fun actualizar(deltaTiempo: Double) {
        if (estado != EstadoJuego.JUGANDO) return

        // Actualizar física de todas las bolas
        
        for (bola in bolasEnJuego) {
            bola.actualizar(deltaTiempo)
        }

        // Eliminar bolas que salieron del escenario
        bolasEnJuego.removeIf { bola ->
            bola.area.posicion.y < 0
        }
        val bolasAEliminar = mutableListOf<Bola>()
        // Detectar colisiones
        for (bola in bolasEnJuego.toList()) {
            val colision = motorFisica.detectarColisiones(bola, obtenerElementosDelNivel())
            if (colision != null) {
                when (colision) {
                    is Objetivo -> {
                        println("Objetivo alcanzado!")
                        nivelActual.objetivos = nivelActual.objetivos - colision
                        if (nivelActual.objetivos.isEmpty()) {
                            println("Nivel completado")
                            pasarAlSiguienteNivel()
                        }
                    }
                    is Obstaculo -> {
                        println("Colisión con obstáculo tipo ${colision.tipo}")
                        bolasAEliminar.add(bola)
                    }
                }
            }
        }
        bolasEnJuego.removeAll(bolasAEliminar)
        // Comprobar derrota (sin bolas ni objetivos completados)
        if (bolasRestantes == 0 && bolasEnJuego.isEmpty() && nivelActual.objetivos.isNotEmpty()) {
            estado = EstadoJuego.DERROTA
            println(" Derrota. Fin del juego.")
        }
    }

    // Cambiar al siguiente nivel
    private fun pasarAlSiguienteNivel() {
        indiceNivel++
        if (indiceNivel >= niveles.size) {
            estado = EstadoJuego.VICTORIA
            println("Completaste todos los niveles!")
        } else {
            bolasRestantes = niveles[indiceNivel].limiteDeBolas
            println("Pasando al nivel ${indiceNivel + 1}")
        }
    }

    // Obtener todos los elementos del nivel actual
    private fun obtenerElementosDelNivel(): List<ElementoDeJuego> {
        val elementos = mutableListOf<ElementoDeJuego>()
        elementos.addAll(nivelActual.obstaculos)
        elementos.addAll(nivelActual.objetivos)
        return elementos
    }
}