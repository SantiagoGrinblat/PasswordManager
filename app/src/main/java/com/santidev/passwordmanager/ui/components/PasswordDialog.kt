package com.santidev.passwordmanager.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun PasswordDialog(
  onDismiss: () -> Unit,
  onSave: (title: String, password: String) -> Unit
) {
  Dialog(onDismissRequest = onDismiss) {
    Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(MaterialTheme.colorScheme.background)
          .padding(16.dp)
      ) {
        val title = remember { mutableStateOf("") }
        val secretPassword = remember { mutableStateOf("") }
        
        OutlinedTextField(
          value = title.value,
          onValueChange = { title.value = it },
          modifier = Modifier.fillMaxWidth(),
          label = { Text(text = "Titulo", color = Color.White) },
          colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
          )
        )
        
        Spacer(modifier = Modifier.height(6.dp))
        
        OutlinedTextField(
          value = secretPassword.value,
          onValueChange = { secretPassword.value = it },
          modifier = Modifier.fillMaxWidth(),
          label = { Text(text = "Contraseña", color = Color.White) },
          colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
          ),
          leadingIcon = {
            Icon(
              imageVector = Icons.Default.Lock,
              contentDescription = "Lock Icon"
            )
          }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
          onClick = {
            if (secretPassword.value.isNotEmpty() && title.value.isNotEmpty()) {
              onSave(title.value, secretPassword.value)
              onDismiss()
            }
          },
          modifier = Modifier.fillMaxWidth(),
          colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFC6A766)
          ),
          shape = RoundedCornerShape(12.dp)
        ) {
          Text(
            text = "Guardar",
            color = Color.White,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }
  }
}