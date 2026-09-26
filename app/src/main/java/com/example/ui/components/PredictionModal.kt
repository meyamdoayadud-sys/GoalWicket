package com.example.ui.components

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Match
import com.example.service.AdMobManager
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PredictionModal(
    match: Match,
    onDismiss: () -> Unit,
    onOpenChat: (String) -> Unit,
    onLiveTvClick: (() -> Unit)? = null
) {
    val context = LocalContext.current
    var isProUnlocked by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = SportsSurface,
        dragHandle = { BottomSheetDefaults.DragHandle(color = SportsCardBorder) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(SportsPitchGreen.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "AI",
                            tint = SportsPitchGreen,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Gemini Live Match Prediction",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = SportsTextPrimary
                        )
                        Text(
                            text = "${match.homeTeam} vs ${match.awayTeam}",
                            fontSize = 13.sp,
                            color = SportsTextSecondary
                        )
                    }
                }
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = SportsTextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Probability Overview Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = SportsSurfaceVariant),
                shape = RoundedCornerShape(12.dp),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(SportsCardBorder)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "WIN PROBABILITY / জয়ের সম্ভাবনা",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = SportsCricketAmber
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = match.homeTeamCode,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = SportsPitchGreen
                            )
                            Text(
                                text = "${match.winProbabilityHome}%",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = SportsPitchGreen
                            )
                        }

                        if (match.winProbabilityDraw > 0) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "DRAW",
                                    fontSize = 12.sp,
                                    color = SportsTextMuted
                                )
                                Text(
                                    text = "${match.winProbabilityDraw}%",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SportsTextMuted
                                )
                            }
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = match.awayTeamCode,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = SportsElectricCyan
                            )
                            Text(
                                text = "${match.winProbabilityAway}%",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = SportsElectricCyan
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Gauge
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
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
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ==================== A TO Z PRODUCTION & LIVE MARKETS ====================
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = SportsSurfaceVariant),
                shape = RoundedCornerShape(12.dp),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(SportsElectricCyan.copy(alpha = 0.5f))
                )
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "🔥", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "A to Z ম্যাচ প্রডাকশন ও প্রেডিকশন",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = SportsElectricCyan
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(SportsPitchGreen.copy(alpha = 0.15f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "LIVE & PRE-MATCH",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = SportsPitchGreen
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    val atoz = match.atoz

                    // 1. CARDS: হলুদ কার্ড ও লাল কার্ড
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(SportsDarkBg)
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "🟨", fontSize = 13.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "হলুদ কার্ড (Yellow Cards):",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SportsTextPrimary
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "লাইভ: ${match.homeTeamCode} ${atoz.yellowCardsHome} - ${match.awayTeamCode} ${atoz.yellowCardsAway}",
                                fontSize = 11.sp,
                                color = Color(0xFFFFD600),
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "🔮 ${atoz.yellowCardsPrediction}",
                                fontSize = 11.sp,
                                color = SportsTextSecondary
                            )
                        }

                        // Red card risk indicator
                        Column(horizontalAlignment = Alignment.End) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "🟥", fontSize = 13.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "লাল কার্ড",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SportsLiveRed
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(SportsLiveRed.copy(alpha = 0.2f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "${atoz.redCardsHome} লাল কাড",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SportsLiveRed
                                )
                            }
                            Text(
                                text = atoz.redCardRisk,
                                fontSize = 10.sp,
                                color = SportsTextMuted
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // 2. CORNERS: কর্নার কিক
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(SportsDarkBg)
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "🚩", fontSize = 13.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "কর্নার কিক (Corner Kicks)",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SportsTextPrimary
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "লাইভ কর্নার: ${match.homeTeamCode} ${atoz.cornersHome} - ${match.awayTeamCode} ${atoz.cornersAway} (মোট ${atoz.cornersHome + atoz.cornersAway})",
                                fontSize = 11.sp,
                                color = SportsElectricCyan,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "🔮 ${atoz.cornersPrediction}",
                                fontSize = 11.sp,
                                color = SportsTextSecondary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // 3. BTTS: Both Teams To Score (উভয় দল গোল করবে কি?)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(SportsDarkBg)
                            .padding(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "⚽", fontSize = 13.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "BTTS (উভয় দল গোল করবে কি?)",
                                    fontSize = 12.sp,
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

                    Spacer(modifier = Modifier.height(8.dp))

                    // 4. OVER / UNDER: ওভার / আন্ডার গোল ও রান
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(SportsDarkBg)
                            .padding(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "📈", fontSize = 13.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (match.sport == com.example.model.SportType.FOOTBALL) "Over / Under গোল প্রেডিকশন" else "Over / Under রান ও ছক্কা",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SportsTextPrimary
                                )
                            }
                            Text(
                                text = if (match.sport == com.example.model.SportType.FOOTBALL) atoz.overUnderPredictionBangla else atoz.cricketOverUnderRuns,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SportsCricketAmber
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        if (match.sport == com.example.model.SportType.FOOTBALL) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Over 1.5: ${atoz.over1_5Goals}%", fontSize = 11.sp, color = SportsTextSecondary)
                                Text(text = "Over 2.5: ${atoz.over2_5Goals}%", fontSize = 11.sp, color = SportsPitchGreen, fontWeight = FontWeight.Bold)
                                Text(text = "Under 2.5: ${atoz.under2_5Goals}%", fontSize = 11.sp, color = SportsTextMuted)
                                Text(text = "Over 3.5: ${atoz.over3_5Goals}%", fontSize = 11.sp, color = SportsTextSecondary)
                            }
                        } else {
                            Text(
                                text = "💥 ${atoz.cricketTotalSixesPrediction} | ⚡ ${atoz.cricketPowerplayPrediction}",
                                fontSize = 11.sp,
                                color = SportsCricketAmber
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // 5. CORRECT SCORE & HT/FT
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(SportsDarkBg)
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "🎯", fontSize = 13.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "সম্ভাব্য সঠিক স্কোর (Correct Score):",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SportsTextPrimary
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = atoz.topCorrectScores.joinToString("  •  ") { "${it.first} (${it.second}%)" },
                                fontSize = 11.sp,
                                color = SportsPitchGreen,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "⏱️ ${atoz.halfTimeFullTimePrediction}",
                                fontSize = 10.sp,
                                color = SportsTextSecondary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // 6. DOUBLE CHANCE PREDICTION (ডাবল চান্স প্রেডিকশন)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF1E1B4B))
                            .border(1.dp, Color(0xFF6366F1).copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "🎲", fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(5.dp))
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
                                    text = "HIGH ACCURACY",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.Black
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Double Chance 1X, 12, X2 Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // 1X (Home Win or Draw)
                            Card(
                                modifier = Modifier.weight(1f),
                                colors = CardDefaults.cardColors(containerColor = SportsDarkBg),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = "1X (Home/Draw)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SportsPitchGreen)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(text = "${atoz.doubleChance1X}%", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = SportsPitchGreen)
                                    Text(text = "স্বাগতিক জয়/ড্র", fontSize = 8.sp, color = SportsTextMuted)
                                }
                            }

                            // 12 (Any Team Win)
                            Card(
                                modifier = Modifier.weight(1f),
                                colors = CardDefaults.cardColors(containerColor = SportsDarkBg),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = "12 (No Draw)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SportsElectricCyan)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(text = "${atoz.doubleChance12}%", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = SportsElectricCyan)
                                    Text(text = "যেকোনো দলের জয়", fontSize = 8.sp, color = SportsTextMuted)
                                }
                            }

                            // X2 (Away Win or Draw)
                            Card(
                                modifier = Modifier.weight(1f),
                                colors = CardDefaults.cardColors(containerColor = SportsDarkBg),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = "X2 (Away/Draw)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFFA5B4FC))
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(text = "${atoz.doubleChanceX2}%", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFFA5B4FC))
                                    Text(text = "সফরকারী জয়/ড্র", fontSize = 8.sp, color = SportsTextMuted)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Best Double Chance Pick
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "⭐ সেরা ডাবল চান্স পছন্দ: ", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFFD600))
                            Text(text = atoz.doubleChanceBestPick, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SportsPitchGreen)
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = atoz.doubleChanceExplanation,
                            fontSize = 10.sp,
                            color = Color(0xFFC7D2FE),
                            lineHeight = 15.sp
                        )
                    }
                }
            }

            if (onLiveTvClick != null) {
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = onLiveTvClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SportsLiveRed,
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.LiveTv,
                        contentDescription = "Live TV",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "📺 এই ম্যাচের লাইভ গেম টিভি ও ভিডিও দেখুন",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Bangla Prediction Box
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = SportsDarkBg),
                shape = RoundedCornerShape(12.dp),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(SportsPitchGreen.copy(alpha = 0.4f))
                )
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "🇧🇩", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "বাংলা প্রেডিকশন ও ট্যাকটিক্যাল বিশ্লেষণ",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = SportsPitchGreen
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = match.aiPredictionBangla,
                        fontSize = 13.sp,
                        lineHeight = 20.sp,
                        color = SportsTextPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // English Prediction Box
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = SportsDarkBg),
                shape = RoundedCornerShape(12.dp),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(SportsElectricCyan.copy(alpha = 0.4f))
                )
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "🌐", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "English Match Prediction & Form Analysis",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = SportsElectricCyan
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = match.aiPredictionEnglish,
                        fontSize = 13.sp,
                        lineHeight = 20.sp,
                        color = SportsTextPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Pro AI Unlock with Rewarded Ad (Uses AdMob Rewarded Units)
            if (!isProUnlocked) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = SportsSurfaceVariant),
                    shape = RoundedCornerShape(12.dp),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = androidx.compose.ui.graphics.SolidColor(SportsCricketAmber.copy(alpha = 0.5f))
                    )
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Stars,
                                contentDescription = "VIP",
                                tint = SportsCricketAmber,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "VIP AI Prediction & Pitch Blueprint",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = SportsCricketAmber
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Watch a quick sponsored video ad to unlock Deep Player vs Player Matchup telemetry & AI betting probability score.",
                            fontSize = 12.sp,
                            color = SportsTextSecondary
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = {
                                if (context is Activity) {
                                    AdMobManager.showRewarded(
                                        activity = context,
                                        onRewardEarned = {
                                            isProUnlocked = true
                                        }
                                    )
                                } else {
                                    isProUnlocked = true
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = SportsCricketAmber),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayCircleOutline,
                                contentDescription = "Watch",
                                tint = Color.Black
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "ভিডিও দেখে VIP প্রেডিকশন আনলক করুন",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1B3828)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "⭐ VIP AI Insights Unlocked!",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = SportsPitchGreen
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "• Player to Watch: Strike rotation in middle overs will dictate 70% of match outcome.\n• Weather & Pitch: Dew factor favors second innings chasing team by +14%.\n• Projected Final Score Range: ${if (match.sport == com.example.model.SportType.FOOTBALL) "2.5 Total Goals Over" else "175-190 Runs"}",
                            fontSize = 12.sp,
                            color = SportsTextPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Ask Gemini custom question button
            Button(
                onClick = {
                    onOpenChat("আজকের ${match.homeTeam} বনাম ${match.awayTeam} ম্যাচের বিস্তারিত প্রেডিকশন এবং একাদশ বিশ্লেষণ দাও")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SportsPitchGreen,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = "Chat",
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "🤖 Gemini AI চ্যাটবটে এই ম্যাচ নিয়ে কথা বলুন",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
