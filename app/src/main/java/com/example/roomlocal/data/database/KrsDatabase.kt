package com.example.roomlocal.data.database

import android.content.Context
import android.provider.CalendarContract.Instances
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomlocal.data.dao.MahasiswaDao
import com.example.roomlocal.data.entity.Mahasiswa

@Database(entities = [Mahasiswa::class], version = 1, exportSchema = false)
abstract class KrsDatabase : RoomDatabase() {

    //Medefinisikan fungsi untuk mengakses data mahasiswa
    abstract fun  mahasiswaDao(): MahasiswaDao

    companion object{
        @Volatile // memastikan bahawa nilai variabel instance selalu sama di semua
        private var  Instances: KrsDatabase? = null


        fun getDatabase(context: Context): KrsDatabase{
            return (Instances ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    KrsDatabase::class.java,
                    "krsDatabase"
                )
                    .build().also { Instances =it }
            })
        }
    }
}