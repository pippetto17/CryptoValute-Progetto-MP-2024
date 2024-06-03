package it.magi.stonks.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import it.magi.stonks.R
import it.magi.stonks.utilities.Utilities
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.regex.Pattern


class RegistrationViewModel(application: Application) : AndroidViewModel(application) {


    private val requestQueue = Volley.newRequestQueue(application)

    private var currencyList = MutableLiveData<List<String>>()
    fun getCurrencyList(): LiveData<List<String>> {
        return currencyList
    }

    var _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    var _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    var _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword

    var _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    var _surname = MutableStateFlow("")
    val surname: StateFlow<String> = _surname

    var _selectedCurrency = MutableStateFlow("BTC")
    val selectedCurrency: StateFlow<String> = _selectedCurrency
    var _currentCurrency = MutableStateFlow("")
    val currentCurrency: StateFlow<String> = _currentCurrency

    var _screen = MutableStateFlow(1)
    val screen: MutableStateFlow<Int> = _screen


    fun registerUser(
        email: String,
        password: String,
        confirmPassword: String,
        name: String,
        surname: String,
        currencyPreference: String
    ): Int {
        Log.d("Signup", "registerUser email: $email")
        Log.d("Signup", "registerUser password: $password")
        Log.d("Signup", "registerUser confirmPassword: $confirmPassword")
        val auth: FirebaseAuth = Firebase.auth
        if (checkValidEmail(email) && password == confirmPassword) {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(
                        "RealTimeDatabase",
                        "Trying to register user: $email name: $name surname: $surname"
                    )
                    try {
                        val database =
                            FirebaseDatabase.getInstance("https://criptovalute-b1e06-default-rtdb.europe-west1.firebasedatabase.app/")
                        val myRef = database.getReference().child("users")
                            .child(Utilities().convertDotsToCommas(email).lowercase())
                        Log.d("RealTimeDatabase", "MyRef: $myRef")
                        myRef.child("name").setValue(
                            Utilities().convertDotsToCommas(
                                Utilities().capitalizeFirstChar(name)
                            )
                        )
                        myRef.child("surname").setValue(
                            Utilities().convertDotsToCommas(
                                Utilities().capitalizeFirstChar(surname)
                            )
                        )
                        Log.d(
                            "RealTimeDatabase",
                            "User registered successfully on RealTimeDatabase"
                        )
                        SaveCurrencyPreference(currencyPreference)
                        Log.d("Shared Preferences", "currencyPreference: $currencyPreference")
                    } catch (e: Exception) {
                        Log.d("RealTimeDatabase", "Error: ${e.message}")
                    }
                    Log.d("Signup", "User created successfully")
                    Firebase.auth.currentUser?.sendEmailVerification()
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("Signup", "Email sent.")
                            } else {
                                Log.d("Signup", "Error, email not sent.")
                            }
                        }
                }
            }
            return 0
        } else {
            println("Email non valida o password non corrispondenti")
            return 1
        }
    }

    fun checkValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$".toRegex()
        return emailRegex.matches(email)
    }


    fun validatePassword(password: String): List<Int> {
        val errors = mutableListOf<Int>()

        // Check password length
        Log.d("Signup", "Password length: ${password.length}")
        if (password.length < 6) {
            errors.add(1) // Error code for insufficient length
        }

        // Check for special character
        val specialCharacterPattern =
            Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]")
        if (!specialCharacterPattern.matcher(password).find()) {
            errors.add(2) // Error code for missing special character
        }

        // Check for number
        val numberPattern = Pattern.compile("\\d")
        if (!numberPattern.matcher(password).find()) {
            errors.add(3) // Error code for missing number
        }

        // Check for uppercase letter
        if (!password.any { it.isUpperCase() }) {
            errors.add(4) // Error code for missing uppercase letter
        }


        return errors
    }

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

    fun SaveCurrencyPreference(string: String) {
        val sharedPreferences =
            getApplication<Application>().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("currency", string.lowercase())
        editor.apply()
    }
}