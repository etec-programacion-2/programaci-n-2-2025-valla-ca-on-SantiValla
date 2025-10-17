package org.example
class ControladordeJuego(
    private val niveles: List<Nivel>

){
    private var indiceNivel = 0
    private var bolasRestantes = niveles[indiceNivel].limiteDeBolas
    private var estado = EstadoJuego.JUGANDO
    private val motor = MotorDeFisica()
    fun iniciarjuego(): {
        while (estado == EstadoJuego.JUGANDO) {
            actualizar()
            }
            }
        
}