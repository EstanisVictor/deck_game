package model

class Jogador (var nome: String, var mao: ArrayList<Carta>, var pontosVida: Int) {
    fun getInfo(){
        println("================================================")
        println("Nome: $nome")
        println("Pontos de vida: $pontosVida")
    }

    fun getMao(){
        for (carta in mao){
            carta.getInfo()
        }
    }

    fun getCartaMao(id: Int): Carta?{
        for (carta in mao){
            if (carta.id == id){
                return carta
            }
        }
        return null
    }
}