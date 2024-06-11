package it.magi.stonks.composables

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.magi.stonks.R
import it.magi.stonks.ui.theme.DarkBgColor
import it.magi.stonks.ui.theme.GreenStock
import it.magi.stonks.ui.theme.titleFont

@Composable
fun CustomScrollableTabRow(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabClick: (Int) -> Unit
) {
    val density = LocalDensity.current
    val tabWidths = remember {
        val tabWidthStateList = mutableStateListOf<Dp>()
        repeat(tabs.size) {
            tabWidthStateList.add(0.dp)
        }
        tabWidthStateList
    }
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        edgePadding = 0.dp,
        containerColor = DarkBgColor,
        contentColor = Color.White,
        indicator = { tabPositions ->
            if (selectedTabIndex < tabPositions.size) {
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = GreenStock
                )
            }
        }
    ) {
        tabs.forEachIndexed { tabIndex, title ->
            if (title != "Trending") {
                Tab(
                    text = {
                        Text(
                            text = title.uppercase(),
                            color = if (tabIndex == selectedTabIndex) Color.White else Color.LightGray,
                            fontSize = 12.sp,
                            fontFamily = titleFont(),
                            onTextLayout = { textLayoutResult ->
                                tabWidths[tabIndex] =
                                    with(density) { textLayoutResult.size.width.toDp() }
                            }
                        )
                    },
                    selected = selectedTabIndex == tabIndex,
                    onClick = { onTabClick(tabIndex) },
                    selectedContentColor = Color.White,
                )
            } else {
                Tab(
                    modifier = Modifier.width(20.dp),
                    text = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_trending),
                            contentDescription = "",
                            modifier = Modifier
                                .size(20.dp)
                        )
                    },
                    selected = selectedTabIndex == tabIndex,
                    onClick = { onTabClick(tabIndex) },
                    selectedContentColor = Color.White,
                )
            }
        }
    }
}
