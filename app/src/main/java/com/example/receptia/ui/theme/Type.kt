package com.example.receptia.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.receptia.R

val RalewayFont = FontFamily(
    Font(R.font.raleway, FontWeight.Normal),
    Font(R.font.raleway_medium, FontWeight.Medium),
    Font(R.font.raleway_bold, FontWeight.Bold),
    Font(R.font.raleway_semibold, FontWeight.SemiBold),
    Font(R.font.raleway_extrabold, FontWeight.ExtraBold),
)

val Typography.titleBoldLarge: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = RalewayFont,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            lineHeight = 36.sp,
            letterSpacing = 0.4.sp,
        )
    }

val Typography.titleBoldMedium: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = RalewayFont,
            fontWeight = FontWeight.Bold,
            fontSize = 21.sp,
            lineHeight = 25.sp,
            letterSpacing = 0.4.sp,
        )
    }

val Typography.titleSemiBoldMedium: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = RalewayFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            lineHeight = 22.sp,
            letterSpacing = 0.22.sp,
        )
    }

val Typography.titleSemiBoldSmall: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = RalewayFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.22.sp,
        )
    }

val Typography.titleMediumSmall: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = RalewayFont,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 22.sp,
            letterSpacing = 0.22.sp,
        )
    }

val Typography.loginBody: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = RalewayFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            letterSpacing = 0.2.sp,
        )
    }

val ReceptIaTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = RalewayFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.2.sp,
    ),
)
