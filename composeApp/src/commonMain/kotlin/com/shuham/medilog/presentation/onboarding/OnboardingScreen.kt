package com.shuham.medilog.presentation.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shuham.medilog.ui.theme.Primary
import com.shuham.medilog.ui.theme.Secondary
import com.shuham.medilog.ui.theme.TextPrimary
import com.shuham.medilog.ui.theme.TextSecondary
import kotlinx.coroutines.launch
import medilog.composeapp.generated.resources.Res
import medilog.composeapp.generated.resources.arrow_forward_ios_24px
import medilog.composeapp.generated.resources.cloud_off_24px
import medilog.composeapp.generated.resources.health_and_safety_24px
import medilog.composeapp.generated.resources.security_24px
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/**
 * Onboarding Page Data
 */
data class OnboardingPage(
    val title: String,
    val description: String,
    val icon: DrawableResource,
    val gradientColors: List<androidx.compose.ui.graphics.Color>
)


/**
 * Onboarding pages content
 */
val onboardingPages = listOf(
    OnboardingPage(
        title = "Offline-First",
        description = "Access patient records anytime, anywhere. No internet required. Your data stays on your device.",
        icon = Res.drawable.cloud_off_24px,
        gradientColors = listOf(
            androidx.compose.ui.graphics.Color(0xFF008080),
            androidx.compose.ui.graphics.Color(0xFF00A6A6)
        )
    ),
    OnboardingPage(
        title = "AI-Powered Triage",
        description = "Get instant health risk assessments with our intelligent analysis system. Quick, accurate, and reliable.",
        icon = Res.drawable.health_and_safety_24px,
        gradientColors = listOf(
            androidx.compose.ui.graphics.Color(0xFF10B981),
            androidx.compose.ui.graphics.Color(0xFF34D399)
        )
    ),
    OnboardingPage(
        title = "Secure & Private",
        description = "Your patient data is encrypted and protected. HIPAA-compliant security you can trust.",
        icon = Res.drawable.security_24px,
        gradientColors = listOf(
            androidx.compose.ui.graphics.Color(0xFF6366F1),
            androidx.compose.ui.graphics.Color(0xFF818CF8)
        )
    )
)

/**
 * Modern Onboarding Screen
 * Features: HorizontalPager, animated indicators, smooth transitions
 */
@Composable
fun OnboardingScreen(
    onGetStarted: () -> Unit,
    onSkip: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { onboardingPages.size })
    val coroutineScope = rememberCoroutineScope()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Skip Button (Top Right)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = onSkip,
                enabled = pagerState.currentPage < onboardingPages.size - 1
            ) {
                Text(
                    text = "Skip",
                    color = if (pagerState.currentPage < onboardingPages.size - 1) 
                        TextSecondary 
                    else 
                        TextSecondary.copy(alpha = 0.3f),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
        
        // Horizontal Pager
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            pageSpacing = 16.dp
        ) { page ->
            OnboardingPageContent(
                page = onboardingPages[page],
                isVisible = pagerState.currentPage == page
            )
        }
        
        // Bottom Section (Indicators + Button)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Page Indicators
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                repeat(onboardingPages.size) { index ->
                    PageIndicator(
                        isSelected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )
                    if (index < onboardingPages.size - 1) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
            
            // Action Button
            if (pagerState.currentPage == onboardingPages.size - 1) {
                // Last page - Show "Get Started" button
                Button(
                    onClick = onGetStarted,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 4.dp
                    )
                ) {
                    Text(
                        text = "Get Started",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            } else {
                // Other pages - Show "Next" button
                OutlinedButton(
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Primary
                    )
                ) {
                    Text(
                        text = "Next",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(Res.drawable.arrow_forward_ios_24px),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

/**
 * Individual Onboarding Page Content
 */
@Composable
private fun OnboardingPageContent(
    page: OnboardingPage,
    isVisible: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Animated Icon Container
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(tween(600)) + slideInVertically(
                animationSpec = tween(600),
                initialOffsetY = { it / 3 }
            )
        ) {
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(page.gradientColors)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(page.icon),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = androidx.compose.ui.graphics.Color.White
                )
            }
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Title
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(tween(600, delayMillis = 200))
        ) {
            Text(
                text = page.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                textAlign = TextAlign.Center
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Description
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(tween(600, delayMillis = 400))
        ) {
            Text(
                text = page.description,
                style = MaterialTheme.typography.bodyLarge,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )
        }
    }
}

/**
 * Page Indicator Dot
 */
@Composable
private fun PageIndicator(
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val width = animateDpAsState(
        targetValue = if (isSelected) 32.dp else 8.dp,
        animationSpec = tween(300),
        label = "indicator_width"
    )
    
    Surface(
        modifier = Modifier
            .width(width.value)
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp)),
        color = if (isSelected) Primary else Secondary,
        onClick = onClick
    ) {}
}

