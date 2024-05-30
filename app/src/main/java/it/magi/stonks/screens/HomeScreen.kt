package it.magi.stonks.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import it.magi.stonks.R
import it.magi.stonks.composables.CoinItem
import it.magi.stonks.models.Coin
import it.magi.stonks.viewmodels.HomeViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel) {

    val context = LocalContext.current
    val coins = viewModel.getCoinsList().observeAsState()
    val apiKey = stringResource(R.string.api_key)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentWidth()
    ) {
        Button(onClick = {
            viewModel.filterCoins(
                context,
                apiKey = apiKey,
                currency = "usd"
            )
        }) {
            Text(text = "Click me")
        }

        LazyColumn {
            items(coins.value ?: emptyList()) { coin ->
                CoinItem(
                    imageURI = coin.image,
                    name = coin.name ?: "Unknown",
                    symbol = coin.symbol ?: "Unknown",
                    price = coin.current_price.toString(),
                )
            }
        }
    }
}