package com.openkin.startaiprog.screen.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.runtime.Composable

@Composable
fun TabAnimation(
    isTabShown: Boolean,
    content: @Composable AnimatedVisibilityScope.() -> Unit,
) {
    AnimatedVisibility(
        visible = isTabShown,
        enter = expandHorizontally(
            animationSpec = tween(
                durationMillis = 1000,
                easing = LinearOutSlowInEasing,
            )
        ),
        exit = shrinkHorizontally(
            animationSpec = tween(
                durationMillis = 1000,
                easing = LinearOutSlowInEasing,
            )
        ),
    ) {
        content()
    }
}
