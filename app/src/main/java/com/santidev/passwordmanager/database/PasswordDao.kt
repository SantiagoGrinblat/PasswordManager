package com.santidev.passwordmanager.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {
  
  @Insert
  fun createPassword(password: PasswordEntity)
  
  @Query("SELECT * FROM passwords")
  fun getPasswords(): Flow<List<PasswordEntity>>
  
  @Delete
  fun deletePassword(password: PasswordEntity)
}