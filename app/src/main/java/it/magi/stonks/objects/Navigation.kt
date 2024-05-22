package it.magi.stonks.objects

sealed class Navigation(var route: String, var title: String) {
    object Login : Navigation("Login", "Login")
    object SignUp : Navigation("SignUp", "SignUp")
}