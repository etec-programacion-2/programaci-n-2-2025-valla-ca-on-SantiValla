package org.example
class Canon(
    private var _angulo : Double
): ElementoDeJuego() {
    var angulo: Double
       get() = _angulo
       set(value) { _angulo = ((value % 360) + 360) % 360 }
}