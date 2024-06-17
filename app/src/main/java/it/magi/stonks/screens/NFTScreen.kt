package it.magi.stonks.screens

import android.content.Intent
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.util.Log
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import it.magi.stonks.R
import it.magi.stonks.activities.apiKey
import it.magi.stonks.composables.CustomTopAppBar
import it.magi.stonks.models.Links
import it.magi.stonks.ui.theme.CoinContainerColor
import it.magi.stonks.ui.theme.FormContainerColor
import it.magi.stonks.ui.theme.GreenStock
import it.magi.stonks.ui.theme.RedStock
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

    val context = LocalContext.current

    class NFT {
        val id = nftData?.id ?: ""
        val name = nftData?.name ?: ""
        val description = nftData?.description ?: ""
        val image = nftData?.image?.small ?: ""
        val contractAddress = nftData?.contract_address ?: ""
        val assetPlatformId = nftData?.asset_platform_id ?: ""
        val symbol = nftData?.symbol ?: ""

        val nativeCurrency = nftData?.native_currency ?: ""
        val nativeCurrencySymbol = nftData?.native_currency_symbol ?: ""
        val floorPrice = nftData?.floor_price?.usd ?: ""
        val marketCap = nftData?.market_cap?.usd ?: ""
        val volume24h = nftData?.volume_24h?.usd ?: ""
        val links = nftData?.links ?: Links("", "", "")
    }

    val nft = NFT()

    val items = listOf(
        "Contract Address" to nft.contractAddress,
        "Asset Platform ID" to nft.assetPlatformId,
        "Native Currency" to nft.nativeCurrency,
        "Native Currency Symbol" to nft.nativeCurrencySymbol,
        "Floor Price" to nft.floorPrice.toString(),
        "Market Cap" to nft.marketCap.toString(),
        "Volume 24h" to nft.volume24h.toString()
    )

    @Composable
    fun NFTDetailRow(label: String, value: String, modifier: Modifier = Modifier) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = value,
                color = Color.White,
                fontSize = 12.sp,
                textAlign = TextAlign.End
            )
        }
    }

    @Composable
    fun DataDivider() {
        HorizontalDivider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }

    fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        ContextCompat.startActivity(context, intent, null)
    }

    @Composable
    fun LinksCard(links: Links) {
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (links.homepage.isNotEmpty()) {
                    IconButton(onClick = { openLink(links.homepage) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.homepage_logo),
                            contentDescription = "Homepage",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
                if (links.twitter.isNotEmpty()) {
                    IconButton(onClick = { openLink(links.twitter) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.twitter_logo),
                            contentDescription = "Twitter",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
                if (links.discord.isNotEmpty()) {
                    IconButton(onClick = { openLink(links.discord) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.discord_logo),
                            contentDescription = "Discord",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
        }
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
                        if (nft.image.contains("svg")) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(nft.image)
                                    .decoderFactory(SvgDecoder.Factory())
                                    .build(),
                                contentDescription = "nftImage",
                                placeholder = painterResource(R.drawable.star_coin),
                                modifier = Modifier.size(100.dp)
                            )
                        }
                        if (nft.image.contains("png") || nft.image.contains("jpg")) {
                            AsyncImage(
                                model = nft.image,
                                contentDescription = "nftImage",
                                placeholder = painterResource(R.drawable.star_coin),
                                modifier = Modifier.size(100.dp)
                            )
                        }
                        if (nft.image.contains("gif")) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(nft.image)
                                    .decoderFactory(
                                        if (SDK_INT >= 28) {
                                            ImageDecoderDecoder.Factory()
                                        } else {
                                            GifDecoder.Factory()
                                        }
                                    )
                                    .build(),
                                contentDescription = "nftGif",
                                placeholder = painterResource(R.drawable.star_coin),
                                modifier = Modifier.size(100.dp)
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(start = 10.dp)
                                .align(Alignment.CenterVertically)
                        ) {
                            Text(
                                text = nft.name,
                                color = Color.White,
                                fontFamily = titleFont(),
                                fontSize = 20.sp
                            )
                            Text(
                                text = nft.symbol,
                                color = Color.LightGray,
                                fontFamily = titleFont(),
                                fontSize = 20.sp
                            )
                        }
                    }
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 0.dp),
                        value = stringResource(R.string.nft_screen_description),
                        onValueChange = {},
                        readOnly = true,
                        textStyle = TextStyle(
                            color = GreenStock,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            fontFamily = titleFont()
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = FormContainerColor,
                            unfocusedContainerColor = FormContainerColor
                        )
                    )
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp, top = 0.dp, bottom = 10.dp),
                        value = nft.description,
                        onValueChange = {},
                        readOnly = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = FormContainerColor,
                            unfocusedContainerColor = FormContainerColor
                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        stringResource(R.string.market_data_title),
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
                                NFTDetailRow(
                                    label = label,
                                    value = value
                                )
                                DataDivider()
                            }
                        }
                    }
                    Text(
                        stringResource(R.string.nft_screen_links_title),
                        color = RedStock,
                        fontSize = 20.sp,
                        fontFamily = titleFont(),
                        modifier = Modifier.padding(10.dp)
                    )
                    LinksCard(nft.links)
                }
            }
        }
    }
}

