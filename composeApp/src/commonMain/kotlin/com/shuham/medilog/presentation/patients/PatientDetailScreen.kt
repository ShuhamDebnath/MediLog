package com.shuham.medilog.presentation.patients

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shuham.medilog.ui.theme.OnPrimary
import com.shuham.medilog.ui.theme.Primary
import com.shuham.medilog.ui.theme.Success
import com.shuham.medilog.ui.theme.TextPrimary
import com.shuham.medilog.ui.theme.TextSecondary
import medilog.composeapp.generated.resources.Res
import medilog.composeapp.generated.resources.add_24px
import medilog.composeapp.generated.resources.arrow_back_ios_24px
import medilog.composeapp.generated.resources.arrow_forward_ios_24px
import medilog.composeapp.generated.resources.biotech_24px
import medilog.composeapp.generated.resources.calendar_month_24px
import medilog.composeapp.generated.resources.cloud_done_24px
import medilog.composeapp.generated.resources.edit_24px
import medilog.composeapp.generated.resources.lightbulb_24px
import medilog.composeapp.generated.resources.more_horiz_24px
import org.jetbrains.compose.resources.painterResource

/**
 * Patient History / Detail Screen
 * Matches Design: Timeline View, AI Cards, Tab Pills
 */
@Composable
fun PatientDetailScreen(
    patientId: String,
    onBack: () -> Unit
) {
    // Mock Data based on Screenshot
    val patientName = "Sarah Jenkins"
    val patientInfo = "34 Yrs • Female"
    val medicalId = "#ML-8829"
    val lastSynced = "2M AGO"

    val backgroundColor = Color(0xFFF8F9FA)

    Box(modifier = Modifier.fillMaxSize().background(backgroundColor)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // 1. Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Back Button
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .shadow(2.dp, CircleShape)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable { onBack() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.arrow_back_ios_24px),
                        contentDescription = "Back",
                        tint = Primary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Title
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Patient History",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = TextPrimary
                    )
                    Text(
                        text = "MEDICAL ID: $medicalId",
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        color = Primary,
                        letterSpacing = 0.5.sp
                    )
                }

                // More Button
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .shadow(2.dp, CircleShape)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable { /* More options */ },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.more_horiz_24px),
                        contentDescription = "More",
                        tint = TextSecondary
                    )
                }
            }

            // Scrollable Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                // 2. Patient Profile Card
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(3.dp, Color.White, CircleShape)
                            .shadow(4.dp, CircleShape)
                    ) {
                        // Placeholder Avatar
                        Box(
                            modifier = Modifier.fillMaxSize().background(Color(0xFFE0F2F1)),
                            contentAlignment = Alignment.Center
                        ) {
                            // Image would go here
                            Text(
                                "SJ",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = patientName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = TextPrimary
                    )
                    Text(
                        text = patientInfo,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = TextSecondary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Offline Status Pill
                    Box(
                        modifier = Modifier
                            .background(Primary.copy(alpha = 0.1f), RoundedCornerShape(20.dp))
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(Res.drawable.cloud_done_24px),
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "OFFLINE READY • LAST SYNCED $lastSynced",
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                color = Primary,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 3. Tabs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TabPill("All Activity", isSelected = true)
                    TabPill("AI Scans", isSelected = false)
                    TabPill("Clinical Notes", isSelected = false)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // NEW: Add Today Report Button
                Button(
                    onClick = { /* TODO: Add Report Action */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary,
                        contentColor = OnPrimary
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.add_24px),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Add Today's Report",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // 4. Timeline Section
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    // Vertical Dotted Line (Background)
                    TimelineLine(
                        modifier = Modifier
                            .padding(start = 24.dp) // Center of the timeline circles
                            .fillMaxHeight()
                            .width(2.dp)
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // -- TODAY --
                        TimelineHeader("TOD", "TODAY, OCT 24")

                        // AI Scan Card
                        TimelineItem {
                            AIScanCard()
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // -- YESTERDAY --
                        TimelineHeader(
                            "YST",
                            "YESTERDAY, OCT 23",
                            color = Color(0xFFE5E7EB),
                            textColor = TextSecondary
                        )

                        // Note Card
                        TimelineItem {
                            ConsultationNoteCard()
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Blood Work Card
                        TimelineItem {
                            BloodWorkCard()
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // -- PAST --
                        TimelineHeader(
                            "OCT",
                            "OCT 15, 2023",
                            color = Color(0xFFE5E7EB),
                            textColor = TextSecondary
                        )

                        TimelineItem {
                            IntakeAssessmentCard()
                        }

                        Spacer(modifier = Modifier.height(80.dp)) // Space for scrolling
                    }
                }
            }
        }
    }
}

/**
 * Timeline Components
 */
@Composable
fun TimelineHeader(
    badgeText: String,
    dateText: String,
    color: Color = Primary,
    textColor: Color = Primary
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        // Circle Badge
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(color, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = badgeText,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (color == Primary) OnPrimary else TextSecondary
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = dateText,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = if (color == Primary) Primary else TextSecondary.copy(alpha = 0.7f),
            letterSpacing = 1.sp
        )
    }
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
fun TimelineItem(content: @Composable () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
        Spacer(modifier = Modifier.width(48.dp)) // Space for Badge column

        Box(modifier = Modifier.padding(start = 16.dp).weight(1f)) {
            content()
        }
    }
}

@Composable
fun TimelineLine(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxHeight()) {
        drawLine(
            color = Color(0xFFE0E0E0),
            start = Offset(0f, 0f),
            end = Offset(0f, size.height),
            strokeWidth = 4f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f),
            cap = StrokeCap.Round
        )
    }
}

/**
 * Card Components
 */
@Composable
fun AIScanCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(20.dp), spotColor = Color(0x10000000))
            .background(Color.White, RoundedCornerShape(20.dp))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row {
                    Icon(
                        painter = painterResource(Res.drawable.lightbulb_24px), // Using Lightbulb as AI icon
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("AI Triage Scan", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("10:15 AM • Dr. Aris Thorne", fontSize = 11.sp, color = TextSecondary)
                    }
                }

                // Urgent Badge
                Box(
                    modifier = Modifier
                        .background(Primary, RoundedCornerShape(12.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        "URGENT",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                // Image Placeholder
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Black)
                ) {
                    // In real app: AsyncImage
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        "High confidence detection of acute inflammatory markers in upper dermis.",
                        fontSize = 12.sp,
                        color = TextSecondary,
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "AI CONFIDENCE",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text("94%", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Primary)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    LinearProgressIndicator(
                        progress = { 0.94f },
                        modifier = Modifier.fillMaxWidth().height(4.dp)
                            .clip(RoundedCornerShape(2.dp)),
                        color = Primary,
                        trackColor = Color(0xFFE0F2F1),
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(Color(0xFFF3F4F6), RoundedCornerShape(12.dp))
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "VIEW FULL ANALYSIS",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        painter = painterResource(Res.drawable.arrow_forward_ios_24px),
                        null,
                        tint = Primary,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ConsultationNoteCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(16.dp), spotColor = Color(0x08000000))
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column {
            Row {
                Box(
                    modifier = Modifier.size(32.dp).background(Color(0xFFFFF7ED), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.edit_24px),
                        null,
                        tint = Color(0xFFF97316),
                        modifier = Modifier.size(16.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Consultation Note", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("03:45 PM • Nurse Sarah Chen", fontSize = 11.sp, color = TextSecondary)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "\"Patient reports reduced discomfort after starting the new regimen. Site appears less erythematous. Recommended continuing current dosage.\"",
                fontSize = 13.sp,
                color = TextSecondary,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
fun BloodWorkCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(16.dp), spotColor = Color(0x08000000))
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(32.dp).background(Color(0xFFECFDF5), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.biotech_24px),
                    null,
                    tint = Success,
                    modifier = Modifier.size(16.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("Blood Work Panel", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("11:20 AM • Lab Corp", fontSize = 11.sp, color = TextSecondary)
            }

            Box(
                modifier = Modifier
                    .background(Success.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("NORMAL", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Success)
            }
        }
    }
}

@Composable
fun IntakeAssessmentCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(1.dp, RoundedCornerShape(16.dp), spotColor = Color(0x05000000))
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(32.dp).background(Color(0xFFF3F4F6), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.calendar_month_24px),
                    null,
                    tint = TextSecondary,
                    modifier = Modifier.size(16.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("Intake Assessment", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("09:00 AM • Admin", fontSize = 11.sp, color = TextSecondary)
            }

            Icon(
                painter = painterResource(Res.drawable.arrow_forward_ios_24px),
                null,
                tint = TextSecondary.copy(alpha = 0.5f),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun TabPill(text: String, isSelected: Boolean) {
    Box(
        modifier = Modifier
            .background(
                if (isSelected) Primary else Color.White,
                RoundedCornerShape(20.dp)
            )
            .border(
                1.dp,
                if (isSelected) Primary else Color(0xFFE5E7EB),
                RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) OnPrimary else TextSecondary
        )
    }
}