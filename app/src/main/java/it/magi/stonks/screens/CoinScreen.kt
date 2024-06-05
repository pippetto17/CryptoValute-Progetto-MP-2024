package it.magi.stonks.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import it.magi.stonks.composables.CustomTopAppBar
import it.magi.stonks.ui.theme.FormContainerColor
import it.magi.stonks.viewmodels.CoinViewModel

@Composable
fun CoinScreen(
    navController: NavController,
    viewModel: CoinViewModel,
    coinId: String?,

    ) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopAppBar(
                navController = navController,
                isHome = false
            )
        },
        containerColor = FormContainerColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (coinId != null) {
                Text(coinId)
            } else {
                Text("Error")
            }

        }
    }

}