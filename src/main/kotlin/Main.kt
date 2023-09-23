import model.DAO.FakeDaoSaveCartas
import model.Game
import model.Jogador
import tools.LeitorCartas

fun main(args: Array<String>) {
    var baralho = FakeDaoSaveCartas()
    LeitorCartas.getCartas(baralho)

    println("****************************************")

    var jogador1: Jogador = Jogador("Jogador 1", arrayListOf(), 20)
    var jogador2: Jogador = Jogador("Jogador 2", arrayListOf(), 20)

    Game(baralho, jogador1, jogador2).iniciarJogo()

    baralho.listAllCartas()
}