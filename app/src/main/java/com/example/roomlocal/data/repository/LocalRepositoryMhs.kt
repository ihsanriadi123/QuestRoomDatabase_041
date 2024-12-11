package com.example.roomlocal.data.repository

import com.example.roomlocal.data.dao.MahasiswaDao
import com.example.roomlocal.data.entity.Mahasiswa

class LocalRepositoryMhs(
    private val mahasiswaDao: MahasiswaDao
) : RepositoryMhs{
    override suspend fun insertMhs(mahasiswa: Mahasiswa) {
        mahasiswaDao.insertMahasiswa(mahasiswa)
    }
}