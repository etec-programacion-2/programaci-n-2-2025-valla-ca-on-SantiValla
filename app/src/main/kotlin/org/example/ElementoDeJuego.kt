package org.example
abstract class ElementoDeJuego(
    private var _area : Rectangulo
    ) {
    var area: Rectangulo
       get() = _area
       set(value) { _area = value }
   
    }