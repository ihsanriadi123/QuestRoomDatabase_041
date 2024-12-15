package com.example.tugas_pertemuan10.ui.view.mahasiswa

import FormErrorState
import MahasiswaEvent
import MahasiswaViewModel
import MhsUIState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan10.ui.view.mahasiswa.TopAppBar
import com.example.pertemuan10.ui.viewmodel.PenyediaViewModel
import com.example.roomlocal.ui.navigation.AlamatNavigasi
import kotlinx.coroutines.launch

object DestinationInsert : AlamatNavigasi {
    override val route: String = "insert_mhs"
}

@Composable
fun InsertMhsView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MahasiswaViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val uiState = viewModel.uiState //Ambil UI State dari ViewModel
    val snackbarHostState = remember {SnackbarHostState()} //Snackbar state
    val coroutineScope = rememberCoroutineScope()

    // Observasi perubahan snackbarMessage
    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message) //tampilkan snackbar
                viewModel.reseSnackBarMessage()
            }
        }
    }

    Scaffold(modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Mahasiswa"
            )
            //isi body
            InsertBodyMhs(
                uiState = uiState,
                onValueChange = {updateEvent ->
                    viewModel.updateState(updateEvent) //update state di viewmodel
                },
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveData()
                    }
                    onNavigate()
                }
            )
        }
    }
}

@Composable
fun InsertBodyMhs(
    modifier: Modifier = Modifier,
    onValueChange: (MahasiswaEvent) -> Unit,
    uiState: MhsUIState,
    onClick: () -> Unit
){
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormMahasiwa(
            mahasiswaEvent = uiState.mahasiswaEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan")
        }
    }
}

@Composable
fun FormMahasiwa(
    mahasiswaEvent: MahasiswaEvent = MahasiswaEvent(),
    onValueChange: (MahasiswaEvent) -> Unit,
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
){
    val jenisKelamin = listOf("Laki-laki", "Perempuan")
    val kelas = listOf("A","B","C","D","E")

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.nama,
            onValueChange = { onValueChange(mahasiswaEvent.copy(nama = it)) },
            label = { Text(text = "Nama") },
            isError = errorState.nama != null,
            placeholder = { Text(text = "Masukkan Nama") }
        )
        Text(
            text = errorState.nama ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.nim,
            onValueChange = { onValueChange(mahasiswaEvent.copy(nim = it)) },
            label = { Text(text = "NIM") },
            isError = errorState.nim != null,
            placeholder = { Text(text = "Masukkan NIM") }
        )
        Text(
            text = errorState.nim ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Jenis Kelamin")
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            jenisKelamin.forEach{jk ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ){
                    RadioButton(
                        selected = mahasiswaEvent.jenisKelamin == jk,
                        onClick = {
                            onValueChange(mahasiswaEvent.copy(jenisKelamin = jk))
                        },
                    )
                    Text(text = jk)
                }
            }
        }
        Text(text = errorState.jenisKelamin ?: "",
            color = Color.Red)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.alamat,
            onValueChange = { onValueChange(mahasiswaEvent.copy(alamat = it)) },
            label = { Text(text = "Alamat") },
            isError = errorState.alamat != null,
            placeholder = { Text(text = "Masukkan Alamat") }
        )
        Text(
            text = errorState.alamat ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Kelas")
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            kelas.forEach{kls ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ){
                    RadioButton(
                        selected = mahasiswaEvent.kelas == kls,
                        onClick = {
                            onValueChange(mahasiswaEvent.copy(kelas = kls))
                        },
                    )
                    Text(text = kls)
                }
            }
        }
        Text(text = errorState.kelas ?: "",
            color = Color.Red)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.angkatan,
            onValueChange = { onValueChange(mahasiswaEvent.copy(angkatan = it)) },
            label = { Text(text = "Angkatan") },
            isError = errorState.angkatan != null,
            placeholder = { Text(text = "Masukkan Angkatan") }
        )
        Text(
            text = errorState.angkatan ?: "",
            color = Color.Red
        )
    }
}