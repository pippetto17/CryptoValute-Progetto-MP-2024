package it.magi.stonks.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import it.magi.stonks.R
import it.magi.stonks.models.SparkLine
import it.magi.stonks.viewmodels.CoinViewModel

@Composable
fun CoinScreen(
    navController: NavController,
    viewModel: CoinViewModel,
    coinId: String?,

) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (coinId != null) {
            Text(coinId)
        }else{
            Text("Error")
        }

    }
}