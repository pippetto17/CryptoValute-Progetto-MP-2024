package it.magi.stonks.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import it.magi.stonks.R

sealed interface NavigationIcon
data class ImageVectorIcon(val imageVector: ImageVector) : NavigationIcon
data class ResourceIcon(val resourceId: Int) : NavigationIcon

sealed class NavigationItem(var route: String, val icon: NavigationIcon, var title: String) {
    data object Stonks : NavigationItem("stonks", ResourceIcon(R.drawable.ic_stock_chart), "Stonks")
    data object Wallet : NavigationItem("wallet", ResourceIcon(R.drawable.ic_wallet), "Wallet")
    data object News : NavigationItem("news", ResourceIcon(R.drawable.ic_newspaper), "News")
    data object Account : NavigationItem("profile", ResourceIcon(R.drawable.ic_user_nav), "Account")
}