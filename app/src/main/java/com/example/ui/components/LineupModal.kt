package com.example.ui.components

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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Match
import com.example.model.Player
import com.example.model.SportType
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LineupModal(
    match: Match,
    onDismiss: () -> Unit,
    onAskAi: (String) -> Unit
) {
    var selectedTeamTab by remember { mutableIntStateOf(0) } // 0 = Home, 1 = Away
    var filterType by remember { mutableStateOf("ALL") } // "ALL", "GOALS", "CARDS", "BENCH"
    var selectedPlayerDetail by remember { mutableStateOf<Player?>(null) }

    val currentLineup = if (selectedTeamTab == 0) match.homeLineup else match.awayLineup
    val allMatchPlayers = remember(match) { match.homeLineup + match.awayLineup }
    val allGoalScorers = remember(allMatchPlayers) { allMatchPlayers.filter { it.goals > 0 } }
    val allCardedPlayers = remember(allMatchPlayers) { allMatchPlayers.filter { it.yellowCards > 0 || it.redCards > 0 } }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = SportsSurface,
        dragHandle = { BottomSheetDefaults.DragHandle(color = SportsCardBorder) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.92f)
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "ম্যাচ স্কোয়াড ও লাইনআপ",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = SportsTextPrimary
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(SportsPitchGreen.copy(alpha = 0.2f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "WITH FACES",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                color = SportsPitchGreen
                            )
                        }
                    }
                    Text(
                        text = "${match.homeTeam} vs ${match.awayTeam} • ${match.league.name}",
                        fontSize = 12.sp,
                        color = SportsTextSecondary
                    )
                }
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = SportsTextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // ==================== 1. TOP GOAL SCORERS SHOWCASE (গোলদাতা - ফেস সহ) ====================
            if (allGoalScorers.isNotEmpty()) {
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
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(allGoalScorers) { scorer ->
                                val isHome = match.homeLineup.any { it.name == scorer.name }
                                Card(
                                    modifier = Modifier.clickable { selectedPlayerDetail = scorer },
                                    colors = CardDefaults.cardColors(containerColor = SportsDarkBg),
                                    shape = RoundedCornerShape(8.dp),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, SportsPitchGreen.copy(alpha = 0.5f))
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        PlayerFaceAvatar(
                                            player = scorer,
                                            size = 38.dp,
                                            isHomeTeam = isHome
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text(
                                                    text = scorer.name,
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.White
                                                )
                                                if (scorer.countryFlag.isNotBlank()) {
                                                    Spacer(modifier = Modifier.width(3.dp))
                                                    Text(text = scorer.countryFlag, fontSize = 10.sp)
                                                }
                                            }
                                            Text(
                                                text = "⚽ ${scorer.goals} গোল (${scorer.goalMinutes.joinToString(", ")})",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = SportsPitchGreen
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // ==================== 2. TOP BOOKINGS SHOWCASE (কার্ড প্রাপ্ত প্লেয়ার - ফেস সহ) ====================
            if (allCardedPlayers.isNotEmpty()) {
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
                                text = "কার্ড প্রাপ্ত প্লেয়ার (Cards & Bookings - ফেস সহ):",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFCA5A5)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(allCardedPlayers) { carded ->
                                val isHome = match.homeLineup.any { it.name == carded.name }
                                val hasRed = carded.redCards > 0
                                Card(
                                    modifier = Modifier.clickable { selectedPlayerDetail = carded },
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
                                        PlayerFaceAvatar(
                                            player = carded,
                                            size = 38.dp,
                                            isHomeTeam = isHome
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text(
                                                    text = carded.name,
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.White
                                                )
                                                if (carded.countryFlag.isNotBlank()) {
                                                    Spacer(modifier = Modifier.width(3.dp))
                                                    Text(text = carded.countryFlag, fontSize = 10.sp)
                                                }
                                            }
                                            if (hasRed) {
                                                Text(
                                                    text = "🟥 লাল কার্ড (${carded.redCardMinute})",
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = SportsLiveRed
                                                )
                                            } else {
                                                Text(
                                                    text = "🟨 হলুদ কার্ড (${carded.yellowCardMinute})",
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFFFFD600)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Team Switcher Tab
            TabRow(
                selectedTabIndex = selectedTeamTab,
                containerColor = SportsSurfaceVariant,
                contentColor = SportsPitchGreen,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Tab(
                    selected = selectedTeamTab == 0,
                    onClick = { selectedTeamTab = 0 },
                    text = {
                        Text(
                            text = "${match.homeTeamCode} (${match.homeFormation})",
                            fontWeight = if (selectedTeamTab == 0) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTeamTab == 0) SportsPitchGreen else SportsTextSecondary
                        )
                    }
                )
                Tab(
                    selected = selectedTeamTab == 1,
                    onClick = { selectedTeamTab = 1 },
                    text = {
                        Text(
                            text = "${match.awayTeamCode} (${match.awayFormation})",
                            fontWeight = if (selectedTeamTab == 1) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTeamTab == 1) SportsElectricCyan else SportsTextSecondary
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Filter Chips Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                val teamGoalsCount = currentLineup.count { it.goals > 0 }
                val teamCardsCount = currentLineup.count { it.yellowCards > 0 || it.redCards > 0 }

                FilterChip(
                    selected = filterType == "ALL",
                    onClick = { filterType = "ALL" },
                    label = { Text("সব প্লেয়ার (${currentLineup.size})", fontSize = 10.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = SportsPitchGreen,
                        selectedLabelColor = Color.Black
                    )
                )
                FilterChip(
                    selected = filterType == "GOALS",
                    onClick = { filterType = "GOALS" },
                    label = { Text("⚽ গোলদাতা ($teamGoalsCount)", fontSize = 10.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = SportsPitchGreen,
                        selectedLabelColor = Color.Black
                    )
                )
                FilterChip(
                    selected = filterType == "CARDS",
                    onClick = { filterType = "CARDS" },
                    label = { Text("🟨🟥 কার্ড ($teamCardsCount)", fontSize = 10.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFFFFD600),
                        selectedLabelColor = Color.Black
                    )
                )
                FilterChip(
                    selected = filterType == "BENCH",
                    onClick = { filterType = "BENCH" },
                    label = { Text("বেঞ্চ / রিজার্ভ", fontSize = 10.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = SportsSurfaceVariant,
                        selectedLabelColor = SportsTextPrimary
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Filtered lists
            val filteredLineup = when (filterType) {
                "GOALS" -> currentLineup.filter { it.goals > 0 }
                "CARDS" -> currentLineup.filter { it.yellowCards > 0 || it.redCards > 0 }
                "BENCH" -> currentLineup.filter { it.isSub }
                else -> currentLineup
            }

            val startingXI = filteredLineup.filterNot { it.isSub }
            val substitutes = filteredLineup.filter { it.isSub }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (filterType != "BENCH" && startingXI.isNotEmpty()) {
                    item {
                        Text(
                            text = if (match.sport == SportType.FOOTBALL) "STARTING XI (${startingXI.size})" else "PLAYING XI (${startingXI.size})",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = SportsPitchGreen,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }

                    items(startingXI) { player ->
                        PlayerRowItem(
                            player = player,
                            isHome = selectedTeamTab == 0,
                            onClick = { selectedPlayerDetail = player }
                        )
                    }
                }

                if (substitutes.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (match.sport == SportType.FOOTBALL) "SUBSTITUTES / BENCH (${substitutes.size})" else "BENCH / RESERVES (${substitutes.size})",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = SportsTextMuted,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }

                    items(substitutes) { player ->
                        PlayerRowItem(
                            player = player,
                            isHome = selectedTeamTab == 0,
                            onClick = { selectedPlayerDetail = player }
                        )
                    }
                }

                if (filteredLineup.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(30.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "এই ফিল্টারে কোনো প্লেয়ার পাওয়া যায়নি",
                                fontSize = 12.sp,
                                color = SportsTextMuted
                            )
                        }
                    }
                }

                // AI Tactical Query Button at bottom
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {
                            val teamName = if (selectedTeamTab == 0) match.homeTeam else match.awayTeam
                            onAskAi("আজকের ম্যাচে ${teamName}-এর লাইনআপে কে গোল করেছে, কে লাল কার্ড বা হলুদ কার্ড পেয়েছে এবং প্লেয়ারদের ট্যাকটিক্যাল ভূমিকা বিশ্লেষণ করো")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SportsSurfaceVariant,
                            contentColor = SportsPitchGreen
                        ),
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, SportsCardBorder)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "Ask AI",
                            tint = SportsPitchGreen,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "🤖 Gemini AI-কে জিজ্ঞেস করুন লাইনআপ ও কার্ড সম্পর্কে",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }

    // ==================== PLAYER DETAIL MODAL SHEET ====================
    selectedPlayerDetail?.let { player ->
        AlertDialog(
            onDismissRequest = { selectedPlayerDetail = null },
            confirmButton = {
                TextButton(onClick = { selectedPlayerDetail = null }) {
                    Text("বন্ধ করুন (Close)", color = SportsPitchGreen)
                }
            },
            title = null,
            containerColor = SportsSurface,
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Big Player Face Avatar
                    PlayerFaceAvatar(
                        player = player,
                        size = 72.dp,
                        showBadges = true,
                        isHomeTeam = selectedTeamTab == 0
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = player.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        if (player.countryFlag.isNotBlank()) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = player.countryFlag, fontSize = 16.sp)
                        }
                    }

                    Text(
                        text = "#${player.number} • ${player.role} • ${if (player.isSub) "সরাসরি বেঞ্চ" else "মূল একাদশ"}",
                        fontSize = 12.sp,
                        color = SportsTextSecondary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Goals Section
                    if (player.goals > 0) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = SportsDarkBg),
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, SportsPitchGreen)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = "⚽", fontSize = 16.sp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "গোল করেছে:",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SportsPitchGreen
                                    )
                                }
                                Text(
                                    text = "${player.goals} টি গোল (${player.goalMinutes.joinToString(", ")})",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = SportsPitchGreen
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // Red Card Section
                    if (player.redCards > 0) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = SportsDarkBg),
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, SportsLiveRed)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = "🟥", fontSize = 16.sp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "লাল কার্ড পেয়েছে:",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SportsLiveRed
                                    )
                                }
                                Text(
                                    text = "সরাসরি লাল কার্ড (${player.redCardMinute})",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = SportsLiveRed
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // Yellow Card Section
                    if (player.yellowCards > 0) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = SportsDarkBg),
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFFD600))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = "🟨", fontSize = 16.sp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "হলুদ কার্ড পেয়েছে:",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFFFD600)
                                    )
                                }
                                Text(
                                    text = "হলুদ কার্ড (${player.yellowCardMinute})",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFFFFD600)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // Performance Stat
                    if (player.performanceStat.isNotBlank()) {
                        Text(
                            text = "ম্যাচ স্ট্যাটাস: ${player.performanceStat}",
                            fontSize = 12.sp,
                            color = SportsCricketAmber,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Text(
                        text = "ম্যাচ রেটিং: ⭐ ${player.rating}/10",
                        fontSize = 12.sp,
                        color = SportsElectricCyan,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        )
    }
}

/**
 * Enhanced PlayerRowItem with:
 * - Distinct player face avatar
 * - Goal badge with minute if scored
 * - Yellow card / Red card badge with minute
 * - Captain / Wicket-Keeper indicator
 * - Role & Match rating
 */
@Composable
fun PlayerRowItem(
    player: Player,
    isHome: Boolean,
    onClick: () -> Unit = {}
) {
    val hasRed = player.redCards > 0
    val hasYellow = player.yellowCards > 0
    val hasGoal = player.goals > 0

    val cardBorderColor = when {
        hasRed -> SportsLiveRed.copy(alpha = 0.8f)
        hasYellow -> Color(0xFFFFD600).copy(alpha = 0.5f)
        hasGoal -> SportsPitchGreen.copy(alpha = 0.6f)
        else -> SportsCardBorder.copy(alpha = 0.5f)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(SportsSurfaceVariant)
            .border(1.dp, cardBorderColor, RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            // Player Face Avatar
            PlayerFaceAvatar(
                player = player,
                size = 46.dp,
                showBadges = true,
                isHomeTeam = isHome
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = player.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = SportsTextPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (player.countryFlag.isNotBlank()) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = player.countryFlag, fontSize = 11.sp)
                    }
                    if (player.isCaptain) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(3.dp))
                                .background(SportsCricketAmber)
                                .padding(horizontal = 3.dp, vertical = 1.dp)
                        ) {
                            Text(
                                text = "C",
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.Black
                            )
                        }
                    }
                    if (player.isWicketKeeper) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(3.dp))
                                .background(SportsElectricCyan)
                                .padding(horizontal = 3.dp, vertical = 1.dp)
                        ) {
                            Text(
                                text = "WK",
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.Black
                            )
                        }
                    }
                }

                // Event Badges: Goal, Red Card, Yellow Card
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(top = 2.dp)
                ) {
                    if (hasGoal) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(3.dp))
                                .background(SportsPitchGreen.copy(alpha = 0.2f))
                                .border(0.5.dp, SportsPitchGreen, RoundedCornerShape(3.dp))
                                .padding(horizontal = 4.dp, vertical = 1.dp)
                        ) {
                            Text(
                                text = "⚽ ${player.goals} Goal (${player.goalMinutes.joinToString(", ")})",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = SportsPitchGreen
                            )
                        }
                    }

                    if (hasRed) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(3.dp))
                                .background(SportsLiveRed.copy(alpha = 0.2f))
                                .border(0.5.dp, SportsLiveRed, RoundedCornerShape(3.dp))
                                .padding(horizontal = 4.dp, vertical = 1.dp)
                        ) {
                            Text(
                                text = "🟥 লাল কার্ড (${player.redCardMinute})",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = SportsLiveRed
                            )
                        }
                    }

                    if (hasYellow) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(3.dp))
                                .background(Color(0xFFFFD600).copy(alpha = 0.15f))
                                .border(0.5.dp, Color(0xFFFFD600), RoundedCornerShape(3.dp))
                                .padding(horizontal = 4.dp, vertical = 1.dp)
                        ) {
                            Text(
                                text = "🟨 হলুদ কার্ড (${player.yellowCardMinute})",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFD600)
                            )
                        }
                    }
                }

                // Performance stat
                if (player.performanceStat.isNotBlank()) {
                    Text(
                        text = player.performanceStat,
                        fontSize = 10.sp,
                        color = SportsCricketAmber,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        // Right side: Role and rating
        Column(horizontalAlignment = Alignment.End) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(SportsDarkBg)
                    .padding(horizontal = 7.dp, vertical = 3.dp)
            ) {
                Text(
                    text = player.role,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = SportsTextSecondary
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "⭐ ${player.rating}",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = SportsPitchGreen
            )
        }
    }
}
