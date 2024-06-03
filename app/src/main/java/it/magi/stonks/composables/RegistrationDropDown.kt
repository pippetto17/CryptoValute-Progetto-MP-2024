package it.magi.stonks.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import it.magi.stonks.R
import it.magi.stonks.ui.theme.FormContainerColor
import it.magi.stonks.ui.theme.titleFont
import it.magi.stonks.viewmodels.RegistrationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDown(viewModel: RegistrationViewModel, currencyList: List<String>) {
    Log.d("currencyList", "DropDown: $currencyList")


    var isExpanded by remember {
        mutableStateOf(false)
    }

    var selectedText by remember { mutableStateOf(currencyList[0]) }
    val currencyState = viewModel.selectedCurrency.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded }
        ) {
            TextField(
                modifier = Modifier
                    .menuAnchor(),
                label = {
                    Text(
                        text = stringResource(R.string.signup_select_currency),
                        color = Color.White,
                        fontFamily = titleFont()
                    )
                },
                value = currencyState.value.uppercase(),
                onValueChange = {
                    viewModel._selectedCurrency.value = selectedText
                },
                readOnly = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White,
                    cursorColor = Color.White
                ),
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_currency),
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) }

            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
                modifier = Modifier.background(FormContainerColor)
            ) {
                currencyList.forEachIndexed { index, text ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = text.uppercase(),
                                color = Color.White,
                            )
                        },
                        onClick = {
                            viewModel._selectedCurrency.value = currencyList[index]
                            isExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}