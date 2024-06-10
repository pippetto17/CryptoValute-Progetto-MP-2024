package it.magi.stonks.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector
import it.magi.stonks.R

sealed interface NavigationIcon
data class ImageVectorIcon(val imageVector: ImageVector) : NavigationIcon
data class ResourceIcon(val resourceId: Int) : NavigationIcon

sealed class NavigationItem(var route: String, val icon: NavigationIcon, var title: String) {
    data object Home : NavigationItem("home", ResourceIcon(R.drawable.ic_stock_chart), "Home")
    data object Wallet : NavigationItem("wallet", ResourceIcon(R.drawable.ic_wallet), "Wallet")
    data object News : NavigationItem("news", ResourceIcon(R.drawable.ic_newspaper), "News")
    data object Other : NavigationItem("other", ImageVectorIcon(Icons.Default.MoreVert), "Other")
}