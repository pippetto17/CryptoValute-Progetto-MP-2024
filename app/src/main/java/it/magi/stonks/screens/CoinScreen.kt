package it.magi.stonks.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import it.magi.stonks.R
import it.magi.stonks.composables.CustomTopAppBar
import it.magi.stonks.composables.LineChartGithub
import it.magi.stonks.composables.SignButton
import it.magi.stonks.models.SparkLine
import it.magi.stonks.ui.theme.CoinContainerColor
import it.magi.stonks.ui.theme.FormContainerColor
import it.magi.stonks.ui.theme.RedStock
import it.magi.stonks.ui.theme.titleFont
import it.magi.stonks.utilities.Utilities
import it.magi.stonks.viewmodels.StonksViewModel
import okhttp3.internal.toImmutableList


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
        val currentPrice = coin?.current_price ?: 0.0f
        val priceInMyCurrency = Utilities().formatPrice(currentPrice)
        val image = coin?.image ?: ""
        val price_change_24h = coin?.price_change_24h ?: 0.0f
    }

    val items = listOf(
        "Market cap" to (coin?.market_cap?.toString() ?: "N/A"),
        "Market cap rank" to (coin?.market_cap_rank?.toString() ?: "N/A"),
        "Fully diluted valuation" to (coin?.fully_diluted_valuation?.toString() ?: "N/A"),
        "Total volume" to (coin?.total_volume?.toString() ?: "N/A"),
        "High 24h" to (coin?.high_24h?.toString() ?: "N/A"),
        "Low 24h" to (coin?.low_24h?.toString() ?: "N/A"),
        "Price change 24h" to (coin?.price_change_24h?.toString() ?: "0.0"),
        "Price change percentage 24h" to ((coin?.price_change_percentage_24h?.toString() ?: "0.0") + "%"),
        "Market cap change 24h" to (coin?.market_cap_change_24h?.toString() ?: "0.0"),
        "Market cap change percentage 24h" to ((coin?.market_cap_change_percentage_24h?.toString() ?: "0.0") + "%"),
        "Circulating supply" to (coin?.circulating_supply?.toString() ?: "0.0"),
        "Total supply" to (coin?.total_supply?.toString() ?: "0.0"),
        "Max supply" to (coin?.max_supply?.toString() ?: "0.0"),
        "ATH" to (coin?.ath?.toString() ?: "0.0"),
        "ATH change percentage" to ((coin?.ath_change_percentage?.toString() ?: "0.0") + "%"),
        "ATH date" to (coin?.ath_date ?: ""),
        "ATL" to (coin?.atl?.toString() ?: "0.0"),
        "ATL change percentage" to ((coin?.atl_change_percentage?.toString() ?: "0.0") + "%"),
        "ATL date" to (coin?.atl_date ?: ""),
        "Last updated" to (coin?.last_updated ?: "")
    )

    @Composable
    fun CoinDetailRow(label: String, value: String, modifier: Modifier = Modifier) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = value, color = Color.White, fontSize = 15.sp)
        }
    }

    @Composable
    fun DataDivider(){
        HorizontalDivider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.padding(10.dp)
        )
    }

    //API Request della coinMarketChart
    viewModel.coinMarketChartDataById(
        apiKey,
        coinId,
        7
    ) {
        Log.d("CoinMarketChart", "value: $it")
    }

    val chartApiResponse = viewModel.getCoinMarketChart().observeAsState().value
    if (chartApiResponse != null) {
        val chartData = viewModel.formatCoinMarketChartData(chartApiResponse)
    }

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
                text = stringResource(id = R.string.coin_screen_add_to_wallet),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 80.dp, end = 80.dp)
                    .height(40.dp),
                textSize = 15.sp
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
                                fontSize = 25.sp,
                                fontFamily = titleFont()
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
                    Spacer(modifier = Modifier.height(20.dp))
                    if (chartApiResponse != null) {
                        val chartData = viewModel.formatCoinMarketChartData(chartApiResponse)
                        val graphColor = if(Coin().price_change_24h > 0) Color.Green else Color.Red
                        Log.d("CoinMarketChart", "immutablelist: $chartData")

                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .requiredHeight(190.dp)){
                            LineChartGithub(
                                Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(),
                                data = chartData,
                                graphColor = graphColor,
                                showDashedLine = true
                            )
                        }


                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        stringResource(id = R.string.market_data_title),
                        color = RedStock,
                        fontSize = 20.sp,
                        fontFamily = titleFont(),
                        modifier = Modifier.padding(10.dp)
                    )
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = CoinContainerColor
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 10.dp
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp)
                        ) {
                            items.forEach { (label, value) ->
                                CoinDetailRow(label = label, value = value)
                                DataDivider()
                            }

                            coin?.price_change_percentage_1h_in_currency?.let {
                                CoinDetailRow(
                                    label = "price_change_percentage_1h_in_currency",
                                    value = it.toString()
                                )
                                DataDivider()
                            }
                            coin?.price_change_percentage_24h_in_currency?.let {
                                CoinDetailRow(
                                    label = "price_change_percentage_24h_in_currency",
                                    value = it.toString()
                                )
                                DataDivider()
                            }
                            coin?.price_change_percentage_7d_in_currency?.let {
                                CoinDetailRow(
                                    label = "price_change_percentage_7d_in_currency",
                                    value = it.toString()
                                )
                                DataDivider()
                            }
                            coin?.price_change_percentage_14d_in_currency?.let {
                                CoinDetailRow(
                                    label = "price_change_percentage_14d_in_currency",
                                    value = it.toString()
                                )
                                DataDivider()
                            }
                            coin?.price_change_percentage_30d_in_currency?.let {
                                CoinDetailRow(
                                    label = "price_change_percentage_30d_in_currency",
                                    value = it.toString()
                                )
                                DataDivider()
                            }
                            coin?.price_change_percentage_200d_in_currency?.let {
                                CoinDetailRow(
                                    label = "price_change_percentage_200d_in_currency",
                                    value = it.toString()
                                )
                                DataDivider()
                            }
                            coin?.price_change_percentage_1y_in_currency?.let {
                                CoinDetailRow(
                                    label = "price_change_percentage_1y_in_currency",
                                    value = it.toString()
                                )
                                DataDivider()
                            }
                        }
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
        }
    }
}


