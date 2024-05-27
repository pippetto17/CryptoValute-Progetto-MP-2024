package it.magi.stonks.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel: ViewModel() {
    fun logOut(){
        var auth= FirebaseAuth.getInstance()
        auth.signOut()
        auth.currentUser?.reload()


    }
}