package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.League
import com.example.model.Match
import com.example.model.MatchStatus
import com.example.model.SportType
import com.example.ui.components.AdMobBanner
import com.example.ui.components.MatchCard
import com.example.ui.theme.*
import com.example.viewmodel.SportsViewModel

@Composable
fun HomeScreen(
    viewModel: SportsViewModel,
    onMatchClick: (Match) -> Unit,
    onLineupClick: (Match) -> Unit,
    onPredictionClick: (Match) -> Unit,
    onLiveTvClick: (Match) -> Unit,
    onOpenAiChat: () -> Unit
) {
    val matches by viewModel.matches.collectAsState()
    val selectedSport by viewModel.selectedSport.collectAsState()
    val selectedLeague by viewModel.selectedLeague.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val lastUpdatedTimestamp by viewModel.lastUpdatedTimestamp.collectAsState()

    val context = LocalContext.current
    val isRealConnected by viewModel.isRealSportsApiConnected.collectAsState()
    val isRefreshingReal by viewModel.isRefreshingRealData.collectAsState()
    val realSyncStatus by viewModel.realDataSyncStatus.collectAsState()

    // 4-second auto update rotation animation
    val infiniteTransition = rememberInfiniteTransition(label = "syncRotation")
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    // Filter matches
    val filteredMatches = remember(matches, selectedSport, selectedLeague, searchQuery) {
        matches.filter { m ->
            val sportMatch = selectedSport == null || m.sport == selectedSport
            val leagueMatch = selectedLeague == null || m.league.id == selectedLeague?.id
            val searchMatch = searchQuery.isBlank() ||
                    m.homeTeam.contains(searchQuery, ignoreCase = true) ||
                    m.awayTeam.contains(searchQuery, ignoreCase = true) ||
                    m.league.name.contains(searchQuery, ignoreCase = true)
            sportMatch && leagueMatch && searchMatch
        }
    }

    val liveMatches = filteredMatches.filter { it.status == MatchStatus.LIVE }
    val upcomingMatches = filteredMatches.filter { it.status == MatchStatus.UPCOMING }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SportsDarkBg)
    ) {
        // Top App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SportsSurface)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, SportsPitchGreen, CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.goalwicket_logo_1790399892347),
                        contentDescription = "Logo",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "GoalWicket",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = SportsTextPrimary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "LIVE",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = SportsLiveRed
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Sync,
                            contentDescription = "Auto Refresh",
                            modifier = Modifier
                                .size(11.dp)
                                .rotate(rotationAngle),
                            tint = SportsPitchGreen
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "Auto-update 4s",
                            fontSize = 10.sp,
                            color = SportsPitchGreen,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Right Action Buttons: Real Data Sync, APK Download & AI Chat
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Real Live Sports Sync Button
                FilledTonalButton(
                    onClick = { viewModel.refreshRealWorldMatches(context) },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = SportsElectricCyan.copy(alpha = 0.15f),
                        contentColor = SportsElectricCyan
                    ),
                    contentPadding = PaddingValues(horizontal = 9.dp, vertical = 6.dp)
                ) {
                    if (isRefreshingReal) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(13.dp),
                            color = SportsElectricCyan,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Sync",
                            modifier = Modifier.size(14.dp),
                            tint = SportsElectricCyan
                        )
                    }
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "আসল খেলা",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                // APK Download Button
                FilledTonalButton(
                    onClick = { viewModel.toggleApkDialog(true) },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = SportsPitchGreen.copy(alpha = 0.15f),
                        contentColor = SportsPitchGreen
                    ),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("apk_download_header_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = "APK Download",
                        modifier = Modifier.size(14.dp),
                        tint = SportsPitchGreen
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "APK",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                // Gemini AI Assistant Quick Launch
                IconButton(
                    onClick = onOpenAiChat,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(SportsPitchGreen.copy(alpha = 0.15f))
                        .testTag("open_ai_chat_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "Gemini AI",
                        tint = SportsPitchGreen,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        // 100% Real Live Sports Data Status Banner
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0F172A))
                .border(0.5.dp, SportsPitchGreen.copy(alpha = 0.35f))
                .clickable { viewModel.refreshRealWorldMatches(context) }
                .padding(horizontal = 14.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(if (isRefreshingReal) Color(0xFFFFD600) else SportsPitchGreen)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (isRefreshingReal) "🔄 TheSportsDB থেকে আসল খেলার ডেটা লোড হচ্ছে..." else realSyncStatus,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isRefreshingReal) Color(0xFFFFD600) else SportsPitchGreen,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(
                text = "রিফ্রেশ করুন",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = SportsElectricCyan
            )
        }

        // Sport Filter Tabs (ALL / FOOTBALL / CRICKET)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = selectedSport == null,
                onClick = { viewModel.selectSport(null) },
                label = { Text("⚡ All Sports") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = SportsPitchGreen,
                    selectedLabelColor = Color.Black,
                    containerColor = SportsSurfaceVariant,
                    labelColor = SportsTextPrimary
                )
            )
            FilterChip(
                selected = selectedSport == SportType.FOOTBALL,
                onClick = { viewModel.selectSport(SportType.FOOTBALL) },
                label = { Text("⚽ Football") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = SportsPitchGreen,
                    selectedLabelColor = Color.Black,
                    containerColor = SportsSurfaceVariant,
                    labelColor = SportsTextPrimary
                )
            )
            FilterChip(
                selected = selectedSport == SportType.CRICKET,
                onClick = { viewModel.selectSport(SportType.CRICKET) },
                label = { Text("🏏 Cricket") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = SportsPitchGreen,
                    selectedLabelColor = Color.Black,
                    containerColor = SportsSurfaceVariant,
                    labelColor = SportsTextPrimary
                )
            )
        }

        // Horizontal League Chips Bar
        val relevantLeagues = remember(selectedSport) {
            when (selectedSport) {
                SportType.FOOTBALL -> League.ALL_FOOTBALL_LEAGUES
                SportType.CRICKET -> League.ALL_CRICKET_LEAGUES
                null -> League.ALL_LEAGUES
            }
        }

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            item {
                SuggestionChip(
                    onClick = { viewModel.selectLeague(null) },
                    label = { Text("All Leagues", fontSize = 11.sp) },
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = if (selectedLeague == null) SportsCardBorder else SportsSurface
                    ),
                    border = SuggestionChipDefaults.suggestionChipBorder(
                        borderColor = if (selectedLeague == null) SportsPitchGreen else SportsCardBorder,
                        enabled = true
                    )
                )
            }
            items(relevantLeagues) { league ->
                SuggestionChip(
                    onClick = { viewModel.selectLeague(league) },
                    label = { Text("${league.logoEmoji} ${league.name}", fontSize = 11.sp) },
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = if (selectedLeague?.id == league.id) SportsCardBorder else SportsSurface
                    ),
                    border = SuggestionChipDefaults.suggestionChipBorder(
                        borderColor = if (selectedLeague?.id == league.id) SportsElectricCyan else SportsCardBorder,
                        enabled = true
                    )
                )
            }
        }

        // Live Matches and Content
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // AdMob Banner Unit
            item {
                AdMobBanner()
            }

            // Featured Live Game TV & Video Streams Banner
            item {
                val topMatch = liveMatches.firstOrNull() ?: filteredMatches.firstOrNull()
                topMatch?.let { featured ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .clickable { onLiveTvClick(featured) },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF1E1B4B)
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, SportsLiveRed.copy(alpha = 0.6f))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(SportsLiveRed.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.LiveTv,
                                        contentDescription = "Live TV",
                                        tint = SportsLiveRed,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "📺 লাইভ গেম টিভি ও ভিডিও",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(3.dp))
                                                .background(SportsLiveRed)
                                                .padding(horizontal = 4.dp, vertical = 1.dp)
                                        ) {
                                            Text(
                                                text = "HD LIVE",
                                                fontSize = 8.sp,
                                                fontWeight = FontWeight.Black,
                                                color = Color.White
                                            )
                                        }
                                    }
                                    Text(
                                        text = "${featured.homeTeam} বনাম ${featured.awayTeam} • টিভি চ্যানেল ও ক্লিপস",
                                        fontSize = 11.sp,
                                        color = Color(0xFFC7D2FE),
                                        maxLines = 1,
                                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                                    )
                                }
                            }
                            FilledTonalButton(
                                onClick = { onLiveTvClick(featured) },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.filledTonalButtonColors(
                                    containerColor = SportsLiveRed,
                                    contentColor = Color.White
                                ),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "Watch",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // Live Score Section
            if (liveMatches.isNotEmpty()) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(SportsLiveRed)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "LIVE MATCHES (${liveMatches.size})",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = SportsTextPrimary
                            )
                        }
                        Text(
                            text = "Auto-updated",
                            fontSize = 11.sp,
                            color = SportsPitchGreen
                        )
                    }
                }

                items(liveMatches, key = { it.id }) { match ->
                    MatchCard(
                        match = match,
                        onClick = { onMatchClick(match) },
                        onLineupClick = { onLineupClick(match) },
                        onPredictionClick = { onPredictionClick(match) },
                        onLiveTvClick = { onLiveTvClick(match) }
                    )
                }
            }

            // Upcoming & Fixture Matches Section
            if (upcomingMatches.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "UPCOMING MATCHES & FIXTURES",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = SportsTextSecondary,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                    )
                }

                items(upcomingMatches, key = { it.id }) { match ->
                    MatchCard(
                        match = match,
                        onClick = { onMatchClick(match) },
                        onLineupClick = { onLineupClick(match) },
                        onPredictionClick = { onPredictionClick(match) },
                        onLiveTvClick = { onLiveTvClick(match) }
                    )
                }
            }

            if (filteredMatches.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No matches found for selected league",
                            color = SportsTextMuted,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}
