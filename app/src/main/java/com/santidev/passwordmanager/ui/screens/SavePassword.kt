package com.santidev.passwordmanager.ui.screens

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.santidev.passwordmanager.MainViewModel
import com.santidev.passwordmanager.biometric.BiometricHelper

@Composable
fun SavePassword() {
  
  SecurityScreen()
  
}

@Composable
fun SecurityScreen() {
  val authorized = remember {
    mutableStateOf(false)
  }
  val context = LocalContext.current
  
  val authorize: () -> Unit = {
    BiometricHelper.showPrompt(context as AppCompatActivity) {
      authorized.value = true
    }
  }
  
  LaunchedEffect(Unit) {
    authorize()
  }
  
  val blurValue by animateDpAsState(
    targetValue = if (authorized.value) 0.dp else 15.dp,
    animationSpec = tween(100)
  )
  
  val viewModel: MainViewModel = viewModel()
  
  val passwords by viewModel.passwordsViewModel.collectAsState()
  
  val dialogOpen = remember {
    mutableStateOf(false)
  }
  
  Surface(
    modifier = Modifier.fillMaxSize(),
    color = MaterialTheme.colorScheme.background
  ) {
    if (dialogOpen.value) {
      Dialog(onDismissRequest = { dialogOpen.value = false }) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(MaterialTheme.colorScheme.background)
              .padding(16.dp)
          ) {
            val secretPassword = remember {
              mutableStateOf("")
            }
            OutlinedTextField(
              value = secretPassword.value, onValueChange = {
                secretPassword.value = it
              }, modifier = Modifier.fillMaxWidth(), label = {
                Text(text = "Titulo")
              },
              leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock Icon")
              }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
              onClick = {
                viewModel.createOne(secretPassword.value)
                dialogOpen.value = false
              }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFC6A766)
              ), shape = RoundedCornerShape(12.dp)
            ) {
              Text(text = "Guardar", color = Color.White, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }
    
    Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
      Column {
        FloatingActionButton(onClick = {
          dialogOpen.value = true
        }, containerColor = Color(0xFFC6A766)) {
          Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
            tint = Color.White
          )
        }
      }
    }) { paddings ->
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(paddings)
          .blur(
            blurValue,
            edgeTreatment = BlurredEdgeTreatment.Unbounded
          )
      )
      {
        LazyColumn(
          modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          items(passwords.reversed(), key = { it.id ?: 0 }) {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(
                  MaterialTheme.colorScheme.primary
                )
            ) {
              Text(text = it.password)
            }
          }
        }
      }
    }
  }
}