package model

import model.DAO.FakeDaoSaveCartas

class Game (var baralho: FakeDaoSaveCartas, var jogador1: Jogador, var jogador2: Jogador){

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
            turno(jogador1, jogador2)

//            turno(jogador2, jogador1)
        }
    }

    fun turno(jogador: Jogador, oponente: Jogador){
        if(jogador.mao.size < 10){
            sacarCarta(jogador)
            fazerJogada(jogador, oponente)
        }else if(jogador.mao.size == 10){
            descartarCarta(jogador)
            sacarCarta(jogador)
            fazerJogada(jogador, oponente)
        }
    }

    fun sacarCarta(jogador: Jogador){
        var carta = baralho.cartas.random()
        jogador.mao.add(carta)
        baralho.cartas.remove(carta)
    }

    fun descartarCarta(jogador: Jogador){
        jogador.getMao()
        println("...............................................")
        println("Selecione um ID das cartas acima para descartar:")
        var cartaEscolhida = readLine()!!.toInt()
        var carta = jogador.getCartaMao(cartaEscolhida)
        if (carta == null){
            println("Carta não encontrada")
            return descartarCarta(jogador)
        }
        jogador1.mao.remove(carta)
    }

    fun fazerJogada(jogador: Jogador, oponente: Jogador){
        println("================================================")
        println("Vez de ${jogador.nome}")
        println("Escolha sua jogada:\n1 - Jogar carta\n2 - Atacar\n3 - Alterar modo de carta")
        var escolha = readLine()!!.toInt()

        if (escolha == 1){
            if (jogador.verificaMao()) { //Se o jogador possuir ao menos 1 monstro na mão para jogar
                escolherCarta(jogador)
            }else if (!jogador.campo_batalha.isEmpty()){ //Se o jogador possuir monstros no campo de batalha
                escolherCarta(jogador)
            }else{ //Se o jogador não possuir monstros na mão e nem no campo de batalha
                jogador.getMao()
                println("Você não possui monstro para jogar nesse turno")
            }
        }
    }

    fun escolherCarta(jogador: Jogador){

        println("================================================")
        jogador.getMao()
        println("Selecione o ID da carta que deseja jogar:")

        var cartaEscolhida = readLine()!!.toInt()

        var carta = jogador.getCartaMao(cartaEscolhida)

        if (carta == null){
            println("Carta não encontrada")
            return escolherCarta(jogador)
        }

        if(carta.tipo.equals("monstro", true)){

            while (true){
                println("Escolha o modo que deseja posicionar o monstro:\n1 - ATAQUE\n2 - DEFESA")
                var escolhaModo = readLine()

                if (escolhaModo.equals("1")){ //true para modo ataque
                    carta.modo = true
                    carta.controleTurno++
                    break
                }else if (escolhaModo.equals("2")){ //false para modo defesa
                    carta.modo = false
                    break
                }else{
                    println("Opção inválida")
                }
            }

            if (jogador.campo_batalha.size < 5){
                jogador.campo_batalha.add(carta)
            }else{
                println("Campo de batalha cheio!! Você não pode jogar mais monstros")
                println("Selecione a opção:\n1 - Escolher outra carta\n2 - Substituir monstro no campo de batalha\n3 - Finalizar turno")
                var op = readLine()!!.toInt()
                if (op == 1){
                    return escolherCarta(jogador)
                }else if (op == 2){
                    //FALTA
                }else{
                    println("Turno finalizado")
                    return
                }
            }

        }else if(!jogador.campo_batalha.isEmpty()){
            //Aqui o usuário escolheu um equipamento
            var carta_monstro: Carta? = null

            while (carta_monstro == null){

                jogador.printCampoBatalha()
                println("Escolha o ID do monstro que deseja equipar:")
                var idMonstro = readLine()!!.toInt()
                carta_monstro = jogador.getCartaCampo(idMonstro)

                if (carta_monstro == null){
                    println("Carta não encontrada")
                }
            }
            carta_monstro.upgrade_carta(carta)
        }else{
            println("Você não possui monstro em campo")
            return escolherCarta(jogador)
        }

        jogador.mao.remove(carta)
    }
}