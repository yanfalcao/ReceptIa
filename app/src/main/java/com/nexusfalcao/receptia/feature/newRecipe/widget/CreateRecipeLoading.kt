package com.nexusfalcao.receptia.feature.newRecipe.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.nexusfalcao.receptia.R
import com.nexusfalcao.receptia.ui.preview.ThemePreviewShowsBakground
import com.nexusfalcao.receptia.ui.theme.ReceptIaTheme

@Composable
fun CreateRecipeLoading() {
    val anim = rememberLottieAnimatable()
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.animation_boiling_pot),
    )

    LaunchedEffect(composition) {
        anim.animate(
            composition,
            iterations = LottieConstants.IterateForever,
        )
    }

    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.scrim)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(15.dp),
                )
                .padding(horizontal = 30.dp, vertical = 15.dp)
                .width(IntrinsicSize.Min),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LottieAnimation(
                modifier = Modifier.size(200.dp),
                composition = anim.composition,
                progress = { anim.progress },
            )

            Text(
                text = stringResource(id = R.string.creating_recipe_loading),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@ThemePreviewShowsBakground
@Composable
fun CreateRecipeLoadingPreview() {
    ReceptIaTheme {
        Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
            CreateRecipeLoading()
        }
    }
}
