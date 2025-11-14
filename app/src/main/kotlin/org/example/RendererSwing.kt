package org.example
import java.awt.*
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JFrame
import javax.swing.JPanel

// Clase que implementa Renderer usando Swing
class RendererSwing(
    private val ancho: Int,  // ancho de la ventana
    private val alto: Int,   // alto de la ventana
) : Renderer {

    // Ventana principal del juego (pública para acceder desde JuegoSwing)
    val frame = JFrame("CannonShooting2D")

    // Panel encargado de dibujar los elementos
    private val panel = object : JPanel() {
    var elementos: List<ElementoDeJuego> = emptyList()  // elementos actuales a dibujar
    var pantallaFinal: PantallaFinal? = null
    // Método de Swing para redibujar el panel
    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2 = g as Graphics2D

        // Fondo negro
        g2.color = Color.BLACK
        g2.fillRect(0, 0, ancho, alto)
        
        if (pantallaFinal != null) {
            pantallaFinal!!.dibujar(g2)
            return //para dejar de dibujar los elementos de juego despues de una pantalla final
            }
        g.color = Color.WHITE
        g.font = Font("Arial", Font.PLAIN, 14)
        g.drawString("Presiona ESPACIO para disparar, presiona ESC para salir", 30, 30)
        // Dibujar cada elemento del juego
        for (e in elementos) {
            val x = e.area.posicion.x.toInt()
            val y = alto - e.area.posicion.y.toInt()
            val w = e.area.tamaño.x.toInt() //w de width (ancho en inglés)
            val h = e.area.tamaño.y.toInt() //h de height (alto en inglés)

            when (e) {
                is Canon -> {
                g.color = Color.RED

                // Guardamos la posicion inicial
                val posicioninicialcanon = g2.transform

                // Calculamos el ángulo en radianes
                val anguloRad = Math.toRadians(e.angulo)

                // Rotamos el contexto alrededor del origen del cañón
                g2.rotate(-anguloRad, x.toDouble(), y.toDouble())

                // Dibujamos el cañón como un rectángulo
                g2.fillRect(x, y - h, w, h)

                // Restauramos la posicion inicial
                g2.transform = posicioninicialcanon

                // Dibujar la rueda del cañón
                val radioRueda = (h * 0.8).toInt()
                val ruedaX = x - radioRueda / 2
                val ruedaY = y - radioRueda / 2

                g2.color = Color.DARK_GRAY
                g2.fillOval(ruedaX, ruedaY, radioRueda, radioRueda)
                }

                is Bola -> {
                g.color = Color.YELLOW
                g.fillOval(x, y - h, w, h)
                }

                is Objetivo -> {
                g.color = Color.GREEN
                g.fillRect(x, y - h, w, h)
                }

                is Obstaculo -> {
                when (e.tipo) {
                    TipoObstaculo.ESTATICO -> {
                        g.color = Color.GRAY
                        g.fillRect(x, y - h, w, h)
                    }

                    TipoObstaculo.GIRATORIO -> {
                        g2.color = Color.MAGENTA

                        // Guardamos la transformacion actual
                        val posicioninicialobstaculo = g2.transform

                        // Calculamos el centro del obstáculo (punto de rotación)
                        val centroX = x + w / 2.0
                        val centroY = y - h / 2.0

                        // Rotamos el contexto alrededor del centro
                        g2.rotate(Math.toRadians(e.obtenerAngulo()), centroX, centroY)

                        // Dibujamos el rectángulo centrado en su posición original
                        g2.fillRect(x, y - h, w, h)

                        // Restauramos la transformacion
                        g2.transform = posicioninicialobstaculo
                    }
                }
            }

            else -> {
                g.color = Color.WHITE
                g.fillRect(x, y - h, w, h)
            }
        }
    }

           
        }
    }

    init {
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(ancho, alto)
        frame.add(panel)
        frame.isVisible = true
    }

    // Método llamado desde el juego cada ciclo de dibujo
    override fun dibujar(elementos: List<ElementoDeJuego>) {
        panel.elementos = elementos
        panel.pantallaFinal = null
        panel.repaint()
    }
    fun dibujarPantallaFinal(pantalla: PantallaFinal) {
        panel.pantallaFinal = pantalla
        panel.repaint()
    }
}