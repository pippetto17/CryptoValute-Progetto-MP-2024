package it.magi.stonks.screens

import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import it.magi.stonks.R
import it.magi.stonks.composables.CoinItem
import it.magi.stonks.viewmodels.HomeViewModel

@Composable
fun CryptoListScreen(navController: NavController, viewModel: HomeViewModel,application: Application) {

    val apiKey = stringResource(R.string.api_key)
    val currency = viewModel.getCurrencyPreference(application)
    val filterState = viewModel.filter.collectAsState()
    Log.d("CryptoScreen", "currency preference: $currency")
    viewModel.filterCoinsApiRequest(
        apiKey = apiKey,
        currency = currency,
        ids = filterState.value,
        priceChangePercentage = "24h"
    )
    //viewModel.trendingListApiRequest(apiKey)
    //viewModel.coinMarketChartDataById(apiKey, "bitcoin", "usd", 30)
    val trendingList = viewModel.getTrendingList().observeAsState()
    val coins =viewModel.getCoinsList().observeAsState()
    Log.d("CryptoScreen", "coins: $coins")

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
            Text(
                "#",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(30.dp)
            )
            Text(
                "Currency",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(80.dp)
            )
            Text(
                "Price",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.End,
                modifier = Modifier.width(120.dp)
            )
            Text(
                "24 Hours",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(70.dp)
            )
        }
        LazyColumn (){
            items(coins.value?: emptyList()) { coin ->
                CoinItem(
                    prefCurrency = currency,
                    rank = coin.market_cap_rank?.toInt().toString(),
                    imageURI = coin.image,
                    name = coin.name ?: "Unknown",
                    symbol = coin.symbol ?: "Unknown",
                    price = coin.current_price.toString(),
                    priceChangePercentage24h = coin.price_change_percentage_24h ?: 0.0.toFloat(),
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







