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

        while (jogador1.pontosVida > 0 && jogador2.pontosVida > 0){
            turno(jogador1, jogador2)

            if (jogador2.pontosVida <= 0 || jogador1.pontosVida <= 0){
                break
            }

            turno(jogador2, jogador1)
        }
    }

    fun turno(jogador: Jogador, oponente: Jogador){

        for (carta in jogador.campo_batalha){
            carta.atacou = false
            carta.controleTurno++
        }

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
                escolherCarta(jogador, oponente)
            }else if (!jogador.campo_batalha.isEmpty()){ //Se o jogador possuir monstros no campo de batalha
                escolherCarta(jogador, oponente)
            }else{ //Se o jogador não possuir monstros na mão e nem no campo de batalha
                jogador.getMao()
                println("Você não possui monstro para jogar nesse turno")
            }
        }else if (escolha == 2){
            if (jogador.campo_batalha.isEmpty()){
                println("Você não possui monstros no campo de batalha para atacar")
                fazerJogada(jogador, oponente)
            }else{
                atacar(jogador, oponente)
            }
        }
    }

    fun atacar(jogador: Jogador, oponente: Jogador){
        while (true){
            jogador.printCampoBatalha()
            println("Selecione o monstro do seu campo que irá atacar:")

            var cartaEscolhida = readLine()!!.toInt()
            var minha_carta = jogador.getCartaCampo(cartaEscolhida)

            if (minha_carta == null){
                println("Carta não encontrada")
                continue
            }

            if (!minha_carta.modo){
                println("Você não pode atacar com essa carta pois ela está em modo defesa")
                continue
            }

            if (minha_carta.controleTurno == 0){
                println("Você não pode atacar com essa carta nesse turno")
                continue
            }

            if (minha_carta.controleTurno >= 1 && minha_carta.modo){
                println("Selecione o monstro do campo do oponente para atacar:")
                oponente.printCampoBatalha()
                var cartaOponenteEscolhida = readLine()!!.toInt()
                var cartaOponente = oponente.getCartaCampo(cartaOponenteEscolhida)

                if (cartaOponente == null){
                    println("Carta não encontrada")
                    continue
                }

                if (cartaOponente.modo) {
                    minha_carta.atacou = true
                    if (minha_carta.ptAtaque > cartaOponente.ptAtaque) {
                        oponente.campo_batalha.remove(cartaOponente)
                        var dif = minha_carta.ptAtaque - cartaOponente.ptAtaque
                        oponente.pontosVida -= dif
                        println("Você destruiu a carta do oponente")
                        println("O oponente perdeu $dif pontos de vida")

                        if (oponente.pontosVida <= 0) {
                            println("O ${oponente.nome} chegou a 0 pontos de vida, ${jogador.nome} venceu o jogo!!")
                            break
                        }

                        println("Pontos de vida restante do oponente: ${oponente.pontosVida}")
                        minha_carta.controleTurno = 0
                    } else if (minha_carta.ptAtaque < cartaOponente.ptAtaque) {
                        jogador.campo_batalha.remove(minha_carta)
                        var dif =  cartaOponente.ptAtaque - minha_carta.ptAtaque
                        jogador.pontosVida -= dif
                        println("Sua carta foi destruída")
                        println("Você perdeu $dif pontos de vida")

                        if(jogador.pontosVida <= 0){
                            println("Seus pontos de vida chegaram a 0, você perdeu o jogo!!")
                            break
                        }
                        println("Pontos de vida restante: ${jogador.pontosVida}")
                    } else {
                        jogador.campo_batalha.remove(minha_carta)
                        oponente.campo_batalha.remove(cartaOponente)
                        println("Ambos os monstros foram destruídos")
                        println("Nenhum jogador perdeu pontos de vida")
                    }
                }else{
                    minha_carta.atacou = true
                    if (minha_carta.ptAtaque > cartaOponente.ptDefesa) {
                        oponente.campo_batalha.remove(cartaOponente)
                        println("Você destruiu a carta do oponente")
                        println("O oponente não perdeu pontos de vida")
                        println("Pontos de vida restante do oponente: ${oponente.pontosVida}")
                        minha_carta.controleTurno = 0
                    } else if (minha_carta.ptAtaque < cartaOponente.ptDefesa) {
                        var dif =  cartaOponente.ptDefesa - minha_carta.ptAtaque
                        jogador.pontosVida -= dif
                        println("Sua carta foi destruída")
                        println("Você perdeu $dif pontos de vida")
                        println("Pontos de vida restante: ${jogador.pontosVida}")
                        jogador.campo_batalha.remove(minha_carta)
                    } else {
                        jogador.campo_batalha.remove(minha_carta)
                        oponente.campo_batalha.remove(cartaOponente)
                        println("Ambos os monstros foram destruídos")
                        println("Nenhum jogador perdeu pontos de vida")
                    }
                }
            }

            if (oponente.pontosVida <= 0){
                println("O jogador ${oponente.nome} perdeu o jogo")
                break
            }else if(jogador.pontosVida <= 0){
                println("O jogador ${jogador.nome} perdeu o jogo")
                break
            }

            if (!jogador.verificaAtaque()){
                println("================================================")
                println("Deseja atacar novamente?\n1 - SIM\n2 - NÃO")
                var escolha = readLine()!!.toInt()

                if (escolha == 2){
                    break
                }
            }else{
                println("Você não possui mais monstros para atacar")
                break
            }
        }
    }

    fun escolherCarta(jogador: Jogador, oponente: Jogador){

        println("================================================")
        jogador.getMao()
        println("Selecione o ID da carta que deseja jogar:")

        var cartaEscolhida = readLine()!!.toInt()

        var carta = jogador.getCartaMao(cartaEscolhida)

        if (carta == null){
            println("Carta não encontrada")
            return escolherCarta(jogador, oponente)
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
                    return escolherCarta(jogador, oponente)
                }else if (op == 2){
                    while (true){
                        println("Selecione o ID do monstro que deseja substituir:")
                        jogador.printCampoBatalha()
                        var idMonstro = readLine()!!.toInt()
                        var monstro = jogador.getCartaCampo(idMonstro)

                        if (monstro == null){
                            println("Carta não encontrada, selecione novamente")
                            continue
                        }
                        jogador.campo_batalha.remove(monstro)
                        return escolherCarta(jogador, oponente)
                    }
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
            println("Você deseja atacar ou finalizar turno?\n1 - Atacar\n2 - Finalizar turno")
            var op = readLine()!!.toInt()
            if (op == 1) {
                atacar(jogador, oponente)
            }else{
                println("Turno finalizado")
                return
            }
        }else{
            println("Você não possui monstro em campo")
            return escolherCarta(jogador, oponente)
        }

        jogador.mao.remove(carta)
    }
}