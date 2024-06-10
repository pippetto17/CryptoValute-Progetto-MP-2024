package it.magi.stonks.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import it.magi.stonks.R
import it.magi.stonks.composables.NFTItem
import it.magi.stonks.viewmodels.StonksViewModel

@Composable
fun NFTScreen(viewModel: StonksViewModel) {
    val context = LocalContext.current
    val apiKey = stringResource(R.string.api_key)
    val currency = "usd"
    Log.d("CryptoScreen", "currency preference: $currency")
    viewModel.NFtsApiRequest(
        apiKey = apiKey
    )
    val coins = viewModel.getNFTsList().observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentWidth(Alignment.Start)
    ) {
        LazyColumn {
            items(coins.value ?: emptyList()) { coin ->
                NFTItem(
                    name = coin.name,
                    symbol = coin.symbol
                )
            }
        }
    }
}