package com.example.service

import android.util.Log
import com.example.BuildConfig
import com.example.model.Match
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object GeminiService {
    private const val TAG = "GeminiService"
    private const val BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent"

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(45, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    suspend fun getLiveSportsAnswer(
        userPrompt: String,
        activeMatches: List<Match>
    ): String = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Exception) {
            ""
        }

        // Build rich context about all live/upcoming matches, lineups, odds and A-to-Z production
        val contextSummary = buildString {
            appendLine("=== CURRENT LIVE & UPCOMING MATCH DATA & A-TO-Z PRODUCTION ===")
            activeMatches.take(6).forEach { m ->
                appendLine("[${m.sport.displayName}] League: ${m.league.name}")
                appendLine("Match: ${m.homeTeam} (${m.homeScore}) vs ${m.awayTeam} (${m.awayScore})")
                appendLine("Status: ${m.status} | Clock: ${m.liveClock} | Info: ${m.statusSummary}")
                appendLine("Venue: ${m.venue} | Win Probability: ${m.homeTeamCode} ${m.winProbabilityHome}% - ${m.awayTeamCode} ${m.winProbabilityAway}%")
                appendLine("A-to-Z Markets: Red Card Risk: ${m.atoz.redCardRisk} | Yellow Cards: ${m.atoz.yellowCardsHome} - ${m.atoz.yellowCardsAway} (Prediction: ${m.atoz.yellowCardsPrediction})")
                appendLine("Corners: ${m.atoz.cornersHome} - ${m.atoz.cornersAway} (Prediction: ${m.atoz.cornersPrediction})")
                appendLine("BTTS (Both Teams To Score): Yes ${m.atoz.bttsYesPercentage}% / No ${m.atoz.bttsNoPercentage}% | ${m.atoz.bttsPredictionBangla}")
                appendLine("Over/Under: Over 1.5 (${m.atoz.over1_5Goals}%), Over 2.5 (${m.atoz.over2_5Goals}%), Under 2.5 (${m.atoz.under2_5Goals}%) | Cricket: ${m.atoz.cricketOverUnderRuns}, Sixes: ${m.atoz.cricketTotalSixesPrediction}")
                appendLine("Home Lineup XI: ${m.homeLineup.filterNot { it.isSub }.joinToString { "${it.name} (${it.role})" }}")
                appendLine("Away Lineup XI: ${m.awayLineup.filterNot { it.isSub }.joinToString { "${it.name} (${it.role})" }}")
                appendLine("Live Events: ${m.recentEvents.take(2).joinToString { "${it.timeOrOver} ${it.title} - ${it.description}" }}")
                appendLine("Pre-set AI Insight: ${m.aiPredictionBangla}")
                appendLine("-------------------")
            }
        }

        val systemInstruction = """
            You are "GoalWicket AI", an elite sports analyst and AI chatbot inside the GoalWicket Live Score app.
            You specialize in Football (World Cup, Champions League, Premier League, La Liga, Serie A, Bundesliga, Europa League, Copa Libertadores, Nations League) and Cricket (ICC World Cup, T20 World Cup, IPL, BPL, PSL, Big Bash, Ashes, Asia Cup).
            
            Special A to Z Markets & Production Covered:
            - লাল কার্ড (Red Cards) risk & referee discipline
            - হলুদ কার্ড (Yellow Cards) count & Over/Under cards
            - কর্নার কিক (Corners) count & Over 9.5 / 10.5 corners
            - BTTS (Both Teams To Score / উভয় দল গোল করবে কি?)
            - Over/Under (ওভার/আন্ডার ২.৫ গোল বা ক্রিকেট ওভার/আন্ডার রান ও ছক্কা)
            - A to Z সম্পূর্ণ ম্যাচ প্রেডিকশন (A to Z Match Production)
            
            Guidelines:
            1. Language handling:
               - If the user asks in Bangla (e.g. "লাল কার্ড কর্নার হলুদ কাড btts over", "আজকের ম্যাচের প্রেডিকশন দাও", "lineup কি", "কে জিতবে?"), reply primarily in natural, enthusiastic Bengali (Bangla) with key English sports terms and clear bullet points.
               - If the user asks in English, reply in English.
               - If mixed or requested in both, provide both Bangla and English summary!
            2. Match analysis:
               - Give concrete statistics, percentages, and breakdowns for Red Cards, Yellow Cards, Corners, BTTS, and Over/Under.
               - Keep the tone analytical, fast, and exciting for sports fans.
        """.trimIndent()

        if (apiKey.isNotBlank() && apiKey != "MY_GEMINI_API_KEY") {
            try {
                val fullPrompt = "$systemInstruction\n\n$contextSummary\n\nUser Question: $userPrompt"

                val jsonBody = JSONObject().apply {
                    val contentsArray = JSONArray().apply {
                        val contentObj = JSONObject().apply {
                            val partsArray = JSONArray().apply {
                                put(JSONObject().put("text", fullPrompt))
                            }
                            put("parts", partsArray)
                        }
                        put(contentObj)
                    }
                    put("contents", contentsArray)
                }

                val mediaType = "application/json; charset=utf-8".toMediaType()
                val requestBody = jsonBody.toString().toRequestBody(mediaType)
                val request = Request.Builder()
                    .url("$BASE_URL?key=$apiKey")
                    .post(requestBody)
                    .build()

                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val bodyString = response.body?.string().orEmpty()
                    val responseJson = JSONObject(bodyString)
                    val text = responseJson.optJSONArray("candidates")
                        ?.optJSONObject(0)
                        ?.optJSONObject("content")
                        ?.optJSONArray("parts")
                        ?.optJSONObject(0)
                        ?.optString("text")

                    if (!text.isNullOrBlank()) {
                        return@withContext text.trim()
                    }
                } else {
                    Log.w(TAG, "Gemini API error code: ${response.code}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Gemini request failed: ${e.message}")
            }
        }

        // High quality fallback engine responding accurately in Bangla or English
        generateSmartLocalAnalysis(userPrompt, activeMatches)
    }

    private fun generateSmartLocalAnalysis(prompt: String, matches: List<Match>): String {
        val lower = prompt.lowercase()
        val isBangla = prompt.any { it.code in 0x0980..0x09FF } ||
                lower.contains("প্রেডিকশন") || lower.contains("লাইনআপ") || lower.contains("ম্যাচ")

        val liveCricket = matches.firstOrNull { it.sport == com.example.model.SportType.CRICKET && it.status == com.example.model.MatchStatus.LIVE }
        val liveFootball = matches.firstOrNull { it.sport == com.example.model.SportType.FOOTBALL && it.status == com.example.model.MatchStatus.LIVE }

        // A to Z Production, Cards, Corners, BTTS, Over/Under queries
        if (lower.contains("লাল কার্ড") || lower.contains("হলুদ") || lower.contains("কাড") || lower.contains("card") ||
            lower.contains("কর্নার") || lower.contains("corner") || lower.contains("btts") || lower.contains("over") ||
            lower.contains("under") || lower.contains("a ti z") || lower.contains("a to z") || lower.contains("production")) {
            return if (isBangla) {
                buildString {
                    appendLine("🔥 **GoalWicket A to Z সম্পূর্ণ ম্যাচ প্রেডিকশন ও লাইভ প্রডাকশন:**\n")
                    if (liveFootball != null) {
                        val atoz = liveFootball.atoz
                        appendLine("⚽ **${liveFootball.homeTeam} বনাম ${liveFootball.awayTeam} (${liveFootball.league.name}):**")
                        appendLine("🟨 **হলুদ কার্ড (Yellow Cards):** লাইভ ${atoz.yellowCardsHome}-${atoz.yellowCardsAway} | প্রেডিকশন: ${atoz.yellowCardsPrediction}")
                        appendLine("🟥 **লাল কার্ড (Red Cards):** লাইভ ${atoz.redCardsHome}-${atoz.redCardsAway} | ঝুঁকি: ${atoz.redCardRisk}")
                        appendLine("🚩 **কর্নার কিক (Corners):** লাইভ ${atoz.cornersHome}-${atoz.cornersAway} | প্রেডিকশন: ${atoz.cornersPrediction}")
                        appendLine("⚽ **BTTS (উভয় দল গোল করবে কি?):** ${atoz.bttsPredictionBangla} (হ্যাঁ ${atoz.bttsYesPercentage}% / না ${atoz.bttsNoPercentage}%)")
                        appendLine("📈 **Over / Under গোল:** Over 1.5 (${atoz.over1_5Goals}%), Over 2.5 (${atoz.over2_5Goals}%), Under 2.5 (${atoz.under2_5Goals}%)")
                        appendLine("🎯 **সঠিক স্কোর (Top Correct Scores):** ${atoz.topCorrectScores.joinToString { "${it.first} (${it.second}%)" }}")
                        appendLine("⏱️ **হাফ-টাইম / ফুল-টাইম:** ${atoz.halfTimeFullTimePrediction}\n")
                    }
                    if (liveCricket != null) {
                        val atoz = liveCricket.atoz
                        appendLine("🏏 **${liveCricket.homeTeam} বনাম ${liveCricket.awayTeam} (${liveCricket.league.name}):**")
                        appendLine("📊 **রান Over / Under:** ${atoz.cricketOverUnderRuns}")
                        appendLine("💥 **ছক্কা প্রেডিকশন (Total 6s):** ${atoz.cricketTotalSixesPrediction}")
                        appendLine("⚡ **পাওয়ারপ্লে (১-৬ ওভার):** ${atoz.cricketPowerplayPrediction}")
                        appendLine("🌟 **প্লেয়ার প্রডাকশন:** ${atoz.cricketTopPerformers}\n")
                    }
                    appendLine("✅ প্রতিটি ম্যাচের বিশদ কার্ড, কর্নার, BTTS ও ওভার/আন্ডার দেখতে ম্যাচ কার্ডে 'AI প্রেডিকশন'-এ ট্যাপ করুন!")
                }
            } else {
                buildString {
                    appendLine("🔥 **GoalWicket A to Z Complete Match Markets & Predictions:**\n")
                    if (liveFootball != null) {
                        val atoz = liveFootball.atoz
                        appendLine("⚽ **${liveFootball.homeTeam} vs ${liveFootball.awayTeam} (${liveFootball.league.name}):**")
                        appendLine("🟨 **Yellow Cards:** Live ${atoz.yellowCardsHome}-${atoz.yellowCardsAway} | ${atoz.yellowCardsPrediction}")
                        appendLine("🟥 **Red Cards Risk:** ${atoz.redCardRisk}")
                        appendLine("🚩 **Corners:** Live ${atoz.cornersHome}-${atoz.cornersAway} | ${atoz.cornersPrediction}")
                        appendLine("⚽ **BTTS (Both Teams To Score):** Yes ${atoz.bttsYesPercentage}% / No ${atoz.bttsNoPercentage}%")
                        appendLine("📈 **Over/Under Goals:** Over 1.5 (${atoz.over1_5Goals}%), Over 2.5 (${atoz.over2_5Goals}%), Under 2.5 (${atoz.under2_5Goals}%)")
                        appendLine("🎯 **Likely Scores:** ${atoz.topCorrectScores.joinToString { "${it.first} (${it.second}%)" }}\n")
                    }
                    if (liveCricket != null) {
                        val atoz = liveCricket.atoz
                        appendLine("🏏 **${liveCricket.homeTeam} vs ${liveCricket.awayTeam} (${liveCricket.league.name}):**")
                        appendLine("📊 **Runs Over/Under:** ${atoz.cricketOverUnderRuns}")
                        appendLine("💥 **Total 6s Forecast:** ${atoz.cricketTotalSixesPrediction}")
                        appendLine("⚡ **Powerplay Forecast:** ${atoz.cricketPowerplayPrediction}")
                    }
                    appendLine("✅ All A-to-Z production markets are refreshed in real-time every 4 seconds!")
                }
            }
        }

        // Lineup queries
        if (lower.contains("lineup") || lower.contains("লাইনআপ") || lower.contains("একাদশ") || lower.contains("squad")) {
            return if (isBangla) {
                buildString {
                    appendLine("📋 **লাইভ ম্যাচের অফিসিয়াল লাইনআপ:**\n")
                    if (liveCricket != null) {
                        appendLine("🏏 **${liveCricket.homeTeam} বনাম ${liveCricket.awayTeam} (${liveCricket.league.name})**")
                        appendLine("🔹 **${liveCricket.homeTeamCode} একাদশ:** ${liveCricket.homeLineup.filterNot { it.isSub }.take(6).joinToString { it.name }}...")
                        appendLine("🔹 **${liveCricket.awayTeamCode} একাদশ:** ${liveCricket.awayLineup.filterNot { it.isSub }.take(6).joinToString { it.name }}...\n")
                    }
                    if (liveFootball != null) {
                        appendLine("⚽ **${liveFootball.homeTeam} বনাম ${liveFootball.awayTeam} (${liveFootball.league.name})**")
                        appendLine("🔹 **${liveFootball.homeTeam} (${liveFootball.homeFormation}):** ${liveFootball.homeLineup.filterNot { it.isSub }.take(6).joinToString { it.name }}...")
                        appendLine("🔹 **${liveFootball.awayTeam} (${liveFootball.awayFormation}):** ${liveFootball.awayLineup.filterNot { it.isSub }.take(6).joinToString { it.name }}...")
                    }
                    appendLine("\n💡 সম্পূর্ণ সাবস্টিটিউট ও ট্যাকটিক্যাল ফরমেশন দেখতে যেকোনো ম্যাচ কার্ডে ট্যাপ করুন!")
                }
            } else {
                buildString {
                    appendLine("📋 **Official Live Match Lineups:**\n")
                    if (liveCricket != null) {
                        appendLine("🏏 **${liveCricket.homeTeam} vs ${liveCricket.awayTeam}**")
                        appendLine("• ${liveCricket.homeTeamCode} XI: ${liveCricket.homeLineup.filterNot { it.isSub }.take(5).joinToString { it.name }}...")
                        appendLine("• ${liveCricket.awayTeamCode} XI: ${liveCricket.awayLineup.filterNot { it.isSub }.take(5).joinToString { it.name }}...\n")
                    }
                    if (liveFootball != null) {
                        appendLine("⚽ **${liveFootball.homeTeam} vs ${liveFootball.awayTeam}**")
                        appendLine("• ${liveFootball.homeTeam} (${liveFootball.homeFormation}): ${liveFootball.homeLineup.filterNot { it.isSub }.take(5).joinToString { it.name }}...")
                        appendLine("• ${liveFootball.awayTeam} (${liveFootball.awayFormation}): ${liveFootball.awayLineup.filterNot { it.isSub }.take(5).joinToString { it.name }}...")
                    }
                    appendLine("\n👉 Tap any match card to see the pitch diagram and substitutes!")
                }
            }
        }

        // Prediction queries
        if (lower.contains("predict") || lower.contains("প্রেডিকশন") || lower.contains("কে জিতবে") || lower.contains("winner") || lower.contains("odds")) {
            return if (isBangla) {
                buildString {
                    appendLine("🔮 **GoalWicket এআই লাইভ ম্যাচ প্রেডিকশন:**\n")
                    matches.filter { it.status == com.example.model.MatchStatus.LIVE }.forEach { m ->
                        val emoji = if (m.sport == com.example.model.SportType.FOOTBALL) "⚽" else "🏏"
                        appendLine("$emoji **${m.homeTeam} বনাম ${m.awayTeam}** (${m.league.name})")
                        appendLine("📊 জয়ের সম্ভাবনা: **${m.homeTeamCode} ${m.winProbabilityHome}%** | **${m.awayTeamCode} ${m.winProbabilityAway}%**" +
                                if (m.winProbabilityDraw > 0) " | ড্র ${m.winProbabilityDraw}%" else "")
                        appendLine("🎯 বিশ্লেষণ: ${m.aiPredictionBangla}\n")
                    }
                    appendLine("🔥 এআই পরামর্শ: লাইভ মোমেন্টাম ও সাম্প্রতিক বল-বাই-বল আপডেটের ওপর ভিত্তি করে এই প্রেডিকশন প্রতি ৪ সেকেন্ডে স্বয়ংক্রিয়ভাবে আপডেট হয়।")
                }
            } else {
                buildString {
                    appendLine("🔮 **GoalWicket AI Live Match Predictions:**\n")
                    matches.filter { it.status == com.example.model.MatchStatus.LIVE }.forEach { m ->
                        val emoji = if (m.sport == com.example.model.SportType.FOOTBALL) "⚽" else "🏏"
                        appendLine("$emoji **${m.homeTeam} vs ${m.awayTeam}** (${m.league.name})")
                        appendLine("📊 Win Probability: **${m.homeTeamCode} ${m.winProbabilityHome}%** vs **${m.awayTeamCode} ${m.winProbabilityAway}%**" +
                                if (m.winProbabilityDraw > 0) " | Draw ${m.winProbabilityDraw}%" else "")
                        appendLine("💡 Insight: ${m.aiPredictionEnglish}\n")
                    }
                    appendLine("⚡ Live odds and probabilities update continuously every 4 seconds as match dynamics shift.")
                }
            }
        }

        // General questions in Bangla
        if (isBangla) {
            return "🎯 **GoalWicket AI স্পোর্টস অ্যাসিস্ট্যান্ট:**\n\n" +
                    "বর্তমানে চলছে হাই-ভোল্টেজ ক্রিকেট (IPL, BPL) এবং ফুটবল (Champions League, Premier League) লড়াই!\n\n" +
                    "• **আজকের সেরা ম্যাচ:** রিয়াল মাদ্রিদ বনাম ম্যানচেস্টার সিটি (২-২) এবং কেকেআর বনাম সিএসকে।\n" +
                    "• আপনি জিজ্ঞেস করতে পারেন: *'আজকের ম্যাচের প্রেডিকশন দাও'*, *'lineup কি'*, বা *'কে জিতবে?'*।\n" +
                    "• ১ মাসের সম্পূর্ণ ফিক্সচার ও লাইভ আপডেট সরাসরি এই অ্যাপে প্রতি ৪ সেকেন্ডে রিলোড হচ্ছে।"
        }

        // Default response in English
        return "🎯 **GoalWicket AI Sports Assistant:**\n\n" +
                "Live action is underway across Football (Champions League, Premier League) and Cricket (IPL, BPL, World Cup)!\n\n" +
                "• **Current Highlight:** Real Madrid vs Manchester City (Champions League) & KKR vs CSK (IPL).\n" +
                "• You can ask: *'Give me today's match prediction'*, *'What is the lineup?'*, or *'Who will win?'* (in English or Bangla).\n" +
                "• All live scores refresh automatically every 4 seconds with live ball-by-ball and minute-by-minute action!"
    }
}
