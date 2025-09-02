package org.example
data class Rectangulo(
    private var _posicion : Vector,
    private var _tamaño: Vector
) {
    var posicion: Vector
       get() = _posicion
       set(value) { _posicion = value }
   
    var tamaño: Vector
       get() = _tamaño
       set(value) {_tamaño = value }
       
     
      fun intersecta(otro: Rectangulo): Boolean {
        val thisIzq = posicion.x
        val thisDer = posicion.x + tamaño.x
        val thisArriba = posicion.y
        val thisAbajo = posicion.y + tamaño.y

        val otherIzq = otro.posicion.x
        val otherDer = otro.posicion.x + otro.tamaño.x
        val otherArriba = otro.posicion.y
        val otherAbajo = otro.posicion.y + otro.tamaño.y

        return (thisIzq < otherDer &&
                thisDer > otherIzq &&
                thisArriba < otherAbajo &&
                thisAbajo > otherArriba)
     }
       }