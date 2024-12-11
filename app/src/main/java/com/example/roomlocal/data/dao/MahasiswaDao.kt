package com.example.roomlocal.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.roomlocal.data.entity.Mahasiswa

@Dao
interface MahasiswaDao {
    @Insert
    suspend fun insertMahasiswa(mahasiswa: Mahasiswa)
}