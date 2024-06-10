package it.magi.stonks.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import it.magi.stonks.R
import it.magi.stonks.composables.DropDown
import it.magi.stonks.composables.OtherDropDown
import it.magi.stonks.viewmodels.OtherViewModel

@Composable
fun OtherScreen(navController: NavController, viewModel: OtherViewModel) {
    val context = LocalContext.current
    val apiKey = stringResource(R.string.api_key)
    viewModel.getSupportedCurrencies(apiKey)
    val currencyListState = viewModel.getCurrencyList().observeAsState()
    val currencyList = currencyListState.value ?: listOf("EUR", "USD")
    val currentCurrency by remember { mutableStateOf(viewModel.currentCurrency.value) }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OtherDropDown(
            modifier = Modifier.fillMaxWidth(),
            viewModel,
            currencyList
        )
        Button(onClick = { viewModel.changePreferredCurrency(context,currentCurrency)}) {
            Text(text = "Change")
        }
        Button(onClick = {
            viewModel.logOut(context)
        }) {
            Text(text = "logout")
        }
    }
}