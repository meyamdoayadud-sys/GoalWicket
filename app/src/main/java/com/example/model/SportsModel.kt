package com.example.model

enum class SportType(val displayName: String, val iconEmoji: String) {
    FOOTBALL("Football", "⚽"),
    CRICKET("Cricket", "🏏")
}

enum class MatchStatus {
    LIVE,
    UPCOMING,
    FINISHED
}

data class League(
    val id: String,
    val name: String,
    val sport: SportType,
    val countryOrRegion: String,
    val logoEmoji: String
) {
    companion object {
        // Football Leagues
        val WORLD_CUP = League("fb_wc", "World Cup", SportType.FOOTBALL, "International", "🏆")
        val CHAMPIONS_LEAGUE = League("fb_ucl", "Champions League", SportType.FOOTBALL, "Europe", "⭐")
        val PREMIER_LEAGUE = League("fb_epl", "Premier League", SportType.FOOTBALL, "England", "🦁")
        val LA_LIGA = League("fb_laliga", "La Liga", SportType.FOOTBALL, "Spain", "🇪🇸")
        val SERIE_A = League("fb_seriea", "Serie A", SportType.FOOTBALL, "Italy", "🇮🇹")
        val BUNDESLIGA = League("fb_bundesliga", "Bundesliga", SportType.FOOTBALL, "Germany", "🇩🇪")
        val EUROPA_LEAGUE = League("fb_uel", "Europa League", SportType.FOOTBALL, "Europe", "🟠")
        val COPA_LIBERTADORES = League("fb_copa", "Copa Libertadores", SportType.FOOTBALL, "South America", "🌎")
        val NATIONS_LEAGUE = League("fb_unl", "Nations League", SportType.FOOTBALL, "Europe", "🌍")

        // Cricket Leagues
        val ICC_WORLD_CUP = League("cr_cwc", "ICC World Cup", SportType.CRICKET, "International", "🏆")
        val T20_WORLD_CUP = League("cr_t20wc", "T20 World Cup", SportType.CRICKET, "International", "⚡")
        val IPL = League("cr_ipl", "IPL", SportType.CRICKET, "India", "🇮🇳")
        val BPL = League("cr_bpl", "BPL", SportType.CRICKET, "Bangladesh", "🇧🇩")
        val PSL = League("cr_psl", "PSL", SportType.CRICKET, "Pakistan", "🇵🇰")
        val BIG_BASH = League("cr_bbl", "Big Bash", SportType.CRICKET, "Australia", "🇦🇺")
        val ASHES = League("cr_ashes", "Ashes", SportType.CRICKET, "Eng vs Aus", "🏺")
        val ASIA_CUP = League("cr_asiacup", "Asia Cup", SportType.CRICKET, "Asia", "🌏")

        val ALL_FOOTBALL_LEAGUES = listOf(
            WORLD_CUP, CHAMPIONS_LEAGUE, PREMIER_LEAGUE, LA_LIGA,
            SERIE_A, BUNDESLIGA, EUROPA_LEAGUE, COPA_LIBERTADORES, NATIONS_LEAGUE
        )

        val ALL_CRICKET_LEAGUES = listOf(
            ICC_WORLD_CUP, T20_WORLD_CUP, IPL, BPL,
            PSL, BIG_BASH, ASHES, ASIA_CUP
        )

        val ALL_LEAGUES = ALL_FOOTBALL_LEAGUES + ALL_CRICKET_LEAGUES
    }
}

data class Player(
    val name: String,
    val number: Int,
    val role: String, // e.g. "FW", "MF", "DF", "GK" or "Batsman", "Bowler", "All-Rounder", "Wicket-Keeper"
    val isCaptain: Boolean = false,
    val isWicketKeeper: Boolean = false,
    val isSub: Boolean = false,
    val performanceStat: String = "", // e.g. "1 Goal (36'), 2 Assists" or "48 (32b), 1/24"
    val avatarEmoji: String = "👤", // Distinctive face avatar emoji e.g. 👨🏻‍🦱, 🧔🏻, 👨🏾‍🦲, 🧑🏼, 👨🏻, 🧔🏽, 🧑🏿, 👱🏻‍♂️
    val goals: Int = 0, // কে গোল করেছে
    val goalMinutes: List<String> = emptyList(), // e.g. ["12'"], ["36'"]
    val yellowCards: Int = 0, // কে ইয়োলো কার্ড পেয়েছে
    val yellowCardMinute: String = "", // e.g. "55'"
    val redCards: Int = 0, // কে লাল কার্ড পেয়েছে
    val redCardMinute: String = "", // e.g. "78'"
    val assists: Int = 0,
    val rating: String = "7.5",
    val countryFlag: String = ""
)

data class LiveEvent(
    val id: String,
    val timeOrOver: String,
    val title: String,
    val description: String,
    val type: EventType,
    val team: String
)

enum class EventType {
    GOAL,
    WICKET,
    FOUR,
    SIX,
    YELLOW_CARD,
    RED_CARD,
    SUBSTITUTION,
    COMMENTARY
}

data class AtoZStatsAndPrediction(
    // Football Card Stats & Predictions (হলুদ কার্ড ও লাল কার্ড)
    var yellowCardsHome: Int = 1,
    var yellowCardsAway: Int = 2,
    var redCardsHome: Int = 0,
    var redCardsAway: Int = 0,
    val yellowCardsPrediction: String = "Over 3.5 হলুদ কার্ড (সম্ভাবনা ৭২%)",
    val redCardRisk: String = "লাল কার্ডের ঝুঁকি: কম (১৮% সম্ভাবনা)",

    // Corner Kicks (কর্নার কিক)
    var cornersHome: Int = 5,
    var cornersAway: Int = 4,
    val cornersPrediction: String = "Over 9.5 কর্নার কিক (সম্ভাবনা ৬৮%)",

    // BTTS (Both Teams To Score / উভয় দল গোল করবে কি?)
    val bttsYesPercentage: Int = 76,
    val bttsNoPercentage: Int = 24,
    val bttsPredictionBangla: String = "BTTS: হ্যাঁ (Both Teams to Score - ৭৬% নিশ্চিত)",

    // Over / Under Goals & Runs (ওভার / আন্ডার গোল)
    val over1_5Goals: Int = 88,
    val over2_5Goals: Int = 66,
    val under2_5Goals: Int = 34,
    val over3_5Goals: Int = 38,
    val overUnderPredictionBangla: String = "Over 2.5 গোল (সম্ভাবনা ৬৬%)",

    // Correct Score & HT/FT (সঠিক স্কোর ও হাফ-টাইম)
    val topCorrectScores: List<Pair<String, Int>> = listOf("2-1" to 32, "2-2" to 22, "3-1" to 19),
    val halfTimeFullTimePrediction: String = "HT/FT: ড্র / হোম জয় (সম্ভাবনা ৪১%)",

    // Double Chance Prediction (ডাবল চান্স প্রেডিকশন: 1X, 12, X2)
    val doubleChance1X: Int = 70, // Home Win or Draw
    val doubleChance12: Int = 72, // Home Win or Away Win (No Draw)
    val doubleChanceX2: Int = 58, // Away Win or Draw
    val doubleChanceBestPick: String = "1X (হোম জয় বা ড্র - ৭০% নিশ্চিত)",
    val doubleChanceExplanation: String = "হোম মাঠের সুবিধা এবং শক্তিশালী ডিফেন্সের কারণে ১X ডাবল চান্স সবচেয়ে নিরাপদ ও নির্ভরযোগ্য প্রেডিকশন।",

    // Cricket specific A-Z (রান ওভার/আন্ডার, ছক্কা, পাওয়ারপ্লে)
    val cricketOverUnderRuns: String = "Over 176.5 রান (সম্ভাবনা ৬৪%)",
    val cricketTotalSixesPrediction: String = "Over 12.5 ছক্কা (সম্ভাবনা ৭২%)",
    val cricketPowerplayPrediction: String = "পাওয়ারপ্লে (১-৬ ওভার): ৫২-৫৮ রান",
    val cricketTopPerformers: String = "টপ ব্যাটার: ওপেনার ব্যাটার | টপ বোলার: ডেথ পেসার (২+ উইকেট)"
)

data class LiveTvChannel(
    val id: String,
    val name: String,
    val quality: String = "Full HD 1080p",
    val language: String = "বাংলা / English",
    val isLive: Boolean = true,
    val serverBadge: String = "Server 1 • Ultra Fast"
)

data class LiveVideoItem(
    val id: String,
    val title: String,
    val timeOrMinute: String,
    val duration: String,
    val badge: String, // "LIVE TV", "GOAL", "SIX", "HIGHLIGHTS"
    val channelOrSource: String,
    val quality: String = "1080p 60fps",
    val views: String = "142K views",
    val videoSimDescription: String = "HD Broadcast Stream with Bengali & English commentary"
)

data class Match(
    val id: String,
    val sport: SportType,
    val league: League,
    val homeTeam: String,
    val awayTeam: String,
    val homeTeamCode: String,
    val awayTeamCode: String,
    val homeFlagEmoji: String,
    val awayFlagEmoji: String,
    var homeScore: String,
    var awayScore: String,
    var status: MatchStatus,
    val matchTime: String,
    val date: String, // YYYY-MM-DD
    val venue: String,
    var liveClock: String,
    var statusSummary: String,
    var currentStrikerOrAttacker: String = "",
    var currentBowlerOrDefender: String = "",
    val winProbabilityHome: Int,
    val winProbabilityDraw: Int,
    val winProbabilityAway: Int,
    val aiPredictionBangla: String,
    val aiPredictionEnglish: String,
    val homeFormation: String = "4-3-3",
    val awayFormation: String = "4-2-3-1",
    val homeLineup: List<Player>,
    val awayLineup: List<Player>,
    var recentEvents: List<LiveEvent> = emptyList(),
    var isUserFavorite: Boolean = false,
    var atoz: AtoZStatsAndPrediction = AtoZStatsAndPrediction(),
    var liveTvChannels: List<LiveTvChannel> = emptyList(),
    var videoHighlights: List<LiveVideoItem> = emptyList()
)

data class ChatMessage(
    val id: String,
    val text: String,
    val isUser: Boolean,
    val timestamp: String,
    val matchedMatchId: String? = null
)
