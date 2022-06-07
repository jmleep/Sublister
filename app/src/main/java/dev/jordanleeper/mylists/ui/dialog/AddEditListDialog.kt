package dev.jordanleeper.mylists.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import dev.jordanleeper.mylists.ui.button.ListColorButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditListDialog(
    showAddListDialog: MutableState<Boolean>,
    label: String,
    colors: List<String>,
    textColors: List<String>,
    currentName: String? = null,
    currentColor: String? = null,
    addNewList: (newListName: String, newListColor: String, newTextColor: String?) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    var newListName by remember { mutableStateOf("") }
    newListName = currentName ?: ""

    var newListColor by remember { mutableStateOf(colors[0]) }
    newListColor = currentColor ?: colors[0]

    fun resetDialogFields() {
        newListName = ""
        newListColor = colors[0]
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
                            fontSize = 25.sp
                        )
                        println("NAME: $newListName")
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
                                    val newTextColor =
                                        if (colors.indexOf(newListColor) != -1) textColors[colors.indexOf(
                                            newListColor
                                        )] else null
                                    addNewList(
                                        newListName,
                                        newListColor,
                                        newTextColor
                                    )
                                    resetDialogFields()
                                }
                            ),
                            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        )
                        LaunchedEffect(true) {
                            focusRequester.requestFocus()
                        }
                        Row(
                            modifier = Modifier
                                .padding(top = 15.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            colors.forEach() { color ->
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
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                            ) {
                                Text(
                                    "Cancel",
                                    modifier = Modifier.background(MaterialTheme.colorScheme.tertiary),
                                    color = MaterialTheme.colorScheme.onTertiary
                                )
                            }
                            Button(
                                onClick = {
                                    val newTextColor =
                                        if (colors.indexOf(newListColor) != -1) textColors[colors.indexOf(
                                            newListColor
                                        )] else null

                                    addNewList(
                                        newListName,
                                        newListColor,
                                        newTextColor
                                    )
                                    resetDialogFields()
                                }, modifier = Modifier.padding(start = 15.dp)
                            ) {
                                Text("Add")
                            }
                        }
                    }
                }
            })
    }
}