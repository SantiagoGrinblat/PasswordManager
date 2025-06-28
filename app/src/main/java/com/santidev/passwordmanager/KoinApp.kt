package com.santidev.passwordmanager

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.santidev.passwordmanager.database.SecretDatabase
import org.koin.core.context.startKoin
import org.koin.dsl.module

class KoinApp: Application() {
  override fun onCreate() {
    super.onCreate()
    startKoin {
      modules(module {
        single {
          Room.databaseBuilder(this@KoinApp, SecretDatabase::class.java, "password")
            .addMigrations(MIGRATION_1_2)
            .build()
        }
      })
    }
  }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
  override fun migrate(db: SupportSQLiteDatabase) {
    db.execSQL("ALTER TABLE passwords ADD COLUMN title TEXT NOT NULL DEFAULT ''")
  }
}