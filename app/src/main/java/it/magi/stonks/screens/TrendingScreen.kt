package it.magi.stonks.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import it.magi.stonks.activities.apiKey
import it.magi.stonks.composables.CoinItem
import it.magi.stonks.viewmodels.StonksViewModel

@Composable
fun TrendingScreen(
    viewModel: StonksViewModel,
    prefCurrency: String
) {
    val filterState = viewModel.filter.collectAsState()
    Log.d("CryptoScreen", "currency preference: $prefCurrency")
    viewModel.filterCoinsApiRequest(
        apiKey = apiKey,
        currency = prefCurrency,
        ids = filterState.value,
        priceChangePercentage = "24h"
    )
    viewModel.trendingListApiRequest(apiKey)
    //viewModel.coinMarketChartDataById(apiKey, "bitcoin", "usd", 30)
    val trendingList = viewModel.getTrendingList().observeAsState()
    val trendingCoins = trendingList.value?.coins
    val coins = viewModel.getCoinsList().observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Trending",
        )
        LazyColumn {
            items(trendingCoins ?: emptyList()) { coin ->
                CoinItem(
                    prefCurrency = prefCurrency,
                    rank = coin.item.market_cap_rank.toString(),
                    imageURI = coin.item.large,
                    name = coin.item.name,
                    symbol = coin.item.symbol,
                    price = coin.item.price_btc.toFloat(),
                    priceChangePercentage24h = coin.item.score.toFloat(),
                    id = coin.item.id,
                    onClick = {
                    },
                )
            }

        }
    }
}