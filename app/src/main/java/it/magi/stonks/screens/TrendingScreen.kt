package it.magi.stonks.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import it.magi.stonks.R
import it.magi.stonks.activities.apiKey
import it.magi.stonks.composables.TrendingCoinItem
import it.magi.stonks.composables.TrendingNFTItem
import it.magi.stonks.ui.theme.DarkBgColor
import it.magi.stonks.ui.theme.GreenStock
import it.magi.stonks.ui.theme.RedStock
import it.magi.stonks.ui.theme.titleFont
import it.magi.stonks.utilities.Utilities
import it.magi.stonks.viewmodels.StonksViewModel

@Composable
fun TrendingScreen(
    navController: NavController,
    viewModel: StonksViewModel,
    prefCurrency: String
) {
    val filterState = viewModel.filter.collectAsState()
    Log.d("CryptoScreen", "currency preference: $prefCurrency")
    viewModel.filterCoinsApiRequest(
        apiKey = apiKey,
        currency = prefCurrency,
        ids = filterState.value,
        priceChangePercentage = "24h"
    )
    viewModel.trendingListApiRequest(apiKey)
    val trendingList = viewModel.getTrendingList().observeAsState()

    val shortTrendingCoins = trendingList.value?.coins?.subList(0, 3)
    val trendingCoins = trendingList.value?.coins?.subList(3, trendingList.value?.coins?.size ?: 15)


    val shortTrendingNFT = trendingList.value?.nfts?.subList(0, 3)
    val trendingNFT = trendingList.value?.nfts?.subList(3, trendingList.value?.nfts?.size ?: 15)

    Log.d("nft", shortTrendingNFT.toString())
    var expandedCoin by remember { mutableStateOf(false) }

    var expandedNFt by remember { mutableStateOf(false) }

    val accountName = Utilities().getAccountName()
    val greetings = listOf("👋", "👊", "✌️", "🤙", "🫡", "🖖", "🙋‍♂️")
    val randomString = remember { mutableStateOf(greetings.random()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Hi $accountName ${randomString.value}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            color = Color.White,
            fontSize = 30.sp,
            fontFamily = titleFont()
        )
        if (!expandedNFt) {
            Text(
                stringResource(R.string.trending_screen_subtitle_coins),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 10.dp, end = 10.dp),
                color = Color.White,
                fontSize = 15.sp,
                fontFamily = titleFont()
            )
            LazyColumn {
                items(
                    shortTrendingCoins ?: emptyList()
                ) { coin ->
                    TrendingCoinItem(
                        rank = coin.item.market_cap_rank.toString(),
                        imageURI = coin.item.large,
                        name = coin.item.name,
                        symbol = coin.item.symbol,
                        price = coin.item.data.price.toFloat(),
                        marketCap = coin.item.data.market_cap,
                        priceChangePercentage24h = coin.item.score.toFloat(),
                        onClick = {
                            navController.navigate("coin/${coin.item.id}")
                        },
                    )
                }
            }
            Card(
                shape = RoundedCornerShape(5.dp),
                colors = CardDefaults.cardColors(
                    containerColor = DarkBgColor,
                    contentColor = if (expandedCoin) RedStock else GreenStock
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp)
                    .clickable { expandedCoin = !expandedCoin },
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    text = if (expandedCoin) "See less ▲" else "See more ▼",
                    fontFamily = titleFont(),
                    fontSize = 12.sp
                )
            }
            if (expandedCoin) {
                LazyColumn {
                    items(
                        trendingCoins ?: emptyList()
                    ) { coin ->
                        TrendingCoinItem(
                            rank = coin.item.market_cap_rank.toString(),
                            imageURI = coin.item.large,
                            name = coin.item.name,
                            symbol = coin.item.symbol,
                            price = coin.item.data.price.toFloat(),
                            marketCap = coin.item.data.market_cap,
                            priceChangePercentage24h = coin.item.score.toFloat(),
                            onClick = {
                                navController.navigate("coin/${coin.item.id}")
                            },
                        )
                    }
                }
            }
        }
        Text(
            stringResource(R.string.trending_screen_subtitle_nfts),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, start = 10.dp, end = 10.dp),
            color = Color.White,
            fontSize = 15.sp,
            fontFamily = titleFont()
        )
        LazyColumn {
            items(
                shortTrendingNFT ?: emptyList()
            ) { nft ->
                TrendingNFTItem(
                    thumb = nft.thumb,
                    name = nft.name,
                    nativeCurrency = nft.native_currency_symbol,
                    floorPriceInNativeCurrency = nft.floor_price_in_native_currency.toFloat(),
                    floorPrice24HPercentage = nft.floor_price_24h_percentage_change.toFloat(),
                    id = nft.id,
                )
            }
        }
        Card(
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(
                containerColor = DarkBgColor,
                contentColor = if (expandedNFt) RedStock else GreenStock
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp)
                .clickable { expandedNFt = !expandedNFt },
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = if (expandedNFt) "See less ▲" else "See more ▼",
                fontFamily = titleFont(),
                fontSize = 12.sp
            )
        }
        if (expandedNFt) {
            LazyColumn {
                items(
                    trendingNFT ?: emptyList()
                ) { nft ->
                    TrendingNFTItem(
                        thumb = nft.thumb,
                        name = nft.name,
                        nativeCurrency = nft.native_currency_symbol,
                        floorPriceInNativeCurrency = nft.floor_price_in_native_currency.toFloat(),
                        floorPrice24HPercentage = nft.floor_price_24h_percentage_change.toFloat(),
                        id = nft.id,
                    )
                }
            }
        }
    }
}