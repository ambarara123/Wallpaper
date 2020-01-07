package com.example.wallpaper.utils

fun checkString(input: String): StringValidation {
    val specialChars = "~`!@#$%^&*()-_=+\\|[{]};:'\",<.>/?"
    var currentCharacter: Char
    var numberPresent = false
    var upperCasePresent = false
    var specialCharacterPresent = false

    for (element in input) {
        currentCharacter = element
        when {
            Character.isUpperCase(currentCharacter) -> {
                upperCasePresent = true
            }
            Character.isDigit(currentCharacter) -> {
                numberPresent = true
            }
            specialChars.contains(currentCharacter.toString()) -> {
                specialCharacterPresent = true
            }
        }
    }

    return StringValidation(upperCasePresent,numberPresent,specialCharacterPresent)
}

class StringValidation(
    var containsUpperCase: Boolean = false,
    var containsNumber: Boolean = false,
    var containsSymbols: Boolean = false
)