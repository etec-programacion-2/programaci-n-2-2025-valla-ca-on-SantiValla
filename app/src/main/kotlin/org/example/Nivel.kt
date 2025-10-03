package org.example
data class Nivel(
private var _obstaculos: List<Obstaculo>,
private var _objetivos: List<Objetivo>,
private var _limiteDeBolas: Int) {
    
    var obstaculos: List<Obstaculo>
       get() = _obstaculos
       set(value) { _obstaculos = value }

    var objetivos: List<Objetivo>
        get() = _objetivos
        set(value) { _objetivos = value }
        
    var limiteDeBolas: Int
        get() = _limiteDeBolas
        set(value) { _limiteDeBolas = value }
}