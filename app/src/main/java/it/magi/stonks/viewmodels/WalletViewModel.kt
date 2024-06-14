package it.magi.stonks.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import it.magi.stonks.models.Coin
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

    private var coinsList = MutableLiveData<List<Coin>>()

    fun coinPriceApiRequest(
        apiKey: String,
        id: String,
        currency: String,
        callback: (Float) -> Unit
    ) {
        Log.d("API", "simple coin price CURRENCY $currency")

        val url =
            "https://api.coingecko.com/api/v3/simple/price?x_cg_demo_api_key=$apiKey&ids=$id&vs_currencies=$currency"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("API", "simple coin price Request for $id $currency: $response ")
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

    fun filterCoinsApiRequest(apiKey: String, currency: String, ids: String = "", categories: String = "", order: String = "", priceChangePercentage: String = "",onResult: (List<Coin>) -> Unit) {
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
                onResult(value)

                Log.d("API", "value: ${value}")

            },
            { error -> Log.d("API", "filterCoins Request Error $error") })
        requestQueue.add(stringRequest)
        Log.d("API", "returning coinsList: ${coinsList.value}")
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
                .child("walletName")
                .setValue(walletName.lowercase())
        } catch (e: Exception) {
            Log.e("RealTimeDatabase", "errore nella creazione di un nuovo wallet: " + e.toString())
        }
    }
    fun getAllCoinsFromAllWallets(database: FirebaseDatabase, onResult: (String) -> Unit) {
        val auth = FirebaseAuth.getInstance()
        val email=auth.currentUser?.email ?: ""
        val userRef = database.getReference("users")
            .child(Utilities().convertDotsToCommas(email).lowercase())

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val coins = mutableSetOf<String>()
                val walletsSnapshot = snapshot.child("wallets")

                for (walletSnapshot in walletsSnapshot.children) {
                    val cryptoSnapshot = walletSnapshot.child("crypto")
                    for (cryptoChildSnapshot in cryptoSnapshot.children) {
                        val cryptoName = cryptoChildSnapshot.key
                        cryptoName?.let { coins.add(it) }
                    }
                }

                onResult(coins.joinToString(" "))
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("RealTimeDatabase", "Failed to read wallet details", error.toException())
                onResult("")
            }
        })
    }

    fun addCryptoToWallet(
        database: FirebaseDatabase,
        walletName: String,
        coinName: String,
        valueToAdd: String
    ) {
        val email = FirebaseAuth.getInstance().currentUser?.email ?: ""
        val userRef: DatabaseReference = database.getReference("users")
            .child(Utilities().convertDotsToCommas(email).lowercase())
            .child("wallets")

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (walletSnapshot in snapshot.children) {
                        val currentWalletName = walletSnapshot.child("walletName").getValue(String::class.java)
                        if (currentWalletName == walletName) {
                            val walletKey = walletSnapshot.key
                            if (walletKey != null) {
                                val walletRef = userRef.child(walletKey).child("crypto").child(coinName)
                                walletRef.addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(cryptoSnapshot: DataSnapshot) {
                                        val existingValue = cryptoSnapshot.getValue(String::class.java)?.toDoubleOrNull() ?: 0.0
                                        val newValue = existingValue + valueToAdd.toFloatOrNull()!!
                                        walletRef.setValue(newValue.toString())
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        println("Errore durante l'accesso al database: ${error.message}")
                                    }
                                })
                                return
                            }
                        }
                    }
                    println("Il wallet $walletName non esiste.")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Errore durante l'accesso al database: ${error.message}")
            }
        })
    }

    fun getWalletCoinsList(database: FirebaseDatabase, walletName: String, onWalletDetailsReady: (Map<String, String>) -> Unit) {
        val email = FirebaseAuth.getInstance().currentUser?.email ?: ""
        val userRef: DatabaseReference = database.getReference("users")
            .child(Utilities().convertDotsToCommas(email).lowercase())

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val walletDetails = mutableMapOf<String, String>()
                val walletsSnapshot = snapshot.child("wallets")
                for (childSnapshot in walletsSnapshot.children) {
                    val currentWalletName = childSnapshot.child("walletName").getValue(String::class.java)
                    if (currentWalletName == walletName) {
                        val cryptoSnapshot = childSnapshot.child("crypto")
                        for (cryptoChildSnapshot in cryptoSnapshot.children) {
                            val cryptoName = cryptoChildSnapshot.key
                            val cryptoAmount = cryptoChildSnapshot.getValue(String::class.java)
                            cryptoName?.let { name -> cryptoAmount?.let { amount -> walletDetails[name] = amount } }
                        }
                        break
                    }
                }
                Log.d("RealTimeDatabase", "walletDetails: $walletDetails")
                onWalletDetailsReady(walletDetails)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("RealTimeDatabase", "Failed to read wallet details", error.toException())
                onWalletDetailsReady(emptyMap())
            }
        })
    }

    fun getWalletsList(database: FirebaseDatabase, onWalletListReady: (List<String>) -> Unit) {
        val email = FirebaseAuth.getInstance().currentUser?.email ?: ""
        val userRef: DatabaseReference = database.getReference("users")
            .child(Utilities().convertDotsToCommas(email).lowercase())

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val walletList = mutableListOf<String>()
                val walletsSnapshot = snapshot.child("wallets")
                for (childSnapshot in walletsSnapshot.children) {
                    val walletName = childSnapshot.child("walletName").getValue(String::class.java)
                    walletName?.let { walletList.add(it) }
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
