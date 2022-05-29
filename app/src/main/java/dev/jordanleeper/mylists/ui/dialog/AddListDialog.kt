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
import dev.jordanleeper.mylists.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddListDialog(
    showAddListDialog: MutableState<Boolean>,
    addNewList: (newListName: String, newListColor: String, myTextColor: String) -> Unit
) {
    var newListName by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    var newListColor by remember { mutableStateOf(MyBlue) }

    val myTextColor = when (newListColor) {
        MyBlue -> onMyColorTextWhite
        else -> onMyColorTextBlack
    }

    fun resetDialogFields() {
        newListName = ""
        newListColor = MyBlue
    }

    if (showAddListDialog.value) {
        Dialog(
            onDismissRequest = { showAddListDialog.value = false },
            content = {
                Card() {
                    Column(modifier = Modifier.padding(15.dp)) {
                        Text(
                            "Add List",
                            modifier = Modifier
                                .padding(bottom = 5.dp),
                            fontSize = 25.sp
                        )
                        OutlinedTextField(
                            value = newListName,
                            onValueChange = { newListName = it },
                            label = { Text("Name") },
                            modifier = Modifier
                                .focusRequester(focusRequester),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    addNewList(
                                        newListName,
                                        newListColor,
                                        myTextColor
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
                            ListColorButton(
                                color = MyBlue,
                                activeColor = newListColor,
                                onChange = { newListColor = MyBlue }
                            )
                            ListColorButton(
                                color = MyRose,
                                activeColor = newListColor,
                                onChange = { newListColor = MyRose }
                            )
                            ListColorButton(
                                color = MyYellow,
                                activeColor = newListColor,
                                onChange = { newListColor = MyYellow }
                            )
                            ListColorButton(
                                color = MySage,
                                activeColor = newListColor,
                                onChange = { newListColor = MySage }
                            )
                            ListColorButton(
                                color = MyPeach,
                                activeColor = newListColor,
                                onChange = { newListColor = MyPeach }
                            )
                            ListColorButton(MyGrape, newListColor) {
                                newListColor = MyGrape
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
                                    addNewList(newListName, newListColor, myTextColor)
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