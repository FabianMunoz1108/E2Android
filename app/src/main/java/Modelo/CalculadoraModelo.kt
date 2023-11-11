package Modelo

class CalculadoraModelo {
    private val results: MutableList<String> = mutableListOf()

    fun add(number1: Int, number2: Int): String {
        val result = "Suma ${number1} + ${number2} = ${number1 + number2}"
        results.add(result)
        return result
    }

    fun calculaIMC(weight: Double, height: Double): String {
        if (weight <= 0.0 || height <= 0.0) {
            throw IllegalArgumentException("Peso y altura deben ser valores positivos.")
        }
        // BMI formula: weight (kg) / (height (m) * height (m))
        val heightInMeters = height / 100.0  // Assuming height is in centimeters
        val result: Double = weight / (heightInMeters * heightInMeters)
        val text = "IMC ${weight} (kg)/ (${height} (cm) *  ${height} (cm)) = ${result}"
        results.add(text)

        return text
    }

    fun getResults(): List<String> {
        return results
    }
}