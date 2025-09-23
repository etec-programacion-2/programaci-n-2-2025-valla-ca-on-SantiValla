package org.example
class Bola(
    private var _velocidad : Vector
): ElementoDeJuego() {
    var velocidad: Vector
       get() = _velocidad
       set(value) { _velocidad = value }
    fun actualizar(deltaTiempo: Double) {
         val gravedad = Vector(0.0, -9.8)
         velocidad += gravedad * deltaTiempo  
         posicion += velocidad * deltaTiempo
    }
}