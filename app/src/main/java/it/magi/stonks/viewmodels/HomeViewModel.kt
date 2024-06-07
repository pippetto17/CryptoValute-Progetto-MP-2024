package it.magi.stonks.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import it.magi.stonks.models.Coin
import it.magi.stonks.models.CoinMarketChart
import it.magi.stonks.models.NFT
import it.magi.stonks.models.NFTData
import it.magi.stonks.models.TrendingList
import it.magi.stonks.utilities.Utilities
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val requestQueue = Volley.newRequestQueue(application)

    private var coinsList = MutableLiveData<List<Coin>>()

    private var nftList = MutableLiveData<List<NFT>>()

    private var nftData = MutableLiveData<NFTData>()

    private var trendingList = MutableLiveData<TrendingList>()

    private var marketChartById= MutableLiveData<CoinMarketChart>()


    var _screen = MutableStateFlow(1)

    var _filter = MutableStateFlow("")
    val filter: StateFlow<String> = this._filter


    fun getTrendingList(): LiveData<TrendingList> {
        return trendingList
    }

    fun getCoinsList(): LiveData<List<Coin>> {
        Log.d("API-COIN", "getCoinsList: ${coinsList.value}")
        return coinsList
    }


    fun getNFTsList(): LiveData<List<NFT>> {
        return nftList
    }

    fun getCoinMarketChart(): LiveData<CoinMarketChart> {
        return marketChartById

    }

    fun filterCoinsApiRequest(
        apiKey: String,
        currency: String,
        ids: String = "",
        categories: String = "",
        order: String = "",
        priceChangePercentage: String = ""
    ) {
        val baseUrl =
            "https://api.coingecko.com/api/v3/coins/markets?x_cg_demo_api_key=$apiKey&vs_currency=$currency&sparkline=true"
        val idsCasting = Utilities().removeSpacesAndConvertToLowerCase(ids)

        var url =
            "$baseUrl&ids=$idsCasting&categories=$categories&order=$order&price_change_percentage=$priceChangePercentage"

        if (ids.isEmpty()) {
            url = url.replace("&ids=$ids", "")
        }
        if (categories.isEmpty()) {
            url = url.replace("&categories=$categories", "")
        }
        if (order.isEmpty()) {
            url = url.replace("&order=$order", "")
        }
        if (priceChangePercentage.isEmpty()) {
            url = url.replace("&price_change_percentage=$priceChangePercentage", "")
        }
        Log.d("API", "filter coins id: $ids category: $categories order: $order priceChangePercentage: $priceChangePercentage")

        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                Log.d("API", "filterCoins Request Successful")
                val gson = Gson()
                val coinListType = object : TypeToken<List<Coin>>() {}.type

                val value = gson.fromJson<List<Coin>>(response, coinListType)
                coinsList.value = value

                Log.d("API", "value: ${coinsList.value}")

            },
            { error -> Log.d("API", "filterCoins Request Error $error") })
        requestQueue.add(stringRequest)
        Log.d("API", "returning coinsList: ${coinsList.value}")
    }

    fun NFtsApiRequest(
        apiKey: String,
        order: String = ""
    ) {
        val baseUrl = "https://api.coingecko.com/api/v3/nfts/list?x_cg_demo_api_key=$apiKey"
        var url = "$baseUrl&$order"
        if (order.isEmpty()) {
            url = url.replace("&order=$order", "")
        }


        Log.d("API", "get all nfts url: $url")

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("API", "get all nfts Request Successful, response: $response ")
                Log.d("API", "response type: ${response}")
                val gson = Gson()
                val nftListType = object : TypeToken<List<NFT>>() {}.type

                val value = gson.fromJson<List<NFT>>(response, nftListType)
                nftList.value = value
                Log.d("API", "value: ${nftList.value}")

            },
            { error -> Log.d("API", "get all nfts Request Error $error") })
        requestQueue.add(stringRequest)
        Log.d("API", "returning all nftList: ${nftList.value}")
    }

    fun filterNFTApiRequest(
        apiKey: String,
        id: String,
    ) {
        val url = "https://api.coingecko.com/api/v3/nfts/$id?x_cg_demo_api_key=$apiKey"
        Log.d("API", "fetching NFT by id url: $url")

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("API", "fetch NFT by id Request Successful, response: $response ")
                Log.d("API", "response type: ${response}")
                val gson = Gson()
                val nftType = object : TypeToken<NFT>() {}.type

                val value = gson.fromJson<NFTData>(response, nftType)
                nftData.value = value
                Log.d("API", "value: ${nftData.value}")

            },
            { error -> Log.d("API", "fetching NFT by id Request Error $error") })
        requestQueue.add(stringRequest)
    }

    fun trendingListApiRequest(apiKey: String) {
        val url = "https://api.coingecko.com/api/v3/search/trending?x_cg_demo_api_key=$apiKey"
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                Log.d("API", "Trending list Request Successful, response: $response ")
                Log.d("API", "response type: ${response}")
                val gson = Gson()
                val trendingListType = object : TypeToken<TrendingList>() {}.type

                val value = gson.fromJson<TrendingList>(response, trendingListType)
                trendingList.value = value
                Log.d("API", "value: ${coinsList.value}")

            },
            { error -> Log.d("API", "Trending List Request Error $error") })
        requestQueue.add(stringRequest)
        Log.d("API", "returning trendingList: ${coinsList.value}")
    }

    fun coinMarketChartDataById(apiKey: String,id: String,currency: String,days: Int){
        val url="https://api.coingecko.com/api/v3/coins/bitcoin/market_chart?$id?x_cg_demo_api_key=$apiKey&vs_currency=$currency&days=$days"
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                Log.d("API", " Market chart by id Request Successful, response: $response ")
                Log.d("API", "response type: ${response}")
                val gson = Gson()
                val marketChartType = object : TypeToken<CoinMarketChart>() {}.type

                val value = gson.fromJson<CoinMarketChart>(response, marketChartType)
                marketChartById.value = value

            },
            { error -> Log.d("API", "Market chart by id Request Error $error") })
        requestQueue.add(stringRequest)
        Log.d("API", "returning market chart by id: ${marketChartById.value}")
    }

    @Composable
    fun getCurrencyPreference(): String {
        val database =
            FirebaseDatabase.getInstance("https://criptovalute-b1e06-default-rtdb.europe-west1.firebasedatabase.app/")
        val auth = FirebaseAuth.getInstance()
        val email = Utilities().convertDotsToCommas(auth.currentUser?.email?:"")
        var currency by rememberSaveable {
            mutableStateOf("")
        }
        val myRef = database.getReference().child("users").child(email.toString()).child("currency")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val currencySnapshot = dataSnapshot.getValue(String::class.java)
                currency = currencySnapshot?:"Unknown"
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("CryptoScreen", "Failed to read value.", error.toException())
            }
        })
        return currency
    }


}