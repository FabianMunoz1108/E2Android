package Modelo

class CalculadoraModelo {
    private val results: MutableList<String> = mutableListOf()

    fun add(number1:Int, number2: Int):String{
        val result = "${number1} + ${number2} = ${number1 + number2}"
        results.add(result)
        return  result
    }
    fun getResults(): List<String>{
        return results
    }
}