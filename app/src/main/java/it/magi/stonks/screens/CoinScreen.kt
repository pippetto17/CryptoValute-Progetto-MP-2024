package it.magi.stonks.screens

import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import it.magi.stonks.composables.CoinMarketChart
import it.magi.stonks.composables.CustomTopAppBar
import it.magi.stonks.composables.SignButton
import it.magi.stonks.ui.theme.FormContainerColor
import it.magi.stonks.viewmodels.HomeViewModel

@Composable
fun CoinScreen(
    navController: NavController,
    viewModel: HomeViewModel,
    apiKey: String,
    coinId: String,
    currency: String,


) {
    viewModel.coinMarketChartDataById(apiKey, coinId, currency, 1)
    val chartData = viewModel.getCoinMarketChart().observeAsState()
    val priceList=chartData.value?.prices
    Log.d("priceList",priceList.toString())
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            CustomTopAppBar(
                navController = navController,
                isHome = false,
                viewModel = viewModel
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
            CoinMarketChart()
            Spacer(modifier = Modifier.fillMaxHeight(0.5f))
            Text(coinId, color = Color.White)
            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            SignButton(
                onclick = { /*TODO*/ },
                text = "Add to Wallet"
            )
        }
    }

}


