package org.example
class Bola(
    posicion: Vector,
    tamaño: Vector,
    private var _velocidad : Vector
): ElementoDeJuego(Rectangulo(posicion, tamaño)) {
    var velocidad: Vector
       get() = _velocidad
       set(value) { _velocidad = value }
    fun actualizar(deltaTiempo: Double) {
        val gravedad = Vector(0.0, -9.8) //la gravedad no influye en el eje x por lo que en ese eje su valor es de 0
        velocidad += gravedad * deltaTiempo  //la velocidad de la bola en el eje y va a variar en funcion de la gravedad por el tiempo, en el eje x el valor de velocidad inicial que declaramos en la clase cañon se mantiene como un valor constante, esto lo logramos haciendo que en el eje x la gravedad sea igual a 0 como declaramos en la linea de arriba
        area.posicion += velocidad * deltaTiempo //la posicion de la bola cambiará en ambos ejes ya que posicion es una variable de tipo vector, donde tanto en el eje x como en el eje y variara en funcion a la velocidad por el tiempo, pero en el eje y el valor de velocidad variara a su vez segun la gravedad por el tiempo
    }
}