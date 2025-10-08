package com.santidev.passwordmanager.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.santidev.passwordmanager.R
import com.santidev.passwordmanager.database.PasswordEntity
import com.santidev.passwordmanager.utils.CopyButton

@Composable
fun PasswordListItem(
  password: PasswordEntity,
  isAuthorized: Boolean,
  onDelete: (PasswordEntity) -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(8.dp))
      .background(color = Color.Transparent)
      .border(1.dp, Color(0xFFA39F9F), RoundedCornerShape(8.dp))
      .padding(16.dp)
  ) {
    Column {
      Text(
        text = password.title,
        fontWeight = FontWeight.Bold,
        color = Color.White
      )
      
      HorizontalDivider(
        modifier = Modifier
          .fillMaxWidth()
          .height(15.dp),
        thickness = 1.dp,
        color = Color(0xFFA39F9F)
      )
      
      Text(
        text = password.password,
        fontWeight = FontWeight.Light,
        color = Color.White
      )
      
      Spacer(modifier = Modifier.height(6.dp))
      
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        AnimatedVisibility(visible = isAuthorized) {
          Button(
            onClick = { onDelete(password) },
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
              contentDescription = "Button Delete",
              tint = Color(0xFFC6A766)
            )
          }
        }
        
        CopyButton(textToCopy = password.password)
      }
    }
  }
}