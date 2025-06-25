package com.santidev.passwordmanager.utils

fun generatePassword(
  length: Int = 16,
  useUppercase: Boolean = true,
  useNumbers: Boolean = true,
  useSymbols: Boolean = true
) : String {
  val lowercase = "abcdefghijklmnopqrstuvwxyz"
  val uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
  val numbers = "0123456789"
  val symbols = "!@#\$%^&*()_+[]{}|;:,.<>?/"
  
  var charPool = lowercase
  if (useUppercase) charPool += uppercase
  if (useNumbers) charPool += numbers
  if (useSymbols) charPool += symbols
  
  return (1..length)
    .map { charPool.random() }
    .joinToString("")
}