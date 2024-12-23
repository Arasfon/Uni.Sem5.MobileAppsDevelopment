package com.arasfon.uni.sem5.drivenext.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.text.NumberFormat

@Composable
fun formattedNumber(number: Number): String {
    val context = LocalContext.current
    val locale = context.resources.configuration.locales[0]
    val formatter = NumberFormat.getNumberInstance(locale)

    val formattedNumber = formatter.format(number)

    return formattedNumber
}
