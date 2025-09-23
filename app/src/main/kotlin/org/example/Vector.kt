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
      //con la sobrecarga de operadores simplificamos el codigo de fisica al ayudarme a escribir las operaciones de forma natural
    operator fun plus(otro: Vector): Vector {
        val sumx = this.x + otro.x
        val sumy = this.y + otro.y
        val resultado = Vector(sumx, sumy)
        return resultado 
    } 
    operator fun minus(otro: Vector): Vector {
        val resx = this.x - otro.x
        val resy = this.y - otro.y
        val resultado = Vector(resx, resy)
        return resultado 
    } 
    operator fun times(escalar: Double): Vector {
        val mulx = this.x * escalar
        val muly = this.y * escalar
        val resultado = Vector(mulx, muly)
        return resultado }
    operator fun div(escalar: Double): Vector {
        val divx = this.x / escalar
        val divy = this.y / escalar
        val resultado = Vector(divx, divy)
        return resultado 
    } 
} 
