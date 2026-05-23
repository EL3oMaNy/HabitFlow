package com.example.habitflow.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    isArabic: Boolean,
    onNavigateToDashboard: () -> Unit
) {
    val entryAlpha = remember { Animatable(0f) }
    val entryScale = remember { Animatable(0.85f) }
    val entryOffsetY = remember { Animatable(20f) }

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 0.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    LaunchedEffect(Unit) {
        launch {
            entryAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(1200, easing = FastOutSlowInEasing)
            )
        }
        launch {
            entryScale.animateTo(
                targetValue = 1f,
                animationSpec = tween(1200, easing = FastOutSlowInEasing)
            )
        }
        launch {
            entryOffsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(1200, easing = FastOutSlowInEasing)
            )
        }

        delay(2400)
        onNavigateToDashboard()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .offset(y = entryOffsetY.value.dp)
                .scale(entryScale.value)
                .alpha(entryAlpha.value)
        ) {
            Box(
                modifier = Modifier
                    .size(128.dp)
                    .padding(bottom = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .scale(pulseScale)
                        .alpha(pulseAlpha)
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                )

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .scale(pulseScale * 1.15f)
                        .alpha(pulseAlpha * 0.5f)
                        .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                )

                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .rotate(3f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.rotate(-3f),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "H",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary,
                            letterSpacing = 2.sp
                        )
                        Text(
                            text = "F",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            letterSpacing = 2.sp
                        )
                    }
                }
            }

            Text(
                text = if (isArabic) "مسار العادات" else "HabitFlow",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (isArabic) "الترتيب والتركيز اليومي" else "Daily organization and focus",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.outline,
                letterSpacing = 1.sp
            )
        }
    }
}