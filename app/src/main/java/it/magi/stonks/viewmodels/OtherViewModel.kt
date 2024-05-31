package it.magi.stonks.viewmodels

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import it.magi.stonks.activities.StartActivity

class OtherViewModel: ViewModel() {
    fun logOut(context: Context) {
        var auth = FirebaseAuth.getInstance()
        auth.signOut()
        auth.currentUser?.reload()
        ContextCompat.startActivity(
            context,
            Intent(context, StartActivity::class.java),
            null
        )
    }
}