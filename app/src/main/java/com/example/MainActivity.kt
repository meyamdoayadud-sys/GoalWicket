package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.SportsScore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Match
import com.example.service.AdMobManager
import com.example.ui.components.ApkDownloadDialog
import com.example.ui.components.LineupModal
import com.example.ui.components.LiveGameTvModal
import com.example.ui.components.PredictionModal
import com.example.ui.screens.ChatAssistantScreen
import com.example.ui.screens.FixturesScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MatchDetailScreen
import com.example.ui.theme.*
import com.example.viewmodel.SportsViewModel

enum class NavigationTab(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    LIVE_SCORES("Scores", Icons.Default.SportsScore),
    FIXTURES("Fixtures", Icons.Default.CalendarMonth),
    GEMINI_AI("Gemini AI", Icons.Default.AutoAwesome)
}

class MainActivity : ComponentActivity() {

    private val sportsViewModel: SportsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize AdMob
        AdMobManager.initialize(this)

        setContent {
            MyApplicationTheme {
                MainContent(viewModel = sportsViewModel)
            }
        }
    }
}

@Composable
fun MainContent(viewModel: SportsViewModel) {
    var currentTab by remember { mutableStateOf(NavigationTab.LIVE_SCORES) }
    var lineupMatch by remember { mutableStateOf<Match?>(null) }
    var predictionMatch by remember { mutableStateOf<Match?>(null) }
    var liveTvMatch by remember { mutableStateOf<Match?>(null) }
    var activeDetailMatch by remember { mutableStateOf<Match?>(null) }
    var initialChatPrompt by remember { mutableStateOf<String?>(null) }

    val showApkDialog by viewModel.showApkDialog.collectAsState()

    // Handle back button when on detail match screen
    BackHandler(enabled = activeDetailMatch != null) {
        activeDetailMatch = null
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = SportsDarkBg,
        bottomBar = {
            if (activeDetailMatch == null) {
                NavigationBar(
                    containerColor = SportsSurface,
                    contentColor = SportsTextPrimary,
                    tonalElevation = 8.dp
                ) {
                    NavigationTab.entries.forEach { tab ->
                        val isSelected = currentTab == tab
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { currentTab = tab },
                            modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}"),
                            icon = {
                                Icon(
                                    imageVector = tab.icon,
                                    contentDescription = tab.label,
                                    tint = if (isSelected) SportsPitchGreen else SportsTextSecondary
                                )
                            },
                            label = {
                                Text(
                                    text = tab.label,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) SportsPitchGreen else SportsTextSecondary
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = SportsPitchGreen.copy(alpha = 0.15f)
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (activeDetailMatch != null) {
                MatchDetailScreen(
                    match = activeDetailMatch!!,
                    viewModel = viewModel,
                    onBack = { activeDetailMatch = null },
                    onOpenAiChatWithPrompt = { prompt ->
                        activeDetailMatch = null
                        currentTab = NavigationTab.GEMINI_AI
                        viewModel.sendChatMessage(prompt)
                    }
                )
            } else {
                when (currentTab) {
                    NavigationTab.LIVE_SCORES -> {
                        HomeScreen(
                            viewModel = viewModel,
                            onMatchClick = { match -> activeDetailMatch = match },
                            onLineupClick = { match -> lineupMatch = match },
                            onPredictionClick = { match -> predictionMatch = match },
                            onLiveTvClick = { match -> liveTvMatch = match },
                            onOpenAiChat = { currentTab = NavigationTab.GEMINI_AI }
                        )
                    }
                    NavigationTab.FIXTURES -> {
                        FixturesScreen(
                            viewModel = viewModel,
                            onMatchClick = { match -> activeDetailMatch = match },
                            onLineupClick = { match -> lineupMatch = match },
                            onPredictionClick = { match -> predictionMatch = match },
                            onLiveTvClick = { match -> liveTvMatch = match }
                        )
                    }
                    NavigationTab.GEMINI_AI -> {
                        ChatAssistantScreen(
                            viewModel = viewModel,
                            initialPrompt = initialChatPrompt
                        )
                    }
                }
            }

            // Lineup BottomSheet
            lineupMatch?.let { match ->
                LineupModal(
                    match = match,
                    onDismiss = { lineupMatch = null },
                    onAskAi = { prompt ->
                        lineupMatch = null
                        currentTab = NavigationTab.GEMINI_AI
                        viewModel.sendChatMessage(prompt)
                    }
                )
            }

            // Prediction BottomSheet
            predictionMatch?.let { match ->
                PredictionModal(
                    match = match,
                    onDismiss = { predictionMatch = null },
                    onOpenChat = { prompt ->
                        predictionMatch = null
                        currentTab = NavigationTab.GEMINI_AI
                        viewModel.sendChatMessage(prompt)
                    },
                    onLiveTvClick = {
                        val m = predictionMatch
                        predictionMatch = null
                        liveTvMatch = m
                    }
                )
            }

            // Live Game TV & Video Streams BottomSheet
            liveTvMatch?.let { match ->
                LiveGameTvModal(
                    match = match,
                    onDismiss = { liveTvMatch = null },
                    onOpenAiChat = { prompt ->
                        liveTvMatch = null
                        currentTab = NavigationTab.GEMINI_AI
                        viewModel.sendChatMessage(prompt)
                    }
                )
            }

            // APK Download Dialog
            if (showApkDialog) {
                ApkDownloadDialog(
                    onDismiss = { viewModel.toggleApkDialog(false) },
                    onDownloadApk = { ctx ->
                        viewModel.downloadApk(ctx)
                        viewModel.toggleApkDialog(false)
                    },
                    onShareApp = { ctx ->
                        viewModel.shareApp(ctx)
                        viewModel.toggleApkDialog(false)
                    }
                )
            }
        }
    }
}
