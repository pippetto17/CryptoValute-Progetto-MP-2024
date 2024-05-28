package it.magi.stonks.objects

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson


class APIRequests {
    fun getPing(context: Context, apiKey: String) {
        val url = "https://api.coingecko.com/api/v3/ping?x_cg_demo_api_key=${apiKey}"
        val queue = Volley.newRequestQueue(context)

        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                Log.d("API", "Request Successful, response: $response")
            },
            { error -> Log.d("API", "Request Error $error") })
        queue.add(stringRequest)
    }

    fun getCoinsSimpleList(context: Context, apiKey: String) {
        val url = "https://api.coingecko.com/api/v3/coins/list?x_cg_demo_api_key=${apiKey}"
        val queue = Volley.newRequestQueue(context)

        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                Log.d("API", "get Coins Simple List Request Successful, response: $response")
            },
            { error -> Log.d("API", "get Coins Simple List Request Error $error") })
        queue.add(stringRequest)
    }

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
    ) : List<Coin> {
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
                Log.d("API", "filterCoins Request Successful, response: $response")
                val coinList = createCoinList(response)
            },
            { error -> Log.d("API", "filterCoins Request Error $error") })
        queue.add(stringRequest)
    }

    fun createCoinList(response: String): List<Coin> {
        val gson = Gson()
        return gson.fromJson(response, Array<Coin>::class.java).toList()
    }

    data class Coin(
        val id: String,
        val symbol: String,
        val name: String,
        val image: String,
        val current_price: Double,
        val market_cap: Long,
        val market_cap_rank: Int,
        val fully_diluted_valuation: Long,
        val total_volume: Long,
        val high_24h: Double,
        val low_24h: Double,
        val price_change_24h: Double,
        val price_change_percentage_24h: Double,
        val market_cap_change_24h: Double,
        val market_cap_change_percentage_24h: Double,
        val circulating_supply: Double,
        val total_supply: Double,
        val max_supply: Double,
        val ath: Double,
        val ath_change_percentage: Double,
        val ath_date: String,
        val atl: Double,
        val atl_change_percentage: Double,
        val atl_date: String,
        val roi: Any?, // Assuming this field can be null
        val last_updated: String
    )


}