package com.example.roomlocal.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.roomlocal.KrsApp

object PenyediaViewModel {

    // Factory to create MahasiswaViewModel instances
    val Factory = viewModelFactory {
        initializer {
            MahasiswaViewModel(
                krsApp().containerApp.repositoryMhs
            )
        }
    }
}

// Extension function to retrieve KrsApp instance from CreationExtras
fun CreationExtras.krsApp(): KrsApp {
    return this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KrsApp}