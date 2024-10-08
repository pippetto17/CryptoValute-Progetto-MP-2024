package it.magi.stonks.composables

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.magi.stonks.R
import it.magi.stonks.ui.theme.DarkBgColor
import it.magi.stonks.viewmodels.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtherDropDown(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel,
    currencyList: List<String>,
    context: Context
) {
    Log.d("currencyList", "DropDown: $currencyList")

    var isExpanded by remember {
        mutableStateOf(false)
    }

    val selectedText by remember { mutableStateOf(currencyList[0]) }
    val currencyState = viewModel.selectedCurrency.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded }
        ) {
            Row {
                TextField(
                    value = stringResource(R.string.currency),
                    modifier = Modifier.fillMaxWidth(0.6f),
                    onValueChange = {},
                    readOnly = true,
                    enabled = false,
                    textStyle = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent
                    )
                )
                Column {
                    TextField(
                        modifier = modifier
                            .menuAnchor()
                            .wrapContentHeight(align = Alignment.CenterVertically),
                        value = currencyState.value.uppercase(),
                        onValueChange = {
                            viewModel._selectedCurrency.value = selectedText
                        },
                        readOnly = true,
                        textStyle = TextStyle(
                            textAlign = TextAlign.End
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color.White,
                            focusedTrailingIconColor = Color.White,
                            unfocusedTrailingIconColor = Color.White,
                        ),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) }
                    )

                    ExposedDropdownMenu(
                        expanded = isExpanded,
                        onDismissRequest = { isExpanded = false },
                        modifier = Modifier
                            .height(160.dp)
                            .background(DarkBgColor)
                    ) {
                        currencyList.forEachIndexed { index, text ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = text.uppercase(),
                                        color = Color.White
                                    )
                                },
                                onClick = {
                                    viewModel._selectedCurrency.value = currencyList[index]
                                    viewModel.changePreferredCurrency(context, viewModel._selectedCurrency.value)
                                    Toast.makeText(context, "Currency changed", Toast.LENGTH_SHORT).show()
                                    isExpanded = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }
            }
        }
    }
}