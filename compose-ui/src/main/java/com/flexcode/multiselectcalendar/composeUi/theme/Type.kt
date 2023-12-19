package com.flexcode.multiselectcalendar.composeUi.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.flexcode.multiselectcalendar.composeUi.R

// Set of Material typography styles to start with
val Typography =
    Typography(
        bodyLarge =
        TextStyle(
            fontFamily = FontFamily(Font(R.font.hellix_medium)),
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            letterSpacing = 0.5.sp,
            lineHeight = 20.sp,
        ),
        bodyMedium =
        TextStyle(
            fontFamily = FontFamily(Font(R.font.hellix_regular)),
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            letterSpacing = 0.5.sp,
            lineHeight = 20.sp,
        ),
        labelMedium =
        TextStyle(
            fontFamily = FontFamily(Font(R.font.hellix_regular)),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            letterSpacing = 0.5.sp,
            lineHeight = 20.sp,
        ),
        titleLarge =
        TextStyle(
            fontFamily = FontFamily(Font(R.font.hellix_semibold)),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 28.sp,
            letterSpacing = 0.sp,
            lineHeight = 20.sp,
        ),
        titleMedium =
        TextStyle(
            fontFamily = FontFamily(Font(R.font.hellix_semibold)),
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp,
            letterSpacing = 0.sp,
            lineHeight = 20.sp,
        ),
        titleSmall =
        TextStyle(
            fontFamily = FontFamily(Font(R.font.hellix_semibold)),
            fontWeight = FontWeight.ExtraLight,
            fontSize = 24.sp,
            letterSpacing = 0.sp,
            lineHeight = 20.sp,
        ),
        labelSmall =
        TextStyle(
            fontFamily = FontFamily(Font(R.font.hellix_regular)),
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            letterSpacing = 0.5.sp,
            lineHeight = 20.sp,
        ),
    )
