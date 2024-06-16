package it.magi.stonks.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import it.magi.stonks.activities.apiKey
import it.magi.stonks.models.Explorer
import it.magi.stonks.models.FloorPrice
import it.magi.stonks.models.Image
import it.magi.stonks.models.MarketCap
import it.magi.stonks.models.PriceChange
import it.magi.stonks.models.Volume24h
import it.magi.stonks.viewmodels.StonksViewModel

@Composable
fun NFTScreen(
    navController: NavController,
    viewModel: StonksViewModel,
    nftId: String
) {
    var dataLoading by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = true) {
        dataLoading = true
        viewModel.NFTDataByIdApiRequest(apiKey, nftId) {
            Log.d("NFTScreen", "Success $it")
            dataLoading = false
        }
    }
    val nftData = viewModel.getNFTData().observeAsState().value

    class NFT {
        val id = nftData?.id ?: ""
        val name = nftData?.name ?: ""
        val description = nftData?.description ?: ""
        val image = nftData?.image ?: ""
        val contractAddress = nftData?.contractAddress ?: ""
        val assetPlatformId = nftData?.assetPlatformId ?: ""
        val symbol = nftData?.symbol ?: ""

        val nativeCurrency = nftData?.nativeCurrency ?: ""
        val nativeCurrencySymbol = nftData?.nativeCurrencySymbol ?: ""
        val floorPrice = nftData?.floorPrice ?: ""
        val marketCap = nftData?.marketCap ?: ""
        val volume24h = nftData?.volume24h ?: ""
        val links = nftData?.links ?: ""
    }
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (dataLoading) {
                CircularProgressIndicator()
            } else {
                Log.d("NFTScreen", "nftid ${NFT().id}")
                Column {
                    Text(text = NFT().id, color = Color.White)
                }
            }
        }
    }
}