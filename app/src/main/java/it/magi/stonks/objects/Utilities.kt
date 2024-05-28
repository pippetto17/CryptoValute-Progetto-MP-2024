package it.magi.stonks.objects

import it.magi.stonks.viewmodels.RegistrationViewModel

class Utilities {
    fun capitalizeFirstChar(str: String): String {
        return str.replaceFirstChar { it.uppercase() }
    }

    fun convertDotsToCommas(input: String): String {
        return input.replace('.', ',')
    }
    fun convertCommasToDots(input: String): String {
        return input.replace(',', '.')
    }


    fun testSignup(){
        RegistrationViewModel().registerUser("test@gmail.com","Abc123,","Abc123,","TestName","TestSurname")
    }
}