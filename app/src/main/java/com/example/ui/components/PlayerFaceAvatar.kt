package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Player
import com.example.ui.theme.*

/**
 * High-polish Player Face Avatar with status indicators for:
 * - Goals scored (⚽ with count)
 * - Yellow cards (🟨 with count)
 * - Red cards (🟥)
 * - Captain (©)
 * - Jersey number badge
 */
@Composable
fun PlayerFaceAvatar(
    player: Player,
    modifier: Modifier = Modifier,
    size: Dp = 46.dp,
    showBadges: Boolean = true,
    isHomeTeam: Boolean = true
) {
    val borderColor = when {
        player.redCards > 0 -> SportsLiveRed
        player.yellowCards > 0 -> Color(0xFFFFD600)
        player.goals > 0 -> SportsPitchGreen
        isHomeTeam -> SportsPitchGreen.copy(alpha = 0.8f)
        else -> SportsElectricCyan.copy(alpha = 0.8f)
    }

    val gradientBg = when {
        player.redCards > 0 -> listOf(Color(0xFF450A0A), Color(0xFF1F0505))
        player.yellowCards > 0 -> listOf(Color(0xFF422006), Color(0xFF1E1004))
        player.goals > 0 -> listOf(Color(0xFF064E3B), Color(0xFF022C22))
        isHomeTeam -> listOf(Color(0xFF14532D), Color(0xFF052E16))
        else -> listOf(Color(0xFF0C4A6E), Color(0xFF082F49))
    }

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        // Main Avatar Circle
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(Brush.radialGradient(gradientBg))
                .border(1.5.dp, borderColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            // Player Face Emoji / Portrait
            Text(
                text = player.avatarEmoji.ifBlank { "👤" },
                fontSize = (size.value * 0.52f).sp
            )
        }

        if (showBadges) {
            // Top-Left: Captain Badge
            if (player.isCaptain) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = (-2).dp, y = (-2).dp)
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFFD600))
                        .border(1.dp, Color.Black, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "C",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black
                    )
                }
            }

            // Top-Right: Card Badge (Red Card or Yellow Card)
            if (player.redCards > 0) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 3.dp, y = (-3).dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(SportsLiveRed)
                        .border(0.5.dp, Color.White, RoundedCornerShape(2.dp))
                        .padding(horizontal = 3.dp, vertical = 1.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🟥",
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else if (player.yellowCards > 0) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 3.dp, y = (-3).dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color(0xFFFFD600))
                        .border(0.5.dp, Color.Black, RoundedCornerShape(2.dp))
                        .padding(horizontal = 3.dp, vertical = 1.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🟨",
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Bottom-Right: Goal Badge
            if (player.goals > 0) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(x = 4.dp, y = 3.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(1.dp, SportsPitchGreen, CircleShape)
                        .padding(2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "⚽",
                            fontSize = 8.sp
                        )
                        if (player.goals > 1) {
                            Text(
                                text = "${player.goals}",
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.Black
                            )
                        }
                    }
                }
            } else {
                // Bottom-Right / Center Bottom: Jersey Number Badge
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = 4.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.Black.copy(alpha = 0.85f))
                        .border(0.5.dp, borderColor, RoundedCornerShape(4.dp))
                        .padding(horizontal = 4.dp, vertical = 1.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "#${player.number}",
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
