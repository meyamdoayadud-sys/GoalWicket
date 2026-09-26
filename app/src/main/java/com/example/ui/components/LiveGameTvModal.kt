package com.example.ui.components

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
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
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.LiveTvChannel
import com.example.model.LiveVideoItem
import com.example.model.Match
import com.example.model.SportType
import com.example.service.AdMobManager
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiveGameTvModal(
    match: Match,
    onDismiss: () -> Unit,
    onOpenAiChat: (String) -> Unit
) {
    val context = LocalContext.current
    var selectedChannel by remember { mutableStateOf(match.liveTvChannels.firstOrNull()) }
    var selectedVideo by remember { mutableStateOf(match.videoHighlights.firstOrNull()) }
    var isPlaying by remember { mutableStateOf(true) }
    var isMuted by remember { mutableStateOf(false) }
    var currentQuality by remember { mutableStateOf("1080p 60fps") }
    var isUltraHdUnlocked by remember { mutableStateOf(false) }

    // Pulsing live on-air animation
    val infiniteTransition = rememberInfiniteTransition(label = "tvPulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

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
            // Header Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(SportsLiveRed.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LiveTv,
                            contentDescription = "Live TV",
                            tint = SportsLiveRed,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Live Game TV & Videos",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = SportsTextPrimary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(SportsLiveRed.copy(alpha = pulseAlpha))
                                    .padding(horizontal = 5.dp, vertical = 1.dp)
                            ) {
                                Text(
                                    text = "ON AIR",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White
                                )
                            }
                        }
                        Text(
                            text = "${match.homeTeam} vs ${match.awayTeam} • ${selectedChannel?.name ?: "HD TV Stream"}",
                            fontSize = 11.sp,
                            color = SportsTextSecondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
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

            Spacer(modifier = Modifier.height(10.dp))

            // ==================== TV VIDEO PLAYER CONTAINER ====================
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color(0xFF0F172A),
                                Color(0xFF0A0F1D)
                            )
                        )
                    )
                    .border(1.dp, SportsCardBorder, RoundedCornerShape(12.dp))
                    .testTag("live_tv_player_container")
            ) {
                // TV Broadcast simulation background
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Top Bar in TV Player
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(SportsLiveRed)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "LIVE TV",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = selectedChannel?.name ?: "HD Broadcast",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }

                        // Quality Badge
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color.Black.copy(alpha = 0.6f))
                                .border(0.5.dp, SportsPitchGreen, RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = if (isUltraHdUnlocked) "4K ULTRA HD" else currentQuality,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = SportsPitchGreen
                            )
                        }
                    }

                    // Center Stadium Action Graphic / Broadcast Screen
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = match.homeFlagEmoji, fontSize = 28.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = match.homeTeamCode,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(SportsDarkBg)
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "${match.homeScore} - ${match.awayScore}",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Black,
                                    color = SportsPitchGreen
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = match.awayTeamCode,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = match.awayFlagEmoji, fontSize = 28.sp)
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        // Video Title / Active Playing Info
                        Text(
                            text = selectedVideo?.title ?: "${match.homeTeam} vs ${match.awayTeam} Live Broadcast",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = SportsElectricCyan,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    // Bottom Player Controls
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = { isPlaying = !isPlaying },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = "Play/Pause",
                                    tint = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            IconButton(
                                onClick = { isMuted = !isMuted },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    imageVector = if (isMuted) Icons.AutoMirrored.Filled.VolumeOff else Icons.AutoMirrored.Filled.VolumeUp,
                                    contentDescription = "Mute",
                                    tint = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "${match.liveClock} • ${match.statusSummary}",
                                fontSize = 10.sp,
                                color = SportsTextSecondary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        // Fullscreen
                        IconButton(
                            onClick = {
                                if (context is Activity) {
                                    AdMobManager.showInterstitial(context)
                                }
                            },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Fullscreen,
                                contentDescription = "Fullscreen",
                                tint = SportsPitchGreen
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // ==================== TV CHANNEL SWITCHER TABS ====================
            Text(
                text = "📡 লাইভ টিভি চ্যানেল (Live TV Channels)",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = SportsPitchGreen
            )

            Spacer(modifier = Modifier.height(6.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(match.liveTvChannels) { channel ->
                    val isSelected = selectedChannel?.id == channel.id
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) SportsPitchGreen.copy(alpha = 0.2f) else SportsSurfaceVariant)
                            .border(
                                1.dp,
                                if (isSelected) SportsPitchGreen else SportsCardBorder,
                                RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                selectedChannel = channel
                                if (context is Activity) {
                                    AdMobManager.loadInterstitial(context)
                                }
                            }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(if (isSelected) SportsPitchGreen else SportsLiveRed)
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = channel.name,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) SportsPitchGreen else SportsTextPrimary
                                )
                            }
                            Text(
                                text = "${channel.language} • ${channel.quality}",
                                fontSize = 9.sp,
                                color = SportsTextSecondary
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // ==================== ULTRA HD 4K UNLOCK BANNER (REWARDED AD) ====================
            if (!isUltraHdUnlocked) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(SportsSurfaceVariant)
                        .border(1.dp, SportsCricketAmber.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                        .clickable {
                            if (context is Activity) {
                                AdMobManager.showRewarded(
                                    activity = context,
                                    onRewardEarned = {
                                        isUltraHdUnlocked = true
                                        currentQuality = "4K ULTRA HD 60FPS"
                                    }
                                )
                            } else {
                                isUltraHdUnlocked = true
                            }
                        }
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Hd,
                            contentDescription = "HD",
                            tint = SportsCricketAmber,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "4K Ultra HD & বাফারলেস লাইভ স্ট্রিম আনলক করুন",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SportsCricketAmber
                            )
                            Text(
                                text = "ছোট একটি ভিডিও অ্যাড দেখে ফ্রি 4K আনলক করুন",
                                fontSize = 9.sp,
                                color = SportsTextSecondary
                            )
                        }
                    }
                    Icon(
                        imageVector = Icons.Default.PlayCircle,
                        contentDescription = "Unlock",
                        tint = SportsCricketAmber,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // ==================== MATCH VIDEO HIGHLIGHTS & CLIPS ====================
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🎬 ম্যাচ ভিডিও ক্লিপস ও হাইলাইটস (Videos)",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = SportsElectricCyan
                )
                Text(
                    text = "${match.videoHighlights.size} টি ভিডিও",
                    fontSize = 10.sp,
                    color = SportsTextMuted
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(match.videoHighlights) { video ->
                    val isCurrent = selectedVideo?.id == video.id
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedVideo = video
                                isPlaying = true
                            },
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isCurrent) SportsSurfaceVariant else SportsDarkBg
                        ),
                        border = CardDefaults.outlinedCardBorder().copy(
                            brush = androidx.compose.ui.graphics.SolidColor(
                                if (isCurrent) SportsPitchGreen else SportsCardBorder
                            )
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Video Thumbnail with Play Button
                            Box(
                                modifier = Modifier
                                    .size(width = 72.dp, height = 48.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(
                                        Brush.linearGradient(
                                            listOf(Color(0xFF1E293B), Color(0xFF0F172A))
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Play",
                                    tint = if (isCurrent) SportsPitchGreen else Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(2.dp)
                                        .clip(RoundedCornerShape(2.dp))
                                        .background(Color.Black.copy(alpha = 0.8f))
                                        .padding(horizontal = 3.dp, vertical = 1.dp)
                                ) {
                                    Text(
                                        text = video.duration,
                                        fontSize = 8.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(3.dp))
                                            .background(
                                                if (video.badge.contains("LIVE")) SportsLiveRed else SportsElectricCyan
                                            )
                                            .padding(horizontal = 4.dp, vertical = 1.dp)
                                    ) {
                                        Text(
                                            text = video.badge,
                                            fontSize = 8.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = video.quality,
                                        fontSize = 9.sp,
                                        color = SportsTextMuted
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = video.title,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = SportsTextPrimary,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "${video.channelOrSource} • ${video.views}",
                                    fontSize = 10.sp,
                                    color = SportsTextSecondary
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Ask Gemini AI about live stream and highlights
            Button(
                onClick = {
                    onOpenAiChat("আজকের ${match.homeTeam} বনাম ${match.awayTeam} ম্যাচের লাইভ টিভি চ্যানেল ও ভিডিও হাইলাইটস সম্পর্কে বিস্তারিত বলো")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SportsSurfaceVariant,
                    contentColor = SportsPitchGreen
                )
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = "AI",
                    tint = SportsPitchGreen,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "🤖 Gemini AI-কে জিজ্ঞেস করুন লাইভ টিভি ও ভিডিও সম্পর্কে",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
