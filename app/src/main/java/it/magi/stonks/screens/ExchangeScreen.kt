package it.magi.stonks.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import it.magi.stonks.composables.ExchangeItem
import it.magi.stonks.ui.theme.CoinContainerColor
import it.magi.stonks.viewmodels.StonksViewModel

@Composable
fun ExchangeScreen(viewModel: StonksViewModel)
{
    viewModel.exchangesListApiRequest(apiKey)
    viewModel.exchangeDataById(apiKey,"bybit_spot")

    val exchanges = viewModel.getExchangesList().observeAsState()
    val exchangesList = exchanges.value ?: emptyList()
    val reverseExchangesList = exchangesList.reversed()

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
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = stringResource(R.string.exchange_screen_exchange),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width(70.dp)
            )
            Spacer(modifier = Modifier.width(110.dp))
            Text(
                text = stringResource(R.string.exchange_screen_volume),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width(70.dp)
                    .padding(start = 10.dp, end = 10.dp)
            )
            Text(
                text = stringResource(R.string.exchange_screen_trust),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width(70.dp),
            )
        }
        LazyColumn {
            items(if (!descendingOrder) exchangesList else reverseExchangesList) { exchange ->
                ExchangeItem(
                    rank = exchange.trust_score_rank.toString(),
                    name = exchange.name,
                    id = exchange.id,
                    imageURI = exchange.image,
                    price = exchange.trade_volume_24h_btc,
                    trust = exchange.trust_score,
                )
            }
        }
    }
}