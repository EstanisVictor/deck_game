package model

import tools.Colors

open class Carta (var id: Int, var nome: String, var desc: String, var ptAtaque: Int, var ptDefesa: Int, var tipo: String) {
    var cor = Colors()
    var controleTurno: Int = 0
    var atacou: Boolean = false

    //Se for true automaticamente é modo ataque
    var modo: Boolean = tipo.equals("monstro", true)

    fun getInfo(){
        println("${cor.reset}================================================")
        println("${cor.green}ID: $id")
        println("${cor.cyan}Nome: $nome")
        println("${cor.cyan}Descrição: $desc")
        println("${cor.red}Pontos de ataque: $ptAtaque")
        println("${cor.blue}Pontos de defesa: $ptDefesa")
        println("${cor.cyan}Tipo: $tipo")
    }

    fun upgrade_carta(carta: Carta){
        ptAtaque += carta.ptAtaque
        ptDefesa += carta.ptDefesa
        nome += " + " + carta.nome
    }

}