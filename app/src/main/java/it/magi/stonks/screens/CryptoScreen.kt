package it.magi.stonks.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import it.magi.stonks.R
import it.magi.stonks.composables.CoinItem
import it.magi.stonks.composables.FilterBar
import it.magi.stonks.viewmodels.HomeViewModel

@Composable
fun CryptoScreen(viewModel: HomeViewModel) {

    val context = LocalContext.current
    val coins = viewModel.getCoinsList().observeAsState()
    val apiKey = stringResource(R.string.api_key)
    val currency = "eur"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentWidth(Alignment.Start)
    ) {
        FilterBar(viewModel)
        Button(onClick = {
            viewModel.filterCoins(
                context,
                apiKey = apiKey,
                currency = currency
            )
        }) {
            Text(text = "Show coins")
        }
        LazyColumn {
            items(coins.value ?: emptyList()) { coin ->
                CoinItem(
                    imageURI = coin.image,
                    name = coin.name ?: "Unknown",
                    price = coin.current_price.toString(),
                    currency = currency,
                )
            }
        }
    }
}