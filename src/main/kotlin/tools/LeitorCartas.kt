package tools

import model.Carta
import model.DAO.FakeDaoSaveCartas
import java.io.File
import java.io.InputStream
import java.nio.charset.Charset

class LeitorCartas (){

    companion object{
        private lateinit var cartas:List<Carta>

        fun getCartas(baralho: FakeDaoSaveCartas):List<Carta>{
            if(!::cartas.isInitialized){
                cartas = lerCartasCSV().map {
                    val dados = it.split(";")
                    Carta(dados[0].toInt(), dados[1], dados[2], dados[3].toInt(), dados[4].toInt(), dados[5])
                }

                for (carta in cartas){
                    baralho.salvarCartas(carta)
                }

            }else{
                println("Cartas já carregadas")
            }
            return cartas.map { it }  //retorna uma replica das cartas
        }

        private fun lerCartasCSV():List<String>{
            val streamDados:InputStream = File("cartas1.csv").inputStream()
            val leitorStream = streamDados.bufferedReader(Charset.forName("windows-1252"))
            return leitorStream.lineSequence()
                .filter { it.isNotBlank() }
                .toList()
        }
    }



}