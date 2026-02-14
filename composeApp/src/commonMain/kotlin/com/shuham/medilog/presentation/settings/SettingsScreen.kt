package com.shuham.medilog.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shuham.medilog.data.repository.PreferencesRepository
import com.shuham.medilog.ui.theme.Error
import com.shuham.medilog.ui.theme.ErrorBackground
import com.shuham.medilog.ui.theme.OnPrimary
import com.shuham.medilog.ui.theme.Primary
import com.shuham.medilog.ui.theme.Secondary
import com.shuham.medilog.ui.theme.Surface
import com.shuham.medilog.ui.theme.SurfaceVariant
import com.shuham.medilog.ui.theme.TextPrimary
import com.shuham.medilog.ui.theme.TextSecondary
import com.shuham.medilog.ui.theme.TextTertiary
import medilog.composeapp.generated.resources.Res
import medilog.composeapp.generated.resources.arrow_back_ios_24px
import medilog.composeapp.generated.resources.dark_mode_24px
import medilog.composeapp.generated.resources.edit_24px
import medilog.composeapp.generated.resources.info_24px
import medilog.composeapp.generated.resources.keyboard_arrow_right_24px
import medilog.composeapp.generated.resources.lock_24px
import medilog.composeapp.generated.resources.logout_24px
import medilog.composeapp.generated.resources.sync_24px
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

/**
 * Settings Screen
 * Matches Design: Clean White UI, Grouped Settings, Profile Badge
 */
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onLogout: () -> Unit,
    preferencesRepository: PreferencesRepository = koinInject()
) {
    val userName by preferencesRepository.getUserName()
        .collectAsState(initial = "Dr. Smith")

    // Mock Data
    val userRole = "Chief Medical Officer"
    var isDarkMode by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceVariant) // Light Grey background for the screen
            .statusBarsPadding()
    ) {
        // 1. Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable { onBack() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.arrow_back_ios_24px),
                    contentDescription = "back",
                    tint = TextSecondary,
                    modifier = Modifier.size(24.dp)
                )
            }



            Text(
                text = "Settings",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = TextPrimary
            )

            // Spacer to balance the centered title
            Spacer(modifier = Modifier.width(60.dp))

        }

        // Scrollable Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 2. Profile Section with Edit Badge
            Box(contentAlignment = Alignment.BottomEnd) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .shadow(8.dp, CircleShape)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(4.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    // Placeholder for Image
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Secondary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = userName?.firstOrNull()?.toString() ?: "D",
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold,
                            color = Primary
                        )
                    }
                }

                // Edit Icon Badge
                Box(
                    modifier = Modifier
                        .offset(x = 4.dp, y = 4.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Primary)
                        .border(2.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.edit_24px),
                        contentDescription = "Edit Profile",
                        tint = OnPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = userName ?: "Dr. Smith",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = TextPrimary
            )

            Text(
                text = userRole,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 3. Settings Group 1 (General)
            SettingsGroup {
                SettingsItem(
                    icon = Res.drawable.dark_mode_24px,
                    title = "App Theme",
                    trailing = {
                        Switch(
                            checked = isDarkMode,
                            onCheckedChange = { isDarkMode = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = OnPrimary,
                                checkedTrackColor = Primary,
                                uncheckedThumbColor = OnPrimary,
                                uncheckedTrackColor = TextTertiary.copy(alpha = 0.3f),
                                uncheckedBorderColor = Color.Transparent
                            )
                        )
                    }
                )

                Divider()

                SettingsItem(
                    icon = Res.drawable.sync_24px,
                    title = "Sync Status",
                    subtitle = "Last synced: 2m ago",
                    trailing = {
                        Text(
                            text = "Sync Now",
                            color = Primary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            modifier = Modifier.clickable { /* Sync Logic */ }
                        )
                    }
                )

                Divider()

                SettingsItem(
                    icon = Res.drawable.info_24px,
                    title = "About App",
                    trailing = { ChevronIcon() },
                    onClick = { /* Navigate */ }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 4. Settings Group 2 (Security)
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "SECURITY & DATA",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }

            SettingsGroup {
                SettingsItem(
                    icon = Res.drawable.lock_24px,
                    title = "HIPAA Compliance",
                    trailing = { ChevronIcon() },
                    onClick = { /* Navigate */ }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 5. Logout Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(ErrorBackground)
                    .clickable { onLogout() },
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(Res.drawable.logout_24px),
                        contentDescription = "Logout",
                        tint = Error,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Logout",
                        color = Error,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Footer
            Text(
                text = "MediLog Pro Version 2.4.1 (Build 890)",
                color = TextSecondary.copy(alpha = 0.6f),
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

/**
 * Reusable Settings Group Container (White Card)
 */
@Composable
fun SettingsGroup(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(24.dp),
                spotColor = Color(0x10000000)
            )
            .clip(RoundedCornerShape(24.dp))
            .background(Surface),
        content = content
    )
}

/**
 * Individual Settings Row
 */
@Composable
fun SettingsItem(
    icon: DrawableResource,
    title: String,
    subtitle: String? = null,
    trailing: @Composable () -> Unit,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon Circle
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Secondary), // Light Teal Background
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = Primary, // Dark Teal Icon
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Text
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = TextPrimary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
            if (subtitle != null) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    color = TextSecondary,
                    fontSize = 13.sp
                )
            }
        }

        // Trailing Content (Switch, Arrow, etc.)
        trailing()
    }
}

@Composable
fun Divider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(SurfaceVariant) // Very subtle divider
    )
}

@Composable
fun ChevronIcon() {
    Icon(
        painter = painterResource(Res.drawable.keyboard_arrow_right_24px),
        contentDescription = null,
        tint = TextTertiary,
        modifier = Modifier.size(20.dp)
    )
}