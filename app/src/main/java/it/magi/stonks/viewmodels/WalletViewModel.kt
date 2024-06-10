package it.magi.stonks.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import it.magi.stonks.utilities.Utilities
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

class WalletViewModel(application: Application) : AndroidViewModel(application) {
    private val requestQueue = Volley.newRequestQueue(application)

    var _quantity = MutableStateFlow("")
    val quantity: StateFlow<String> = _quantity

    var _totalSpent = MutableStateFlow("")
    val totalSpent: StateFlow<String> = _totalSpent


    var _coinPrice = MutableStateFlow(0f)
    val coinPrice: StateFlow<Float> = this._coinPrice

    fun coinPriceApiRequest(apiKey: String, id: String, currency: String, callback: (Float) -> Unit){
        val url =
            "https://api.coingecko.com/api/v3/simple/price?x_cg_demo_api_key=$apiKey&ids=$id&vs_currencies=$currency"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("API", "simple coin price Request Successful, response: $response ")

                try {
                    val jsonObject = JSONObject(response)
                    val coinObject = jsonObject.getJSONObject(id)
                    val result = coinObject.getDouble(currency).toFloat()
                    callback(result)

                    Log.d("API", "Coin price: $result")
                } catch (e: JSONException) {
                    Log.e("API", "Error parsing JSON response: ${e.message}")
                }
            },
            { error -> Log.d("API", "simple coin price Request Error $error") })

        requestQueue.add(stringRequest)
    }

    val printResultCallback: (Float?) -> Unit = { result ->
        if (result != null) {
            Log.d("API", "Coin price callback: $result")
            _coinPrice.value = result
        } else {
            println("Errore durante il recupero del prezzo")
        }
    }

    fun createNewWallet(walletName: String, database: FirebaseDatabase, auth: FirebaseAuth) {
        val email = auth.currentUser?.email ?: ""
        val myRef = database
            .getReference()
            .child("users")
            .child(Utilities().convertDotsToCommas(email).lowercase())
        try {
            myRef
                .child("wallets")
                .setValue(walletName.lowercase())
        } catch (e: Exception) {
            Log.e("RealTimeDatabase", "errore nella creazione di un nuovo wallet: " + e.toString())
        }
    }

    fun addCoinToWallet(
        id: String,
        amount: Float,
        walletName: String,
        database: FirebaseDatabase,
        auth: FirebaseAuth,
        onResult: (Float) -> Unit // Callback to handle the result
    ) {
        val email = auth.currentUser?.email ?: ""
        val walletRef: DatabaseReference = database.getReference("users")
            .child(Utilities().convertDotsToCommas(email).lowercase())
            .child("wallets")
            .child(walletName.lowercase())
            .child(id.lowercase())
            .child("amount")

        walletRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val actualAmountSnapshot = snapshot.getValue(Float::class.java) ?: 0f
                val newAmount = actualAmountSnapshot + amount
                if (newAmount > 0) {
                    walletRef.setValue(newAmount)
                        .addOnSuccessListener {
                            onResult(newAmount) // Notify success with the new amount
                        }
                        .addOnFailureListener { error ->
                            Log.e("RealTimeDatabase", "Failed to update value.", error)
                            onResult(-1f)
                        }
                } else {
                    onResult(actualAmountSnapshot)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("RealTimeDatabase", "Failed to read value.", error.toException())
                onResult(-1f) // Indicate failure
            }
        })
    }

}
