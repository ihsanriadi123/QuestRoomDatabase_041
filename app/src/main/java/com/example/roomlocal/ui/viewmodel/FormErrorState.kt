package com.example.roomlocal.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomlocal.data.entity.Mahasiswa
import com.example.roomlocal.data.repository.RepositoryMhs
import kotlinx.coroutines.launch

// Data class representing a Mahasiswa event
data class MahasiswaEvent(
    val nim: String = "",
    val nama: String = "",
    var jenisKelamin: String = "",
    val alamat: String = "",
    val kelas: String = "",
    val angkatan: String = ""
)

// Extension function to convert MahasiswaEvent to Mahasiswa entity
fun MahasiswaEvent.toMahasiswaEntity(): Mahasiswa = Mahasiswa(
    nim = nim,
    nama = nama,
    jenisKelamin = jenisKelamin,
    alamat = alamat,
    kelas = kelas,
    angkatan = angkatan
)

// Data class to hold form error states
data class FormErrorState(
    val nim: String? = null,
    val nama: String? = null,
    val jenisKelamin: String? = null,
    val alamat: String? = null,
    val kelas: String? = null,
    val angkatan: String? = null
) {
    fun isValid(): Boolean {
        return nim == null &&
                nama == null &&
                jenisKelamin == null &&
                alamat == null &&
                kelas == null &&
                angkatan == null
    }
}

// Data class representing UI state for Mahasiswa
data class MhsUIState(
    val mahasiswaEvent: MahasiswaEvent = MahasiswaEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null
)

// ViewModel to manage UI state for Mahasiswa
class MahasiswaViewModel(
    private val repositoryMhs: RepositoryMhs
) : ViewModel() {

    var uiState by mutableStateOf(MhsUIState())

    // Function to update the state based on user input
    fun updateState(mahasiswaEvent: MahasiswaEvent) {
        uiState = uiState.copy(
            mahasiswaEvent = mahasiswaEvent
        )
    }

    // Function to validate fields
    private fun validateFields(): Boolean {
        val event = uiState.mahasiswaEvent
        val errorState = FormErrorState(
            nim = if (event.nim.isNotEmpty()) null else "NIM tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            jenisKelamin = if (event.jenisKelamin.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong",
            alamat = if (event.alamat.isNotEmpty()) null else "Alamat tidak boleh kosong",
            kelas = if (event.kelas.isNotEmpty()) null else "Kelas tidak boleh kosong",
            angkatan = if (event.angkatan.isNotEmpty()) null else "Angkatan tidak boleh kosong"
        )

        // Update the state with the new error state
        uiState = uiState.copy(isEntryValid = errorState)

        // Return whether the form is valid
        return errorState.isValid()
    }

    // Function to save data to the repository
    fun saveData() {
        val currentEvent = uiState.mahasiswaEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    // Save the Mahasiswa entity to the repository
                    repositoryMhs.insertMhs(currentEvent.toMahasiswaEntity())

                    // Update the UI state upon successful save
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        mahasiswaEvent = MahasiswaEvent(), // Reset input form
                        isEntryValid = FormErrorState() // Reset error state
                    )
                } catch (e: Exception) {
                    // Handle the exception and show an error message
                    uiState = uiState.copy(
                        snackBarMessage = "Data gagal disimpan"
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data anda"
            )
        }
    }

    // Function to reset snackbar message after being shown
    fun resetSnackBarMessage() {
        uiState = uiState.copy(snackBarMessage = null)
    }
}

