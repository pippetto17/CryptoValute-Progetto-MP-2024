package it.magi.stonks.viewmodels

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import it.magi.stonks.R
import it.magi.stonks.activities.StartActivity
import it.magi.stonks.utilities.Utilities
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

class SettingsViewModel(application: Application, prefCurrency: String) :
    AndroidViewModel(application) {
    private val requestQueue = Volley.newRequestQueue(application)

    private var currencyList = MutableLiveData<List<String>>()
    fun getCurrencyList(): LiveData<List<String>> {
        return currencyList
    }

    val prefCurrency = prefCurrency

    var _selectedCurrency = MutableStateFlow(prefCurrency)
    val selectedCurrency: StateFlow<String> = _selectedCurrency

    fun getSupportedCurrencies(apiKey: String) {
        val url =
            "https://api.coingecko.com/api/v3/simple/supported_vs_currencies?x_cg_demo_api_key=${apiKey}"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d(
                    "API",
                    "get all supported currency Request Successful, response: $response"

                )
                val gson = Gson()
                val currencyListType = object : TypeToken<List<String>>() {}.type

                val value = gson.fromJson<List<String>>(response, currencyListType)
                currencyList.value = value
                Log.d("API", "value: ${currencyList.value}")
            },
            { error -> Log.d("API", "get all supported currency Request Error $error") })
        requestQueue.add(stringRequest)
    }

    fun changePreferredCurrency(context: Context, newCurrency: String) {
        val changedCurrency = newCurrency
        val email = FirebaseAuth.getInstance().currentUser?.email
        val database =
            FirebaseDatabase.getInstance(context.getString(R.string.db_url))
        if (email != null) {
            val myRef = database.getReference().child("users")
                .child(Utilities().convertDotsToCommas(email).lowercase())
            val currency = myRef.child("currency").setValue(changedCurrency)
            _selectedCurrency.value = changedCurrency
        }
    }

    suspend fun deleteFirebaseUser(context: Context) {
        val user = FirebaseAuth.getInstance().currentUser
        val email = user?.email
        val database =
            FirebaseDatabase.getInstance(context.getString(R.string.db_url))
        if(email != null) {
            val myRef = database.getReference().child("users")
                .child(Utilities().convertDotsToCommas(email).lowercase())
            try {
                user.delete().await()
                myRef.removeValue().await()
                Log.d("API", "User deleted successfully")

                ContextCompat.startActivity(
                    context,
                    Intent(context, StartActivity::class.java),
                    null
                )
            } catch (e: Exception) {
                Toast.makeText(context, "You must logout and log back in to delete your account. Error: ${e.message}", Toast.LENGTH_SHORT).show()
                println("Error deleting user: ${e.message}")
            }
        }
    }

    fun logOut(context: Context) {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        auth.signOut()

        ContextCompat.startActivity(
            context,
            Intent(context, StartActivity::class.java),
            null
        )
    }
}