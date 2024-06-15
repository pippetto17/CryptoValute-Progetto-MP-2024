package it.magi.stonks.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import it.magi.stonks.R
import it.magi.stonks.activities.apiKey
import it.magi.stonks.composables.CoinItem
import it.magi.stonks.ui.theme.CoinContainerColor
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
    val reverseCoinsList = coinsList.reversed()

    var descendingOrder by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .padding(start = 5.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier
                    .width(55.dp)
                    .height(30.dp)
                    .clickable { descendingOrder = !descendingOrder },
                colors = CardDefaults.cardColors(
                    containerColor = CoinContainerColor,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
            ) {
                Text(
                    if (descendingOrder) "# ▲"
                    else "# ▼",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .wrapContentHeight(Alignment.CenterVertically),
                )
            }
            Text(
                text = stringResource(id = R.string.currency),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width(70.dp)
            )
            Text(
                stringResource(R.string.crypto_list_screen_price_market_cap),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                lineHeight = 16.sp,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .width(100.dp)
            )
            Text(
                stringResource(R.string.crypto_list_screen_trend),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width(100.dp)
                    .padding(start = 10.dp, end = 10.dp)
            )
            Text(
                stringResource(R.string.crypto_list_screen_24_h),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width(70.dp),
            )
        }
        LazyColumn {
            items(if (!descendingOrder) coinsList else reverseCoinsList) { coin ->
                CoinItem(
                    prefCurrency = prefCurrency,
                    rank = coin.market_cap_rank?.toInt().toString(),
                    imageURI = coin.image,
                    symbol = coin.symbol ?: "Unknown",
                    price = coin.current_price ?: 0.0.toFloat(),
                    marketCap = coin.market_cap ?: 0.0.toFloat(),
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







