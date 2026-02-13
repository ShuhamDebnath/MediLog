UI/UX Design Specifications: MediLog
====================================

**Project:** MediLog (KMP Health-Tech App) **Design System:** Material Design 3 (Customized for iOS/Minimal Look) **Theme:** Ultra-Clean, White Backgrounds, Soft Shadows, Minimalist.

1\. Design System Foundation
----------------------------

### 1.1 Color Palette

| Color Token | Color Code | Usage |
|--------------|--------------|--------|
| **Primary** | `#008080` (Teal) | Active Buttons, Icons, Highlights |
| **On Primary** | `#FFFFFF` (White) | Text on Primary backgrounds |
| **Secondary** | `#E0F2F1` (Light Teal) | Button backgrounds (Inactive), Accents |
| **Background** | `#FFFFFF` (Pure White) | Main Screen Backgrounds |
| **Surface** | `#FFFFFF` (Pure White) | Cards, Sheets (Distinguished by Shadow) |
| **Text Primary** | `#111827` (Almost Black) | Headlines, Body Text |
| **Text Secondary** | `#6B7280` (Cool Grey) | Captions, Subtitles |
| **Error** | `#EF4444` (Soft Red) | High Risk, Errors |
| **Success** | `#10B981` (Emerald) | Normal Risk, Success |
| **Shadow** | `0px 4px 12px rgba(0,0,0,0.08)` | Cards, Floating Buttons |


### 1.2 Typography (Clean Sans-Serif)

-   **Font Family:** Inter or San Francisco (System Default).

-   **Headlines:** Bold, Tight tracking.

-   **Body:** Regular, comfortable line height (1.5).

### 1.3 UI Components Style

-   **Cards:** Pure white, Rounded Corners (16dp), Soft Drop Shadow, No borders.

-   **Buttons:** * *Primary:* Pill-shaped (Rounded 50%), Primary Color, Slight Shadow.

    -   *Secondary:* Circular Icon Buttons, White background, Drop Shadow.

-   **Inputs:** Light Grey background (`#F3F4F6`), Rounded Corners (12dp), No stroke unless focused.

2\. Screen Specifications & AI Prompts
--------------------------------------

### 2.1 Onboarding Screens (Carousel)

**Visuals:** Clean white page. Large centered illustration. Minimal text. **AI Prompt:**

> "Create a Jetpack Compose Onboarding screen. Use a HorizontalPager with 3 pages. Each page has a white background, a central vector illustration (use a placeholder Box), a bold headline (`fontSize = 24.sp`, `fontWeight = Bold`, color = `#111827`), and a grey subtitle centered below it. Place a 'Next' button at the bottom: a large pill-shaped button with color `#008080` and white text. Add a 'Skip' text button at the top right. Use the text: 'Offline-First', 'AI Triage', 'Secure Sync'."

### 2.2 Login / Auth Screen

**Visuals:** Minimalist centered card or clean vertical layout. **AI Prompt:**

> "Create a Login Screen in Compose. Use a pure white background. Place the logo (teal cross) at the top center. Below it, add a 'Welcome Back' headline. Create two input fields: 'Email' and 'Password'. Style the inputs with a light grey fill (`#F3F4F6`), rounded corners (12.dp), and no border. Add a 'Login' button: wide, height 56.dp, rounded corners 50%, teal background, shadow `elevation = 4.dp`. Add a 'Forgot Password?' text button below. ensure adequate spacing (24.dp) between elements."

### 2.3 Dashboard (Home)

**Visuals:** White background. Profile top right. 2x2 Grid of stats with shadows. **AI Prompt:**

> "Create a Dashboard Screen. Top Bar: 'MediLog' title (Bold, 22.sp) on left, circular profile image on right. Body: A vertical ScrollColumn. First, a 'Welcome, Dr. Smith' text. Next, a Staggered Grid of 3 Summary Cards. Style the cards: White background, RoundedCorner(16.dp), `shadow(elevation = 8.dp, spotColor = Color(0x10000000))`. Card 1: 'Total Patients' (Large number). Card 2: 'Pending Sync' (Orange icon). Card 3: 'High Risk' (Red icon). Below the grid, add a 'Recent Activity' section title and a list of 3 mock patient rows. Add a Floating Action Button (FAB): Circular, Teal, White 'Add' icon, with elevation."

### 2.4 Patient List Screen

**Visuals:** Search bar at top. List of cards. **AI Prompt:**

> "Create a Patient List Screen. Top: A search bar styled as a grey rounded box (`#F3F4F6`) with a search icon, no shadow. Below it, a Row of Filter Chips: 'All', 'High Risk', 'Synced'. Style chips: rounded, light teal background for selected state. Main Content: A LazyColumn of Patient Cards. Each Card: White surface, `padding=8.dp`, `rounded=16.dp`, soft shadow. Inside card: Row layout. Left: Circular Avatar with initials (Teal background). Center: Column with Name (Bold) and 'Age - Gender'. Right: A status icon (Cloud or Check). Background of the screen must be `#FFFFFF`."

### 2.5 Add Patient Form

**Visuals:** Clean form, ample whitespace. **AI Prompt:**

> "Create an 'Add Patient' screen. Use a Column with `verticalArrangement = Arrangement.spacedBy(20.dp)`. Fields: Name, Age, Contact, Notes. Style all TextFields as 'OutlinedTextField' but with a custom borderless look: Light Grey fill (`#F9FAFB`), rounded corners (12.dp). For Gender, use a custom Segmented Control (Row of 3 clickable boxes: Male, Female, Other) where the selected one is Teal and others are Grey. Bottom: A 'Save Patient' button pinned to the bottom of the screen with a shadow."

### 2.6 Patient Detail & AI Scan

**Visuals:** Tab layout (Overview / Scan). Large camera area. **AI Prompt:**

> "Create a Patient Detail screen. Top: Patient Name and ID. Below: A TabRow (Teal text, indicator) with 'History' and 'AI Scan'. In the 'AI Scan' tab: A large central box (aspect ratio 4:3) with a dashed grey border and a 'Camera' icon in the center (Placeholder for camera preview). Below it, two circular buttons with shadows: 'Capture' (Large, Teal) and 'Gallery' (Small, White). Below that, a 'Result Card' that is hidden by default. When visible, it should be a Card with a colored background (Light Red for risk) and a Row containing an Alert Icon and 'High Risk detected' text."

### 2.7 Settings / Profile Screen

**Visuals:** List layout with clear actions. **AI Prompt:**

> "Create a Settings/Profile screen. Top: Large centered circular profile avatar with 'Dr. Name' below it. Body: A Column of settings options. Item 1: 'App Theme' (Row with Text and a Switch). Item 2: 'Sync Status' (Row with Text and a 'Sync Now' text button). Item 3: 'About App' (Row with Text and chevron icon). Bottom: A 'Logout' button styled in Light Red (`#FEE2E2`) with Red text."

### 2.8 Global Feedback Elements

**Visuals:** Non-intrusive notifications. **AI Prompt:**

> "Create a custom Snackbar host. The Snackbar should be floating, rounded corners (8.dp), dark grey background (`#374151`) with white text. For errors (e.g., 'No Internet'), use a Light Red background with Red text. Create a Loading Dialog: A simple white Card with a CircularProgressIndicator and 'Please wait...' text."

3\. Assets Required (Checklist)
-------------------------------

### 3.1 XML / SVG Vectors

Ensure you have these in your `commonMain/resources/drawable`:

-   `ic_logo.xml`

-   `ic_doctor_illustration.xml`

-   `ic_scan_illustration.xml`

-   `ic_empty_state.xml`

### 3.2 String Resources

-   `app_name`: "MediLog"

-   `btn_login`: "Login"

-   `lbl_patients`: "Patients"