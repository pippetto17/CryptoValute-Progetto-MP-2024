package it.magi.stonks.screens

import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.database.FirebaseDatabase
import it.magi.stonks.R
import it.magi.stonks.activities.apiKey
import it.magi.stonks.composables.CoinItem
import it.magi.stonks.composables.CustomTopAppBar
import it.magi.stonks.models.Coin
import it.magi.stonks.ui.theme.FormContainerColor
import it.magi.stonks.ui.theme.titleFont
import it.magi.stonks.utilities.Utilities
import it.magi.stonks.viewmodels.StonksViewModel
import it.magi.stonks.viewmodels.WalletViewModel

@Composable
fun WalletScreen(navController: NavController, viewModel: WalletViewModel) {
    val database = FirebaseDatabase.getInstance(stringResource(id = R.string.db_url))
    val application = LocalContext.current.applicationContext as Application
    val currency = Utilities().getCurrencyPreference()
    //screens handling
    var tabState by remember { mutableIntStateOf(0) }
    //values
    var walletList by remember { mutableStateOf(listOf<String>()) }
    var allAccounCoins by remember { mutableStateOf("") }
    var coinsList by remember { mutableStateOf<List<Coin>>(emptyList()) }
    //loading flags
    var isLoadingWalletList by remember { mutableStateOf(true) }
    var isLoadingAccountCoinsList by remember { mutableStateOf(true) }
    var isLoadingCoinsDatas by remember { mutableStateOf(true) }
    LaunchedEffect(key1 = true) {
        isLoadingWalletList = true
        viewModel.getWalletsList(database) {
            walletList = it
            isLoadingWalletList = false // Finish loading
        }
    }




    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopAppBar(
                navController = navController,
                viewModel = StonksViewModel(application = application),
                isHome = false
            )
        },
        containerColor = FormContainerColor,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            if (isLoadingWalletList) {
                CircularProgressIndicator()
            } else {
                ScrollableTabRow(
                    modifier = Modifier.fillMaxWidth(),
                    selectedTabIndex = tabState
                ) {
                    (listOf("overview") + walletList).forEachIndexed { index, title ->
                        Tab(
                            text = { Text(text = title.uppercase()) },
                            selected = tabState == index,
                            onClick = { tabState = index
                            })
                    }
                }
                when (tabState) {
                    0 -> {

                        if (isLoadingWalletList) {
                            CircularProgressIndicator()
                        } else {

                            LazyColumn() {
                                items(walletList.size) {
                                    var totalValue by remember { mutableStateOf(0.0) }
                                    var coinsAmount by remember { mutableStateOf(0.0) }
                                    var coinsNumber by remember { mutableStateOf(0) }
                                    Column(
                                        Modifier
                                            .fillMaxWidth()
                                            .width(400.dp)
                                            .padding(20.dp)
                                    ) {
                                        Card(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(150.dp),
                                            shape = RoundedCornerShape(10.dp),
                                            onClick = {
                                                Log.d("changing to it", "${it+1}")
                                                tabState = it+1
                                            }) {
                                            LaunchedEffect(key1 = true) {
                                                viewModel.getWalletCoinsList(
                                                    database,
                                                    walletList[it]
                                                ) { walletDetails ->
                                                    Log.d(
                                                        "WalletScreen",
                                                        "WalletDetails: $walletDetails"
                                                    )
                                                    for ((crypto, amount) in walletDetails) {
                                                        coinsNumber += 1
                                                        coinsAmount += amount.toFloat()
                                                        Log.d(
                                                            "WalletScreen",
                                                            "${walletList[it]}Adding $crypto  + $amount"
                                                        )
                                                        viewModel.coinPriceApiRequest(
                                                            apiKey,
                                                            crypto.lowercase(),
                                                            currency
                                                        ) { pricePerValue ->
                                                            Log.d(
                                                                "WalletScreen",
                                                                "Adding $crypto valuex: $pricePerValue * $amount"
                                                            )
                                                            if (pricePerValue != null)
                                                            totalValue += pricePerValue * amount.toFloat()
                                                        }
                                                        Log.d("WalletScreen", "Adding $crypto")
                                                    }
                                                }
                                            }
                                            Column(
                                                Modifier
                                                    .fillMaxSize()
                                                    .padding(5.dp)
                                            ) {
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(5.dp)
                                                ) {
                                                    Text(
                                                        text = walletList[it].uppercase(),
                                                        color = Color.White,
                                                        fontFamily = titleFont(),
                                                        fontSize = 25.sp
                                                    )
                                                }
                                                Spacer(modifier = Modifier.height(10.dp))
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(start = 15.dp)
                                                ) {
                                                    Text(
                                                        text = "VALUE: ",
                                                        color = Color.White,
                                                        fontSize = 15.sp
                                                    )
                                                    Text(
                                                        text = totalValue.toString() + currency.uppercase(),
                                                        color = Color.White,
                                                        fontSize = 15.sp
                                                    )
                                                }
                                                Spacer(modifier = Modifier.height(5.dp))
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(start = 15.dp)
                                                ) {
                                                    Text(
                                                        text = "COINS: ",
                                                        color = Color.White,
                                                        fontSize = 15.sp
                                                    )
                                                    Text(
                                                        text = coinsNumber.toString(),
                                                        color = Color.White,
                                                        fontSize = 15.sp
                                                    )
                                                }
                                                Spacer(modifier = Modifier.height(5.dp))
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(start = 15.dp)
                                                ) {
                                                    Text(
                                                        text = "STOCKS: ",
                                                        color = Color.White,
                                                        fontSize = 15.sp
                                                    )
                                                    Text(
                                                        text = coinsAmount.toString(),
                                                        color = Color.White,
                                                        fontSize = 15.sp
                                                    )
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            viewModel.getAllCoinsFromAllWallets(database) {
                                Log.d("WalletScreen", "getAllCoinsIdFromWallets: $it")
                                allAccounCoins = it
                                isLoadingAccountCoinsList = false
                            }
                            viewModel.filterCoinsApiRequest(apiKey, currency, allAccounCoins) {
                                coinsList = it
                                isLoadingCoinsDatas = false
                            }
                            if (isLoadingCoinsDatas||isLoadingAccountCoinsList) {
                                CircularProgressIndicator()
                            } else {
                                Text(text = "Your coins",color = Color.White,fontFamily = titleFont(),fontSize = 20.sp)
                                LazyColumn {
                                    Log.d("WalletScreen", "coinsList: $coinsList")
                                    items(coinsList) { coin ->
                                        CoinItem(
                                            prefCurrency = currency,
                                            rank = coin.market_cap_rank?.toInt().toString(),
                                            marketCap = coin.market_cap ?:0f,
                                            imageURI = coin.image,
                                            symbol = coin.symbol ?: "Unknown",
                                            price = coin.current_price ?: 0.0.toFloat(),
                                            priceChangePercentage24h = coin.price_change_percentage_24h
                                                ?: 0.0.toFloat(),
                                            id = coin.id ?: "Unknown",
                                            onClick = {
                                                navController.navigate("coin/${coin.id}")
                                            },
                                        )
                                    }
                                }
                            }

                        }

                    }
                    else -> {
                        val walletIndex = tabState - 1
                        if (walletIndex in walletList.indices) {
                            val walletName = walletList[walletIndex]
                            WalletDetailsScreen(walletName, currency,viewModel)
                        } else {
                            Text(text = "Invalid wallet index")
                        }
                    }
                }

            }
        }
    }

}