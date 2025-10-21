package org.example

class ControladorDeJuego(
    private val niveles: List<Nivel>,
    private val canon: Canon
) {
    private var indiceNivel = 0
    private var estado = EstadoJuego.JUGANDO
    private var bolasRestantes = niveles[indiceNivel].limiteDeBolas
    private val motorFisica = MotorDeFisica()

    // 👇 Lista con las bolas activas (las que están en vuelo)
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
            println("⚠️ No quedan más bolas o el juego no está activo.")
        }
    }

    // 🕹️ Bucle de actualización (física, colisiones, estados)
    fun actualizar(deltaTiempo: Double) {
        if (estado != EstadoJuego.JUGANDO) return

        // 1️ Actualizar física de todas las bolas
        for (bola in bolasEnJuego) {
            bola.actualizar(deltaTiempo)
        }

        // 2️ Eliminar bolas que salieron del escenario
        bolasEnJuego.removeIf { bola ->
            bola.area.posicion.y < 0
        }

        // 3️ Detectar colisiones (bola vs. obstáculos/objetivos)
        for (bola in bolasEnJuego) {
            val colision = motorFisica.detectarColisiones(bola, obtenerElementosDelNivel())
            if (colision != null) {
                when (colision) {
                    is Objetivo -> {
                        println("🎯 ¡Objetivo alcanzado!")
                        nivelActual.objetivos = nivelActual.objetivos - colision
                        if (nivelActual.objetivos.isEmpty()) {
                            println("🏁 Nivel completado")
                            pasarAlSiguienteNivel()
                        }
                    }
                    is Obstaculo -> {
                        println("💥 Colisión con obstáculo tipo ${colision.tipo}")
                        // Aquí podrías implementar rebote o pérdida de velocidad
                    }
                }
            }
        }

        // 4️⃣ Comprobar derrota (sin bolas ni objetivos completados)
        if (bolasRestantes == 0 && bolasEnJuego.isEmpty() && nivelActual.objetivos.isNotEmpty()) {
            estado = EstadoJuego.DERROTA
            println("💀 No quedan más bolas. Fin del juego.")
        }
    }

    // 🔄 Cambiar al siguiente nivel
    private fun pasarAlSiguienteNivel() {
        indiceNivel++
        if (indiceNivel >= niveles.size) {
            estado = EstadoJuego.VICTORIA
            println("🏆 ¡Completaste todos los niveles!")
        } else {
            bolasRestantes = niveles[indiceNivel].limiteDeBolas
            println("➡️ Pasando al nivel ${indiceNivel + 1}")
        }
    }

    // 📦 Obtener todos los elementos del nivel actual
    private fun obtenerElementosDelNivel(): List<ElementoDeJuego> {
        val elementos = mutableListOf<ElementoDeJuego>()
        elementos.addAll(nivelActual.obstaculos)
        elementos.addAll(nivelActual.objetivos)
        return elementos
    }
}