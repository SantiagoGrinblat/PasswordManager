package com.santidev.passwordmanager.utils

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.santidev.passwordmanager.R
import com.santidev.passwordmanager.navigation.SavePassword
import com.santidev.passwordmanager.ui.screens.SavePassword

@Composable
fun PasswordGeneratorScreen(navController: NavHostController) {
  
  var length by remember { mutableStateOf(12) }
  var useNumbers by remember { mutableStateOf(true) }
  var useSymbols by remember { mutableStateOf(true) }
  var password by remember { mutableStateOf("") }
  val snackbarHostState = remember { SnackbarHostState() }
  
  fun regenerate() {
    password = generatePassword(
      length = length,
      useNumbers = useNumbers,
      useSymbols = useSymbols
    )
  }
  
  LaunchedEffect(Unit) {
    regenerate()
  }
  
  Scaffold(
    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    bottomBar = {
    
    }
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(24.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text("TU CONTRASEÑA GENERADA:", fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold)
      Spacer(modifier = Modifier.height(16.dp))
      Text(
        text = password,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        modifier = Modifier
          .border(1.dp, Color(0xFFA39F9F), RoundedCornerShape(8.dp))
          .padding(8.dp)
      )
      Spacer(modifier = Modifier.height(16.dp))
      // 👉 Botón copiar al portapapeles
      CopyButton(textToCopy = password)
      
      Spacer(modifier = Modifier.height(16.dp))
      
      Button(
        onClick = { regenerate() },
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.elevatedButtonColors(
          containerColor = Color(0xFFC6A766),
          contentColor = Color.Black
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 8.dp)
      ) {
        Text("Generar nueva clave", fontSize = 16.sp, fontWeight = FontWeight.Bold)
      }
      
      Spacer(modifier = Modifier.height(24.dp))
      
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
      ) {
        Text(
          text = when {
            length < 6 -> "Seguridad: Insegura"
            length < 12 -> "Seguridad: Moderada"
            else -> "Seguridad: Segura"
          },
          fontSize = 16.sp,
          fontWeight = FontWeight.SemiBold,
          color = when {
            length < 6 -> Color.Red
            length < 12 -> Color(0xFFFFA500)
            else -> Color.Green
          }
        )
        
        Spacer(modifier = Modifier.width(8.dp))
        
        Icon(
          painter = painterResource(
            id = when {
              length < 6 -> R.drawable.skull
              length < 12 -> R.drawable.skull
              else -> R.drawable.skull
            }
          ),
          contentDescription = "Nivel de seguridad",
          tint = when {
            length < 6 -> Color.Red
            length < 12 -> Color(0xFFFFA500)
            else -> Color.Green
          },
          modifier = Modifier.size(24.dp)
        )
      }
      
      Spacer(modifier = Modifier.height(16.dp))
      
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        ElevatedButton(
          onClick = {
            if (length > 4) {
              length--
              regenerate()
            }
          },
          shape = CircleShape,
          colors = ButtonDefaults.elevatedButtonColors(
            containerColor = Color(0xFF2C2C2E),
            contentColor = Color(0xFFC6A766)
          ),
          elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 6.dp),
          contentPadding = PaddingValues(0.dp),
          modifier = Modifier.size(46.dp)
        ) {
          Icon(
            painter = painterResource(id = R.drawable.minus),
            contentDescription = "Button minus"
          )
        }
        
        Spacer(modifier = Modifier.width(18.dp))
        
        Row {
          Text("Caracteres:", fontSize = 18.sp, color = Color.White)
          Spacer(modifier = Modifier.width(4.dp))
          Text("$length", fontSize = 18.sp, color = when {
            length < 6 -> Color.Red
            length < 12 -> Color(0xFFFFA500)
            else -> Color.Green
          })
        }
        
        Spacer(modifier = Modifier.width(18.dp))
        
        Button(
          onClick = {
            if (length < 24) {
              length++
              regenerate()
            }
          },
          shape = CircleShape,
          colors = ButtonDefaults.elevatedButtonColors(
            containerColor = Color(0xFF2C2C2E),
            contentColor = Color(0xFFC6A766)
          ),
          elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 6.dp),
          contentPadding = PaddingValues(0.dp),
          modifier = Modifier.size(46.dp)
        ) {
          Icon(
            painter = painterResource(id = R.drawable.plus),
            contentDescription = "Button plus"
          )
        }
      }
      
      Spacer(modifier = Modifier.height(16.dp))
      
      Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
          checked = useNumbers,
          onCheckedChange = {
            useNumbers = it
            regenerate()
          },
          colors = CheckboxDefaults.colors(
            checkedColor = Color(0xFFC6A766),
            uncheckedColor = Color(0xFF888888),
            checkmarkColor = Color(0xFF15202B)
          ),
        )
        Text("Incluir Números", fontSize = 18.sp, color = Color.White)
      }
      
      Spacer(modifier = Modifier.height(8.dp))
      
      Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
          checked = useSymbols,
          onCheckedChange = {
            useSymbols = it
            regenerate()
          },
          colors = CheckboxDefaults.colors(
            checkedColor = Color(0xFFC6A766),
            uncheckedColor = Color(0xFF888888),
            checkmarkColor = Color(0xFF15202B)
          ),
        )
        Text("Incluir Símbolos", fontSize = 18.sp, color = Color.White)
      }
      
      Spacer(modifier = Modifier.height(24.dp))
        
      Button(
        onClick = { navController.navigate(SavePassword) },
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.elevatedButtonColors(
          containerColor = Color(0xFFC6A766),
          contentColor = Color.Black
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 8.dp)
      ) {
        Text("Guardar contenido", fontSize = 16.sp, fontWeight = FontWeight.Bold)
      }
    }
  }
  
}
