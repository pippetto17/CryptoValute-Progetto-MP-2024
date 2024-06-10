package it.magi.stonks.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import it.magi.stonks.composables.CustomTopAppBar
import it.magi.stonks.ui.theme.DarkBgColor
import it.magi.stonks.ui.theme.FormContainerColor
import it.magi.stonks.ui.theme.GreenStock
import it.magi.stonks.ui.theme.titleFont
import it.magi.stonks.viewmodels.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel,
    prefCurrency: String
) {
    var tabState by remember { mutableIntStateOf(0) }
    val titles = listOf("Cryptovalute", "NFT", "Exchange")
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
            TabRow(
                selectedTabIndex = tabState,
                containerColor = DarkBgColor,
                contentColor = Color.White,
                indicator = { tabPositions ->
                    if (tabState < tabPositions.size) {
                        TabRowDefaults.Indicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[tabState]),
                            color = GreenStock
                        )
                    }
                }
            ) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        text = {
                            Text(
                                text = title.uppercase(),
                                color = if (tabState == index) Color.White else Color.LightGray,
                                fontSize = 12.sp,
                                fontFamily = titleFont()
                            )
                        },
                        selected = tabState == index,
                        onClick = { tabState = index },
                        selectedContentColor = Color.White,
                    )
                }
            }
            when (tabState) {
                0 -> {
                    CryptoListScreen(navController, viewModel, prefCurrency)
                }

                1 -> {
                    NFTScreen(viewModel)
                }

                2 -> {
                    ExchangeScreen(viewModel)
                }
            }
        }
    }
}