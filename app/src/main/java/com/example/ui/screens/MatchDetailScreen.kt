package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Hd
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.EventType
import com.example.model.Match
import com.example.model.MatchStatus
import com.example.model.SportType
import com.example.ui.components.AdMobBanner
import com.example.ui.components.PlayerFaceAvatar
import com.example.ui.components.PlayerRowItem
import com.example.ui.theme.*
import com.example.viewmodel.SportsViewModel

@Composable
fun MatchDetailScreen(
    match: Match,
    viewModel: SportsViewModel,
    onBack: () -> Unit,
    onOpenAiChatWithPrompt: (String) -> Unit
) {
    val context = LocalContext.current
    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Live Events, 1 = Lineup, 2 = AI Prediction
    val isLive = match.status == MatchStatus.LIVE

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SportsDarkBg)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SportsSurface)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack, modifier = Modifier.testTag("detail_back_btn")) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = SportsTextPrimary
                    )
                }
                Text(
                    text = "${match.league.logoEmoji} ${match.league.name}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = SportsTextPrimary
                )
            }

            IconButton(onClick = { viewModel.shareApp(context) }) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share",
                    tint = SportsElectricCyan
                )
            }
        }

        // Hero Scoreboard Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SportsSurface),
            border = CardDefaults.outlinedCardBorder().copy(
                brush = androidx.compose.ui.graphics.SolidColor(SportsCardBorder)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Status pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isLive) SportsLiveRed.copy(alpha = 0.2f) else SportsSurfaceVariant)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (isLive) "🔴 LIVE • ${match.liveClock}" else "${match.date} • ${match.matchTime}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isLive) SportsLiveRed else SportsElectricCyan
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Teams & Score
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    // Home
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = match.homeFlagEmoji, fontSize = 32.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = match.homeTeam,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = SportsTextPrimary
                        )
                        Text(
                            text = if (isLive) match.homeScore else "-",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = SportsPitchGreen
                        )
                    }

                    Text(
                        text = "VS",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = SportsTextMuted
                    )

                    // Away
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = match.awayFlagEmoji, fontSize = 32.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = match.awayTeam,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = SportsTextPrimary
                        )
                        Text(
                            text = if (isLive) match.awayScore else "-",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = SportsElectricCyan
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Venue & Status summary
                Text(
                    text = "📍 ${match.venue}",
                    fontSize = 11.sp,
                    color = SportsTextSecondary
                )
                if (match.statusSummary.isNotBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "⚡ " + match.statusSummary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = SportsCricketAmber
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                FilledTonalButton(
                    onClick = { selectedTab = 4 },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = SportsLiveRed.copy(alpha = 0.2f),
                        contentColor = SportsLiveRed
                    ),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LiveTv,
                        contentDescription = "Live TV",
                        modifier = Modifier.size(16.dp),
                        tint = SportsLiveRed
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "📺 লাইভ টিভি ও ম্যাচ ভিডিও",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Tabs
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = SportsSurface,
            contentColor = SportsPitchGreen,
            modifier = Modifier.fillMaxWidth()
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Events", fontSize = 11.sp, fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal) }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Lineup", fontSize = 11.sp, fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal) }
            )
            Tab(
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 },
                text = { Text("A-Z প্রেডিকশন", fontSize = 11.sp, fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Normal) }
            )
            Tab(
                selected = selectedTab == 3,
                onClick = { selectedTab = 3 },
                text = { Text("Gemini AI", fontSize = 11.sp, fontWeight = if (selectedTab == 3) FontWeight.Bold else FontWeight.Normal) }
            )
            Tab(
                selected = selectedTab == 4,
                onClick = { selectedTab = 4 },
                text = { Text("📺 Live TV", fontSize = 11.sp, fontWeight = if (selectedTab == 4) FontWeight.Bold else FontWeight.Normal) }
            )
        }

        val allMatchPlayers = remember(match) { match.homeLineup + match.awayLineup }
        val goalScorers = remember(allMatchPlayers) { allMatchPlayers.filter { it.goals > 0 } }
        val cardedPlayers = remember(allMatchPlayers) { allMatchPlayers.filter { it.yellowCards > 0 || it.redCards > 0 } }

        // Tab Content
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                AdMobBanner()
            }

            when (selectedTab) {
                0 -> {
                    // Live Commentary & Events
                    if (match.recentEvents.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(30.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Match will begin soon. Live commentary will stream automatically.",
                                    color = SportsTextMuted,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    } else {
                        items(match.recentEvents) { event ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = SportsSurfaceVariant),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(
                                                when (event.type) {
                                                    EventType.GOAL, EventType.SIX -> SportsPitchGreen
                                                    EventType.WICKET, EventType.RED_CARD -> SportsLiveRed
                                                    EventType.FOUR, EventType.YELLOW_CARD -> SportsCricketAmber
                                                    else -> SportsElectricCyan
                                                }
                                            )
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = event.timeOrOver,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = "${event.title} • ${event.team}",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = SportsTextPrimary
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = event.description,
                                            fontSize = 12.sp,
                                            color = SportsTextSecondary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                1 -> {
                    // Lineups with Faces, Goal Scorers and Cards
                    // Goal Scorers Showcase
                    if (goalScorers.isNotEmpty()) {
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF064E3B).copy(alpha = 0.35f)),
                                border = androidx.compose.foundation.BorderStroke(1.dp, SportsPitchGreen.copy(alpha = 0.6f))
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = "⚽", fontSize = 14.sp)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "ম্যাচের গোলদাতা (Goal Scorers - ফেস সহ):",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = SportsPitchGreen
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        items(goalScorers) { scorer ->
                                            val isHome = match.homeLineup.any { it.name == scorer.name }
                                            Card(
                                                colors = CardDefaults.cardColors(containerColor = SportsDarkBg),
                                                shape = RoundedCornerShape(8.dp),
                                                border = androidx.compose.foundation.BorderStroke(1.dp, SportsPitchGreen.copy(alpha = 0.5f))
                                            ) {
                                                Row(
                                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    PlayerFaceAvatar(player = scorer, size = 36.dp, isHomeTeam = isHome)
                                                    Spacer(modifier = Modifier.width(8.dp))
                                                    Column {
                                                        Text(text = scorer.name, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                                        Text(
                                                            text = "⚽ ${scorer.goals} গোল (${scorer.goalMinutes.joinToString(", ")})",
                                                            fontSize = 10.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = SportsPitchGreen
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Bookings Showcase
                    if (cardedPlayers.isNotEmpty()) {
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF450A0A).copy(alpha = 0.25f)),
                                border = androidx.compose.foundation.BorderStroke(1.dp, SportsLiveRed.copy(alpha = 0.5f))
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = "🟨 🟥", fontSize = 13.sp)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "কার্ড প্রাপ্ত প্লেয়ার (Bookings / Cards - ফেস সহ):",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFFFCA5A5)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        items(cardedPlayers) { carded ->
                                            val isHome = match.homeLineup.any { it.name == carded.name }
                                            val hasRed = carded.redCards > 0
                                            Card(
                                                colors = CardDefaults.cardColors(containerColor = SportsDarkBg),
                                                shape = RoundedCornerShape(8.dp),
                                                border = androidx.compose.foundation.BorderStroke(
                                                    1.dp,
                                                    if (hasRed) SportsLiveRed else Color(0xFFFFD600).copy(alpha = 0.6f)
                                                )
                                            ) {
                                                Row(
                                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    PlayerFaceAvatar(player = carded, size = 36.dp, isHomeTeam = isHome)
                                                    Spacer(modifier = Modifier.width(8.dp))
                                                    Column {
                                                        Text(text = carded.name, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                                        Text(
                                                            text = if (hasRed) "🟥 লাল কার্ড (${carded.redCardMinute})" else "🟨 হলুদ কার্ড (${carded.yellowCardMinute})",
                                                            fontSize = 10.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = if (hasRed) SportsLiveRed else Color(0xFFFFD600)
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Home Team Lineup
                    item {
                        Text(
                            text = "${match.homeTeam} Lineup (${match.homeFormation})",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = SportsPitchGreen
                        )
                    }
                    items(match.homeLineup.filterNot { it.isSub }) { p ->
                        PlayerRowItem(player = p, isHome = true)
                    }

                    val homeSubs = match.homeLineup.filter { it.isSub }
                    if (homeSubs.isNotEmpty()) {
                        item {
                            Text(
                                text = "বেঞ্চ / রিজার্ভ (${homeSubs.size})",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = SportsTextMuted,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                        items(homeSubs) { p ->
                            PlayerRowItem(player = p, isHome = true)
                        }
                    }

                    // Away Team Lineup
                    item {
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "${match.awayTeam} Lineup (${match.awayFormation})",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = SportsElectricCyan
                        )
                    }
                    items(match.awayLineup.filterNot { it.isSub }) { p ->
                        PlayerRowItem(player = p, isHome = false)
                    }

                    val awaySubs = match.awayLineup.filter { it.isSub }
                    if (awaySubs.isNotEmpty()) {
                        item {
                            Text(
                                text = "বেঞ্চ / রিজার্ভ (${awaySubs.size})",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = SportsTextMuted,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                        items(awaySubs) { p ->
                            PlayerRowItem(player = p, isHome = false)
                        }
                    }
                }
                2 -> {
                    // A-Z Production & Markets (Cards, Corners, BTTS, Over/Under, Scores)
                    val atoz = match.atoz

                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = SportsSurfaceVariant),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = "🔥 A to Z ম্যাচ মার্কেট ও প্রডাকশন",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SportsElectricCyan
                                )
                                Text(
                                    text = "লাল ও হলুদ কার্ড, কর্নার কিক, BTTS ও ওভার/আন্ডার প্রেডিকশন",
                                    fontSize = 11.sp,
                                    color = SportsTextSecondary
                                )
                            }
                        }
                    }

                    // Yellow & Red Cards
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = SportsSurfaceVariant),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = "🟨", fontSize = 14.sp)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "হলুদ কার্ড (Yellow Cards)",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = SportsTextPrimary
                                        )
                                    }
                                    Text(
                                        text = "${match.homeTeamCode} ${atoz.yellowCardsHome} - ${match.awayTeamCode} ${atoz.yellowCardsAway}",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFFFD600)
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "🔮 ${atoz.yellowCardsPrediction}",
                                    fontSize = 12.sp,
                                    color = SportsTextSecondary
                                )

                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    color = SportsCardBorder
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = "🟥", fontSize = 14.sp)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "লাল কার্ড (Red Cards)",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = SportsLiveRed
                                        )
                                    }
                                    Text(
                                        text = "${atoz.redCardsHome + atoz.redCardsAway} লাল কার্ড",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SportsLiveRed
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "ঝুঁকি বিশ্লেষণ: ${atoz.redCardRisk}",
                                    fontSize = 11.sp,
                                    color = SportsTextMuted
                                )
                            }
                        }
                    }

                    // Corners
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = SportsSurfaceVariant),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = "🚩", fontSize = 14.sp)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "কর্নার কিক (Corner Kicks)",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = SportsTextPrimary
                                        )
                                    }
                                    Text(
                                        text = "${match.homeTeamCode} ${atoz.cornersHome} - ${match.awayTeamCode} ${atoz.cornersAway} (মোট ${atoz.cornersHome + atoz.cornersAway})",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SportsElectricCyan
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "🔮 ${atoz.cornersPrediction}",
                                    fontSize = 12.sp,
                                    color = SportsTextSecondary
                                )
                            }
                        }
                    }

                    // BTTS
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = SportsSurfaceVariant),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = "⚽", fontSize = 14.sp)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "BTTS (উভয় দল গোল করবে কি?)",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = SportsTextPrimary
                                        )
                                    }
                                    Text(
                                        text = atoz.bttsPredictionBangla,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SportsPitchGreen
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = "হ্যাঁ (Yes): ${atoz.bttsYesPercentage}%", fontSize = 11.sp, color = SportsPitchGreen)
                                    Text(text = "না (No): ${atoz.bttsNoPercentage}%", fontSize = 11.sp, color = SportsTextMuted)
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(6.dp)
                                        .clip(RoundedCornerShape(3.dp))
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .weight(atoz.bttsYesPercentage.toFloat())
                                            .fillMaxHeight()
                                            .background(SportsPitchGreen)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .weight(atoz.bttsNoPercentage.toFloat())
                                            .fillMaxHeight()
                                            .background(SportsCardBorder)
                                    )
                                }
                            }
                        }
                    }

                    // Over / Under Goals & Runs
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = SportsSurfaceVariant),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = "📈", fontSize = 14.sp)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = if (match.sport == SportType.FOOTBALL) "Over / Under গোল" else "Over / Under রান",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = SportsTextPrimary
                                        )
                                    }
                                    Text(
                                        text = if (match.sport == SportType.FOOTBALL) atoz.overUnderPredictionBangla else atoz.cricketOverUnderRuns,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SportsCricketAmber
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                if (match.sport == SportType.FOOTBALL) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(text = "Over 1.5: ${atoz.over1_5Goals}%", fontSize = 11.sp, color = SportsTextSecondary)
                                        Text(text = "Over 2.5: ${atoz.over2_5Goals}%", fontSize = 11.sp, color = SportsPitchGreen, fontWeight = FontWeight.Bold)
                                        Text(text = "Under 2.5: ${atoz.under2_5Goals}%", fontSize = 11.sp, color = SportsTextMuted)
                                    }
                                } else {
                                    Text(
                                        text = "${atoz.cricketTotalSixesPrediction} • ${atoz.cricketPowerplayPrediction}",
                                        fontSize = 11.sp,
                                        color = SportsCricketAmber
                                    )
                                }
                            }
                        }
                    }

                    // Correct score & HT/FT
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = SportsSurfaceVariant),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "🎯 সম্ভাব্য সঠিক স্কোর: ${atoz.topCorrectScores.joinToString(" • ") { "${it.first} (${it.second}%)" }}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SportsTextPrimary
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "⏱️ ${atoz.halfTimeFullTimePrediction}",
                                    fontSize = 11.sp,
                                    color = SportsTextSecondary
                                )
                            }
                        }
                    }

                    // Double Chance Prediction (ডাবল চান্স প্রেডিকশন: 1X, 12, X2)
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1B4B)),
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF6366F1).copy(alpha = 0.5f))
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = "🎲", fontSize = 14.sp)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "ডাবল চান্স প্রেডিকশন (Double Chance)",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                    }
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(SportsPitchGreen)
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = "TOP PICK",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Black,
                                            color = Color.Black
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Card(
                                        modifier = Modifier.weight(1f),
                                        colors = CardDefaults.cardColors(containerColor = SportsDarkBg),
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Column(modifier = Modifier.padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(text = "1X (Home/Draw)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SportsPitchGreen)
                                            Text(text = "${atoz.doubleChance1X}%", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = SportsPitchGreen)
                                            Text(text = "স্বাগতিক জয়/ড্র", fontSize = 8.sp, color = SportsTextMuted)
                                        }
                                    }
                                    Card(
                                        modifier = Modifier.weight(1f),
                                        colors = CardDefaults.cardColors(containerColor = SportsDarkBg),
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Column(modifier = Modifier.padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(text = "12 (No Draw)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SportsElectricCyan)
                                            Text(text = "${atoz.doubleChance12}%", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = SportsElectricCyan)
                                            Text(text = "যেকোনো দলের জয়", fontSize = 8.sp, color = SportsTextMuted)
                                        }
                                    }
                                    Card(
                                        modifier = Modifier.weight(1f),
                                        colors = CardDefaults.cardColors(containerColor = SportsDarkBg),
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Column(modifier = Modifier.padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(text = "X2 (Away/Draw)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFFA5B4FC))
                                            Text(text = "${atoz.doubleChanceX2}%", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFFA5B4FC))
                                            Text(text = "সফরকারী জয়/ড্র", fontSize = 8.sp, color = SportsTextMuted)
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "⭐ সেরা পছন্দ: ${atoz.doubleChanceBestPick}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFFFD600)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = atoz.doubleChanceExplanation,
                                    fontSize = 10.sp,
                                    color = Color(0xFFC7D2FE)
                                )
                            }
                        }
                    }
                }
                3 -> {
                    // AI Prediction Tab
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = SportsSurfaceVariant),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = "🔮 Gemini AI Live Analysis",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SportsPitchGreen
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = match.aiPredictionBangla,
                                    fontSize = 13.sp,
                                    lineHeight = 20.sp,
                                    color = SportsTextPrimary
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = match.aiPredictionEnglish,
                                    fontSize = 12.sp,
                                    lineHeight = 18.sp,
                                    color = SportsTextSecondary
                                )
                            }
                        }
                    }

                    item {
                        Button(
                            onClick = {
                                onOpenAiChatWithPrompt("আজকের ${match.homeTeam} বনাম ${match.awayTeam} ম্যাচের লাল কার্ড, কর্নার, হলুদ কার্ড, BTTS, ডাবল চান্স এবং Over/Under সহ সম্পূর্ণ A to Z প্রেডিকশন দাও")
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = SportsPitchGreen)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "AI",
                                tint = Color.Black
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "এই ম্যাচের A to Z প্রেডিকশন AI-কে জিজ্ঞেস করুন",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                4 -> {
                    // Live TV & Video Highlights Tab
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = SportsSurfaceVariant),
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, SportsLiveRed.copy(alpha = 0.5f))
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(10.dp)
                                                .clip(CircleShape)
                                                .background(SportsLiveRed)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "📺 লাইভ টিভি ব্রডকাস্ট স্ট্রিম",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = SportsTextPrimary
                                        )
                                    }
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(SportsLiveRed)
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = "1080p 60FPS",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                // Video player card simulation
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(160.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFF0F172A))
                                        .border(1.dp, SportsCardBorder, RoundedCornerShape(8.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(text = match.homeFlagEmoji, fontSize = 24.sp)
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(text = match.homeScore, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = SportsPitchGreen)
                                            Text(text = " - ", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                            Text(text = match.awayScore, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = SportsElectricCyan)
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(text = match.awayFlagEmoji, fontSize = 24.sp)
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "${match.homeTeam} vs ${match.awayTeam}",
                                            fontSize = 12.sp,
                                            color = Color.White,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Text(
                                            text = "${match.liveClock} • লাইভ ধারাভাষ্য ও ভিডিও স্ট্রিম",
                                            fontSize = 10.sp,
                                            color = SportsPitchGreen
                                        )
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Text(
                            text = "📡 উপলব্ধ টিভি চ্যানেল (Available Channels)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = SportsPitchGreen
                        )
                    }

                    items(match.liveTvChannels) { channel ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = SportsDarkBg),
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, SportsCardBorder)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.LiveTv,
                                        contentDescription = "Channel",
                                        tint = SportsLiveRed,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(
                                            text = channel.name,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                        Text(
                                            text = "${channel.language} • ${channel.quality}",
                                            fontSize = 10.sp,
                                            color = SportsTextSecondary
                                        )
                                    }
                                }
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(SportsPitchGreen.copy(alpha = 0.2f))
                                        .padding(horizontal = 6.dp, vertical = 3.dp)
                                ) {
                                    Text(
                                        text = "LIVE",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SportsPitchGreen
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "🎬 ম্যাচ ভিডিও ক্লিপস ও হাইলাইটস (Match Videos)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = SportsElectricCyan
                        )
                    }

                    items(match.videoHighlights) { video ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = SportsDarkBg),
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, SportsCardBorder)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(width = 60.dp, height = 42.dp)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(Color(0xFF1E293B)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "Play",
                                        tint = SportsPitchGreen,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = video.title,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "${video.badge} • ${video.duration} • ${video.views}",
                                        fontSize = 10.sp,
                                        color = SportsTextSecondary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
