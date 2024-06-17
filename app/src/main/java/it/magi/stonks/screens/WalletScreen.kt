package it.magi.stonks.screens

import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.database.FirebaseDatabase
import it.magi.stonks.R
import it.magi.stonks.activities.apiKey
import it.magi.stonks.composables.CoinItem
import it.magi.stonks.composables.NewWalletDialog
import it.magi.stonks.composables.OtherTopAppBar
import it.magi.stonks.composables.WalletCard
import it.magi.stonks.models.Coin
import it.magi.stonks.ui.theme.DarkBgColor
import it.magi.stonks.ui.theme.FormContainerColor
import it.magi.stonks.ui.theme.GreenStock
import it.magi.stonks.ui.theme.titleFont
import it.magi.stonks.ui.theme.walletFont
import it.magi.stonks.utilities.Utilities
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
    var allAccountCoins by remember { mutableStateOf("") }
    var coinsList by remember { mutableStateOf<List<Coin>>(emptyList()) }
    //loading flags
    var isLoadingWalletList by remember { mutableStateOf(true) }
    var isLoadingAccountCoinsList by remember { mutableStateOf(true) }
    var isLoadingCoinsDatas by remember { mutableStateOf(true) }
    //dialog flags
    var showNewWalletDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        isLoadingWalletList = true
        viewModel.getWalletsList(database) {
            walletList = it
            isLoadingWalletList = false // Finish loading
        }
    }

    val gradients = listOf(
        listOf(Color.Blue, Color(0xFF9E45CE)),
        listOf(Color.Red, Color.Yellow),
        listOf(Color.Green, Color(0xFFFF9800)),
        listOf(Color(0xFFFF88F9), Color.Cyan),
        listOf(Color(0xFF9E45CE), Color(255, 215, 0)),
        listOf(Color.Blue, Color.Gray),
        listOf(Color.Green, Color(0xFF662F18)),
        listOf(Color.Red, Color(0xFF1F295F)),
        listOf(Color.Yellow, Color(0xFF2196F3)),
        listOf(Color(0xFFFF9800), Color(0xFFFF88F9)),
        listOf(Color.Red, Color.DarkGray),
        listOf(Color.Green, Color.Cyan),
        listOf(Color.Green, Color.LightGray),
    )
    val randomGradient = remember { mutableStateOf(gradients.random()) }
    val randomGradient2 = remember { mutableStateOf(gradients.random()) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            OtherTopAppBar(
                navController = navController
            )
        },
        containerColor = FormContainerColor,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { innerPadding ->
        if (showNewWalletDialog) {
            NewWalletDialog(
                navController = navController,
                onDismissRequest = { showNewWalletDialog = false },
                viewModel = viewModel
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            if (isLoadingWalletList) {
                CircularProgressIndicator()
            } else {
                TabRow(
                    modifier = Modifier.fillMaxWidth(),
                    selectedTabIndex = tabState,
                    containerColor = DarkBgColor,
                    contentColor = Color.White,
                    indicator = { tabPositions ->
                        if (tabState < tabPositions.size) {
                            TabRowDefaults.SecondaryIndicator(
                                modifier = Modifier.tabIndicatorOffset(tabPositions[tabState]),
                                color = GreenStock
                            )
                        }
                    }
                ) {
                    (listOf("overview") + walletList).forEachIndexed { index, title ->
                        Tab(
                            text = { Text(text = title.uppercase()) },
                            selected = tabState == index,
                            onClick = {
                                tabState = index
                            })
                    }
                }
                when (tabState) {
                    0 -> {
                        if (isLoadingWalletList) {
                            CircularProgressIndicator()
                        } else {
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy((-32).dp)
                            ) {
                                items(walletList.size) {

                                    WalletCard(
                                        walletName = walletList[it].uppercase(),
                                        gradientColors = if (it == 0) randomGradient.value else randomGradient2.value,
                                        tabState = it + 1,
                                        viewModel = viewModel,
                                        database = database,
                                        walletList = walletList,
                                        it = it,
                                        apiKey = apiKey,
                                        currency = currency,
                                        titleFont = titleFont(),
                                        walletFont = walletFont(),
                                        onclick = { tabState = it + 1}
                                    )
                                }
                                if (walletList.size == 1) {
                                    item {
                                        Column(
                                            Modifier
                                                .fillMaxWidth()
                                                .width(400.dp)
                                                .padding(20.dp)
                                        ) {
                                            Card(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(200.dp),
                                                colors = CardDefaults.cardColors(containerColor = GreenStock),
                                                shape = RoundedCornerShape(10.dp),
                                                onClick = {
                                                    showNewWalletDialog = true
                                                }
                                            ) {
                                                Column(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .padding(20.dp),
                                                    horizontalAlignment = Alignment.CenterHorizontally,
                                                ) {
                                                    Text(
                                                        text = "Create New Wallet",
                                                        color = Color.White,
                                                        fontSize = 20.sp,
                                                        fontFamily = titleFont()
                                                    )
                                                    Spacer(modifier = Modifier.height(10.dp))
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.ic_new_wallet),
                                                        contentDescription = "New Wallet",
                                                        modifier = Modifier.size(40.dp),
                                                        tint = Color.Black
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
                                allAccountCoins = it
                                isLoadingAccountCoinsList = false
                            }
                            viewModel.filterCoinsApiRequest(apiKey, currency, allAccountCoins) {
                                coinsList = it
                                isLoadingCoinsDatas = false
                            }
                            if (isLoadingCoinsDatas || isLoadingAccountCoinsList) {
                                CircularProgressIndicator()
                            } else {
                                Text(
                                    text = stringResource(id = R.string.wallet_screen_your_coins),
                                    color = Color.White,
                                    fontFamily = titleFont(),
                                    fontSize = 20.sp
                                )
                                LazyColumn {
                                    Log.d("WalletScreen", "coinsList: $coinsList")
                                    items(coinsList) { coin ->
                                        CoinItem(
                                            prefCurrency = currency,
                                            rank = coin.market_cap_rank?.toInt().toString(),
                                            marketCap = coin.market_cap ?: 0f,
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
                            WalletDetailsScreen(walletName, currency, viewModel)
                        } else {
                            Text(text = stringResource(id = R.string.wallet_screen_invalid))
                        }
                    }
                }

            }
        }
    }

}