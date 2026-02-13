# MediLog - Intelligent Field Health Assistant 🏥

> **Status:** Active Development 🚧 **Target:** Android (Primary), iOS (Scalable)

## 📱 Project Overview

MediLog is an offline-first mobile application designed for frontline healthcare workers operating in low-connectivity environments. It enables rapid patient onboarding, secure record management, and AI-simulated health triage without requiring a constant internet connection.

Built using Kotlin Multiplatform (KMP), MediLog demonstrates how to share 90%+ of business logic (Data, Domain, ViewModels) between platforms while maintaining a 100% native UI performance using Jetpack Compose.

## ✨ Key Features

- ⚡️ Offline-First Architecture: Full CRUD capabilities without internet. Data syncs automatically via WorkManager when connectivity returns.
- 🧠 AI Triage Simulation: Mock AI analysis engine that processes patient images to determine risk levels (Normal, Review, High Risk).
- 🔐 Secure Authentication: Firebase Auth integration with role-based access.
- 📊 Smart Dashboard: Real-time analytics of patient risk distribution and sync status.
- 🎨 Material 3 Design: A clean, clinical UI focused on readability and speed (following Apple's Human Interface Guidelines principles).

## 🛠 Tech Stack

| Layer | Technology | 
|---|---|
| Language | Kotlin 2.0 (K2 Compiler) |
| Architecture | Clean Architecture (MVI/MVVM) |
| UI Framework | Compose Multiplatform (Material 3) |
| Navigation | Jetpack Navigation Compose |
| Dependency Injection | Koin |
| Local Database | Room KMP (SQLite) |
| Remote Backend | Firebase Firestore & Auth |
| Concurrency | Coroutines & Flow |
| Image Loading | Coil 3.0 |

## 🏗 Architecture

The project follows a strict Clean Architecture pattern to ensure separation of concerns and testability.

```mermaid
graph TD
    subgraph "UI Layer (composeApp)"
        UI[Compose Screens] --> VM[Shared ViewModels]
    end

    subgraph "Shared Module (Business Logic)"
        VM --> UC[Use Cases]
        UC --> Repo[Repository Interface]
        Repo --> Local[Room DB (Local)]
        Repo --> Remote[Firebase (Remote)]
    end
```

## Folder Structure
- `composeApp/`: Contains the UI code (Screens, Components, Theme) shared across platforms.
- `shared/`: Contains the Brain of the app.
- `domain/`: Entities & Use Cases (Pure Kotlin).
- `data/`: Repositories, Room Database, & API Client.
- `viewmodels/`: Shared State Management.

## 🚀 Getting Started
### Prerequisites
- Android Studio Ladybug (or newer).
- JDK 17+.
- Kotlin Multiplatform Mobile Plugin installed.

### Installation
1. Clone the repository:
   download and clone:
   d```bash
git clone [https://github.com/yourusername/MediLog.git](https://github.com/yourusername/MediLog.git)
dcd MediLog
defaults to your local directory.
d```
   e.g.,
   git clone https://github.com/yourusername/MediLog.git && cd MediLog  
   and then proceed with setup steps below.
   details omitted for brevity.
   download google-services.json from Firebase Console and place it in composeApp/google-services.json. Enable Authentication and Firestore in Firebase Console. Open the project in Android Studio, sync Gradle, select composeApp configuration, and run on an emulator or device.
   'the rest of setup instructions are straightforward.'
   defaults to your local directory.