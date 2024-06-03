package it.magi.stonks.viewmodels

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonPrimitive
import com.google.gson.reflect.TypeToken
import it.magi.stonks.R
import it.magi.stonks.activities.StartActivity
import it.magi.stonks.models.Coin
import it.magi.stonks.utilities.Utilities
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val requestQueue = Volley.newRequestQueue(application)

    private var coinsList = MutableLiveData<List<Coin>>()

    var _filter = MutableStateFlow("")
    val filter: StateFlow<String> = _filter

    fun getCoinsList(): LiveData<List<Coin>> {
        return coinsList
    }

    fun getAllCoinsWithMarketData(context: Context, apiKey: String, currency: String) {
        val url =
            "https://api.coingecko.com/api/v3/coins/markets?vs_currency=$currency?x_cg_demo_api_key=${apiKey}"

        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                Log.d(
                    "API",
                    "get all coins with market data Request Successful, response: $response"
                )
            },
            { error -> Log.d("API", "get all coins with market data Request Error $error") })
        requestQueue.add(stringRequest)
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

    fun GetCurrencyPreference():String{
        val sharedPreferences = getApplication<Application>().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val savedCurrency = sharedPreferences.getString("currency", null) // Get the value, with null as the default

// Now you can use the savedCurrency variable
        if (savedCurrency != null) {
            // Use the saved currency value
            Log.d("Shared Preferences", "savedCurrency: $savedCurrency")
            return savedCurrency
        } else {
            // Handle the case where no currency is saved
            Log.d("Shared Preferences", "No currency saved")
            return "null"
        }
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