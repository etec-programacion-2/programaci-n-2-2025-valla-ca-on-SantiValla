package org.example
class Bola(
    private var _velocidad : Vector
): ElementoDeJuego() {
    var velocidad: Vector
       get() = _velocidad
       set(value) { _velocidad = value }
}