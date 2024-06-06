package it.magi.stonks.screens

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import it.magi.stonks.composables.CustomTopAppBar
import it.magi.stonks.ui.theme.DarkBgColor
import it.magi.stonks.ui.theme.FormContainerColor
import it.magi.stonks.viewmodels.HomeViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel) {
    var tabState by remember { mutableIntStateOf(0) }
    val titles = listOf("Cryptovalute", "NFT", "Exchange")
    val application = LocalContext.current.applicationContext as Application
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
                contentColor = Color.White
            ) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        text = {
                            Text(
                                text = title,
                                color = if (tabState == index) Color.White else Color.LightGray
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
                    CryptoListScreen(navController, viewModel, application )
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