package dev.jordanleeper.mylists.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import dev.jordanleeper.mylists.data.ListViewModel
import dev.jordanleeper.mylists.data.ParentList
import dev.jordanleeper.mylists.ui.theme.MyBlue
import dev.jordanleeper.mylists.ui.theme.MyRose
import dev.jordanleeper.mylists.ui.theme.MyYellow
import dev.jordanleeper.mylists.ui.theme.getColor
import java.util.*

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddListDialog(showAddListDialog: MutableState<Boolean>, viewModel: ListViewModel) {
    var newListName by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current
    var newListColor by remember { mutableStateOf(MyBlue) }

    fun addNewList() {
        viewModel.addList(
            ParentList(
                0, newListName, newListColor,
                false,
                Date().time
            )
        )
        showAddListDialog.value = false
        newListName = ""
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
                                onDone = { addNewList() }
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
                            FilledIconToggleButton(
                                checked = newListColor == MyBlue,
                                onCheckedChange = { newListColor = MyBlue }) {
                                Box(
                                    modifier = Modifier
                                        .size(25.dp)
                                        .clip(CircleShape)
                                        .background(MyBlue.getColor())
                                )
                            }
                            FilledIconToggleButton(
                                checked = newListColor == MyRose,
                                onCheckedChange = { newListColor = MyRose }) {
                                Box(
                                    modifier = Modifier
                                        .size(25.dp)
                                        .clip(CircleShape)
                                        .background(MyRose.getColor())
                                )
                            }
                            FilledIconToggleButton(
                                checked = newListColor == MyYellow,
                                onCheckedChange = { newListColor = MyYellow }) {
                                Box(
                                    modifier = Modifier
                                        .size(25.dp)
                                        .clip(CircleShape)
                                        .background(MyYellow.getColor())
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
                                    addNewList()
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