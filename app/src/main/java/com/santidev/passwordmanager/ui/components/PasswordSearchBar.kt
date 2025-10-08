package com.santidev.passwordmanager.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PasswordSearchBar(
  searchQuery: String,
  onSearchChange: (String) -> Unit,
  onClear: () -> Unit,
  modifier: Modifier = Modifier
) {
  OutlinedTextField(
    value = searchQuery,
    onValueChange = onSearchChange,
    modifier = modifier
      .fillMaxWidth()
      .padding(16.dp),
    placeholder = { Text("Buscar...", color = Color.Gray) },
    leadingIcon = {
      Icon(
        imageVector = Icons.Default.Search,
        contentDescription = "Search",
        tint = Color(0xFFC6A766)
      )
    },
    trailingIcon = {
      if (searchQuery.isNotEmpty()) {
        IconButton(onClick = onClear) {
          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Clear",
            tint = Color(0xFFC6A766)
          )
        }
      }
    },
    colors = OutlinedTextFieldDefaults.colors(
      focusedTextColor = Color.White,
      unfocusedTextColor = Color.White,
      focusedBorderColor = Color(0xFFC6A766),
      unfocusedBorderColor = Color(0xFFA39F9F)
    ),
    shape = RoundedCornerShape(12.dp)
  )
}