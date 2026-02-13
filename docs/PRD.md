Product Requirements Document (PRD)
===================================

**Project Name:** MediLog - Frontline Health Assistant

**Version:** 1.0.0

**Status:** Approved for Development

**Author:** Lead Architect

1\. Executive Summary
---------------------

Innshipala is a scalable, offline-first mobile application designed for frontline healthcare workers. It streamlines patient onboarding, enables digital record-keeping, and utilizes simulated AI for preliminary health assessments. The goal is to reduce manual paperwork and provide instant, color-coded health risk analysis.

2\. User Persona
----------------

**Role:** Frontline Health Worker (FHW)

-   **Environment:** Low-connectivity areas, high-paced clinics.

-   **Needs:** Fast data entry, offline reliability, clear visual cues (Red/Green indicators).

-   **Pain Points:** Lost paper records, inability to assess risk without specialized tools.

3\. Functional Requirements
---------------------------

### 3.1 Authentication Module

-   **FR-01:** User must log in using Email/Password.

-   **FR-02:** System must validate email format and password strength (min 6 chars).

-   **FR-03:** "Guest Mode" or Mock Login allowed for evaluation purposes.

### 3.2 Dashboard

-   **FR-04:** Display "Summary Cards": Total Patients, Critical Cases, Recent Uploads.

-   **FR-05:** Floating Action Button (FAB) for quick "Add Patient" access.

### 3.3 Patient Management (CRUD)

-   **FR-06:** Create: Form to capture Name, Age, Gender, Contact, Notes.

-   **FR-07:** Read: List view of all patients with search/filter by Name or Risk Level.

-   **FR-08:** Update: Ability to edit patient details.

-   **FR-09:** Delete: Ability to remove patient records (Soft delete preferred).

### 3.4 Image & AI Simulation

-   **FR-10:** Capture image via Camera or select from Gallery.

-   **FR-11:** Image compression to <200KB before storage.

-   **FR-12:** **AI Simulation:** On upload, system generates a mock JSON response:

    -   *Normal* (Green)

    -   *Needs Review* (Orange)

    -   *High Risk* (Red)

-   **FR-13:** Display "Explainability" text (dummy data) justifying the result.

### 3.5 Local Persistence (Offline First)

-   **FR-14:** All data (Patients + Reports) must be stored locally.

-   **FR-15:** App must function 100% without internet connection.

### 3.6 Settings

-   **FR-16:** Toggle Light/Dark Theme.

-   **FR-17:** View User Profile details.

4\. Non-Functional Requirements (NFRs)
--------------------------------------

-   **NFR-01 Scalability:** Architecture must support future backend integration (Firebase/REST).

-   **NFR-02 Performance:** App launch time < 2 seconds. List scrolling must be 60fps.

-   **NFR-03 UI/UX:** Adherence to Material Design 3 guidelines. Accessibility support (text scaling).

5\. Success Metrics
-------------------

-   Successful creation and retrieval of a patient record in offline mode.

-   Successful simulation of AI result with color-coded UI response.

-   Codebase passes strict linting rules.