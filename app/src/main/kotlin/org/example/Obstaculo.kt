class Obstaculo(
    area: Rectangulo,
    private var _tipo: TipoObstaculo
) : ElementoDeJuego(area) {
    val tipo = _tipo
}
