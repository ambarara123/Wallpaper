package com.example.wallpaper.utils

fun checkString(input: String): Pair<Int, Boolean> {
    val specialChars = "~`!@#$%^&*()-_=+\\|[{]};:'\",<.>/?"
    var currentCharacter: Char
    var numberPresent = false
    var upperCasePresent = false
    var specialCharacterPresent = false
    var status = 0


    for (element in input) {
        currentCharacter = element
        when {
            Character.isUpperCase(currentCharacter) -> {
                upperCasePresent = true
                status = 1
            }
            Character.isDigit(currentCharacter) -> {
                numberPresent = true
                status = 2
            }
            specialChars.contains(currentCharacter.toString()) -> {
                specialCharacterPresent = true
                status = 3
            }
        }
    }

    return Pair(status, numberPresent && upperCasePresent && specialCharacterPresent)
}

class StringValidation {
    var continsUpperCase: Boolean = false
    var containsNumber: Boolean = false
    var containsSymbols: Boolean = false
}