package.org example
object CargadorNiveles(){
    fun cargarNiveles(): List<Nivel> {
        val niveles = mutableListOf<Nivel>()

        // Nivel 1
        val obstaculos1 = listOf(
            Obstaculo(Rectangulo(Vector(50.0, 50.0), Vector(20.0, 20.0)), TipoObstaculo.ESTATICO)
        )
        val objetivos1 = listOf(
            Objetivo(Rectangulo(Vector(200.0, 50.0), Vector(20.0, 20.0)))
        )
        niveles.add(Nivel(obstaculos1, objetivos1, 5))

        // Nivel 2
        val obstaculos2 = listOf(
            Obstaculo(Rectangulo(Vector(100.0, 80.0), Vector(30.0, 20.0)), TipoObstaculo.GIRATORIO),
            Obstaculo(Rectangulo(Vector(150.0, 40.0), Vector(20.0, 20.0)), TipoObstaculo.ESTATICO)
        )
        val objetivos2 = listOf(
            Objetivo(Rectangulo(Vector(250.0, 60.0), Vector(20.0, 20.0)))
        )
        niveles.add(Nivel(obstaculos2, objetivos2, 3))

        return niveles
    }
}

