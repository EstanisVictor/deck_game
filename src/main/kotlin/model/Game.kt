package model

import model.DAO.FakeDaoSaveCartas

class Game (var baralho: FakeDaoSaveCartas, var jogador1: Jogador, var jogador2: Jogador){

    var campo_batalha_j1: ArrayList<Carta> = ArrayList()
    var campo_batalha_j2: ArrayList<Carta> = ArrayList()

    fun distribuirCartas(){
        for (i in 1..5){
            var carta = baralho.cartas.random()
            jogador1.mao.add(carta)
            baralho.cartas.remove(carta)

            carta = baralho.cartas.random()
            jogador2.mao.add(carta)
            baralho.cartas.remove(carta)
        }
    }

    fun iniciarJogo(){
        println("Iniciando jogo...")

        distribuirCartas()

        while (true){
            fazerJogada(jogador1, campo_batalha_j1, campo_batalha_j2)

        }
    }

    fun fazerJogada(jogador: Jogador, seu_campo: ArrayList<Carta>, campo_oponente: ArrayList<Carta>){
        println("================================================")
        println("Vez de ${jogador.nome}")
        println("Escolha sua jogada:\n1 - Jogar carta\n2 - Atacar\n 3 - Alterar modo de carta")
        var escolha = readLine()!!.toInt()

        if (escolha == 1){
            var cartaEscolhida: Carta = escolherCarta(jogador1, seu_campo)
            campo_batalha_j1.add(cartaEscolhida)
        }

    }

    fun escolherCarta(jogador: Jogador, seu_campo: ArrayList<Carta>): Carta{

        println("================================================")
        jogador.getMao()
        println("Selecione o ID da carta que deseja jogar:")

        var cartaEscolhida = readLine()!!.toInt()

        var carta = jogador.getCartaMao(cartaEscolhida)

        if (carta == null){
            println("Carta não encontrada")
            return escolherCarta(jogador, seu_campo)
        }
        
        if(carta.tipo.equals("monstro", true)){

            while (true){
                println("Digite a opção desejada:\n1 - ATAQUE\n2 - DEFESA")
                var escolhaModo = readLine()

                if (escolhaModo.equals("1")){
                    carta.modo = true
                    carta.controleTurno++
                    break
                }else if (escolhaModo.equals("2")){
                    carta.modo = false
                    break
                }else{
                    println("Opção inválida")
                }
            }
        }else if(!seu_campo.isEmpty()){

        }else{
            println("Você não possui monstro em campo")
        }

        jogador.mao.remove(carta)

        return carta
    }
}