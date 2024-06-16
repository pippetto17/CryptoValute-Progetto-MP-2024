package it.magi.stonks.composables

import android.util.Log
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
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
import it.magi.stonks.utilities.Utilities
import java.math.BigDecimal

fun convertExponentialToDecimal(number: String): String {
    val bigDecimal = BigDecimal(number)
    return bigDecimal.toPlainString()
}

@Composable
fun PieChart(
    data: Map<String, Float>,
    radiusOuter: Dp = 140.dp,
    //dimensione degli spicchi
    chartBarWidth: Dp = 50.dp,
    animDuration: Int = 1000,
) {

    val totalSum = data.values.sum()
    val floatValue = mutableListOf<Float>()

    // To set the value of each Arc according to
    // the value given in the data, we have used a simple formula.
    // For a detailed explanation check out the Medium Article.
    // The link is in the about section and readme file of this GitHub Repository
    data.values.forEachIndexed { index, values ->
        floatValue.add(index, 360 * values.toFloat() / totalSum.toFloat())
    }

    // add the colors as per the number of data(no. of pie chart entries)
    // so that each data will get a color
    val colors = listOf(
        Color(0xFF800080),
        Color(0xFFFFA500),
        Color(0xFFFFC0CB),
        Color(0xFF00FFFF),
        Color(0xFFA52A2A),
        Color(0xFF808080),
        Color(0xFF00FF00),
        Color(0xFF40E0D0),
        Color(0xFF4B0082),
        Color(0xFFFFD700),
        Color(0xFFC0C0C0),
        Color(0xFF808000),
        Color(0xFFE6E6FA),
        Color(0xFFFF00FF),
        Color(0xFFF5F5DC),
        Color(0xFF000080),
        Color(0xFF228B22),
        Color(0xFFFFFFFF),
        Color(0xFF000000),
        Color(0xFFDAA520),
        Color(0xFF2E8B57),
        Color(0xFF0000CD),
        Color(0xFFFF007F),
        Color(0xFFA9A9A9),
        Color(0xFF556B2F),
        Color(0xFF191970),
        Color(0xFF800000),
        Color(0xFF87CEEB),
        Color(0xFFFF69B4),
        Color(0xFF4169E1),
        Color(0xFF00CED1),
        Color(0xFFB22222),
        Color(0xFF008000),
        Color(0xFFD3D3D3),
        Color(0xFF00FF00),
        Color(0xFF3D59AB),
        Color(0xFFD70040),
        Color(0xFFFFA07A),
        Color(0xFF9370DB),
        Color(0xFF00FFFF),
        Color(0xFF4682B4),
        Color(0xFFFAEBD7),
        Color(0xFFCD853F),
        Color(0xFF00FF7F),
        Color(0xFF1E90FF),
        Color(0xFFCD5C5C),
        )

    var animationPlayed by remember { mutableStateOf(false) }

    var lastValue = 0f

    // it is the diameter value of the Pie
    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) radiusOuter.value * 2f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ), label = ""
    )

    // if you want to stabilize the Pie Chart you can use value -90f
    // 90f is used to complete 1/4 of the rotation
    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * 11f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    // to play the animation only once when the function is Created or Recomposed
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Pie Chart using Canvas Arc

        Box(
            modifier = Modifier.size(animateSize.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .offset { IntOffset.Zero }
                    //seleziona la grandezza del grafico
                    .size(radiusOuter * 1.2f)
                    .rotate(animateRotation)
            ) {
                // draw each Arc for each data entry in Pie Chart
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
            Text(
                text = convertExponentialToDecimal(totalSum.toString())
            )
        }
    }

    // To see the data in more structured way
    // Compose Function in which Items are showing data
    Log.d("PieChart", "passing totalSum1: $totalSum")
    DetailsPieChart(
        data = data,
        colors = colors,
        totalSum = totalSum
    )

}

@Composable
fun DetailsPieChart(
    data: Map<String, Float>,
    colors: List<Color>,
    totalSum: Float
) {
//    Column(
//        modifier = Modifier
//            .padding(top = 80.dp)
//            .fillMaxWidth()
//    ) {
//        // create the data items
//        data.values.forEachIndexed { index, value ->
//            Log.d("PieChart", "passing totalSum2: $totalSum")
//            DetailsPieChartItem(
//                data = Pair(data.keys.elementAt(index), value),
//                color = colors[index],
//                totalSum = totalSum
//            )
//        }
//    }

    Card(Modifier.padding(10.dp)) {
        LazyColumn(
            modifier = Modifier
                .padding(top = 80.dp)
                .fillMaxWidth()
                .height(300.dp)
        ) {
            items(data.values.toList()) { value ->
                val index = data.values.indexOf(value)
                Log.d("PieChart", "passing totalSum2: $totalSum")
                DetailsPieChartItem(
                    data = Pair(data.keys.elementAt(index), value),
                    color = colors[index],
                    totalSum = totalSum
                )
            }
        }
    }
}

@Composable
fun DetailsPieChartItem(
    data: Pair<String, Float>,
    height: Dp = 45.dp,
    color: Color,
    totalSum: Float
) {
    val percentage = (data.second / totalSum) * 100


    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = color,
                    shape = RoundedCornerShape(10.dp)
                )
                .size(height)
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.padding(start = 15.dp),
                text = data.first,
                fontWeight = FontWeight.Medium,
                fontSize = 22.sp,
                color = Color.Black
            )
            Text(
                modifier = Modifier.padding(start = 15.dp),
                text = data.second.toString(),
                fontWeight = FontWeight.Medium,
                fontSize = 22.sp,
                color = Color.Gray
            )
            Log.d("PieChart", "percentage is: $percentage")
            Text(
                modifier = Modifier.padding(start = 15.dp),
                text = Utilities().convertToPercentage(percentage),
                color = Color.White
            )
        }

    }

}