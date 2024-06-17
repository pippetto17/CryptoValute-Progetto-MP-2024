package it.magi.stonks.composables

import android.util.Log
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.FirebaseDatabase
import it.magi.stonks.activities.apiKey
import it.magi.stonks.models.Coin
import it.magi.stonks.utilities.Utilities
import it.magi.stonks.viewmodels.WalletViewModel
import kotlin.random.Random

@Composable
fun PieChart(
    data: Map<String, Float>,
    radiusOuter: Dp = 140.dp,
    chartBarWidth: Dp = 50.dp,
    animDuration: Int = 1000,
    currency: String,
    viewModel: WalletViewModel,
    totalSum: Float,
    walletName: String,
    database: FirebaseDatabase
) {

    val floatValue = mutableListOf<Float>()

    var isCoinDatasLoading by remember { mutableStateOf(false) }
    var isCoinListLoading by remember { mutableStateOf(false) }
    var walletCoins by remember { mutableStateOf<Map<String, String>>(emptyMap()) }

    viewModel.getWalletCoinsList(database, walletName) {
        walletCoins = it
        isCoinListLoading = false
    }

    data.values.forEachIndexed { index, values ->
        Log.d("AIUT", "Value: $values")
        floatValue.add(index, 360 * values / totalSum.toFloat())
    }

    val colors = List(data.size) {
        Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
    }

    var animationPlayed by remember { mutableStateOf(false) }
    var lastValue = 0f

    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) radiusOuter.value * 2f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ), label = ""
    )

    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * 11f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(animateSize.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .offset { IntOffset.Zero }
                    .size(radiusOuter * 1.2f)
                    .rotate(animateRotation)
            ) {
                floatValue.forEachIndexed { index, value ->
                    drawArc(
                        color = colors[index],
                        lastValue,
                        value,
                        useCenter = false,
                        style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt)
                    )
                    lastValue += value
                }
            }
        }
    }
    DetailsPieChart(
        data = data,
        colors = colors,
        totalSum = totalSum,
        currency = currency,
        walletCoins = walletCoins
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
@Composable
fun DetailsPieChart(
    data: Map<String, Float>,
    colors: List<Color>,
    totalSum: Float,
    currency: String,
    walletCoins: Map<String, String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        data.keys.forEachIndexed { index, key ->
            val amount = walletCoins[key]?.toFloatOrNull() ?: 0f
            DetailsPieChartItem(
                data = Pair(key, data[key] ?: 0f),
                color = colors[index],
                totalSum = totalSum,
                currency = currency,
                amount = amount
            )
        }
    }
}

@Composable
fun DetailsPieChartItem(
    data: Pair<String, Float>,
    height: Dp = 45.dp,
    color: Color,
    totalSum: Float,
    currency: String,
    amount: Float
) {
    val percentage = (data.second / totalSum) * 100

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = color,
                    shape = CircleShape
                )
                .size(16.dp)
        )

        Column(
            modifier = Modifier.wrapContentWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(start = 15.dp),
                text = data.first.uppercase(),
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                color = Color.White
            )
            Text(
                modifier = Modifier.padding(start = 15.dp),
                text = Utilities().convertToPercentage(percentage),
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
        Text(
            text = Utilities().formatExponentialPriceToReadable(
                data.second.toString()) + " " + currency.uppercase()
            , // Multiplication
            color = Color.White,
            fontSize = 14.sp
        )
    }
}
