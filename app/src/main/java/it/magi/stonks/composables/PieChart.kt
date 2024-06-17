package it.magi.stonks.composables

import android.util.Log
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.magi.stonks.ui.theme.walletFont
import it.magi.stonks.utilities.Utilities
import java.math.BigDecimal
import kotlin.random.Random

@Composable
fun PieChart(
    data: Map<String, Float>,
    radiusOuter: Dp = 140.dp,
    chartBarWidth: Dp = 50.dp,
    animDuration: Int = 1000,
    currency: String
) {

    val totalSum = data.values.sum()
    val floatValue = mutableListOf<Float>()

    data.values.forEachIndexed { index, values ->
        floatValue.add(index, 360 * values.toFloat() / totalSum.toFloat())
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

    Log.d("PieChart", "passing totalSum1: $totalSum")
    DetailsPieChart(
        data = data,
        colors = colors,
        totalSum = totalSum,
        currency = currency
    )

}

@Composable
fun DetailsPieChart(
    data: Map<String, Float>,
    colors: List<Color>,
    totalSum: Float,
    currency: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        data.values.forEachIndexed { index, value ->
            Log.d("PieChart", "passing totalSum2: $totalSum")
            DetailsPieChartItem(
                data = Pair(data.keys.elementAt(index), value),
                color = colors[index],
                totalSum = totalSum,
                currency = currency
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
    currency: String
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

        Column(modifier = Modifier.fillMaxWidth()) {
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
        Row(){
            Text(
                text = Utilities().formatExponentialPriceToReadable(data.second.toString()),
                color = Color.White,
                fontSize = 14.sp
            )
            Text(
                text = currency.uppercase(),
                color = Color.White,
                fontSize = 14.sp
            )
        }


    }
}