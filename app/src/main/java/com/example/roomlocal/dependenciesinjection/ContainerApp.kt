package com.example.roomlocal.dependenciesinjection

import android.content.Context
import com.example.roomlocal.data.database.KrsDatabase
import com.example.roomlocal.data.repository.LocalRepositoryMhs

interface InterfaceContainerApp {
    val repositoryMhs: LocalRepositoryMhs
}

class ContainerApp(private val context: Context) : InterfaceContainerApp {
    override val repositoryMhs: LocalRepositoryMhs by lazy {
        LocalRepositoryMhs(KrsDatabase.getDatabase(context).mahasiswaDao())
    }
}