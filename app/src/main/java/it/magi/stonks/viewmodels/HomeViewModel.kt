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

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    var coinsList = MutableLiveData<List<Coin>>()
    fun getAllCoinsWithMarketData(context: Context, apiKey: String, currency: String) {
        val url =
            "https://api.coingecko.com/api/v3/coins/markets?vs_currency=$currency?x_cg_demo_api_key=${apiKey}"
        val queue = Volley.newRequestQueue(context)

        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                Log.d(
                    "API",
                    "get all coins with market data Request Successful, response: $response"
                )
            },
            { error -> Log.d("API", "get all coins with market data Request Error $error") })
        queue.add(stringRequest)
    }

    fun filterCoins(
        context: Context,
        apiKey: String,
        currency: String,
        ids: String = "",
        categories: String = "",
        order: String = ""
    ): List<Coin>? {
        val queue = Volley.newRequestQueue(context)
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
        queue.add(stringRequest)
        Log.d("API", "returning coinsList: ${coinsList.value}")
        return coinsList.value
    }

    fun logOut(context: Context) {
        var auth = FirebaseAuth.getInstance()
        auth.signOut()
        auth.currentUser?.reload()
        startActivity(
            context,
            Intent(context, StartActivity::class.java),
            null
        )
    }
}