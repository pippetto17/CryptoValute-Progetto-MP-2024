package it.magi.stonks.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import it.magi.stonks.models.Coin
import it.magi.stonks.models.NFT
import it.magi.stonks.models.NFTData
import it.magi.stonks.utilities.Utilities
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val requestQueue = Volley.newRequestQueue(application)

    private var coinsList = MutableLiveData<List<Coin>>()

    private var nftList = MutableLiveData<List<NFT>>()

    private var nftData = MutableLiveData<NFTData>()


    var _screen = MutableStateFlow(1)
    val screen: MutableStateFlow<Int> = _screen

    var _filter = MutableStateFlow("")
    val filter: StateFlow<String> = this._filter


    fun getNFTsList(): LiveData<List<NFT>> {
        return nftList
    }

    fun getCoinsList(): LiveData<List<Coin>> {
        return coinsList
    }

    fun filterCoins(
        context: Context,
        apiKey: String,
        currency: String,
        ids: String = "",
        categories: String = "",
        order: String = ""
    ){
        val baseUrl =
            "https://api.coingecko.com/api/v3/coins/markets?x_cg_demo_api_key=$apiKey&vs_currency=$currency"
        val idsCasting = Utilities().removeSpacesAndConvertToLowerCase(ids)

        var url = "$baseUrl&ids=$idsCasting&categories=$categories&order=$order"

        if (ids.isEmpty()) {
            url = url.replace("&ids=$ids", "")
        }
        if (categories.isEmpty()) {
            url = url.replace("&categories=$categories", "")
        }
        if (order.isEmpty()) {
            url = url.replace("&order=$order", "")
        }
        Log.d("API", "filterCoins url: $url")

        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                Log.d("API", "filterCoins Request Successful, response: $response ")
                Log.d("API", "response type: ${response}")
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

    fun GetCurrencyPreference(application: Application):String{
        val sharedPreferences = application.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val result=sharedPreferences.getString("currency","null")
        Log.d("SharedPreferences","shared: $result")
        return result.toString()
    }

    fun getNFts(
        context: Context,
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

    fun searchNFT(
        context: Context,
        apiKey: String,
        id: String,
    ) {
        val url="https://api.coingecko.com/api/v3/nfts/$id?x_cg_demo_api_key=$apiKey"
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

    fun getTrendingsList(apiKey: String,){
        val url="https://api.coingecko.com/api/v3/search/trending?x_cg_demo_api_key=$apiKey"
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                Log.d("API", "Trending list Request Successful, response: $response ")
                Log.d("API", "response type: ${response}")
                val gson = Gson()
                val coinListType = object : TypeToken<List<Coin>>() {}.type

                val value = gson.fromJson<List<Coin>>(response, coinListType)
                coinsList.value = value
                Log.d("API", "value: ${coinsList.value}")

            },
            { error -> Log.d("API", "Trending List Request Error $error") })
        requestQueue.add(stringRequest)
        Log.d("API", "returning trendingList: ${coinsList.value}")
    }
}