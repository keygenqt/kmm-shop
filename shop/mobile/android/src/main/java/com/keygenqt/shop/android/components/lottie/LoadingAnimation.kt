/*
 * Copyright 2023 Vitaliy Zarubin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.keygenqt.shop.android.components.lottie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.keygenqt.shop.android.R

/**
 * Page animation
 */
@Composable
fun LoadingAnimation(
    modifier: Modifier = Modifier,
) = LoadingAnimationLarge(
    modifier = modifier.padding(vertical = 11.dp, horizontal = 13.dp),
    boxSize = 30.dp,
    iconSize = 45.dp,
)

@Composable
fun LoadingAnimationLarge(
    modifier: Modifier = Modifier,
    boxSize: Dp = 65.dp,
    iconSize: Dp = 80.dp,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.block_loader))

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .size(boxSize)
                .align(Alignment.Center)
        ) {
            Box(
                modifier = Modifier
                    .size(boxSize)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.8f))
                    .align(Alignment.Center)
            ) {
                LottieAnimation(
                    speed = 1.5f,
                    iterations = Int.MAX_VALUE,
                    composition = composition,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .requiredHeight(iconSize)
                        .requiredWidth(iconSize)
                )
            }
        }
    }
}
