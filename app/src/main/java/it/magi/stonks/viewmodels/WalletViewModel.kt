package it.magi.stonks.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import it.magi.stonks.utilities.Utilities

class WalletViewModel(application: Application) : AndroidViewModel(application) {
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
