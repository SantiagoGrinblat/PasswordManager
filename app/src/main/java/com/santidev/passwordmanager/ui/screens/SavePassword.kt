package com.santidev.passwordmanager.ui.screens

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.santidev.passwordmanager.MainViewModel
import com.santidev.passwordmanager.R
import com.santidev.passwordmanager.biometric.BiometricHelper
import com.santidev.passwordmanager.utils.CopyButton

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
  
  OnLifecycleEvent { owner, event ->
    if (event == Lifecycle.Event.ON_PAUSE) {
      authorized.value = false
    }
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
            val title = remember { mutableStateOf("") }
            OutlinedTextField(
              value = title.value,
              onValueChange = { title.value = it },
              modifier = Modifier.fillMaxWidth(),
              label = { Text(text = "Titulo", color = Color.White) }, colors = OutlinedTextFieldDefaults.colors(
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
                Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock Icon")
              }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
              onClick = {
                if (secretPassword.value.isNotEmpty() == true && title.value.isNotEmpty() == true) {
                  viewModel.createOne(title.value, secretPassword.value)
                  dialogOpen.value = false
                }
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
        AnimatedVisibility(visible = !authorized.value) {
          FloatingActionButton(
            onClick = authorize,
            containerColor = Color(0xFFC6A766)
          ) {
            Icon(
              imageVector = Icons.Default.Lock,
              contentDescription = "Icon Lock",
              tint = Color.White
            )
          }
        }
        Spacer(modifier = Modifier.height(8.dp))
        FloatingActionButton(onClick = {
          dialogOpen.value = true
        }, containerColor = Color(0xFFC6A766)) {
          Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Icon Add",
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
          items(passwords, key = { it.id ?: 0 }) {
            Box(
              modifier = Modifier
                .animateItem()
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(color = Color.Transparent)
                .border(1.dp, Color(0xFFA39F9F), RoundedCornerShape(8.dp))
                .padding(16.dp)
            ) {
              Column {
                Text(text = it.title, fontWeight = FontWeight.Bold, color = Color.White)
                HorizontalDivider(
                  modifier = Modifier
                    .fillMaxWidth()
                    .height(15.dp),
                  thickness = 1.dp,
                  color = Color(0xFFA39F9F)
                )
                
                Text(text = it.password, fontWeight = FontWeight.Light, color = Color.White)
                
                Spacer(modifier = Modifier.height(6.dp))
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                  AnimatedVisibility(visible = authorized.value) {
                    Button(
                      onClick = {viewModel.deleteOne(it)},
                      shape = RoundedCornerShape(50),
                      colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color(0xFF2C2C2E),
                        contentColor = Color.White
                      ),
                      elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 8.dp)
                    ) {
                      Text(text = "BORRAR", color = Color.White)
                      Spacer(modifier = Modifier.width(4.dp))
                      Icon(
                        painter = painterResource(id = R.drawable.deleteicon),
                        contentDescription = "Button Delete" ,
                        tint = Color(0xFFC6A766)
                      )
                    }
                  }
                  CopyButton(
                    textToCopy = it.password
                  )
                }
              }
            }
          }
        }
      }
    }
  }
}

@Composable
fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
  
  val eventHandler = rememberUpdatedState(newValue = onEvent)
  val lifecycleOwner = rememberUpdatedState(newValue = LocalLifecycleOwner.current)
  
  DisposableEffect(lifecycleOwner.value) {
    val lifecycle = lifecycleOwner.value.lifecycle
    val observer = LifecycleEventObserver { owner, event ->
      eventHandler.value(owner, event)
    }
    lifecycle.addObserver(observer)
    
    onDispose {
      lifecycle.removeObserver(observer)
    }
  }
}
