package it.magi.stonks.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import it.magi.stonks.activities.apiKey
import it.magi.stonks.composables.CoinItem
import it.magi.stonks.ui.theme.DarkBgColor
import it.magi.stonks.viewmodels.StonksViewModel

@Composable
fun CryptoListScreen(
    navController: NavController,
    viewModel: StonksViewModel,
    prefCurrency: String
) {
    val filterState = viewModel.filter.collectAsState()
    viewModel.filterCoinsApiRequest(
        apiKey = apiKey,
        currency = prefCurrency,
        ids = filterState.value,
        priceChangePercentage = "24h"
    )
    val coins = viewModel.getCoinsList().observeAsState()
    val coinsList = coins.value ?: emptyList()
    val reverseCoins = coinsList.reversed()

    var x by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier
                    .width(50.dp)
                    .height(30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkBgColor
                ),
                shape = RoundedCornerShape(5.dp),
                contentPadding = PaddingValues(0.dp),
                onClick = { x = !x },
            ) {
                Text(
                    if (x) "# ▲"
                    else "# ▼",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                )
            }
            Text(
                "Currency",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(80.dp)
            )
            Text(
                "Price",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.End,
                modifier = Modifier.width(120.dp)
            )
            Text(
                "24 Hours",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(70.dp)
            )
        }
        LazyColumn {
            items(coinsList) { coin ->
                CoinItem(
                    prefCurrency = prefCurrency,
                    rank = coin.market_cap_rank?.toInt().toString(),
                    imageURI = coin.image,
                    name = coin.name ?: "Unknown",
                    symbol = coin.symbol ?: "Unknown",
                    price = coin.current_price ?: 0.0.toFloat(),
                    priceChangePercentage24h = coin.price_change_percentage_24h ?: 0.0.toFloat(),
                    id = coin.id ?: "Unknown",
                    onClick = {
                        navController.navigate("coin/${coin.id}")
                    },
                )
            }
        }
    }
}







