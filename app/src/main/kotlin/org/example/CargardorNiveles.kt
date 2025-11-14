package org.example
object CargadorNiveles{
    fun cargarNiveles(): List<Nivel> {
        val niveles = mutableListOf<Nivel>()

        // Nivel 1
        val obstaculos1 = listOf(
            Obstaculo(Rectangulo(Vector(300.0, 130.0), Vector(20.0, 200.0)), TipoObstaculo.ESTATICO)
        )
        val objetivos1 = listOf(
            Objetivo(Rectangulo(Vector(620.0, 405.0), Vector(20.0, 20.0))),
            Objetivo(Rectangulo(Vector(620.0, 105.0), Vector(20.0, 20.0)))
        )
        niveles.add(Nivel(obstaculos1, objetivos1, 5))

        // Nivel 2
        val obstaculos2 = listOf(
            Obstaculo(Rectangulo(Vector(350.0, 80.0), Vector(20.0, 300.0)), TipoObstaculo.GIRATORIO),
            Obstaculo(Rectangulo(Vector(150.0, 400.0), Vector(200.0, 20.0)), TipoObstaculo.ESTATICO)
        )
        val objetivos2 = listOf(
            Objetivo(Rectangulo(Vector(650.0, 290.0), Vector(20.0, 20.0)))
        )
        niveles.add(Nivel(obstaculos2, objetivos2, 10))

        return niveles
    }
}

