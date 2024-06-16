package it.magi.stonks.composables

import android.graphics.Paint.Align
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.round
import kotlin.math.roundToInt
import kotlin.math.roundToLong

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShowLineChartPreview() {
    val chartData = listOf(
        Pair(1.0, 1.5),
        Pair(2.0, 1.75),
        Pair(3.0, 3.45),
        Pair(4.0, 2.25),
        Pair(5.0, 6.45),
        Pair(6.0, 3.35),
        Pair(7.0, 8.65),
        Pair(8.0, 0.15),
        Pair(9.0, 3.05),
        Pair(10.0, 4.25)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        LineChart(
            data = chartData,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .align(CenterHorizontally)
        )
    }
}

@Composable
fun LineChart(
    data: List<Pair<Double, Double>> = emptyList(),
    modifier: Modifier = Modifier
) {
    val spacingFromLeft = 80f
    val graphColor = Color.Green   //color for your graph
    val transparentGraphColor = remember { graphColor.copy(alpha = 0.5f) }
    val upperValue = remember(data) { data.maxOfOrNull { it.second } ?: 0.0 }
    val lowerValue = remember(data) { data.minOfOrNull { it.second } ?: 0.0 }
    val percentageFormat = DecimalFormat("0.000")
    val density = LocalDensity.current

    //paint for the text shown in data values
    val textPaint = android.graphics.Paint()
    textPaint.color = android.graphics.Color.BLACK
    textPaint.textAlign = Align.CENTER
    textPaint.textSize = density.run { 12.sp.toPx() }


    Canvas(modifier = modifier) {
        val spacePerData = (size.width - spacingFromLeft) / data.size

        //loop through each index by step of 1
        //data shown horizontally
        (data.indices step 1).forEach { i ->
            val date = Date(data[i].first.toLong())
            val format = SimpleDateFormat("HH", Locale.getDefault())
            val hour = format.format(date)
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    hour.toString(),
                    spacingFromLeft + i * spacePerData,
                    size.height,
                    textPaint
                )
            }
        }

        val priceStep = (upperValue - lowerValue) / 5f
        //data shown vertically
        (data.indices step 1).forEach { i ->
            drawContext.canvas.nativeCanvas.apply {

                drawText(
                   when (i) {
                        0 -> "0.0"
                        1 -> percentageFormat.format((upperValue / 4)).toString()
                        2 -> percentageFormat.format((upperValue / 3)).toString()
                        3 -> percentageFormat.format((upperValue / 2)).toString()
                        4 -> percentageFormat.format(upperValue).toString()
                        else -> ""
                    },
                    40f,
                    size.height - spacingFromLeft - i * size.height / 5f,
                    textPaint
                )
            }
        }


        //Vertical line
        drawLine(
            start = Offset(spacingFromLeft, size.height - spacingFromLeft),
            end = Offset(spacingFromLeft, 0f),
            color = Color.Black,
            strokeWidth = 3f
        )

        //Horizontal line
        drawLine(
            start = Offset(spacingFromLeft, size.height - spacingFromLeft),
            end = Offset(size.width - 40f, size.height - spacingFromLeft),
            color = Color.Black,
            strokeWidth = 3f
        )

        //Use this to show straight line path
        val straightLinePath = Path().apply {
            val height = size.height

            //loop through index only not value
            data.indices.forEach { i ->
                val info = data[i]
                val x1 = spacingFromLeft + i * spacePerData
                val y1 =
                    ((upperValue - info.second).toFloat() / upperValue * height - spacingFromLeft).toFloat()

                if (i == 0) {
                    moveTo(x1, y1)
                }
                lineTo(x1, y1)

                drawCircle(
                    color = Color.Black,
                    radius = 5f,
                    center = Offset(x1, y1)
                ) //Uncomment it to see the end points
            }
        }

        //Use this to show curved path
        var medX: Float
        var medY: Float
        val curvedLinePath = Path().apply {
            val height = size.height
            data.indices.forEach { i ->
                val nextInfo = data.getOrNull(i + 1) ?: data.last()

                val x1 = spacingFromLeft + i * spacePerData
                val y1 =
                    height  - (height* (data[i].second / 5)).toFloat()
                val x2 = spacingFromLeft + (i + 1) * spacePerData
                val y2 =
                    (upperValue - nextInfo.second).toFloat() / upperValue * height
                Log.d("LineChart","($x1 $y1) to  ($x2 $y2)")
                if (i == 0) {
                    moveTo(x1, y1)
                } else {
                    medX = (x1 + x2) / 2f
                    medY = (y1 + y2).toFloat() / 2f
                    quadraticBezierTo(x1 = x1, y1 = y1, x2 = medX, y2 = medY)
                    Log.d("LineChart"," med ($x1 $y1) to  avg($medX $medY)")

                }

            }
        }

        //Now draw path on canvas
        drawPath(
            path = curvedLinePath,
            color = graphColor,
            style = Stroke(
                width = 1.dp.toPx(),
                cap = StrokeCap.Round
            )
        )

        //To show the background transparent gradient
        val fillPath =
            android.graphics.Path(curvedLinePath.asAndroidPath()).asComposePath().apply {
                lineTo(size.width - spacePerData, size.height - spacingFromLeft)
                lineTo(spacingFromLeft, size.height - spacingFromLeft)
                close()
            }

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    transparentGraphColor,
                    Color.Transparent
                ),
                endY = size.height - spacingFromLeft
            )
        )

    }


}