package it.magi.stonks.viewmodels

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import it.magi.stonks.activities.StartActivity

class OtherViewModel: ViewModel() {
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