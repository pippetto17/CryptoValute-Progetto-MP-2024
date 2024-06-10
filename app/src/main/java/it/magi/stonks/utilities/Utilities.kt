package it.magi.stonks.utilities


import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import it.magi.stonks.composables.ConfirmDeleteDialog
import it.magi.stonks.composables.ConfirmEmailDialog
import it.magi.stonks.viewmodels.RegistrationViewModel

class Utilities {

    fun capitalizeFirstChar(str: String): String {
        return str.replaceFirstChar { it.uppercase() }
    }

    fun convertDotsToCommas(input: String): String {
        return input.replace('.', ',')
    }

    fun convertCommasToDots(input: String): String {
        return input.replace(',', '.')
    }

    fun removeSpacesAndConvertToLowerCase(input: String): String {
        // Remove extra spaces and replace them with a single comma
        val noExtraSpaces = input.replace("\\s+".toRegex(), ",")

        // Convert to lowercase
        return noExtraSpaces.lowercase()
    }

    fun testSignup(context: Context, application: Application) {
        RegistrationViewModel(application).registerUser(
            context,
            "test@gmail.com",
            "Abc123,",
            "Abc123,",
            "TestName",
            "TestSurname",
            "EUR"
        )


    }

    fun testSharedPreferences(application: Application) {

    }

    @Composable
    fun EmailSentDialog(
        navController: NavController,
        onDismiss: () -> Unit,
        onDismissButton: () -> Unit,
        onConfirmButton: () -> Unit
    ) {
        ConfirmEmailDialog(
            onDismissRequest = onDismiss,
            onDismissButton = onDismissButton,
            onConfirmButton = onConfirmButton
        )
    }

    @Composable
    fun DeleteAccountDialog(
        onDismiss: () -> Unit,
        onDismissButton: () -> Unit,
        onConfirmButton: () -> Unit
    ) {
        ConfirmDeleteDialog(
            onDismissRequest = onDismiss,
            onDismissButton = onDismissButton,
            onConfirmButton = onConfirmButton
        )
    }

    @Composable
    fun getCurrencyPreference(): String {
        val database =
            FirebaseDatabase.getInstance("https://criptovalute-b1e06-default-rtdb.europe-west1.firebasedatabase.app/")
        val auth = FirebaseAuth.getInstance()
        val email = Utilities().convertDotsToCommas(auth.currentUser?.email ?: "")
        var currency by rememberSaveable {
            mutableStateOf("")
        }
        val myRef = database.getReference().child("users").child(email.toString()).child("currency")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val currencySnapshot = dataSnapshot.getValue(String::class.java)
                currency = currencySnapshot ?: "Unknown"
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("CryptoScreen", "Failed to read value.", error.toException())
            }
        })
        return currency
    }
}