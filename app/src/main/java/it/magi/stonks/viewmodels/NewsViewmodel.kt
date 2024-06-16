package it.magi.stonks.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import it.magi.stonks.models.News

const val newsApiKey="pub_46392f831eb3de25ecfc97a5b9b068a647b85"
class NewsViewModel(application: Application) : AndroidViewModel(application) {
    private val requestQueue = Volley.newRequestQueue(application)



    fun getLatestNewsApiRequest(apiKey: String,onResult: (News) -> Unit) {
        val url ="https://newsdata.io/api/1/latest?apikey=$apiKey&language=en"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("API", " NewsRequest successfull, response: $response ")
                Log.d("API", "response type: ${response}")
                val gson = Gson()
                val newsType = object : TypeToken<News>() {}.type

                val value = gson.fromJson<News>(response, newsType)
                onResult(value)

            },
            { error -> Log.d("API", "Market chart by id Request Error $error") })
        requestQueue.add(stringRequest)
    }
}