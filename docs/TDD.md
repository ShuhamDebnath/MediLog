Technical Design Document (TDD) - KMP Edition
=============================================

**Project:** MediLog (Placeholder Name) **Architecture Pattern:** Clean Architecture (MVI/MVVM) with Repository Pattern **Tech Stack:** Kotlin Multiplatform (KMP) + Compose Multiplatform **Target:** Android (APK) & iOS (Optional/Scalable)

1\. Architectural Overview
--------------------------

The project is structured to maximize code sharing (~90%) while retaining native performance.

### Layers:

1.  **UI Layer (`composeApp`)**: Jetpack Compose (Material 3).

2.  **Presentation Layer (`shared`)**: ViewModels (using `androidx.lifecycle` KMP).

3.  **Domain Layer (`shared`)**: Use Cases, Entities (Pure Kotlin).

4.  **Data Layer (`shared`)**: Repositories, Room DB, Firebase SDK.

2\. Tech Stack Decisions
------------------------
| Component | Technology | Justification |
| --- | --- | --- |
| **Language** | Kotlin | Strict typing, coroutines, flow |
| **UI Framework** | Compose Multiplatform | Share UI code between Android and future iOS |
| **DI** | Koin | The standard for KMP dependency injection. Lightweight & easy setup |
| **Navigation** | Jetpack Navigation | Official `androidx.navigation:navigation-compose`. Standard, type-safe, and familiar to Android devs |
| **Local DB** | Room KMP | Room now supports KMP (SQLite). Familiar to Android devs, strong compile-time checks |
| **Remote DB** | Firebase Firestore | Real-time updates, scalable |
| **Auth** | Firebase Auth | Standard email/password flow |
| **Image Loading** | Coil 3.0 | Now supports KMP |
| **Concurrency** | Coroutines & Flow | Standard asynchronous handling |

---

3\. Data Sync Strategy (Offline-First)
--------------------------------------

**The "Repository" is the Brain.** The UI never talks to Firebase directly. It talks to the Repository.

1.  **Read Strategy:** `Repository` exposes a `Flow<List<Patient>>`.

    -   It emits data from **Room** (Local) immediately.

    -   It triggers a background fetch from **Firebase** (Remote).

    -   If Remote data differs, it updates Room, which automatically updates the Flow.

    -   *Result:* The app works 100% offline.

2.  **Write Strategy:**

    -   User saves patient -> Repository saves to **Room** (marks as `synced = false`).

    -   Repository attempts to push to **Firebase**.

    -   If success: Update Room entry to `synced = true`.

    -   If fail (no internet): Leave as `false`. A `WorkManager` (Android) or periodic background check syncs pending items later.

4\. Database Schema
-------------------

### 4.1 Firebase (Firestore) Structure

-   `users/{userId}`: User profile.

-   `users/{userId}/patients/{patientId}`: Patient documents.

    -   *Note:* Using sub-collections ensures security (User A cannot see User B's patients).

### 4.2 Local Room Database (Entities)

**PatientEntity.kt**

```
@Entity(tableName = "patients")
data class PatientEntity(
    @PrimaryKey val id: String,
    val name: String,
    val age: Int,
    val gender: String, // "MALE", "FEMALE"
    val contact: String,
    val notes: String?,
    val riskLevel: String, // "NORMAL", "REVIEW", "HIGH"
    val isSynced: Boolean = false,
    val lastUpdated: Long
)

```

**RecordEntity.kt**

```
@Entity(tableName = "records")
data class RecordEntity(
    @PrimaryKey val id: String,
    val patientId: String, // Foreign Key
    val localImagePath: String,
    val remoteImageUrl: String?,
    val aiResultJson: String,
    val timestamp: Long
)

```

5\. AI Simulation Logic (Domain Layer)
--------------------------------------

Since we don't have a real AI backend, this logic lives in the `shared` module.

**MockAIService.kt**

```
class MockAIService {
    suspend fun analyzeImage(imageBytes: ByteArray): AIResult {
        delay(2000) // Simulate network latency

        // Randomly determine outcome for demo
        val risk = listOf(Risk.NORMAL, Risk.REVIEW, Risk.HIGH).random()
        return AIResult(
            riskLevel = risk,
            confidence = Random.nextDouble(0.7, 0.99),
            explanation = "Automated analysis detected patterns consistent with ${risk.name}."
        )
    }
}

```

6\. Project Structure (File Tree)
---------------------------------

```
Innshipala/
├── composeApp/
│   ├── src/
│   │   ├── androidMain/        # Android specific setup (Manifest, MainActivity)
│   │   ├── iosMain/            # iOS Entry point
│   │   └── commonMain/         # SHARED UI (Screens, Composables)
│   │       ├── theme/
│   │       ├── navigation/     # NavHost, Routes, NavGraph
│   │       ├── screens/
│   │       │   ├── LoginScreen.kt
│   │       │   ├── DashboardScreen.kt
│   │       │   └── PatientDetailScreen.kt
│   │       └── App.kt          # Main entry point with NavHost
├── shared/
│   ├── src/
│   │   ├── commonMain/         # BUSINESS LOGIC
│   │   │   ├── di/             # Koin Modules
│   │   │   ├── domain/         # Models, UseCases
│   │   │   ├── data/           # Repositories, Room DB, Firebase
│   │   │   └── viewmodels/     # Shared ViewModels

```

7\. Implementation Roadmap
--------------------------

-   **Day 1:** Project Init (KMP Wizard), Koin Setup, Room Setup.

-   **Day 2:** Authentication (Firebase Auth) + Login UI.

-   **Day 3:** Dashboard + Patient List (Room Read operations).

-   **Day 4:** Add Patient (Room Write operations + Sync Logic).

-   **Day 5:** Image Picker (Platform specific) + AI Mock Logic.

-   **Day 6:** UI Polish (Material 3) + Search/Filter.

-   **Day 7:** Documentation, APK Build, Demo Video.