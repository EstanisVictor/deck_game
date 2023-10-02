package model

class Jogador (var nome: String, var mao: ArrayList<Carta>, var pontosVida: Int) {

    var campo_batalha: ArrayList<Carta> = ArrayList()

    fun printCampoBatalha(){
        for (carta in campo_batalha){

            if (carta.atacou){
                continue
            }

            carta.getInfo()
        }
    }

    fun verificaAtaque(): Boolean{
        for (carta in campo_batalha){
            if (carta.atacou){
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
        println("-------------------------------------------")
        println("Você possui ${mao.size} cartas na mão")
        println("-------------------------------------------")
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