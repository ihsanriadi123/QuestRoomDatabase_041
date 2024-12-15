package com.example.roomlocal.ui.view.mahasiswa

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan10.ui.viewmodel.PenyediaViewModel
import com.example.praktikumpertemuan10.ui.view.mahasiswa.InsertBodyMhs
import com.example.roomlocal.ui.viewmodel.UpdateMhsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun UpdateMhsView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateMhsViewModel = viewModel(factory = PenyediaViewModel.Factory) //Inisialisasi Viewmodel
) {
    val uiState = viewModel.updateUIState //Ambil UI State dari ViewModel
    val snackbarHostState = remember { SnackbarHostState() } //Snackbar state
    val coroutineScope = rememberCoroutineScope()

    // Observasi perubahan snackbarMessage
    LaunchedEffect(uiState.snackBarMessage) {
        println("LaunchedEffect triggered")
        uiState.snackBarMessage?.let { message ->
            println("Snackbar message received: $message")
            coroutineScope.launch {
                println("Launching coroutine for snackbar")
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Long
                )
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) }, //Tempat Snackbar di Scaffols
        topBar = {
            TopAppBar(
                judul = "Edit Mahasiswa",
                showBackButton = true,
                onBack = onBack
            )
        }

    ) {padding ->
        Column (
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ){
            //isi body
            InsertBodyMhs<Any>(
                uiState = uiState,
                onValueChange = {updateEvent ->
                    viewModel.updateState(updateEvent) //Update state di viewmodel
                },
                onClick = {
                    coroutineScope.launch {
                        if (viewModel.validateFields()) {
                            viewModel.updateData()
                            delay(600)
                            withContext(Dispatchers.Main) {
                                onNavigate() //Navigasi di main tread
                            }
                        }
                    }
                }
            )
        }
    }
}