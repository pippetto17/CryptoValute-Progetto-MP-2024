package it.magi.stonks.objects

class Utilities {
    fun capitalizeFirstChar(str: String): String {
        return str.replaceFirstChar { it.uppercase() }
    }
}