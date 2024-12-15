package com.example.roomlocal.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan10.ui.viewmodel.toDetailUiEvent
import com.example.roomlocal.data.entity.Mahasiswa
import com.example.roomlocal.data.repository.RepositoryMhs
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateMhsViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoryMhs: RepositoryMhs
) : ViewModel() {

    var updateUIState by mutableStateOf(MhsUIState(this.toDetailUiEvent()))
        private set

    private val _nim: String = checkNotNull(savedStateHandle[DestinasiEdit.NIM])

    init {
        viewModelScope.launch {
            updateUIState = repositoryMhs.getMhs(_nim)
                .filterNotNull()
                .first()
                .toUIStateMhs()
        }
    }

    fun updateState(mahasiswaEvent: MahasiswaEvent){
        updateUIState = updateUIState.copy(
            mahasiswaEvent = mahasiswaEvent,
        )
    }

    fun validateFields(): Boolean{
        val event = updateUIState.mahasiswaEvent
        val errorState = FormErrorState(
            nim = if (event.nim.isNotEmpty()) null else "NIM tidak boleh kosong",
            nama =  if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            jeniskelamin = if (event.jeniskelamin.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong",
            alamat = if (event.alamat.isNotEmpty()) null else "Alamat tidak boleh kosong",
            kelas = if (event.kelas.isNotEmpty()) null else "Kelas tidak boleh kosong",
            angkatan = if (event.angkatan.isNotEmpty()) null else "Angkatan tidak boleh kosong",
        )

        updateUIState = updateUIState.copy(isEntryValid = errorState)
        return errorState.isvalid()
    }

    fun updateData(){
        val currentEvent = updateUIState.mahasiswaEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryMhs.updateMhs(currentEvent.toMahasiswaEntity())
                    updateUIState = updateUIState.copy(
                        snackbarMessage = "Data berhasil diupdate",
                        mahasiswaEvent = MahasiswaEvent(jenisKelamin = jenisKelamin),
                        isEntryValid = FormErrorState()
                    )
                    println("snackBarMessage diatur: ${updateUIState.snackbarMessage}")
                } catch (e: Exception){
                    updateUIState = updateUIState.copy(
                        snackbarMessage = "Data gagal diupdate"
                    )
                }
            }
        } else {
            updateUIState = updateUIState.copy(
                snackbarMessage = "Data gagal diupdate"
            )
        }
        fun resetSnackBarMessage(){
            updateUIState = updateUIState.copy(snackbarMessage = null)
        }
    }

    fun Mahasiswa.toUIStateMhs(): MhsUIState = MhsUIState(
        mahasiswaEvent = this.toDetailUiEvent(),
    )

    class MhsUIState(mahasiswaEvent: Any) {

    }

    fun resetSnackBarMessage() {
        updateUIState = updateUIState.copy(snackbarMessage = null)
    }
}