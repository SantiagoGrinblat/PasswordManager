package com.santidev.passwordmanager.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.santidev.passwordmanager.R

@Composable
fun CopyButton(textToCopy: String) {
  val context = LocalContext.current
  
  Button(
    onClick = {
      copyToClipboard(context, textToCopy)
    },
    shape = RoundedCornerShape(50),
    colors = ButtonDefaults.elevatedButtonColors(
      containerColor = Color(0xFF2C2C2E),
      contentColor = Color.White
    ),
    elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 8.dp)
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(painter = painterResource(id = R.drawable.copy), contentDescription = "Button copy", tint = Color(0xFFC6A766))
      
      Spacer(modifier = Modifier.width(8.dp))
      
      Text(
        text = "Copiar",
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 4.dp)
      )
    }
  }
}

// Función para copiar texto al portapapeles
private fun copyToClipboard(context: Context, text: String) {
  val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
  val clipData = ClipData.newPlainText("Texto copiado", text)
  clipboardManager.setPrimaryClip(clipData)
  
  // Mostrar Toast de confirmación
  Toast.makeText(context, "Copiado al portapapeles", Toast.LENGTH_SHORT).show()
}