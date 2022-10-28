package dev.jordanleeper.sublister.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.google.accompanist.flowlayout.FlowRow
import dev.jordanleeper.sublister.ui.button.ListColorButton
import dev.jordanleeper.sublister.ui.theme.allColors
import dev.jordanleeper.sublister.ui.theme.allTextColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditListDialog(
    showAddListDialog: MutableState<Boolean>,
    label: String,
    currentName: String? = null,
    currentColor: String? = null,
    addNewList: (newListName: String, newListColor: String, newTextColor: String?) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    var newListName by remember { mutableStateOf("") }
    newListName = currentName ?: ""

    var newListColor by remember { mutableStateOf(allColors[0]) }
    newListColor = currentColor ?: allColors[0]

    fun resetDialogFields() {
        newListName = ""
        newListColor = allColors[0]
    }

    fun saveChange() {
        if (newListName !== "") {
            val newTextColor =
                if (allColors.indexOf(newListColor) != -1) allTextColors[allColors.indexOf(
                    newListColor
                )] else null

            addNewList(
                newListName,
                newListColor,
                newTextColor
            )
            resetDialogFields()
        }
    }

    if (showAddListDialog.value) {
        Dialog(
            onDismissRequest = { showAddListDialog.value = false },
            content = {
                Card() {
                    Column(modifier = Modifier.padding(15.dp)) {
                        Text(
                            label,
                            modifier = Modifier
                                .padding(bottom = 5.dp),
                            style = MaterialTheme.typography.titleLarge
                        )
                        OutlinedTextField(
                            value = newListName,
                            onValueChange = { newListName = it },
                            label = { Text("Name") },
                            modifier = Modifier
                                .focusRequester(focusRequester)
                                .width(IntrinsicSize.Min),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    saveChange()
                                }
                            ),
                            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        )
                        LaunchedEffect(true) {
                            focusRequester.requestFocus()
                        }
                        FlowRow(
                            modifier = Modifier
                                .padding(top = 15.dp)
                                .fillMaxWidth()
                        ) {
                            allColors.forEach() { color ->
                                ListColorButton(
                                    color = color,
                                    isChecked = newListColor == color,
                                    onChange = { newListColor = color }
                                )
                            }
                        }
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier
                                .padding(top = 15.dp)
                                .fillMaxWidth()
                        ) {
                            Button(
                                onClick = {
                                    showAddListDialog.value = false
                                    newListName = ""
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                            ) {
                                Text(
                                    "Cancel",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.background(MaterialTheme.colorScheme.secondary),
                                    color = MaterialTheme.colorScheme.onSecondary
                                )
                            }
                            Button(
                                onClick = {
                                    saveChange()
                                }, modifier = Modifier.padding(start = 15.dp)
                            ) {
                                Text("Save", style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            })
    }
}