package it.magi.stonks.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.google.firebase.database.FirebaseDatabase
import it.magi.stonks.R
import it.magi.stonks.activities.apiKey
import it.magi.stonks.composables.PieChart
import it.magi.stonks.composables.WalletCoinItem
import it.magi.stonks.models.Coin
import it.magi.stonks.ui.theme.CoinContainerColor
import it.magi.stonks.ui.theme.DarkBgColor
import it.magi.stonks.ui.theme.FormContainerColor
import it.magi.stonks.ui.theme.titleFont
import it.magi.stonks.ui.theme.walletFont
import it.magi.stonks.utilities.Utilities
import it.magi.stonks.viewmodels.WalletViewModel

@Composable
fun WalletDetailsScreen(walletName: String, currency: String, viewModel: WalletViewModel) {
    val database = FirebaseDatabase.getInstance(stringResource(id = R.string.db_url))

    var isWalletDatasLoading by remember { mutableStateOf(true) }
    var isCoinListLoading by remember { mutableStateOf(true) }
    var isCoinDatasLoading by remember { mutableStateOf(false) }
    var coinsNumber by remember { mutableStateOf(0f) }
    var coinsAmount by remember { mutableStateOf(0f) }
    var totalValue by remember { mutableStateOf(0f) }
    var walletCoins by remember { mutableStateOf<Map<String, String>>(emptyMap()) }
    var coinsWithPriceList by remember { mutableStateOf<Map<String, Float>>(emptyMap()) }

    LaunchedEffect(walletName) {
        isWalletDatasLoading = true
        isCoinListLoading = true
        totalValue = 0f
        coinsAmount = 0f
        coinsNumber = 0f
        coinsWithPriceList = emptyMap()

        viewModel.getWalletCoinsList(database, walletName) { walletDetails ->
            Log.d("WalletScreen", "WalletDetails: $walletDetails")
            walletDetails.forEach { (crypto, amount) ->
                coinsNumber += 1
                coinsAmount += amount.toFloat()
                Log.d("WalletDetailScreen", "${walletName}Adding $crypto + $amount")
                viewModel.coinPriceApiRequest(
                    apiKey,
                    crypto.lowercase(),
                    currency
                ) { pricePerValue ->
                    Log.d("WalletScreen", "Price per value $crypto value: $pricePerValue * $amount")
                    totalValue += pricePerValue * amount.toFloat()
                    coinsWithPriceList += mapOf(crypto to pricePerValue)
                }
            }
            isWalletDatasLoading = false
        }

        viewModel.getWalletCoinsList(database, walletName) {
            walletCoins = it
            isCoinListLoading = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Intestazione fissata
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .background(FormContainerColor)
                .padding(15.dp)
                .align(Alignment.TopCenter)
                .zIndex(1f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = walletName,
                    style = TextStyle(
                        fontSize = 30.sp,
                        shadow = Shadow(
                            color = Color.Gray,
                            offset = Offset(5f, 5f),
                            blurRadius = 3f
                        ),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontFamily = titleFont()
                    )
                )

                if (isWalletDatasLoading) {
                    CircularProgressIndicator()
                } else {
                    Text(
                        text = "$totalValue ${currency.uppercase()}",
                        style = TextStyle(
                            fontSize = 30.sp,
                            shadow = Shadow(
                                color = Color.Gray,
                                offset = Offset(5f, 5f),
                                blurRadius = 3f
                            ),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontFamily = walletFont()
                        )
                    )
                    Text(
                        text = stringResource(R.string.wallet_detail_screen_available_balance).uppercase(),
                        style = TextStyle(
                            fontSize = 20.sp,
                            shadow = Shadow(
                                color = Color.Gray,
                                offset = Offset(5f, 5f),
                                blurRadius = 3f
                            ),
                            color = Color.White,
                            fontFamily = walletFont()
                        )
                    )
                }
            }
        }

        // Contenuto scrollabile
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 130.dp), // Aggiungi un padding superiore per evitare la sovrapposizione
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(
                    topStart = CornerSize(50.dp),
                    topEnd = CornerSize(50.dp),
                    bottomStart = CornerSize(0.dp),
                    bottomEnd = CornerSize(0.dp)
                ),
                colors = CardDefaults.cardColors(
                    containerColor = CoinContainerColor
                )
            ) {
                Column {
                    if (isCoinListLoading) {
                        CircularProgressIndicator()
                    } else {
                        Spacer(modifier = Modifier.height(20.dp))
                        if (isWalletDatasLoading) {
                            CircularProgressIndicator()
                        } else {
                            Log.d(
                                "WalletScreen",
                                "PieChart datas: ${
                                    Utilities().convertMapIntoPairs(coinsWithPriceList)
                                }"
                            )
                            PieChart(
                                data = Utilities().convertMapIntoPairs(coinsWithPriceList),
                                currency = currency,
                            )
                            Column(
                                modifier = Modifier
                                    .wrapContentHeight()
                            ) {
                                walletCoins.keys.forEachIndexed { index, name ->
                                    var coin by remember { mutableStateOf<List<Coin>>(emptyList()) }
                                    LaunchedEffect(walletName) {
                                        coin = emptyList()
                                        viewModel.filterCoinsApiRequest(apiKey, currency, name) {
                                            Log.d("WalletScreen", "Coin Lazy Column: $it")
                                            coin = it
                                            isCoinDatasLoading = false
                                        }
                                    }
                                    if (isCoinDatasLoading) {
                                        CircularProgressIndicator()
                                    } else {
                                        if (coin.isNotEmpty()) {
                                            Log.d(
                                                "AIUT",
                                                "Value: $name Coin to WalletCoinItem: ${coin[0].name}"
                                            )
                                            WalletCoinItem(
                                                prefCurrency = currency,
                                                id = name,
                                                imageURI = coin[0].image,
                                                name = walletCoins.keys.elementAt(index),
                                                amount = walletCoins.values.elementAt(index),
                                                symbol = coin[0].symbol ?: "",
                                                price = coin[0].current_price ?: 0f,
                                            ) {

                                            }
                                        }
                                    }
                                }
                            }

                        }
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }
            }
        }
    }
}