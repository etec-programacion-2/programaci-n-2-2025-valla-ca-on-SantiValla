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
       
     private fun intersecta(otro: Rectangulo) 
       }