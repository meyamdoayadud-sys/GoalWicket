package com.example.ui.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.R
import com.example.ui.theme.*

@Composable
fun ApkDownloadDialog(
    onDismiss: () -> Unit,
    onDownloadApk: (Context) -> Unit,
    onShareApp: (Context) -> Unit
) {
    val context = LocalContext.current

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(containerColor = SportsSurface),
            shape = RoundedCornerShape(18.dp),
            border = CardDefaults.outlinedCardBorder().copy(
                brush = androidx.compose.ui.graphics.SolidColor(SportsPitchGreen.copy(alpha = 0.5f))
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // App Logo icon
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(SportsDarkBg)
                        .border(2.dp, SportsPitchGreen, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.goalwicket_logo_1790399892347),
                        contentDescription = "GoalWicket Logo",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "GoalWicket Live",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = SportsTextPrimary
                )

                Text(
                    text = "Version 1.0.4 • Official Release APK",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = SportsElectricCyan
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Feature Highlights
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(SportsSurfaceVariant)
                        .padding(12.dp)
                ) {
                    Text(
                        text = "⚡ What's inside GoalWicket APK:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = SportsCricketAmber
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "• 🔴 Real-time score refresh every 4 seconds\n• 🤖 Gemini AI Match Predictions in Bangla & English\n• 📋 Full Official Lineups & Tactical Formations\n• 📅 1-Month Fixture List across 17 Global Leagues\n• ⚽ Football: World Cup, UCL, EPL, La Liga & more\n• 🏏 Cricket: IPL, BPL, T20 & ICC World Cup, Ashes",
                        fontSize = 11.sp,
                        lineHeight = 17.sp,
                        color = SportsTextSecondary
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Download Button
                Button(
                    onClick = { onDownloadApk(context) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SportsPitchGreen,
                        contentColor = Color.Black
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = "Download APK",
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Download APK (Direct File)",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Share Button
                OutlinedButton(
                    onClick = { onShareApp(context) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = SportsTextPrimary),
                    border = androidx.compose.foundation.BorderStroke(1.dp, SportsCardBorder)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = SportsElectricCyan,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "বন্ধুদের সাথে শেয়ার করুন (Share APK)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onDismiss) {
                    Text(text = "Close", color = SportsTextMuted, fontSize = 12.sp)
                }
            }
        }
    }
}
