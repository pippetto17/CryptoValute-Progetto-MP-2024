package it.magi.stonks.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import it.magi.stonks.viewmodels.HomeViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel){
    val screenState = viewModel.screen.collectAsState()
    when(screenState.value){
        1 -> {
            CryptoScreen(viewModel)
        }

        2 -> {
            NFTScreen(viewModel = viewModel)
        }
    }
}