package it.magi.stonks.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import it.magi.stonks.composables.CustomScrollableTabRow
import it.magi.stonks.composables.CustomTopAppBar
import it.magi.stonks.ui.theme.FormContainerColor
import it.magi.stonks.viewmodels.StonksViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: StonksViewModel,
    prefCurrency: String
) {
    var tabState by remember { mutableIntStateOf(0) }
    val tabsList = listOf("Trending", "Cryptovalute", "NFT", "Exchange")
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopAppBar(navController = navController, viewModel = viewModel)
        },
        containerColor = FormContainerColor,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        )
        {
            CustomScrollableTabRow(
                tabs = tabsList,
                selectedTabIndex = tabState,
                onTabClick = { tabState = it }
            )

            when (tabState) {
                0 -> {
                    TrendingScreen(navController, viewModel, prefCurrency)
                }

                1 -> {
                    CryptoListScreen(navController, viewModel, prefCurrency)
                }

                2 -> {
                    NFTScreen(viewModel)
                }

                3 -> {
                    ExchangeScreen(viewModel)
                }
            }
        }
    }
}