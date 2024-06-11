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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import it.magi.stonks.utilities.Utilities
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONException
import org.json.JSONObject

class WalletViewModel(application: Application) : AndroidViewModel(application) {
    private val requestQueue = Volley.newRequestQueue(application)

    var _quantity = MutableStateFlow("")
    val quantity: StateFlow<String> = _quantity

    var _totalSpent = MutableStateFlow("")
    val totalSpent: StateFlow<String> = _totalSpent

    var _wallets = MutableStateFlow(emptyList<String>())
    val wallets: StateFlow<List<String>> = _wallets

    var _coinPrice = MutableStateFlow(0f)
    val coinPrice: StateFlow<Float> = this._coinPrice

    var _newWalletName = MutableStateFlow("")
    val newWalletName: StateFlow<String> = _newWalletName



    fun coinPriceApiRequest(
        apiKey: String,
        id: String,
        currency: String,
        callback: (Float) -> Unit
    ) {
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

    val returnWalletListCallback: (List<String>?) -> Unit = { result ->
        if (result != null) {
            _wallets.value = result
        } else {
            println("Errore durante il recupero della lista dei wallet")
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
                .push()
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
                onResult(-1f)
            }
        })
    }

    fun getWalletList(database: FirebaseDatabase, onWalletListReady: (List<String>) -> Unit) {
        val email = FirebaseAuth.getInstance().currentUser?.email ?: ""
        val userRef: DatabaseReference = database.getReference("users")
            .child(Utilities().convertDotsToCommas(email).lowercase())

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val walletList = mutableListOf<String>()
                val walletsSnapshot = snapshot.child("wallets")
                for (childSnapshot in walletsSnapshot.children) {
                    val walletId = childSnapshot.value.toString()
                    walletList.add(walletId)
                }
                onWalletListReady(walletList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("RealTimeDatabase", "Failed to read wallet list", error.toException())
                onWalletListReady(emptyList())
            }
        })
    }

}
