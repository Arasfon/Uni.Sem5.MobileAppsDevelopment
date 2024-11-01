package com.arasfon.uni.sem5.drivenext.common.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.arasfon.uni.sem5.drivenext.R

val montserratFontFamily = FontFamily(
        Font(R.font.montserrat_thin, FontWeight.ExtraLight),
        Font(R.font.montserrat_thinitalic, FontWeight.ExtraLight, FontStyle.Italic),

        Font(R.font.montserrat_extralight, FontWeight.ExtraLight),
        Font(R.font.montserrat_extralightitalic, FontWeight.ExtraLight, FontStyle.Italic),

        Font(R.font.montserrat_light, FontWeight.Light),
        Font(R.font.montserrat_lightitalic, FontWeight.Light, FontStyle.Italic),

        Font(R.font.montserrat_regular, FontWeight.Normal),
        Font(R.font.montserrat_italic, FontWeight.Normal, FontStyle.Italic),

        Font(R.font.montserrat_medium, FontWeight.Medium),
        Font(R.font.montserrat_mediumitalic, FontWeight.Medium, FontStyle.Italic),

        Font(R.font.montserrat_semibold, FontWeight.SemiBold),
        Font(R.font.montserrat_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),

        Font(R.font.montserrat_bold, FontWeight.Bold),
        Font(R.font.montserrat_bolditalic, FontWeight.Bold, FontStyle.Italic),

        Font(R.font.montserrat_extrabold, FontWeight.ExtraBold),
        Font(R.font.montserrat_extrabolditalic, FontWeight.ExtraBold, FontStyle.Italic),

        Font(R.font.montserrat_black, FontWeight.Black),
        Font(R.font.montserrat_blackitalic, FontWeight.Black, FontStyle.Italic),
    )

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = montserratFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = montserratFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    titleLarge = TextStyle(
        fontFamily = montserratFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = montserratFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = montserratFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = montserratFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),
    labelSmall = TextStyle(
        fontFamily = montserratFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp
    )
)
