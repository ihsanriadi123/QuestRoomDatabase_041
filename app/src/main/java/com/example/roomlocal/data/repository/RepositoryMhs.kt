package com.example.roomlocal.data.repository

import com.example.roomlocal.data.entity.Mahasiswa

interface RepositoryMhs {
    suspend fun insertMhs(mahasiswa: Mahasiswa)
}