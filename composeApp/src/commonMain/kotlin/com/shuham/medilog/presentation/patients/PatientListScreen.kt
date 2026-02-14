package com.shuham.medilog.presentation.patients

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import com.shuham.medilog.ui.theme.Error
import com.shuham.medilog.ui.theme.MediLogTheme
import com.shuham.medilog.ui.theme.Primary
import com.shuham.medilog.ui.theme.Success
import com.shuham.medilog.ui.theme.TextPrimary
import com.shuham.medilog.ui.theme.TextSecondary
import com.shuham.medilog.ui.theme.Warning
import medilog.composeapp.generated.resources.Res
import medilog.composeapp.generated.resources.add_24px
import medilog.composeapp.generated.resources.arrow_back_ios_24px
import medilog.composeapp.generated.resources.cloud_done_24px
import medilog.composeapp.generated.resources.cloud_off_24px
import medilog.composeapp.generated.resources.search_24px
import org.jetbrains.compose.resources.painterResource

/**
 * Patient List Screen
 * Displays list of patients with search and filter functionality
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientListScreen(
    onBack: () -> Unit,
    onAddPatient: () -> Unit,
    onPatientClick: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf(FilterType.ALL) }

    // Mock patient data
    val allPatients = listOf(
        Patient(
            id = "1",
            name = "John Doe",
            age = 45,
            gender = "Male",
            contact = "+1 555-0101",
            notes = "Routine checkup. No prior conditions.",
            riskLevel = "Normal",
            isSynced = true
        ),
        Patient(
            id = "2",
            name = "Jane Smith",
            age = 32,
            gender = "Female",
            contact = "+1 555-0102",
            notes = "Complains of mild migraines.",
            riskLevel = "Review",
            isSynced = true
        ),
        Patient(
            id = "3",
            name = "Mike Johnson",
            age = 58,
            gender = "Male",
            contact = "+1 555-0103",
            notes = "High blood pressure readings. Monitoring required.",
            riskLevel = "High",
            isSynced = false
        ),
        Patient(
            id = "4",
            name = "Sarah Williams",
            age = 28,
            gender = "Female",
            contact = "+1 555-0104",
            notes = "Annual physical assessment.",
            riskLevel = "Normal",
            isSynced = true
        ),
        Patient(
            id = "5",
            name = "Robert Brown",
            age = 67,
            gender = "Male",
            contact = "+1 555-0105",
            notes = "Chronic arthritis pain. Medication renewed.",
            riskLevel = "High",
            isSynced = true
        ),
        Patient(
            id = "6",
            name = "Emily Davis",
            age = 41,
            gender = "Female",
            contact = "+1 555-0106",
            notes = "Follow up required for blood tests.",
            riskLevel = "Review",
            isSynced = false
        ),
        Patient(
            id = "7",
            name = "David Wilson",
            age = 52,
            gender = "Male",
            contact = "+1 555-0107",
            notes = "No complaints. Healthy.",
            riskLevel = "Normal",
            isSynced = true
        ),
        Patient(
            id = "8",
            name = "Lisa Anderson",
            age = 36,
            gender = "Female",
            contact = "+1 555-0108",
            notes = "Pre-natal checkup. Vitals stable.",
            riskLevel = "Normal",
            isSynced = true
        )
    )

    // Filter patients based on search and filter selection
    val filteredPatients = allPatients.filter { patient ->
        val matchesSearch = patient.name.contains(searchQuery, ignoreCase = true)
        val matchesFilter = when (selectedFilter) {
            FilterType.ALL -> true
            FilterType.HIGH_RISK -> patient.riskLevel == "High"
            FilterType.SYNCED -> patient.isSynced
            FilterType.PENDING -> !patient.isSynced
        }
        matchesSearch && matchesFilter
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Patients",
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(Res.drawable.arrow_back_ios_24px),
                            contentDescription = "Back",
                            tint = TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddPatient,
                containerColor = Primary,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.shadow(8.dp, CircleShape)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.add_24px),
                    contentDescription = "Add Patient",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            
            // Search Bar - White with shadow
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(12.dp))
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(
                            text = "Search patients...",
                            color = TextSecondary
                        )
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(Res.drawable.search_24px),
                            contentDescription = "Search",
                            tint = TextSecondary
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        cursorColor = Primary,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Filter Chips - White with shadow, selected shows color
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChipCustom(
                    selected = selectedFilter == FilterType.ALL,
                    onClick = { selectedFilter = FilterType.ALL },
                    label = "All",
                    selectedColor = Primary
                )

                FilterChipCustom(
                    selected = selectedFilter == FilterType.HIGH_RISK,
                    onClick = { selectedFilter = FilterType.HIGH_RISK },
                    label = "High Risk",
                    selectedColor = Error
                )

                FilterChipCustom(
                    selected = selectedFilter == FilterType.SYNCED,
                    onClick = { selectedFilter = FilterType.SYNCED },
                    label = "Synced",
                    selectedColor = Success
                )

                FilterChipCustom(
                    selected = selectedFilter == FilterType.PENDING,
                    onClick = { selectedFilter = FilterType.PENDING },
                    label = "Pending",
                    selectedColor = Warning
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Results count
            Text(
                text = "${filteredPatients.size} patients found",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Patient List - Fill remaining space
            if (filteredPatients.isEmpty()) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No patients found",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextSecondary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextButton(onClick = onAddPatient) {
                            Text(
                                text = "Add your first patient",
                                color = Primary
                            )
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredPatients) { patient ->
                        PatientCard(
                            patient = patient,
                            onClick = { onPatientClick(patient.id) }
                        )
                    }
                    
                    // Add bottom padding for FAB
                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
        }
    }
}

/**
 * Custom Filter Chip Component - White with shadow, selected shows color
 */
@Composable
fun FilterChipCustom(
    selected: Boolean,
    onClick: () -> Unit,
    label: String,
    selectedColor: Color
) {
    Box(
        modifier = Modifier
            .shadow(4.dp, RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .background(
                color = if (selected) selectedColor else Color.White,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = label,
            color = if (selected) Color.White else TextSecondary,
            fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal,
            fontSize = 14.sp
        )
    }
}

/**
 * Patient Card Component
 */
@Composable
fun PatientCard(
    patient: Patient,
    onClick: () -> Unit
) {
    val riskColor = when (patient.riskLevel) {
        "Normal" -> Success
        "Review" -> Warning
        "High" -> Error
        else -> Color.Gray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        color = Primary.copy(alpha = 0.1f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = patient.name.split(" ").map { it.first() }.take(2).joinToString(""),
                    color = Primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Patient Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = patient.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                Text(
                    text = "${patient.age} yrs • ${patient.gender}",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }

            // Sync Status Icon
            Icon(
                painter = if (patient.isSynced) painterResource(Res.drawable.cloud_done_24px) else painterResource(
                    Res.drawable.cloud_off_24px
                ),
                contentDescription = if (patient.isSynced) "Synced" else "Pending sync",
                tint = if (patient.isSynced) Success else Warning,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Risk Badge
            Box(
                modifier = Modifier
                    .background(
                        color = riskColor.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = patient.riskLevel,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Medium,
                    color = riskColor
                )
            }
        }
    }
}

/**
 * Filter Type Enum
 */
enum class FilterType {
    ALL,
    HIGH_RISK,
    SYNCED,
    PENDING
}

/**
 * Patient Data Class
 */
data class Patient(
    val id: String,
    val name: String,
    val age: Int,
    val gender: String,
    val contact: String ,
    val riskLevel: String,
    val isSynced: Boolean,
    val notes: String ,
)

// Preview
@Composable
fun PatientListScreenPreview() {
    MediLogTheme {
        Surface {
            PatientListScreen(
                onBack = {},
                onAddPatient = {},
                onPatientClick = {}
            )
        }
    }
}
