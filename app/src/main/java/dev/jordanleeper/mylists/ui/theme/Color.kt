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

val ItemColor = Color(188, 187, 187)
val onItemColor = Color(0, 0, 0)

const val White = "#FFFFFF"
const val Black = "#000000"

const val Yellow = "#FFBF65"
const val Rose = "#F7CAC9"
const val Blue = "#28485C"
const val Sage = "#ACCAA1"
const val Grape = "#B0ABCB"
const val Peach = "#FFCAAF"

class Palette(
    var colors: List<String> = listOf(),
    var textColors: List<String> = listOf()
)

val ParentListItemPalette = listOf(Blue, Rose, Yellow, Sage, Peach, Grape)
val ParentListItemPaletteText = listOf(White, Black, Black, Black, Black, Black)
val parentListPalette = Palette(ParentListItemPalette, ParentListItemPaletteText)

val yellowPalette =
    Palette(listOf("#FFDB59", "#E8B851", "#E89351", "#FF8A59"), listOf(Black, Black, Black, Black))
val rosePalette =
    Palette(listOf("#E87E51", "#FFBF66", "#E8C751", "#FFFB59"), listOf(White, Black, Black, Black))
val bluePalette =
    Palette(listOf("#80CEFF", "#66C4FF", "#B37724", "#FFBF66"), listOf(Black, Black, White, Black))
val sagePalette =
    Palette(listOf("#6D8066", "#DAFFCC", "#364033", "#C4E6B8"), listOf(White, Black, White, Black))
val grapePalette =
    Palette(listOf("#6E6B80", "#DCD6FF", "#373640", "#000000"), listOf(White, Black, White, White))
val peachPalette =
    Palette(listOf("#E2A8A8", "#B88CA0", "#877591", "#575F78"), listOf(Black, Black, White, White))

fun String.getPalette(): Palette {
    return when (this) {
        Yellow -> yellowPalette
        Rose -> rosePalette
        Blue -> bluePalette
        Sage -> sagePalette
        Grape -> grapePalette
        Peach -> peachPalette
        else -> bluePalette
    }
}

val allColors = listOf(
    parentListPalette.colors,
    yellowPalette.colors,
    rosePalette.colors,
    bluePalette.colors,
    sagePalette.colors,
    grapePalette.colors,
    peachPalette.colors
).flatten()

val allTextColors = listOf(
    parentListPalette.textColors,
    yellowPalette.textColors,
    rosePalette.textColors,
    bluePalette.textColors,
    sagePalette.textColors,
    grapePalette.textColors,
    peachPalette.textColors
).flatten()

fun String.getColor(): Color {
    return Color(android.graphics.Color.parseColor(this))
}
