package dev.jordanleeper.mylists.ui.theme

import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val Delete = Color(231, 76, 60)
val MarkCompleted = Color(39, 174, 96)
val OnMarkCompleted = Color.White

val Completed = Color(189, 195, 199)
val OnCompleted = Color(127, 140, 141)

const val MyYellow = "#FFBF65"
const val MyRose = "#F7CAC9"
const val MyBlue = "#59788E"

fun String.getColor(): Color {
    return Color(android.graphics.Color.parseColor(this))
}
