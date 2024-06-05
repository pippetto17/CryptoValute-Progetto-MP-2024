package it.magi.stonks.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import it.magi.stonks.R
import it.magi.stonks.composables.CoinItem
import it.magi.stonks.models.SparkLine
import it.magi.stonks.viewmodels.CoinViewModel
import it.magi.stonks.viewmodels.HomeViewModel

@Composable
fun CryptoListScreen(navController: NavController, viewModel: HomeViewModel) {

    val context = LocalContext.current
    val apiKey = stringResource(R.string.api_key)
    val currency = "usd"
    Log.d("CryptoScreen", "currency preference: $currency")
    viewModel.filterCoins(
        context,
        apiKey = apiKey,
        currency = currency
    )
    val coins = viewModel.getCoinsList().observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn {
            items(coins.value ?: emptyList()) { coin ->
                CoinItem(
                    imageURI = coin.image,
                    name = coin.name ?: "Unknown",
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







