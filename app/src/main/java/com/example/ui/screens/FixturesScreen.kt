package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.League
import com.example.model.Match
import com.example.model.SportType
import com.example.ui.components.AdMobBanner
import com.example.ui.components.MatchCard
import com.example.ui.theme.*
import com.example.viewmodel.SportsViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun FixturesScreen(
    viewModel: SportsViewModel,
    onMatchClick: (Match) -> Unit,
    onLineupClick: (Match) -> Unit,
    onPredictionClick: (Match) -> Unit,
    onLiveTvClick: (Match) -> Unit
) {
    val matches by viewModel.matches.collectAsState()
    val selectedSport by viewModel.selectedSport.collectAsState()
    val selectedLeague by viewModel.selectedLeague.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()

    // Generate 30 days for 1-month fixture list
    val calendarDays = remember {
        val list = mutableListOf<Pair<String, String>>() // "2026-09-26" to "Sep 26\nToday"
        val sdfDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val sdfDisplay = SimpleDateFormat("MMM d\nEEE", Locale.getDefault())
        val cal = Calendar.getInstance()
        cal.set(2026, Calendar.SEPTEMBER, 26) // anchor around current timeline

        for (i in 0 until 30) {
            val dateStr = sdfDate.format(cal.time)
            val label = if (i == 0) "Today\nSep 26" else if (i == 1) "Tmrw\nSep 27" else sdfDisplay.format(cal.time)
            list.add(dateStr to label)
            cal.add(Calendar.DAY_OF_YEAR, 1)
        }
        list
    }

    val filteredMatches = remember(matches, selectedSport, selectedLeague, selectedDate) {
        matches.filter { m ->
            val sportMatch = selectedSport == null || m.sport == selectedSport
            val leagueMatch = selectedLeague == null || m.league.id == selectedLeague?.id
            val dateMatch = selectedDate == null || m.date == selectedDate
            sportMatch && leagueMatch && dateMatch
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SportsDarkBg)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SportsSurface)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = "Fixtures",
                tint = SportsPitchGreen,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = "1-Month Match Fixtures",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = SportsTextPrimary
                )
                Text(
                    text = "All Football & Cricket Leagues (Sep - Oct 2026)",
                    fontSize = 11.sp,
                    color = SportsTextSecondary
                )
            }
        }

        // 30-Day Horizontal Calendar Scroller
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(SportsSurfaceVariant.copy(alpha = 0.5f))
                .padding(vertical = 10.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                // "All Dates" chip
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (selectedDate == null) SportsPitchGreen else SportsSurface)
                        .border(
                            1.dp,
                            if (selectedDate == null) SportsPitchGreen else SportsCardBorder,
                            RoundedCornerShape(10.dp)
                        )
                        .clickable { viewModel.selectDate(null) }
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Full\nMonth",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (selectedDate == null) Color.Black else SportsTextPrimary,
                        lineHeight = 14.sp
                    )
                }
            }

            items(calendarDays) { (dateStr, displayLabel) ->
                val isSelected = selectedDate == dateStr
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isSelected) SportsPitchGreen else SportsSurface)
                        .border(
                            1.dp,
                            if (isSelected) SportsPitchGreen else SportsCardBorder,
                            RoundedCornerShape(10.dp)
                        )
                        .clickable { viewModel.selectDate(dateStr) }
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = displayLabel,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) Color.Black else SportsTextPrimary,
                        lineHeight = 14.sp
                    )
                }
            }
        }

        // Sport filter chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = selectedSport == null,
                onClick = { viewModel.selectSport(null) },
                label = { Text("All Sports (${filteredMatches.size})", fontSize = 11.sp) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = SportsPitchGreen,
                    selectedLabelColor = Color.Black
                )
            )
            FilterChip(
                selected = selectedSport == SportType.FOOTBALL,
                onClick = { viewModel.selectSport(SportType.FOOTBALL) },
                label = { Text("⚽ Football", fontSize = 11.sp) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = SportsPitchGreen,
                    selectedLabelColor = Color.Black
                )
            )
            FilterChip(
                selected = selectedSport == SportType.CRICKET,
                onClick = { viewModel.selectSport(SportType.CRICKET) },
                label = { Text("🏏 Cricket", fontSize = 11.sp) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = SportsPitchGreen,
                    selectedLabelColor = Color.Black
                )
            )
        }

        // Fixtures List
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                AdMobBanner()
            }

            if (filteredMatches.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(50.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No fixtures scheduled for this day.\nTry selecting 'Full Month' or another date.",
                            color = SportsTextMuted,
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )
                    }
                }
            } else {
                items(filteredMatches, key = { it.id }) { match ->
                    MatchCard(
                        match = match,
                        onClick = { onMatchClick(match) },
                        onLineupClick = { onLineupClick(match) },
                        onPredictionClick = { onPredictionClick(match) },
                        onLiveTvClick = { onLiveTvClick(match) }
                    )
                }
            }
        }
    }
}
