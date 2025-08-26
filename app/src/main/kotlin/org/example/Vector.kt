package org.example
data class Vector(
    private var _x : Double,
    private var _y: Double
) {
    var x: Double
       get() = _x
       set(value) { _x = value }
   
    var y: Double
       get() = _y
       set(value) {_y = value }
}