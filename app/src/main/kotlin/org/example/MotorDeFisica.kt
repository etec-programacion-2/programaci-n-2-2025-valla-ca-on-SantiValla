package org.example
class MotorDeFisica:  {
fun detectarColisiones(bola: Bola, elementos: List<ElementoDeJuego>): ElementoDeJuego? {
    for (e in elementos) {
        if (e === bola) continue
        if (bola.area.intersecta(e.area)) {
                return e
            }
        }
        return null
}
}