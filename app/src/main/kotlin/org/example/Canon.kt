package org.example
class Canon(
    val cuerpo: Rectangulo,
     private var _angulo : Double
): ElementoDeJuego(cuerpo) {
    var angulo: Double
       get() = _angulo
       set(value) { _angulo = ((value % 360) + 360) % 360 }

       fun apuntar(nuevoAngulo: Double) {
            this.angulo = nuevoAngulo
       }

       fun disparar(): Bola {
        val rad = Math.toRadians(angulo) //el valor de angulo pasar a estar expresado en grados
        val direccion = Vector(Math.cos(rad), Math.sin(rad)) //el coseno del angulo con el que estamos apuntando define la posicion de la bola en el eje x, mientras que el seno hace lo mismo en el eje y

        // centro superior del cañón
        val bocaCanon = Vector(
            cuerpo.posicion.x + cuerpo.tamaño.x / 2 + direccion.x * (cuerpo.tamaño.y / 2),
            cuerpo.posicion.y + cuerpo.tamaño.y / 2 + direccion.y * (cuerpo.tamaño.y / 2)
        )

        val velocidadInicial = direccion * 5.0 //la potencia con la que la bola saldrá disparada será un valor constante, en este caso 5
        val tamañoBola: Vector = Vector(1.0, 1.0)
        return Bola(bocaCanon, tamañoBola, velocidadInicial)
           
     }
}