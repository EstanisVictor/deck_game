package tools
class Colors {
    val reset = "\u001B[0m"
    // Cores para o texto
    val red = "\u001B[31m"
    val green = "\u001B[32m"
    val yellow = "\u001B[33m"
    val blue = "\u001B[34m"
    val magenta = "\u001B[35m"
    val cyan = "\u001B[36m"
    val white = "\u001B[37m"
    fun printCor(){
        // Exemplos de uso
        println("${red}Texto em vermelho")
        println("${green}Texto em verde")
        println("${yellow}Texto em amarelo")
        println("${blue}Texto em azul")
        println("${magenta}Texto em magenta")
        println("${cyan}Texto em ciano")
        println("${white}Texto em branco")
    }
}