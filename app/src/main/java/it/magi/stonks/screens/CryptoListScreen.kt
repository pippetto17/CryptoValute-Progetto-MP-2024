package it.magi.stonks.screens

import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import it.magi.stonks.R
import it.magi.stonks.composables.CoinItem
import it.magi.stonks.viewmodels.HomeViewModel

@Composable
fun CryptoListScreen(navController: NavController, viewModel: HomeViewModel) {

    val context = LocalContext.current
    val application = LocalContext.current.applicationContext as Application
    val apiKey = stringResource(R.string.api_key)
    val currency = viewModel.getCurrencyPreference(application)
    Log.d("CryptoScreen", "currency preference: $currency")
    viewModel.filterCoinsApiRequest(
        context,
        apiKey = apiKey,
        currency = currency,
        priceChangePercentage = "24h"
    )
    viewModel.trendingListApiRequest(apiKey)
    viewModel.coinMarketChartDataById(apiKey,"bitcoin", "usd", 30)
    val trendingList= viewModel.getTrendingList().observeAsState()
    val coins = viewModel.getCoinsList().observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("#")
            Text("Currency")
            Text("Price")
            Text("24 Hours")
        }
        LazyColumn {
            items(coins.value ?: emptyList()) { coin ->
                CoinItem(
                    imageURI = coin.image,
                    name = coin.name ?: "Unknown",
                    symbol = coin.symbol ?: "Unknown",
                    price = coin.current_price.toString(),
                    id = coin.id ?: "Unknown",
                    onClick = {
                        navController.navigate("coin/${coin.id}")
                    },
                    onAddClick = {}
                )
            }

        }
    }
}







