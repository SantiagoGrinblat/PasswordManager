package com.santidev.passwordmanager.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.santidev.passwordmanager.R
import com.santidev.passwordmanager.navigation.MainScreen
import com.santidev.passwordmanager.navigation.SplashScreen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
  LaunchedEffect(key1 = true) {
    delay(2000)
    navController.navigate(MainScreen) {
      popUpTo(SplashScreen) {inclusive = true}
    }
  }
  Splash()
}

@Composable
fun Splash() {
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Image(
      painter = painterResource(id = R.drawable.fondo),
      contentDescription = null,
      contentScale = ContentScale.Crop,
      modifier = Modifier.fillMaxSize()
    )
  }
}