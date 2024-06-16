package it.magi.stonks.screens

import android.os.Build.VERSION.SDK_INT
import android.util.Log
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import it.magi.stonks.R
import it.magi.stonks.activities.apiKey
import it.magi.stonks.composables.CustomTopAppBar
import it.magi.stonks.composables.TrendingNFTItem
import it.magi.stonks.models.Explorer
import it.magi.stonks.models.FloorPrice
import it.magi.stonks.models.Image
import it.magi.stonks.models.MarketCap
import it.magi.stonks.models.PriceChange
import it.magi.stonks.models.Volume24h
import it.magi.stonks.ui.theme.FormContainerColor
import it.magi.stonks.ui.theme.titleFont
import it.magi.stonks.viewmodels.StonksViewModel

@Composable
fun NFTScreen(
    navController: NavController,
    viewModel: StonksViewModel,
    nftId: String
) {
    viewModel.NFTDataByIdApiRequest(apiKey, nftId) {
        Log.d("NFTScreen", "Success $it")
    }

    val nftData = viewModel.getNFTData().observeAsState().value

    class NFT {
        val id = nftData?.id ?: ""
        val name = nftData?.name ?: ""
        val description = nftData?.description ?: ""
        val image = nftData?.image ?: ""
        val contractAddress = nftData?.contract_address ?: ""
        val assetPlatformId = nftData?.asset_platform_id ?: ""
        val symbol = nftData?.symbol ?: ""

        val nativeCurrency = nftData?.native_currency ?: ""
        val nativeCurrencySymbol = nftData?.native_currency_symbol ?: ""
        val floorPrice = nftData?.floor_price ?: ""
        val marketCap = nftData?.market_cap ?: ""
        val volume24h = nftData?.volume_24h ?: ""
        val links = nftData?.links ?: ""
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
//                        if (NFT().image.contains("jpg")) {
//                            AsyncImage(
//                                model = NFT().image,
//                                contentDescription = "nftImage",
//                                placeholder = painterResource(R.drawable.star_coin),
//                                modifier = Modifier.size(50.dp)
//                            )
//                        }
//                        if (NFT().image.contains("svg")) {
//                            AsyncImage(
//                                model = ImageRequest.Builder(LocalContext.current)
//                                    .data(NFT().image)
//                                    .decoderFactory(SvgDecoder.Factory())
//                                    .build(),
//                                contentDescription = "nftImage",
//                                placeholder = painterResource(R.drawable.star_coin),
//                                modifier = Modifier.size(50.dp)
//                            )
//                        }
//                        if (NFT().image.contains("png")) {
//                            AsyncImage(
//                                model = NFT().image,
//                                contentDescription = "nftImage",
//                                placeholder = painterResource(R.drawable.star_coin),
//                                modifier = Modifier.size(50.dp)
//                            )
//                        }
//                        if (NFT().image.contains("gif")) {
//                            AsyncImage(
//                                model = ImageRequest.Builder(LocalContext.current)
//                                    .data(NFT().image)
//                                    .decoderFactory(
//                                        if (SDK_INT >= 28) {
//                                            ImageDecoderDecoder.Factory()
//                                        } else {
//                                            GifDecoder.Factory()
//                                        }
//                                    )
//                                    .build(),
//                                contentDescription = "nftGif",
//                                placeholder = painterResource(R.drawable.star_coin),
//                                modifier = Modifier.size(50.dp)
//                            )
//                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(start = 10.dp)
                                .align(Alignment.CenterVertically)
                        ) {
                            Text(
                                text = "name: " + NFT().name,
                                color = Color.White,
                                fontSize = 15.sp
                            )
                            Text(
                                text = NFT().symbol,
                                color = Color.LightGray,
                                fontFamily = titleFont(),
                                fontSize = 30.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = "Description",
                        onValueChange = {},
                        readOnly = true,
                    )
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = NFT().description,
                        onValueChange = {},
                        readOnly = true,
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Card {
                        Column(){
                            Text(text = "Contract Address: ${nftData?.contract_address}", color = Color.White, fontSize = 12.sp)
                            Text(text = "Asset Platform ID: ${nftData?.asset_platform_id}", color = Color.White, fontSize = 12.sp)
                            Text(text = "Native Currency: ${nftData?.native_currency}", color = Color.White, fontSize = 12.sp)
                            Text(text = "Native Currency Symbol: ${nftData?.native_currency_symbol}", color = Color.White, fontSize = 12.sp)
                            Text(text = "${nftData?.floor_price}", color = Color.White, fontSize = 12.sp)
                            Text(text = "${nftData?.market_cap}", color = Color.White, fontSize = 12.sp)
                            Text(text = "${nftData?.volume_24h}", color = Color.White, fontSize = 12.sp)
                            Text(text = "${nftData?.links}", color = Color.White, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}