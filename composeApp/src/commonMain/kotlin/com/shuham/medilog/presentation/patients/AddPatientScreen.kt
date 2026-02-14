package com.shuham.medilog.presentation.patients

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shuham.medilog.ui.theme.OnPrimary
import com.shuham.medilog.ui.theme.Primary
import com.shuham.medilog.ui.theme.Secondary
import com.shuham.medilog.ui.theme.TextPrimary
import com.shuham.medilog.ui.theme.TextSecondary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import medilog.composeapp.generated.resources.Res
import medilog.composeapp.generated.resources.add_24px
import medilog.composeapp.generated.resources.add_a_photo_24px
import medilog.composeapp.generated.resources.arrow_back_ios_24px
import medilog.composeapp.generated.resources.call_24px
import medilog.composeapp.generated.resources.check_circle_24px
import medilog.composeapp.generated.resources.lightbulb_24px
import org.jetbrains.compose.resources.painterResource

/**
 * Add Patient Screen
 * Matches Design: Clean White Form, Status Strip, Custom Gender Selector
 */
@Composable
fun AddPatientScreen(
    onBack: () -> Unit,
    onSaved: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf(Gender.MALE) }
    var contact by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    // Background color from screenshot (Light Grey/Greenish tint)
    val backgroundColor = Color(0xFFF0F4F4)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
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
                text = "Add New Patient",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = TextPrimary
            )

            // Spacer to balance the centered title
            Spacer(modifier = Modifier.width(60.dp))
        }

        // 2. Status Strip
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE0F2F1).copy(alpha = 0.5f)) // Very light teal
                .padding(vertical = 12.dp, horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(8.dp).background(Primary, CircleShape))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "READY FOR OFFLINE SYNC",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary,
                    letterSpacing = 0.5.sp
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(Res.drawable.lightbulb_24px),
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "AI Triage Active",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Primary
                )
            }
        }

        // 3. Scrollable Form Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
                .imePadding()
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // -- Full Name --
            FormLabel("FULL NAME")
            CleanTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = "e.g. Johnathan Doe"
            )

            Spacer(modifier = Modifier.height(20.dp))

            // -- Age & Gender Row --
            Row(modifier = Modifier.fillMaxWidth()) {
                // Age
                Column(modifier = Modifier.weight(0.35f)) {
                    FormLabel("AGE")
                    CleanTextField(
                        value = age,
                        onValueChange = { age = it },
                        placeholder = "00",
                        keyboardType = KeyboardType.Number,
                        centered = true
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Gender
                Column(modifier = Modifier.weight(0.65f)) {
                    FormLabel("GENDER")
                    // Custom Gender Pill Selector
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .shadow(2.dp, RoundedCornerShape(28.dp), spotColor = Color(0x10000000))
                            .background(Color.White, RoundedCornerShape(28.dp))
                            .padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Gender.values().forEach { g ->
                            val isSelected = gender == g
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .clip(RoundedCornerShape(24.dp))
                                    .background(if (isSelected) Primary else Color.Transparent)
                                    .clickable { gender = g },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = g.name.lowercase().replaceFirstChar { it.uppercase() },
                                    color = if (isSelected) OnPrimary else TextSecondary,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // -- Contact Number --
            FormLabel("CONTACT NUMBER")
            CleanTextField(
                value = contact,
                onValueChange = { contact = it },
                placeholder = "+1 (555) 000-0000",
                keyboardType = KeyboardType.Phone,
                leadingIcon = {
                    Icon(
                        painter = painterResource(Res.drawable.call_24px),
                        contentDescription = null,
                        tint = TextSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // -- Clinical Notes with AI Badge --
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FormLabel("CLINICAL NOTES", paddingBottom = 8.dp)

                // Badge
                Box(
                    modifier = Modifier
                        .background(Secondary.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "AI Analysis Ready",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Primary
                    )
                }
            }

            CleanTextField(
                value = notes,
                onValueChange = { notes = it },
                placeholder = "Enter patient symptoms, history, or urgent observations...",
                singleLine = false,
                height = 120.dp,
                alignment = Alignment.TopStart
            )

            Spacer(modifier = Modifier.height(24.dp))

            // -- Scan Documents Card --
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .shadow(4.dp, RoundedCornerShape(20.dp), spotColor = Color(0x10000000))
                    .background(Color.White, RoundedCornerShape(20.dp))
                    .clickable { /* Camera Logic */ }
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Camera Icon Circle
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(Secondary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.add_a_photo_24px),
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Scan Documents",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = TextPrimary
                        )
                        Text(
                            text = "Add medical reports or IDs",
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }

                    Icon(
                        painter = painterResource(Res.drawable.add_24px),
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // -- Save Button --
            Button(
                onClick = {
                    isLoading = true
                    coroutineScope.launch {
                        delay(1000)
                        isLoading = false
                        onSaved()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Text(
                    text = if (isLoading) "Saving..." else "Save Patient Profile",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(Res.drawable.check_circle_24px),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
            Spacer(modifier = Modifier.navigationBarsPadding())
        }
    }
}

/**
 * Clean Form Label
 */
@Composable
fun FormLabel(text: String, paddingBottom: Dp = 8.dp) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp,
        color = TextSecondary,
        letterSpacing = 0.5.sp,
        modifier = Modifier.padding(bottom = paddingBottom)
    )
}

/**
 * Custom Clean TextField without borders (White Surface)
 */
@Composable
fun CleanTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    height: Dp = 56.dp,
    centered: Boolean = false,
    alignment: Alignment = Alignment.CenterStart,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = Color(0x10000000)
            )
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp),
        contentAlignment = alignment
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (leadingIcon != null) {
                leadingIcon()
                Spacer(modifier = Modifier.width(12.dp))
            }

            Box(modifier = Modifier.weight(1f)) {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = TextSecondary.copy(alpha = 0.5f),
                        fontSize = 16.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = if (centered) androidx.compose.ui.text.style.TextAlign.Center else androidx.compose.ui.text.style.TextAlign.Start
                    )
                }

                // Using BasicTextField logic for cleaner look, wrapped in Box
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontSize = 16.sp,
                        color = TextPrimary,
                        fontWeight = FontWeight.Medium,
                        textAlign = if (centered) androidx.compose.ui.text.style.TextAlign.Center else androidx.compose.ui.text.style.TextAlign.Start
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                    singleLine = singleLine,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

enum class Gender {
    MALE, FEMALE, OTHER
}