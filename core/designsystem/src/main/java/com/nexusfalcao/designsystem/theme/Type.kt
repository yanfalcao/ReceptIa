package com.nexusfalcao.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.nexusfalcao.designsystem.R

val RalewayFont =
    FontFamily(
        Font(R.font.raleway, FontWeight.Normal),
        Font(R.font.raleway_medium, FontWeight.Medium),
        Font(R.font.raleway_bold, FontWeight.Bold),
        Font(R.font.raleway_semibold, FontWeight.SemiBold),
        Font(R.font.raleway_extrabold, FontWeight.ExtraBold),
    )

val ReceptIaTypography =
    Typography(
        headlineLarge =
            TextStyle(
                fontFamily = RalewayFont,
                fontWeight = FontWeight.Normal,
                fontSize = 32.sp,
                lineHeight = 40.sp,
                letterSpacing = 0.sp,
            ),
        headlineMedium =
            TextStyle(
                fontFamily = RalewayFont,
                fontWeight = FontWeight.Normal,
                fontSize = 28.sp,
                lineHeight = 36.sp,
                letterSpacing = 0.sp,
            ),
        headlineSmall =
            TextStyle(
                fontFamily = RalewayFont,
                fontWeight = FontWeight.Normal,
                fontSize = 24.sp,
                lineHeight = 32.sp,
                letterSpacing = 0.sp,
            ),
        titleLarge =
            TextStyle(
                fontFamily = RalewayFont,
                fontWeight = FontWeight.Normal,
                fontSize = 22.sp,
                lineHeight = 28.sp,
                letterSpacing = 0.sp,
            ),
        titleMedium =
            TextStyle(
                fontFamily = RalewayFont,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.15.sp,
            ),
        titleSmall =
            TextStyle(
                fontFamily = RalewayFont,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp,
            ),
        bodyLarge =
            TextStyle(
                fontFamily = RalewayFont,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
            ),
        bodyMedium =
            TextStyle(
                fontFamily = RalewayFont,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.25.sp,
            ),
        labelLarge =
            TextStyle(
                fontFamily = RalewayFont,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp,
            ),
        labelMedium =
            TextStyle(
                fontFamily = RalewayFont,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.5.sp,
            ),
        labelSmall =
            TextStyle(
                fontFamily = RalewayFont,
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.5.sp,
            ),
    )

/*Typography(
headlineLarge = TextStyle(
fontFamily = RalewayFont,
fontWeight = FontWeight.Bold,
fontSize = 32.sp,
lineHeight = 36.sp,
letterSpacing = 0.4.sp,
),
headlineMedium = TextStyle(
fontFamily = RalewayFont,
fontWeight = FontWeight.SemiBold,
fontSize = 25.sp,
lineHeight = 30.sp,
letterSpacing = 0.4.sp,
),
headlineSmall = TextStyle(
fontFamily = RalewayFont,
fontWeight = FontWeight.Medium,
fontSize = 25.sp,
lineHeight = 36.sp,
letterSpacing = 0.4.sp,
),
titleLarge = TextStyle(
fontFamily = RalewayFont,
fontWeight = FontWeight.Bold,
fontSize = 23.sp,
lineHeight = 25.sp,
letterSpacing = 0.4.sp,
),
titleMedium = TextStyle(
fontFamily = RalewayFont,
fontWeight = FontWeight.Bold,
fontSize = 21.sp,
lineHeight = 25.sp,
letterSpacing = 0.4.sp,
),
titleSmall = TextStyle(
fontFamily = RalewayFont,
fontWeight = FontWeight.Medium,
fontSize = 20.sp,
lineHeight = 25.sp,
letterSpacing = 0.4.sp,
),
bodyMedium = TextStyle(
fontFamily = RalewayFont,
fontWeight = FontWeight.Normal,
fontSize = 18.sp,
lineHeight = 20.sp,
letterSpacing = 0.2.sp,
),
labelLarge = TextStyle(
fontFamily = RalewayFont,
fontWeight = FontWeight.SemiBold,
fontSize = 18.sp,
letterSpacing = 0.2.sp,
),
labelMedium = TextStyle(
fontFamily = RalewayFont,
fontWeight = FontWeight.SemiBold,
fontSize = 16.sp,
lineHeight = 20.sp,
letterSpacing = 0.22.sp,
),
labelSmall = TextStyle(
fontFamily = RalewayFont,
fontWeight = FontWeight.Medium,
fontSize = 14.sp,
lineHeight = 16.sp,
letterSpacing = 0.22.sp,
),
)*/
