package com.santidev.passwordmanager.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PasswordEntity::class], version = 1)
abstract class SecretDatabase: RoomDatabase() {
  abstract fun passwordDao(): PasswordDao
}