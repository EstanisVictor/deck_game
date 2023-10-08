package model.dao

import model.Carta

class FakeDaoSaveCartas (){

    var cartas: ArrayList<Carta> = ArrayList()

    fun salvarCartas(carta: Carta){
        cartas.add(carta)
    }

    fun removerCarta(id: Int){
        for (carta in cartas){
            if (carta.id == id){
                cartas.remove(carta)
            }
        }
    }

    fun listAllCartas(){

        if (cartas.isEmpty()){
            println("Não há cartas cadastradas")
        }else{
            for (carta in cartas){
                carta.getInfo()
            }
        }
    }

    fun recuperaCarta(id: Int): Carta?{
        for (carta in cartas){
            if (carta.id == id){
                return carta
            }
        }
        return null
    }
}