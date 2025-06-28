package com.santidev.passwordmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santidev.passwordmanager.database.PasswordEntity
import com.santidev.passwordmanager.database.SecretDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel: ViewModel(), KoinComponent {
  val db: SecretDatabase by inject()
  val dao = db.passwordDao()
  
  private val _passwords = MutableStateFlow<List<PasswordEntity>>(emptyList())
  val passwordsViewModel = _passwords.asStateFlow()
  
  init {
    getAll()
  }
  
  //funcion para simplificar el scope
  fun ViewModel.launchIO(block: suspend CoroutineScope.() -> Unit) {
    viewModelScope.launch(Dispatchers.IO) {
      block()
    }
  }
  
  fun createOne(title: String, password: String) {
    launchIO {
      dao.createPassword(PasswordEntity(title = title, password = password))
    }
  }
  
  fun getAll() {
    viewModelScope.launch(Dispatchers.IO) {
      launchIO {
        dao.getPasswords().collect { result ->
          _passwords.update { result }
        }
      }
    }
  }
  
  fun deleteOne(password: PasswordEntity) {
    launchIO {
      dao.deletePassword(password)
    }
  }
  
}