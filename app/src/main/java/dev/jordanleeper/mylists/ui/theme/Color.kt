package dev.jordanleeper.mylists.ui.theme

import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF000000)

val Delete = Color(231, 76, 60)
val MarkCompleted = Color(39, 174, 96)
val OnMarkCompleted = Color.White

val Completed = Color(189, 195, 199)
val OnCompleted = Color(127, 140, 141)

const val MyYellow = "#FFBF65"
const val MyRose = "#F7CAC9"
const val MyBlue = "#28485C"
const val MySage = "#ACCAA1"
const val MyGrape = "#B0ABCB"
const val MyPeach = "#FFCAAF"

const val onMyColorTextWhite = "#FFFFFF"
const val onMyColorTextBlack = "#000000"

fun String.getColor(): Color {
    return Color(android.graphics.Color.parseColor(this))
}
