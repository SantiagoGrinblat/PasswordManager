package com.santidev.passwordmanager.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "passwords")
data class PasswordEntity(
  @PrimaryKey(autoGenerate = true)
  val id : Int? = null,
  
  @ColumnInfo(name = "password")
  val password : String,
  
  @ColumnInfo(name = "title")
  val title : String,
)