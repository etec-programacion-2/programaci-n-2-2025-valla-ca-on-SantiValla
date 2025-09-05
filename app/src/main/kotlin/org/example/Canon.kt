package org.example
class Canon(
    var posicion
    private var _angulo : Double
): ElementoDeJuego() {
    var angulo: Double
       get() = _angulo
       set(value) { _angulo = ((value % 360) + 360) % 360 }

       fun apuntar(nuevoAngulo: Double): {
            this.angulo = nuevoAngulo
       }

       fun disparar(): Bola {

     }
}