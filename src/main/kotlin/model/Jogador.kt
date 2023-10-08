package model

import tools.Colors

class Jogador (var nome: String, var mao: ArrayList<Carta>, var pontosVida: Int) {
    var cor = Colors()
    var campo_batalha: ArrayList<Carta> = ArrayList()

    fun printCampoBatalha(){
        println("${cor.magenta}------------------CAMPO DE BATALHA - ${nome}------------------")
        for (carta in campo_batalha){

            if (carta.atacou){
                continue
            }

            carta.getInfo()
            if (carta.modo){
                println("${cor.magenta}Modo: Ataque")
            }else{
                println("${cor.magenta}Modo: Defesa")
            }
        }
    }

    fun verificaAtacou(): Boolean{

        var cont = 0
        for (carta in campo_batalha){
            if (carta.modo){
                cont++
            }
        }

        for (carta in campo_batalha){
            if (carta.atacou){
                cont--
            }
        }
        return cont == 0
    }
    
    fun isAtaque():  Boolean{
        for (carta in campo_batalha){
            if (carta.modo){
                return true
            }
        }
        return false
    }

    fun getInfo(){
        println("================================================")
        println("Nome: $nome")
        println("Pontos de vida: $pontosVida")
    }

    fun verificaMao(): Boolean{
        for (carta in mao){
            if (carta.tipo.equals("monstro", true)){
                return true
            }
        }
        return false
    }

    fun getMao(){
        for (carta in mao){
            carta.getInfo()
        }
        println("${cor.magenta}-------------------------------------------")
        println("${cor.magenta}Você possui ${mao.size} cartas na mão")
        println("${cor.magenta}-------------------------------------------${cor.reset}")
    }

    fun getCartaMao(id: Int): Carta?{
        for (carta in mao){
            if (carta.id == id){
                return carta
            }
        }
        return null
    }

    fun getCartaCampo(id: Int): Carta?{
        for (carta in campo_batalha){
            if (carta.id == id){
                return carta
            }
        }
        return null
    }
}