package com.santidev.passwordmanager.biometric

import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat

object BiometricHelper {
  
  fun createBiometricPrompt(
    activity: AppCompatActivity,
    processSuccess: (BiometricPrompt.AuthenticationResult) -> Unit
  ): BiometricPrompt {
    val executor = ContextCompat.getMainExecutor(activity)
    
    val callback = object : BiometricPrompt.AuthenticationCallback() {
      override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
        super.onAuthenticationError(errorCode, errString)
      }
      
      override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
        super.onAuthenticationSucceeded(result)
        processSuccess(result)
      }
      
      override fun onAuthenticationFailed() {
        super.onAuthenticationFailed()
      }
    }
    
    return BiometricPrompt(activity, executor, callback)
  }
  
  fun createPromptInfo(): BiometricPrompt.PromptInfo =
    BiometricPrompt.PromptInfo.Builder().apply {
      setTitle("Autenticación biometrica")
      setDescription("Use su huella para desbloquear esta seccion")
      setConfirmationRequired(false)
      setNegativeButtonText("Cancelar")
    }.build()
  
  fun showPrompt(activity: AppCompatActivity, onSuccess: () -> Unit) {
    createBiometricPrompt(activity = activity) {
      onSuccess()
    }.authenticate(createPromptInfo())
  }
}
