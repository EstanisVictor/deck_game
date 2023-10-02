package model

open class Carta (var id: Int, var nome: String, var desc: String, var ptAtaque: Int, var ptDefesa: Int, var tipo: String) {

    var controleTurno: Int = 0
    var atacou: Boolean = false

    //Se for true automaticamente é modo ataque
    var modo: Boolean = tipo.equals("monstro", true)

    fun getInfo(){
        println("================================================")
        println("ID: $id")
        println("Nome: $nome")
        println("Descrição: $desc")
        println("Pontos de ataque: $ptAtaque")
        println("Pontos de defesa: $ptDefesa")
        println("Tipo: $tipo")
    }

    fun upgrade_carta(carta: Carta){
        ptAtaque += carta.ptAtaque
        ptDefesa += carta.ptDefesa
        nome += " + " + carta.nome
    }

}