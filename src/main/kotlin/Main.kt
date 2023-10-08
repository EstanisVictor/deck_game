import model.dao.FakeDaoSaveCartas
import model.Game
import model.Jogador
import tools.LeitorCartas

fun main(args: Array<String>) {
    var baralho = FakeDaoSaveCartas()
    LeitorCartas.getCartas(baralho)

    var jogador1: Jogador = Jogador("Vitão", arrayListOf(), 10000)
    var jogador2: Jogador = Jogador("Rilton", arrayListOf(), 10000)

    Game(baralho, jogador1, jogador2).iniciarJogo()
}