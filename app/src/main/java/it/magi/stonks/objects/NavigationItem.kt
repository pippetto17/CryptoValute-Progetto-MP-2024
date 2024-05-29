package it.magi.stonks.objects

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(var route: String, val icon: ImageVector, var title: String) {
    object Home : NavigationItem("home", Icons.Rounded.Home, "Home")
    object Wallet : NavigationItem("wallet", Icons.Rounded.Star, "Wallet")
    object Search : NavigationItem("search", Icons.Rounded.Search, "Search")
    object Other : NavigationItem("other", Icons.Default.MoreVert, "Other")
}