package it.magi.stonks.objects

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class APIRequests {
        fun getPing(context: Context,apiKey: String) {
            val url = "https://api.coingecko.com/api/v3/ping?x_cg_demo_api_key=${apiKey}"
            val queue = Volley.newRequestQueue(context)

            val stringRequest = StringRequest(Request.Method.GET, url,
                { response ->
                    Log.d("API", "Request Successful, response: $response")
                },
                {error-> Log.d("API", "Request Error $error") })
            queue.add(stringRequest)
        }

    fun getCoinList(context: Context,apiKey: String) {
        val url = "https://api.coingecko.com/api/v3/coins/list?x_cg_demo_api_key=${apiKey}"
        val queue = Volley.newRequestQueue(context)

        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->Log.d("API", "Request Successful, response: $response")
            },
            {error-> Log.d("API", "Request Error $error") })
        queue.add(stringRequest)
    }

        data class CoinPrice(
            val usd: String,
            val usd_market_cap: String,
            val usd_24h_vol: String,
            val usd_24h_change: String,
            val last_updated_at: Long
        )

        data class CoinListElement(
            val id: String,
            val symbol: String,
            val name: String,
        )


}