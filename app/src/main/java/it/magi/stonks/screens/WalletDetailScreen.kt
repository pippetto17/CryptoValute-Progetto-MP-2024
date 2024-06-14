package it.magi.stonks.screens


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import it.magi.stonks.R
import it.magi.stonks.activities.apiKey
import it.magi.stonks.composables.WalletCoinItem
import it.magi.stonks.models.Coin
import it.magi.stonks.ui.theme.DarkBgColor
import it.magi.stonks.ui.theme.titleFont
import it.magi.stonks.viewmodels.WalletViewModel

@Composable
fun WalletDetailsScreen(walletName: String, currency: String, viewModel: WalletViewModel) {
    val database = FirebaseDatabase.getInstance(stringResource(id = R.string.db_url))
    Log.d("WalletScreen", "currency: $currency")

    var isWalletDatasLoading by remember { mutableStateOf(true) }
    var isCoinListLoading by remember { mutableStateOf(true) }
    var isCoinDatasLoading by remember { mutableStateOf(true) }

    var coinsNumber by remember { mutableStateOf(0f) }
    var coinsAmount by remember { mutableStateOf(0f) }
    var totalValue by remember { mutableStateOf(0f) }
    var walletCoins by remember { mutableStateOf<Map<String, String>>(emptyMap()) }


    LaunchedEffect(walletName) {
        isWalletDatasLoading = true
        isCoinListLoading = true
        totalValue = 0f
        coinsAmount = 0f
        coinsNumber = 0f
        walletCoins = emptyMap()
        viewModel.getWalletCoinsList(
            database,
            walletName,
        ) { walletDetails ->
            Log.d(
                "WalletScreen",
                "WalletDetails: $walletDetails"
            )
            for ((crypto, amount) in walletDetails) {
                coinsNumber += 1
                coinsAmount += amount.toFloat()
                Log.d(
                    "WalletDetailScreen",
                    "${walletName}Adding $crypto  + $amount"
                )
                viewModel.coinPriceApiRequest(
                    apiKey,
                    crypto.lowercase(),
                    currency
                ) { pricePerValue ->
                    Log.d(
                        "WalletScreen",
                        "Price per value $crypto value: $pricePerValue * $amount"
                    )
                    totalValue += pricePerValue * amount.toFloat()
                }
                isWalletDatasLoading = false
            }
        }

        viewModel.getWalletCoinsList(database, walletName) {
            walletCoins = it
            isCoinListLoading = false
        }
    }


    Text(text = walletName, fontFamily = titleFont(), color = Color.White, fontSize = 20.sp)
    Spacer(modifier = Modifier.height(30.dp))
    if (isWalletDatasLoading) {
        CircularProgressIndicator()
    } else {
        Column(
            Modifier
                .fillMaxWidth()
                .wrapContentWidth()
        ) {
            Text(
                text = "$totalValue $currency",
                fontFamily = titleFont(),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
            Text(text = "Avaliable balance", color = Color.White)
        }
        if (isCoinListLoading) {
            CircularProgressIndicator()
        } else {
            LazyColumn(
                modifier = Modifier
                    .background(DarkBgColor)
                    .padding(horizontal = 24.dp)
                    .fillMaxSize()

            ) {
                items(walletCoins.size){ it ->
                    val name = walletCoins.keys.elementAt(it)
                    var coin by remember { mutableStateOf<List<Coin>>(emptyList()) }
                    LaunchedEffect (walletName){
                        coin = emptyList()
                        viewModel.filterCoinsApiRequest(apiKey,currency,name) {
                            Log.d("WalletScreen", "Coin Lazy Column: $it")
                            coin=it
                            isCoinDatasLoading = false
                        }
                    }
                    if (isCoinDatasLoading) {
                        CircularProgressIndicator()
                    }else {
                        if(coin.isNotEmpty()){
                            Log.d("AIUT", "Value: $name Coin to WalletCoinItem: ${coin[0].name}")
                            WalletCoinItem(
                                prefCurrency = currency,
                                id = name,
                                imageURI =coin[0].image ,
                                name = walletCoins.keys.elementAt(it),
                                amount = walletCoins.values.elementAt(it),
                                symbol = coin[0].symbol?:"",
                                price = coin[0].current_price?:0f,
                            ) {

                            }
                        }
                    }

                }
            }
        }
    }
}