package com.santidev.passwordmanager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.santidev.passwordmanager.navigation.SavePassword
import com.santidev.passwordmanager.ui.screens.MainScreen
import com.santidev.passwordmanager.ui.screens.SavePassword
import com.santidev.passwordmanager.ui.screens.SplashScreen
import com.santidev.passwordmanager.utils.PasswordGeneratorScreen

@Composable
fun NavigationWrapper() {
  val navController = rememberNavController()
  
  NavHost(navController = navController, startDestination = SplashScreen) {
    composable<SplashScreen> {
      SplashScreen(navController = navController)
    }
    composable<MainScreen> {
      MainScreen(navController = navController)
    }
    composable<SavePassword> {
      SavePassword()
    }
  }
  
}
