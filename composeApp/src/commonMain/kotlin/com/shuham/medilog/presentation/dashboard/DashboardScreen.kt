package com.shuham.medilog.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shuham.medilog.data.repository.PreferencesRepository
import com.shuham.medilog.ui.theme.Error
import com.shuham.medilog.ui.theme.OnPrimary
import com.shuham.medilog.ui.theme.Primary
import com.shuham.medilog.ui.theme.Success
import com.shuham.medilog.ui.theme.TextPrimary
import com.shuham.medilog.ui.theme.TextSecondary
import com.shuham.medilog.ui.theme.Warning
import medilog.composeapp.generated.resources.Res
import medilog.composeapp.generated.resources.add_24px
import medilog.composeapp.generated.resources.cloud_off_24px
import medilog.composeapp.generated.resources.lightbulb_24px
import medilog.composeapp.generated.resources.patient_list_24px
import medilog.composeapp.generated.resources.search_24px
import medilog.composeapp.generated.resources.warning_24px
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

/**
 * Dashboard Screen - Main home screen
 * Matches Design: Clean White UI, 2x2 Grid, Pastel Icon Backgrounds
 */
@Composable
fun DashboardScreen(
    onNavigateToPatients: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onAddPatient: () -> Unit,
    preferencesRepository: PreferencesRepository = koinInject()
) {
    val userName by preferencesRepository.getUserName()
        .collectAsState(initial = "Dr. Smith")

    // Mock Data
    val recentPatients = listOf(
        RecentPatient("Sarah Jenkins", "Vitals Recorded • AI Cleared", "2m ago", Success),
        RecentPatient("Michael Ross", "Prescription Updated", "15m ago", Warning),
        RecentPatient("Elena Vance", "Critical: Fever Spike Detected", "42m ago", Error),
        RecentPatient("Jane Doe", "Initial AI Triage Completed", "1h ago", Primary)
    )

    val backgroundColor = Color(0xFFF8F9FA) // Soft grey-white background

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // 1. Header (Logo + Search + Profile)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "MediLog",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Primary
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Search Button
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .clickable { /* TODO */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.search_24px),
                            contentDescription = "Search",
                            tint = TextSecondary,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Profile Avatar
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .clickable { onNavigateToSettings() }
                    ) {
                        // In real app, use AsyncImage here. Using placeholder for now.
                        Box(
                            modifier = Modifier.fillMaxSize().background(Color(0xFFE0F7FA)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("D", color = Primary, fontWeight = FontWeight.Bold)
                        }
                        // Online Status Dot
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .background(Success, CircleShape)
                                .align(Alignment.BottomEnd)
                                .padding(1.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Welcome Section
            Column {
                Text(
                    text = "Welcome,",
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    color = TextPrimary,
                    lineHeight = 36.sp
                )
                Text(
                    text = userName ?: "Dr. Smith",
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    color = Primary, // Teal Color
                    lineHeight = 36.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Ready for your morning rounds?",
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 3. Stats Grid (2x2)
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "PATIENTS",
                        value = "1,284",
                        icon = Res.drawable.patient_list_24px,
                        accentColor = Primary,
                        onClick = onNavigateToPatients
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "OFFLINE",
                        value = "12",
                        icon = Res.drawable.cloud_off_24px,
                        accentColor = Warning, // Orange
                        onClick = {}
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "HIGH RISK",
                        value = "04",
                        icon = Res.drawable.warning_24px,
                        accentColor = Error, // Red
                        onClick = {}
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "AI TRIAGE",
                        value = "28",
                        icon = Res.drawable.lightbulb_24px, // Brain/Idea alternative
                        accentColor = Color(0xFF4285F4), // Blue
                        onClick = {}
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 4. Recent Activity Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recent Activity",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = TextPrimary
                )

                Text(
                    text = "View All",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Primary,
                    modifier = Modifier.clickable { onNavigateToPatients() }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 5. Recent Activity List
            LazyColumn(
                contentPadding = PaddingValues(bottom = 80.dp), // Space for FAB
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(recentPatients) { patient ->
                    RecentActivityItem(patient)
                }
            }
        }

        // 6. Floating Action Button (Squircle Shape)
        FloatingActionButton(
            onClick = onAddPatient,
            containerColor = Primary,
            contentColor = OnPrimary,
            shape = RoundedCornerShape(18.dp), // Squircle
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
                .size(64.dp)
                .shadow(12.dp, RoundedCornerShape(18.dp), spotColor = Primary.copy(alpha = 0.5f)),
            elevation = FloatingActionButtonDefaults.elevation(0.dp)
        ) {
            Icon(
                painter = painterResource(Res.drawable.add_24px),
                contentDescription = "Add Patient",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

/**
 * Modern Stat Card matching the Screenshot
 */
@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    icon: DrawableResource,
    accentColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(160.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(24.dp),
                spotColor = Color(0x10000000)
            )
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            // Icon Circle
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.1f)), // Pastel Background
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Labels
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextSecondary.copy(alpha = 0.7f),
                letterSpacing = 1.sp
            )
            Text(
                text = value,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }
    }
}

/**
 * Recent Activity List Item
 */
@Composable
fun RecentActivityItem(patient: RecentPatient) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .shadow(4.dp, RoundedCornerShape(20.dp), spotColor = Color(0x08000000)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEEEEEE)), // Grey placeholder
                contentAlignment = Alignment.Center
            ) {
                // Use actual Image here in production
                Text(
                    text = patient.name.first().toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = TextSecondary
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Text Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = patient.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = TextPrimary
                )
                Text(
                    text = patient.action,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = TextSecondary,
                    maxLines = 1
                )
            }

            // Time & Status
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = patient.time,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary.copy(alpha = 0.5f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(patient.statusColor, CircleShape)
                )
            }
        }
    }
}

/**
 * Data Model for UI
 */
data class RecentPatient(
    val name: String,
    val action: String,
    val time: String,
    val statusColor: Color
)

@Preview
@Composable
fun DashboardScreenPrev() {
    DashboardScreen(onNavigateToPatients = {}, onNavigateToSettings = {}, onAddPatient = {})
}
