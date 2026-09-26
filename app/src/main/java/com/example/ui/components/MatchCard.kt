package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Match
import com.example.model.MatchStatus
import com.example.model.SportType
import com.example.ui.theme.*

@Composable
fun MatchCard(
    match: Match,
    onClick: () -> Unit,
    onLineupClick: () -> Unit,
    onPredictionClick: () -> Unit,
    onLiveTvClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val isLive = match.status == MatchStatus.LIVE

    // Pulsing animation for live badge
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .testTag("match_card_${match.id}")
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = SportsSurface
        ),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = if (isLive) {
                Brush.linearGradient(
                    listOf(SportsPitchGreen.copy(alpha = 0.6f), SportsCardBorder)
                )
            } else {
                Brush.linearGradient(listOf(SportsCardBorder, SportsCardBorder))
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            // Header: League & Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f, fill = false)
                ) {
                    Text(
                        text = match.league.logoEmoji,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = match.league.name,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = SportsTextSecondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Live or scheduled badge
                if (isLive) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(SportsLiveRed.copy(alpha = 0.15f))
                            .border(1.dp, SportsLiveRed.copy(alpha = pulseAlpha), RoundedCornerShape(20.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(SportsLiveRed.copy(alpha = pulseAlpha))
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "LIVE ${match.liveClock}",
                            color = SportsLiveRed,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(SportsSurfaceVariant)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = match.liveClock,
                            color = SportsElectricCyan,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Main Score Area: Home Team vs Away Team
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Home Team
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = match.homeFlagEmoji, fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = match.homeTeam,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = SportsTextPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Text(
                        text = if (isLive) match.homeScore else "Upcoming",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (isLive) SportsPitchGreen else SportsTextSecondary
                    )
                }

                // VS badge / Live clock
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = if (isLive) "VS" else match.matchTime,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = SportsTextMuted
                    )
                }

                // Away Team
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = match.awayTeam,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = SportsTextPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = match.awayFlagEmoji, fontSize = 20.sp)
                    }
                    Text(
                        text = if (isLive) match.awayScore else match.date,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (isLive) SportsElectricCyan else SportsTextSecondary
                    )
                }
            }

            // Live status commentary or current batters/pitcher
            if (isLive && match.statusSummary.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp))
                        .background(SportsSurfaceVariant.copy(alpha = 0.6f))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = "⚡ " + match.statusSummary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = SportsCricketAmber,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Win Probability Visualizer
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${match.homeTeamCode} ${match.winProbabilityHome}%",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = SportsPitchGreen
                )
                if (match.winProbabilityDraw > 0) {
                    Text(
                        text = "Draw ${match.winProbabilityDraw}%",
                        fontSize = 10.sp,
                        color = SportsTextMuted
                    )
                }
                Text(
                    text = "${match.awayTeamCode} ${match.winProbabilityAway}%",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = SportsElectricCyan
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Probability gauge bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
            ) {
                Box(
                    modifier = Modifier
                        .weight(match.winProbabilityHome.toFloat().coerceAtLeast(1f))
                        .fillMaxHeight()
                        .background(SportsPitchGreen)
                )
                if (match.winProbabilityDraw > 0) {
                    Box(
                        modifier = Modifier
                            .weight(match.winProbabilityDraw.toFloat().coerceAtLeast(1f))
                            .fillMaxHeight()
                            .background(SportsTextMuted.copy(alpha = 0.5f))
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(match.winProbabilityAway.toFloat().coerceAtLeast(1f))
                        .fillMaxHeight()
                        .background(SportsElectricCyan)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // A-Z Quick Market Pills (Cards, Corners, BTTS, Over)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                if (match.sport == SportType.FOOTBALL) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(SportsSurfaceVariant)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "BTTS: ${match.atoz.bttsYesPercentage}%",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = SportsPitchGreen
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(SportsSurfaceVariant)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "Over 2.5: ${match.atoz.over2_5Goals}%",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = SportsElectricCyan
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(SportsSurfaceVariant)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "🟨 ${match.atoz.yellowCardsHome + match.atoz.yellowCardsAway}  🟥 ${match.atoz.redCardsHome + match.atoz.redCardsAway}  🚩 ${match.atoz.cornersHome + match.atoz.cornersAway}",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = SportsTextSecondary
                        )
                    }
                    // Double Chance Highlight Pill
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFF4F46E5).copy(alpha = 0.2f))
                            .border(0.5.dp, Color(0xFF818CF8), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "🎲 ডাবল চান্স: 1X (${match.atoz.doubleChance1X}%) • 12 (${match.atoz.doubleChance12}%) • X2 (${match.atoz.doubleChanceX2}%)",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFA5B4FC)
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(SportsSurfaceVariant)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "Runs: Over 176.5",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = SportsCricketAmber
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(SportsSurfaceVariant)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "💥 12+ Sixes (72%)",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = SportsPitchGreen
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFF4F46E5).copy(alpha = 0.2f))
                            .border(0.5.dp, Color(0xFF818CF8), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "🎲 ডাবল চান্স: ${match.atoz.doubleChanceBestPick}",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFA5B4FC)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Action Buttons: Lineup, Live TV & Videos, AI Prediction
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                OutlinedButton(
                    onClick = onLineupClick,
                    modifier = Modifier
                        .weight(0.9f)
                        .height(36.dp)
                        .testTag("lineup_btn_${match.id}"),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = SportsTextPrimary
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, SportsCardBorder),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.FormatListNumbered,
                        contentDescription = "Lineup",
                        modifier = Modifier.size(13.dp),
                        tint = SportsElectricCyan
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(text = "Lineup", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                }

                if (onLiveTvClick != null) {
                    Button(
                        onClick = onLiveTvClick,
                        modifier = Modifier
                            .weight(1.1f)
                            .height(36.dp)
                            .testTag("live_tv_btn_${match.id}"),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SportsLiveRed.copy(alpha = 0.85f),
                            contentColor = Color.White
                        ),
                        contentPadding = PaddingValues(horizontal = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LiveTv,
                            contentDescription = "Live TV & Video",
                            modifier = Modifier.size(13.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "TV ও ভিডিও",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Button(
                    onClick = onPredictionClick,
                    modifier = Modifier
                        .weight(1.1f)
                        .height(36.dp)
                        .testTag("ai_predict_btn_${match.id}"),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SportsPitchGreen,
                        contentColor = Color.Black
                    ),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "AI Prediction",
                        modifier = Modifier.size(13.dp),
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "AI প্রেডিকশন",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
