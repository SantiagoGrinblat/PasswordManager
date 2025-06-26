package com.santidev.passwordmanager.ui.screens

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.santidev.passwordmanager.biometric.BiometricHelper
import com.santidev.passwordmanager.utils.SecurityScreen

@Composable
fun SavePassword() {
  val authorized = remember {
    mutableStateOf(false)
  }
  val context = LocalContext.current
  
  val authorize:() -> Unit = {
    BiometricHelper.showPrompt(context as AppCompatActivity) {
      authorized.value = true
    }
  }
  
  LaunchedEffect(Unit) {
    authorize()
  }
  
//  val blurValue by animateDpAsState(
//    targetValue = if (authorized.value) 0.dp else 15.dp,
//    animationSpec = tween(100)
//  )
  
  SecurityScreen()
  
}
