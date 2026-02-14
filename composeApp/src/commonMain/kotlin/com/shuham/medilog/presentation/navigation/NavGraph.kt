package com.shuham.medilog.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.shuham.medilog.data.repository.PreferencesRepository
import com.shuham.medilog.presentation.auth.LoginScreen
import com.shuham.medilog.presentation.dashboard.DashboardScreen
import com.shuham.medilog.presentation.onboarding.OnboardingScreen
import com.shuham.medilog.presentation.patients.AddPatientScreen
import com.shuham.medilog.presentation.patients.PatientDetailScreen
import com.shuham.medilog.presentation.patients.PatientListScreen
import com.shuham.medilog.presentation.settings.SettingsScreen
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

/**
 * Main Navigation Graph
 * Handles all screen navigation in the app
 */
@Composable
fun MediLogNavHost(
    navController: NavHostController = rememberNavController(),
    preferencesRepository: PreferencesRepository = koinInject()
) {
    val coroutineScope = rememberCoroutineScope()
    
    // Check onboarding and login state
    val isOnboardingCompleted by preferencesRepository.isOnboardingCompleted()
        .collectAsState(initial = false)
    
    val isLoggedIn by preferencesRepository.isLoggedIn()
        .collectAsState(initial = false)
    
    // Determine start destination
    val startDestination = when {
        !isOnboardingCompleted -> NavRoute.Onboarding
        !isLoggedIn -> NavRoute.Login
        else -> NavRoute.Dashboard
    }
    
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Onboarding Screen
        composable<NavRoute.Onboarding> {
            OnboardingScreen(
                onGetStarted = {
                    // Mark onboarding as completed and navigate to login
                    coroutineScope.launch {
                        preferencesRepository.setOnboardingCompleted(true)
                    }
                    navController.navigate(NavRoute.Login) {
                        popUpTo(NavRoute.Onboarding) { inclusive = true }
                    }
                },
                onSkip = {
                    // Mark onboarding as completed and navigate to login
                    coroutineScope.launch {
                        preferencesRepository.setOnboardingCompleted(true)
                    }
                    navController.navigate(NavRoute.Login) {
                        popUpTo(NavRoute.Onboarding) { inclusive = true }
                    }
                }
            )
        }
        
        // Login Screen
        composable<NavRoute.Login> {
            LoginScreen(
                onLoginSuccess = {
                    // Save user session
                    coroutineScope.launch {
                        preferencesRepository.saveUserSession(
                            userId = "demo_user",
                            name = "Dr. Demo",
                            email = "demo@medilog.com",
                        )
                    }
                    navController.navigate(NavRoute.Dashboard) {
                        popUpTo(NavRoute.Login) { inclusive = true }
                    }
                }
            )
        }
        
        // Dashboard Screen
        composable<NavRoute.Dashboard> {
            DashboardScreen(
                onNavigateToPatients = {
                    navController.navigate(NavRoute.PatientList)
                },
                onNavigateToSettings = {
                    navController.navigate(NavRoute.Settings)
                },
                onAddPatient = {
                    navController.navigate(NavRoute.AddPatient)
                }
            )
        }
        
        // Patient List Screen
        composable<NavRoute.PatientList> {
            PatientListScreen(
                onBack = { navController.popBackStack() },
                onAddPatient = { navController.navigate(NavRoute.AddPatient) },
                onPatientClick = { patientId ->
                    navController.navigate(NavRoute.PatientDetail(patientId))
                }
            )
        }
        
        // Add Patient Screen
        composable<NavRoute.AddPatient> {
            AddPatientScreen(
                onBack = { navController.popBackStack() },
                onSaved = { navController.popBackStack() }
            )
        }
        
        // Patient Detail Screen
        composable<NavRoute.PatientDetail> { backStackEntry ->
            val route = backStackEntry.toRoute<NavRoute.PatientDetail>()
            PatientDetailScreen(
                patientId = route.patientId,
                onBack = { navController.popBackStack() }
            )
        }
        
        // Settings Screen
        composable<NavRoute.Settings> {
            SettingsScreen(
                onBack = { navController.popBackStack() },
                onLogout = {
                    coroutineScope.launch {
                        preferencesRepository.clearUserSession()
                    }
                    navController.navigate(NavRoute.Login) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}
