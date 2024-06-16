package it.magi.stonks.screens

import android.webkit.WebView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import it.magi.stonks.R
import it.magi.stonks.composables.CustomTopAppBar
import it.magi.stonks.composables.LineChart
import it.magi.stonks.composables.OtherDropDown
import it.magi.stonks.composables.SignButton
import it.magi.stonks.ui.theme.DarkBgColor
import it.magi.stonks.ui.theme.FormContainerColor
import it.magi.stonks.ui.theme.GreenStock
import it.magi.stonks.ui.theme.RedStock
import it.magi.stonks.ui.theme.titleFont
import it.magi.stonks.utilities.Utilities
import it.magi.stonks.viewmodels.StonksViewModel
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

@Composable
fun CoinScreen(
    navController: NavController,
    viewModel: StonksViewModel,
    apiKey: String,
    coinId: String,
    currency: String,
) {

    //API Request dei dati della coin
    viewModel.filterCoinsApiRequest(
        apiKey,
        currency,
        coinId
    )
    val coin = viewModel.getCoinsList().observeAsState().value?.get(0)

    class Coin {
        val name = coin?.name ?: ""
        val symbols = coin?.symbol?.uppercase() ?: ""
        val sparkLineURI = Utilities().sparklineURI(coin?.image)
        val current_price = coin?.current_price ?: 0.0f
        val priceInMyCurrency = Utilities().formatPrice(current_price)
        val image = coin?.image ?: ""
        val market_cap = coin?.market_cap ?: 0.0f
        val market_cap_rank = coin?.market_cap_rank ?: 0.0f
        val total_volume = coin?.total_volume ?: 0.0f
        val high_24h = coin?.high_24h ?: 0.0f
        val low_24h = coin?.low_24h ?: 0.0f
    }

    //API Request della coinMarketChart
    viewModel.coinMarketChartDataById(
        apiKey,
        coinId,
        currency,
        7
    )

    val chartData = viewModel.getCoinMarketChart().observeAsState().value?.prices

    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault())
    sdf.timeZone = TimeZone.getTimeZone("Europe/Rome") // Imposta il fuso orario di Roma

    chartData?.forEach { tuple ->
        val milliseconds = tuple.get(0)
        val date = milliseconds.let { Date(it.toLong()) }
        val formattedDate = date.let { sdf.format(it) }
        println("Timestamp corrispondente: $formattedDate")
    }


//    val listOfPairs: List<Pair<Int, Double>> = chartData?.mapNotNull {
//        if (it.size == 2) Pair(it[0].toInt(), it[1]) else null
//    } ?: emptyList()
//
//    val pairs = listOf(
//        Pair(1, 1.5),
//        Pair(2, 1.75),
//        Pair(3, 3.45),
//        Pair(4, 2.25),
//        Pair(5, 6.45),
//        Pair(6, 3.35),
//        Pair(7, 8.65),
//        Pair(8, 0.15),
//        Pair(9, 3.05),
//        Pair(10, 4.25)
//    )
//
//    Log.d("listOfPairs", listOfPairs.toString())
//    Log.d("pairs", pairs.toString())

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            CustomTopAppBar(
                navController = navController,
                isHome = false,
                viewModel = viewModel
            )
        },
        floatingActionButton = {
            SignButton(
                onclick = { navController.navigate("buycoin/$coinId") },
                text = stringResource(id = R.string.coin_screen_add_to_wallet)
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        containerColor = FormContainerColor
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState()),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        AsyncImage(
                            model = Coin().image,
                            contentDescription = "",
                            placeholder = painterResource(R.drawable.star_coin),
                            modifier = Modifier.size(80.dp)
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(start = 10.dp)
                                .align(Alignment.CenterVertically)
                        ) {
                            Text(
                                text = Coin().name,
                                color = Color.White,
                                fontFamily = titleFont(),
                                fontSize = 20.sp
                            )
                            Text(
                                text = Coin().symbols,
                                color = Color.LightGray,
                                fontFamily = titleFont(),
                                fontSize = 20.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = Coin().priceInMyCurrency,
                            color = Color.White,
                            fontFamily = titleFont(),
                            fontSize = 20.sp,
                        )
                        Text(
                            text = currency,
                            color = Color.White,
                            fontStyle = FontStyle.Italic,
                            fontFamily = titleFont(),
                            fontSize = 20.sp,
                            modifier = Modifier.padding(start = 5.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
//                    LineChart(
//                        data = listOfPairs,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(300.dp)
//                            .align(Alignment.CenterHorizontally)
//                    )
                Spacer(modifier = Modifier.height(30.dp))
                Card(
                    shape = RoundedCornerShape(15.dp),
                    border = BorderStroke(1.dp, GreenStock),
                    colors = CardDefaults.cardColors(
                        containerColor = DarkBgColor
                    ),
                    modifier = Modifier
                        .padding(bottom = 40.dp)
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextField(
                                value = stringResource(id = R.string.name),
                                modifier = Modifier.fillMaxWidth(0.3f),
                                onValueChange = {},
                                readOnly = true,
                                enabled = false,
                                textStyle = TextStyle(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                ),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedContainerColor = Color.Transparent
                                )
                            )
                            TextField(
                                value = Utilities().getAccountName().uppercase(),
                                modifier = Modifier.fillMaxWidth(1f),
                                onValueChange = {},
                                readOnly = true,
                                enabled = false,
                                textStyle = TextStyle(
                                    textAlign = TextAlign.End,
                                    fontSize = 14.sp
                                ),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedContainerColor = Color.Transparent
                                )
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextField(
                                value = stringResource(id = R.string.surname),
                                modifier = Modifier.fillMaxWidth(0.4f),
                                onValueChange = {},
                                readOnly = true,
                                enabled = false,
                                textStyle = TextStyle(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                ),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedContainerColor = Color.Transparent
                                )
                            )
                            TextField(
                                value = Utilities().getAccountSurname().uppercase(),
                                modifier = Modifier.fillMaxWidth(1f),
                                onValueChange = {},
                                readOnly = true,
                                enabled = false,
                                textStyle = TextStyle(
                                    textAlign = TextAlign.End,
                                    fontSize = 14.sp
                                ),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedContainerColor = Color.Transparent
                                )
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextField(
                                value = stringResource(id = R.string.email),
                                modifier = Modifier.fillMaxWidth(0.25f),
                                onValueChange = {},
                                readOnly = true,
                                enabled = false,
                                textStyle = TextStyle(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                ),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedContainerColor = Color.Transparent
                                )
                            )
                            TextField(
                                value = "gendoikari01@nerv.jp",
                                modifier = Modifier.fillMaxWidth(1f),
                                onValueChange = {},
                                readOnly = true,
                                enabled = false,
                                textStyle = TextStyle(
                                    textAlign = TextAlign.End,
                                    fontSize = 14.sp
                                ),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedContainerColor = Color.Transparent
                                )
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(120.dp))
            }
        }
    }
}


