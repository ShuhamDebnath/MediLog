package com.shuham.medilog.presentation.navigation

import kotlinx.serialization.Serializable

/**
 * Navigation Routes for MediLog
 * Using Kotlinx Serialization for type-safe navigation
 */
sealed interface NavRoute {
    
    /**
     * Onboarding - First time user introduction
     */
    @Serializable
    data object Onboarding : NavRoute
    
    /**
     * Login - Authentication screen
     */
    @Serializable
    data object Login : NavRoute
    
    /**
     * Dashboard - Main home screen
     */
    @Serializable
    data object Dashboard : NavRoute
    
    /**
     * Patient List - List of all patients
     */
    @Serializable
    data object PatientList : NavRoute
    
    /**
     * Add Patient - Form to add new patient
     */
    @Serializable
    data object AddPatient : NavRoute
    
    /**
     * Patient Detail - View/Edit patient details
     */
    @Serializable
    data class PatientDetail(val patientId: String) : NavRoute
    
    /**
     * Edit Patient - Form to edit existing patient
     */
    @Serializable
    data class EditPatient(val patientId: String) : NavRoute
    
    /**
     * Scan - AI Scan screen for a patient
     */
    @Serializable
    data class Scan(val patientId: String) : NavRoute
    
    /**
     * Settings - App settings and profile
     */
    @Serializable
    data object Settings : NavRoute
}
