package com.example.praktikumpertemuan10.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pertemuan10.ui.view.mahasiswa.HomeMhsView
import com.example.roomlocal.ui.navigation.DestinasiDetail
import com.example.roomlocal.ui.navigation.DestinasiHome
import com.example.roomlocal.ui.navigation.DestinasiInsert
import com.example.roomlocal.ui.navigation.DestinasiUpdate
import com.example.roomlocal.ui.view.mahasiswa.DetailMhsView
import com.example.roomlocal.ui.view.mahasiswa.UpdateMhsView
import com.example.tugas_pertemuan10.ui.view.mahasiswa.InsertMhsView


@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route){

        composable(
            route = DestinasiHome.route
        ){
            HomeMhsView(
                onDetailClick = { nim ->
                    navController.navigate("${DestinasiDetail.route}/$nim")
                    println(
                        "PengelolaHalaman: nim = $nim"
                    )
                },
                onAddMhs = {
                    navController.navigate(DestinasiInsert.route)
                },
                modifier = modifier
            )
        }
        composable(
            route = DestinasiInsert.route
        ){
            InsertMhsView<Any>(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
            )
        }
        composable(
            DestinasiDetail.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetail.NIM){
                    type = NavType.StringType
                }
            )
        ){
            val nim = it.arguments?.getString(DestinasiDetail.NIM)
            nim?.let { nim ->
                DetailMhsView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdate.route}/$it")
                    },
                    modifier = modifier,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
        composable(
            DestinasiUpdate.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdate.NIM){
                    type = NavType.StringType
                }
            )
        ){
            UpdateMhsView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }
    }
}