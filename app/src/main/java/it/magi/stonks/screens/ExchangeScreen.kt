package it.magi.stonks.screens

import androidx.compose.runtime.Composable
import it.magi.stonks.activities.apiKey
import it.magi.stonks.viewmodels.StonksViewModel

@Composable
fun ExchangeScreen(viewModel: StonksViewModel) {
    viewModel.exchangesListApiRequest(apiKey)
    viewModel.exchangeDataById(apiKey,"bybit_spot")
}