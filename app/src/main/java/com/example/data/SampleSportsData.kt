package com.example.data

import com.example.model.*

object SampleSportsData {

    fun getInitialMatches(): List<Match> {
        return listOf(
            // ==================== LIVE CRICKET (IPL) ====================
            Match(
                id = "match_cr_ipl_1",
                sport = SportType.CRICKET,
                league = League.IPL,
                homeTeam = "Kolkata Knight Riders",
                awayTeam = "Chennai Super Kings",
                homeTeamCode = "KKR",
                awayTeamCode = "CSK",
                homeFlagEmoji = "🟣",
                awayFlagEmoji = "🟡",
                homeScore = "186/4",
                awayScore = "168/6",
                status = MatchStatus.LIVE,
                matchTime = "19:30 IST",
                date = "2026-09-26",
                venue = "Eden Gardens, Kolkata",
                liveClock = "18.3 ov",
                statusSummary = "CSK need 19 runs in 9 balls",
                currentStrikerOrAttacker = "MS Dhoni 28* (11b, 3x4, 2x6)",
                currentBowlerOrDefender = "Mitchell Starc 3.3-0-38-2",
                winProbabilityHome = 56,
                winProbabilityDraw = 0,
                winProbabilityAway = 44,
                aiPredictionBangla = "⚡ ম্যাচ প্রেডিকশন: ইডেন গার্ডেন্সের পিচে শেষ দুই ওভারে বল কিছুটা গ্রিপ করছে। তবে ধোনির ফিনিশিং পাওয়ার অসাধারণ। মিচেল স্টার্কের ইয়র্কার নিখুঁত হলে কেকেআরের জয়ের সম্ভাবনা ৫৬%।",
                aiPredictionEnglish = "⚡ Match Prediction: Eden Gardens pitch offers slight reverse swing. With Dhoni at the crease CSK have fighting momentum, but Starc's death-over accuracy gives KKR a slight 56% win probability.",
                homeFormation = "Playing XI + Impact",
                awayFormation = "Playing XI + Impact",
                homeLineup = listOf(
                    Player("Phil Salt", 21, "Wicket-Keeper", isWicketKeeper = true, performanceStat = "54 (28)", avatarEmoji = "👱🏻‍♂️", rating = "8.3", countryFlag = "🏴󠁧󠁢󠁥󠁮󠁧󠁿"),
                    Player("Sunil Narine", 74, "All-Rounder", performanceStat = "36 (18) & 1/26", avatarEmoji = "🧑🏾‍🦱", rating = "8.6", countryFlag = "🌴"),
                    Player("Shreyas Iyer", 96, "Batsman", isCaptain = true, performanceStat = "42 (30)", avatarEmoji = "🧑🏽", rating = "7.9", countryFlag = "🇮🇳"),
                    Player("Venkatesh Iyer", 25, "All-Rounder", performanceStat = "22 (16)", avatarEmoji = "🧑🏽", rating = "7.2", countryFlag = "🇮🇳"),
                    Player("Rinku Singh", 35, "Batsman", performanceStat = "24* (9)", avatarEmoji = "🧔🏻", rating = "8.5", countryFlag = "🇮🇳"),
                    Player("Andre Russell", 12, "All-Rounder", performanceStat = "1/31", avatarEmoji = "🧑🏿", rating = "7.8", countryFlag = "🌴"),
                    Player("Ramandeep Singh", 19, "All-Rounder", avatarEmoji = "🧑🏽", rating = "7.0", countryFlag = "🇮🇳"),
                    Player("Mitchell Starc", 56, "Bowler", performanceStat = "2/38", avatarEmoji = "👱🏻‍♂️", rating = "7.6", countryFlag = "🇦🇺"),
                    Player("Harshit Rana", 22, "Bowler", performanceStat = "1/34", avatarEmoji = "🧑🏽", rating = "7.4", countryFlag = "🇮🇳"),
                    Player("Varun Chakaravarthy", 29, "Bowler", performanceStat = "2/22", avatarEmoji = "🧔🏻", rating = "8.4", countryFlag = "🇮🇳"),
                    Player("Vaibhav Arora", 99, "Bowler", avatarEmoji = "🧑🏽", rating = "7.1", countryFlag = "🇮🇳"),
                    // Bench
                    Player("Manish Pandey", 9, "Batsman", isSub = true, avatarEmoji = "🧑🏽", countryFlag = "🇮🇳"),
                    Player("Rahmanullah Gurbaz", 21, "Wicket-Keeper", isSub = true, avatarEmoji = "🧔🏻", countryFlag = "🇦🇫"),
                    Player("Anukul Roy", 6, "All-Rounder", isSub = true, avatarEmoji = "🧑🏽", countryFlag = "🇮🇳")
                ),
                awayLineup = listOf(
                    Player("Ruturaj Gaikwad", 31, "Batsman", isCaptain = true, performanceStat = "48 (32)", avatarEmoji = "🧑🏽", rating = "8.2", countryFlag = "🇮🇳"),
                    Player("Rachin Ravindra", 8, "All-Rounder", performanceStat = "24 (15)", avatarEmoji = "🧑🏽", rating = "7.4", countryFlag = "🇳🇿"),
                    Player("Daryl Mitchell", 75, "Batsman", performanceStat = "31 (22)", avatarEmoji = "🧔🏼", rating = "7.5", countryFlag = "🇳🇿"),
                    Player("Shivam Dube", 25, "All-Rounder", performanceStat = "35 (19)", avatarEmoji = "🧑🏽", rating = "8.1", countryFlag = "🇮🇳"),
                    Player("Ravindra Jadeja", 8, "All-Rounder", performanceStat = "12 (10) & 1/28", avatarEmoji = "🧔🏻", rating = "7.7", countryFlag = "🇮🇳"),
                    Player("Sameer Rizvi", 1, "Batsman", avatarEmoji = "🧑🏽", rating = "6.9", countryFlag = "🇮🇳"),
                    Player("MS Dhoni", 7, "Wicket-Keeper", isWicketKeeper = true, performanceStat = "28* (11)", avatarEmoji = "🧔🏻", rating = "8.8", countryFlag = "🇮🇳"),
                    Player("Deepak Chahar", 90, "Bowler", performanceStat = "1/40", avatarEmoji = "🧑🏽", rating = "6.8", countryFlag = "🇮🇳"),
                    Player("Shardul Thakur", 54, "All-Rounder", performanceStat = "1/36", avatarEmoji = "🧑🏽", rating = "7.0", countryFlag = "🇮🇳"),
                    Player("Tushar Deshpande", 24, "Bowler", performanceStat = "1/35", avatarEmoji = "🧑🏽", rating = "7.1", countryFlag = "🇮🇳"),
                    Player("Matheesha Pathirana", 81, "Bowler", performanceStat = "2/28", avatarEmoji = "🧑🏽", rating = "8.5", countryFlag = "🇱🇰"),
                    // Bench
                    Player("Moeen Ali", 18, "All-Rounder", isSub = true, avatarEmoji = "🧔🏻", countryFlag = "🏴󠁧󠁢󠁥󠁮󠁧󠁿"),
                    Player("Ajinkya Rahane", 3, "Batsman", isSub = true, avatarEmoji = "🧑🏽", countryFlag = "🇮🇳"),
                    Player("Mustafizur Rahman", 90, "Bowler", isSub = true, avatarEmoji = "🧑🏽", countryFlag = "🇧🇩")
                ),
                recentEvents = listOf(
                    LiveEvent("ev_1", "18.3", "SIX!", "Dhoni hammers a towering six over deep mid-wicket!", EventType.SIX, "CSK"),
                    LiveEvent("ev_2", "18.2", "TWO RUNS", "Driven through cover, aggressive running between wickets", EventType.COMMENTARY, "CSK"),
                    LiveEvent("ev_3", "18.1", "DOT BALL", "Starc fires a wide toe-crusher yorker, beaten outside off", EventType.COMMENTARY, "KKR"),
                    LiveEvent("ev_4", "17.4", "WICKET!", "Jadeja caught at long-on by Rinku off Harshit Rana!", EventType.WICKET, "KKR")
                )
            ),

            // ==================== LIVE FOOTBALL (CHAMPIONS LEAGUE) ====================
            Match(
                id = "match_fb_ucl_1",
                sport = SportType.FOOTBALL,
                league = League.CHAMPIONS_LEAGUE,
                homeTeam = "Real Madrid",
                awayTeam = "Manchester City",
                homeTeamCode = "RMA",
                awayTeamCode = "MCI",
                homeFlagEmoji = "⚪",
                awayFlagEmoji = "🩵",
                homeScore = "2",
                awayScore = "2",
                status = MatchStatus.LIVE,
                matchTime = "20:00 CET",
                date = "2026-09-26",
                venue = "Santiago Bernabéu, Madrid",
                liveClock = "78'",
                statusSummary = "Second Half • Fast Paced Thriller",
                currentStrikerOrAttacker = "Kylian Mbappé on counter attack",
                currentBowlerOrDefender = "Rúben Dias leading defensive block",
                winProbabilityHome = 42,
                winProbabilityDraw = 28,
                winProbabilityAway = 30,
                aiPredictionBangla = "⚡ ম্যাচ প্রেডিকশন: রিয়াল মাদ্রিদের কাউন্টার অ্যাটাকে ভিনিসিয়াস এবং এমবাপ্পে চরম বিপজ্জনক। ম্যান সিটির বল পজেশন ৬২% হলেও বার্নাব্যুতে শেষ ১৫ মিনিটে মাদ্রিদ গোলের দারুণ সুযোগ তৈরি করবে। প্রেডিকশন: ৩-২ রিয়াল মাদ্রিদের পক্ষে।",
                aiPredictionEnglish = "⚡ Match Prediction: High tactical intensity. While Man City control 62% possession, Real Madrid's transitions through Vinicius and Mbappé are razor sharp. Predicted outcome: 3-2 Madrid win or 2-2 draw.",
                homeFormation = "4-3-1-2",
                awayFormation = "4-2-3-1",
                homeLineup = listOf(
                    Player("Thibaut Courtois", 1, "GK", performanceStat = "5 Saves", avatarEmoji = "🧔🏻", rating = "8.2", countryFlag = "🇧🇪"),
                    Player("Dani Carvajal", 2, "DF", isCaptain = true, avatarEmoji = "🧔🏻", yellowCards = 1, yellowCardMinute = "55'", rating = "6.8", countryFlag = "🇪🇸"),
                    Player("Éder Militão", 3, "DF", avatarEmoji = "👨🏾‍🦲", rating = "7.2", countryFlag = "🇧🇷"),
                    Player("Antonio Rüdiger", 22, "DF", avatarEmoji = "🧑🏿", yellowCards = 1, yellowCardMinute = "71'", rating = "7.4", countryFlag = "🇩🇪"),
                    Player("Ferland Mendy", 23, "DF", avatarEmoji = "🧑🏿", rating = "7.1", countryFlag = "🇫🇷"),
                    Player("Federico Valverde", 8, "MF", performanceStat = "1 Goal (36')", avatarEmoji = "👱🏻‍♂️", goals = 1, goalMinutes = listOf("36'"), rating = "8.6", countryFlag = "🇺🇾"),
                    Player("Aurélien Tchouaméni", 14, "MF", avatarEmoji = "👨🏾‍🦲", redCards = 1, redCardMinute = "78'", rating = "6.2", countryFlag = "🇫🇷"),
                    Player("Eduardo Camavinga", 6, "MF", avatarEmoji = "🧑🏿", rating = "7.5", countryFlag = "🇫🇷"),
                    Player("Jude Bellingham", 5, "MF", performanceStat = "1 Assist", avatarEmoji = "👨🏽", assists = 1, rating = "8.3", countryFlag = "🏴󠁧󠁢󠁥󠁮󠁧󠁿"),
                    Player("Rodrygo", 11, "FW", performanceStat = "1 Goal (12')", avatarEmoji = "👨🏽", goals = 1, goalMinutes = listOf("12'"), rating = "8.4", countryFlag = "🇧🇷"),
                    Player("Kylian Mbappé", 9, "FW", performanceStat = "4 Shots on target", avatarEmoji = "👨🏾‍🦲", rating = "8.0", countryFlag = "🇫🇷"),
                    // Bench
                    Player("Vinícius Júnior", 7, "FW", isSub = true, avatarEmoji = "👨🏾‍🦱", countryFlag = "🇧🇷"),
                    Player("Luka Modrić", 10, "MF", isSub = true, avatarEmoji = "👱🏼‍♂️", countryFlag = "🇭🇷"),
                    Player("Brahim Díaz", 21, "FW", isSub = true, avatarEmoji = "👨🏻", countryFlag = "🇲🇦"),
                    Player("Andriy Lunin", 13, "GK", isSub = true, avatarEmoji = "👱🏻‍♂️", countryFlag = "🇺🇦")
                ),
                awayLineup = listOf(
                    Player("Ederson", 31, "GK", performanceStat = "4 Saves", avatarEmoji = "🧔🏽", rating = "7.8", countryFlag = "🇧🇷"),
                    Player("Kyle Walker", 2, "DF", isCaptain = true, avatarEmoji = "👨🏾‍🦲", rating = "7.0", countryFlag = "🏴󠁧󠁢󠁥󠁮󠁧󠁿"),
                    Player("Rúben Dias", 3, "DF", avatarEmoji = "🧔🏻", rating = "7.3", countryFlag = "🇵🇹"),
                    Player("Manuel Akanji", 25, "DF", avatarEmoji = "👨🏾‍🦲", rating = "7.1", countryFlag = "🇨🇭"),
                    Player("Joško Gvardiol", 24, "DF", performanceStat = "1 Goal (66')", avatarEmoji = "🧔🏻", goals = 1, goalMinutes = listOf("66'"), rating = "8.5", countryFlag = "🇭🇷"),
                    Player("Rodri", 16, "MF", performanceStat = "91% Pass Acc", avatarEmoji = "👨🏻", yellowCards = 1, yellowCardMinute = "62'", rating = "7.7", countryFlag = "🇪🇸"),
                    Player("Mateo Kovačić", 8, "MF", avatarEmoji = "🧔🏻", rating = "7.2", countryFlag = "🇭🇷"),
                    Player("Bernardo Silva", 20, "MF", performanceStat = "1 Goal (2')", avatarEmoji = "🧔🏻", goals = 1, goalMinutes = listOf("2'"), rating = "8.2", countryFlag = "🇵🇹"),
                    Player("Kevin De Bruyne", 17, "MF", performanceStat = "2 Assists", avatarEmoji = "👱🏻‍♂️", assists = 2, rating = "8.7", countryFlag = "🇧🇪"),
                    Player("Phil Foden", 47, "FW", avatarEmoji = "👱🏻‍♂️", rating = "7.4", countryFlag = "🏴󠁧󠁢󠁥󠁮󠁧󠁿"),
                    Player("Erling Haaland", 9, "FW", performanceStat = "3 Aerial Duels Won", avatarEmoji = "👱🏼‍♂️", rating = "7.5", countryFlag = "🇳🇴"),
                    // Bench
                    Player("Jérémy Doku", 11, "FW", isSub = true, avatarEmoji = "🧑🏿", countryFlag = "🇧🇪"),
                    Player("Jack Grealish", 10, "MF", isSub = true, avatarEmoji = "👱🏼‍♂️", countryFlag = "🏴󠁧󠁢󠁥󠁮󠁧󠁿"),
                    Player("Stefan Ortega", 18, "GK", isSub = true, avatarEmoji = "🧔🏻", countryFlag = "🇩🇪")
                ),
                recentEvents = listOf(
                    LiveEvent("ev_ucl_0", "78'", "RED CARD!", "Aurélien Tchouaméni receives straight red card for a reckless challenge on Grealish!", EventType.RED_CARD, "RMA"),
                    LiveEvent("ev_ucl_1", "76'", "CHANCE!", "Mbappé unleashes a stinging strike from 20 yards, tipped over by Ederson!", EventType.COMMENTARY, "RMA"),
                    LiveEvent("ev_ucl_2", "66'", "GOAL! 2-2", "Joško Gvardiol fires a thunderbolt into the top right corner!", EventType.GOAL, "MCI"),
                    LiveEvent("ev_ucl_3", "55'", "YELLOW CARD", "Carvajal booked for tactical foul on Foden", EventType.YELLOW_CARD, "RMA"),
                    LiveEvent("ev_ucl_4", "36'", "GOAL! 2-1", "Federico Valverde with a venomous volley into the bottom corner!", EventType.GOAL, "RMA"),
                    LiveEvent("ev_ucl_5", "12'", "GOAL! 1-1", "Rodrygo slides past Akanji and nets clinically into the corner!", EventType.GOAL, "RMA"),
                    LiveEvent("ev_ucl_6", "2'", "GOAL! 0-1", "Bernardo Silva curls a clever free-kick past the wall!", EventType.GOAL, "MCI")
                )
            ),

            // ==================== LIVE CRICKET (BPL) ====================
            Match(
                id = "match_cr_bpl_1",
                sport = SportType.CRICKET,
                league = League.BPL,
                homeTeam = "Comilla Victorians",
                awayTeam = "Fortune Barishal",
                homeTeamCode = "CV",
                awayTeamCode = "FB",
                homeFlagEmoji = "🔴",
                awayFlagEmoji = "🔵",
                homeScore = "172/5",
                awayScore = "154/3",
                status = MatchStatus.LIVE,
                matchTime = "18:00 BST",
                date = "2026-09-26",
                venue = "Sher-e-Bangla National Cricket Stadium, Mirpur",
                liveClock = "16.4 ov",
                statusSummary = "Barishal need 19 runs in 20 balls",
                currentStrikerOrAttacker = "Tamim Iqbal 72* (51b, 7x4, 3x6)",
                currentBowlerOrDefender = "Mustafizur Rahman 3.4-0-28-2",
                winProbabilityHome = 35,
                winProbabilityDraw = 0,
                winProbabilityAway = 65,
                aiPredictionBangla = "⚡ ম্যাচ প্রেডিকশন: তামিম ইকবালের দুর্দান্ত অধিনায়কোচিত ব্যাটিং বরিশালকে চালকের আসনে রেখেছে। মোস্তাফিজের কাটার মিরপুরের স্লো ট্র্যাকে কিছুটা পার্থক্য গড়তে পারে, তবে বরিশালের জয়ের সম্ভাবনা ৬৫%।",
                aiPredictionEnglish = "⚡ Match Prediction: Tamim Iqbal's composed captain's knock puts Fortune Barishal firmly in the driver's seat. Mustafizur's cutters pose a threat, but Barishal hold a 65% win probability.",
                homeFormation = "Playing XI",
                awayFormation = "Playing XI",
                homeLineup = listOf(
                    Player("Litton Das", 16, "Wicket-Keeper", isCaptain = true, isWicketKeeper = true, performanceStat = "46 (30)"),
                    Player("Will Jacks", 15, "Batsman", performanceStat = "38 (21)"),
                    Player("Towhid Hridoy", 77, "Batsman", performanceStat = "29 (24)"),
                    Player("Moeen Ali", 18, "All-Rounder", performanceStat = "25 (14)"),
                    Player("Jaker Ali", 80, "Batsman", performanceStat = "18* (10)"),
                    Player("Andre Russell", 12, "All-Rounder"),
                    Player("Tanvir Islam", 9, "Bowler", performanceStat = "1/22"),
                    Player("Mustafizur Rahman", 90, "Bowler", performanceStat = "2/28"),
                    Player("Sunil Narine", 74, "All-Rounder", performanceStat = "0/30"),
                    Player("Rishad Hossain", 32, "Bowler"),
                    Player("Aliss Islam", 8, "Bowler"),
                    // Bench
                    Player("Imrul Kayes", 62, "Batsman", isSub = true),
                    Player("Mahidul Islam", 11, "Wicket-Keeper", isSub = true)
                ),
                awayLineup = listOf(
                    Player("Tamim Iqbal", 28, "Batsman", isCaptain = true, performanceStat = "72* (51)"),
                    Player("Ahmed Shehzad", 19, "Batsman", performanceStat = "22 (14)"),
                    Player("Soumya Sarkar", 59, "All-Rounder", performanceStat = "28 (20)"),
                    Player("Mushfiqur Rahim", 15, "Wicket-Keeper", isWicketKeeper = true, performanceStat = "18* (12)"),
                    Player("Mahmudullah Riyad", 30, "All-Rounder"),
                    Player("Kyle Mayers", 71, "All-Rounder", performanceStat = "1/25"),
                    Player("Mehidy Hasan Miraz", 53, "All-Rounder", performanceStat = "2/29"),
                    Player("Mohammad Saifuddin", 4, "Bowler", performanceStat = "1/38"),
                    Player("Taijul Islam", 42, "Bowler", performanceStat = "1/24"),
                    Player("Obed McCoy", 61, "Bowler"),
                    Player("Akif Javed", 14, "Bowler"),
                    // Bench
                    Player("Pritom Kumar", 9, "Wicket-Keeper", isSub = true),
                    Player("Kamrul Islam Rabbi", 45, "Bowler", isSub = true)
                ),
                recentEvents = listOf(
                    LiveEvent("ev_bpl_1", "16.4", "FOUR!", "Tamim steps out and lofts over mid-off for a boundary!", EventType.FOUR, "FB"),
                    LiveEvent("ev_bpl_2", "16.2", "SINGLE", "Mushfiqur guides it fine to third man", EventType.COMMENTARY, "FB"),
                    LiveEvent("ev_bpl_3", "15.5", "SIX!", "Tamim picks up the slower ball and deposits it over square leg!", EventType.SIX, "FB")
                )
            ),

            // ==================== LIVE FOOTBALL (PREMIER LEAGUE) ====================
            Match(
                id = "match_fb_epl_1",
                sport = SportType.FOOTBALL,
                league = League.PREMIER_LEAGUE,
                homeTeam = "Arsenal",
                awayTeam = "Liverpool",
                homeTeamCode = "ARS",
                awayTeamCode = "LIV",
                homeFlagEmoji = "🔴",
                awayFlagEmoji = "🟢",
                homeScore = "1",
                awayScore = "0",
                status = MatchStatus.LIVE,
                matchTime = "16:30 GMT",
                date = "2026-09-26",
                venue = "Emirates Stadium, London",
                liveClock = "52'",
                statusSummary = "Second Half Underway",
                currentStrikerOrAttacker = "Bukayo Saka pressing on right wing",
                currentBowlerOrDefender = "Virgil van Dijk marshaling backline",
                winProbabilityHome = 58,
                winProbabilityDraw = 24,
                winProbabilityAway = 18,
                aiPredictionBangla = "⚡ ম্যাচ প্রেডিকশন: আর্সেনালের হাই প্রেসিংয়ে লিভারপুলের বিল্ডআপে কিছুটা ব্যাঘাত ঘটছে। সাকা এবং ওডেগার্ডের কম্বিনেশন খুবই কার্যকর। তবে সালাহর এক ঝলক ম্যাচ বদলে দিতে পারে। প্রেডিকশন: ২-১ আর্সেনালের জয়।",
                aiPredictionEnglish = "⚡ Match Prediction: Arsenal's organized midfield triangle is stifling Liverpool's transitions. Saka and Odegaard are dominating the right flank. Expected score: 2-1 to Arsenal.",
                homeFormation = "4-3-3",
                awayFormation = "4-3-3",
                homeLineup = listOf(
                    Player("David Raya", 22, "GK", performanceStat = "3 Saves"),
                    Player("Ben White", 4, "DF"),
                    Player("William Saliba", 2, "DF"),
                    Player("Gabriel Magalhães", 6, "DF"),
                    Player("Jurriën Timber", 12, "DF"),
                    Player("Thomas Partey", 5, "MF"),
                    Player("Declan Rice", 41, "MF"),
                    Player("Martin Ødegaard", 8, "MF", isCaptain = true, performanceStat = "1 Assist"),
                    Player("Bukayo Saka", 7, "FW", performanceStat = "1 Goal (18')"),
                    Player("Kai Havertz", 29, "FW"),
                    Player("Gabriel Martinelli", 11, "FW"),
                    // Bench
                    Player("Leandro Trossard", 19, "FW", isSub = true),
                    Player("Jorginho", 20, "MF", isSub = true),
                    Player("Gabriel Jesus", 9, "FW", isSub = true)
                ),
                awayLineup = listOf(
                    Player("Alisson Becker", 1, "GK", performanceStat = "4 Saves"),
                    Player("Trent Alexander-Arnold", 66, "DF"),
                    Player("Ibrahima Konaté", 5, "DF"),
                    Player("Virgil van Dijk", 4, "DF", isCaptain = true),
                    Player("Andy Robertson", 26, "DF"),
                    Player("Ryan Gravenberch", 38, "MF"),
                    Player("Alexis Mac Allister", 10, "MF"),
                    Player("Dominik Szoboszlai", 8, "MF"),
                    Player("Mohamed Salah", 11, "FW", performanceStat = "3 Shots"),
                    Player("Darwin Núñez", 9, "FW"),
                    Player("Luis Díaz", 7, "FW"),
                    // Bench
                    Player("Cody Gakpo", 18, "FW", isSub = true),
                    Player("Curtis Jones", 17, "MF", isSub = true),
                    Player("Diogo Jota", 20, "FW", isSub = true)
                ),
                recentEvents = listOf(
                    LiveEvent("ev_epl_1", "49'", "FOUL", "Mac Allister brings down Saka on the halfway line", EventType.COMMENTARY, "LIV"),
                    LiveEvent("ev_epl_2", "18'", "GOAL! 1-0", "Bukayo Saka cuts inside Robertson and smashes near post!", EventType.GOAL, "ARS")
                )
            ),

            // ==================== UPCOMING & FIXTURES (1-MONTH LIST) ====================
            // Football: La Liga
            Match(
                id = "match_fb_laliga_1",
                sport = SportType.FOOTBALL,
                league = League.LA_LIGA,
                homeTeam = "Barcelona",
                awayTeam = "Atletico Madrid",
                homeTeamCode = "BAR",
                awayTeamCode = "ATM",
                homeFlagEmoji = "🔵🔴",
                awayFlagEmoji = "🔴⚪",
                homeScore = "-",
                awayScore = "-",
                status = MatchStatus.UPCOMING,
                matchTime = "21:00 CET",
                date = "2026-09-27",
                venue = "Camp Nou, Barcelona",
                liveClock = "Tomorrow",
                statusSummary = "El Cholo vs Hansi Flick Masterclass",
                winProbabilityHome = 51,
                winProbabilityDraw = 25,
                winProbabilityAway = 24,
                aiPredictionBangla = "⚡ বার্সেলোনা আক্রমণাত্মক ফুটবলে এগিয়ে থাকবে। লামিন ইয়ামাল ও পেদ্রির ফর্ম প্রতিপক্ষকে চাপে রাখবে। প্রেডিকশন: ২-১ বার্সার পক্ষে।",
                aiPredictionEnglish = "⚡ Barcelona's dynamic pressing with Yamal and Pedri will test Atletico's low block. Predicted: 2-1 Barcelona victory.",
                homeFormation = "4-2-3-1",
                awayFormation = "5-3-2",
                homeLineup = listOf(
                    Player("Marc-André ter Stegen", 1, "GK", isCaptain = true),
                    Player("Jules Koundé", 23, "DF"),
                    Player("Pau Cubarsí", 2, "DF"),
                    Player("Iñigo Martínez", 5, "DF"),
                    Player("Alejandro Balde", 3, "DF"),
                    Player("Marc Casadó", 17, "MF"),
                    Player("Pedri", 8, "MF"),
                    Player("Lamine Yamal", 19, "FW"),
                    Player("Dani Olmo", 20, "MF"),
                    Player("Raphinha", 11, "FW"),
                    Player("Robert Lewandowski", 9, "FW"),
                    Player("Ferran Torres", 7, "FW", isSub = true)
                ),
                awayLineup = listOf(
                    Player("Jan Oblak", 13, "GK", isCaptain = true),
                    Player("Nahuel Molina", 16, "DF"),
                    Player("Robin Le Normand", 24, "DF"),
                    Player("José María Giménez", 2, "DF"),
                    Player("Reinildo", 23, "DF"),
                    Player("Rodrigo De Paul", 5, "MF"),
                    Player("Koke", 6, "MF"),
                    Player("Conor Gallagher", 4, "MF"),
                    Player("Antoine Griezmann", 7, "FW"),
                    Player("Julián Álvarez", 19, "FW"),
                    Player("Alexander Sørloth", 9, "FW"),
                    Player("Ángel Correa", 10, "FW", isSub = true)
                )
            ),

            // Cricket: T20 World Cup
            Match(
                id = "match_cr_t20wc_1",
                sport = SportType.CRICKET,
                league = League.T20_WORLD_CUP,
                homeTeam = "India",
                awayTeam = "Pakistan",
                homeTeamCode = "IND",
                awayTeamCode = "PAK",
                homeFlagEmoji = "🇮🇳",
                awayFlagEmoji = "🇵🇰",
                homeScore = "-",
                awayScore = "-",
                status = MatchStatus.UPCOMING,
                matchTime = "14:30 GMT",
                date = "2026-09-28",
                venue = "Melbourne Cricket Ground (MCG)",
                liveClock = "In 2 Days",
                statusSummary = "High Voltage Mega Clash",
                winProbabilityHome = 58,
                winProbabilityDraw = 0,
                winProbabilityAway = 42,
                aiPredictionBangla = "⚡ ভারত-পাকিস্তান মেগা ক্ল্যাশ! বুমরাহের ডেথ বোলিং এবং ভারতের ব্যাটিং গভীরতা তাদের এগিয়ে রাখছে। শাহীন শাহ আফ্রিদির প্রথম ওভারের স্পেল ম্যাচের মোড় ঘোরাতে পারে। প্রেডিকশন: ভারত ফেভারিট (৫৮%)।",
                aiPredictionEnglish = "⚡ Massive clash at the MCG! Bumrah's unmatched bowling and India's middle-order power give them the upper hand. Shaheen's opening spell remains Pakistan's vital weapon.",
                homeFormation = "Playing XI",
                awayFormation = "Playing XI",
                homeLineup = listOf(
                    Player("Rohit Sharma", 45, "Batsman", isCaptain = true),
                    Player("Virat Kohli", 18, "Batsman"),
                    Player("Suryakumar Yadav", 63, "Batsman"),
                    Player("Rishabh Pant", 17, "Wicket-Keeper", isWicketKeeper = true),
                    Player("Hardik Pandya", 33, "All-Rounder"),
                    Player("Shivam Dube", 25, "All-Rounder"),
                    Player("Ravindra Jadeja", 8, "All-Rounder"),
                    Player("Axar Patel", 20, "All-Rounder"),
                    Player("Kuldeep Yadav", 23, "Bowler"),
                    Player("Jasprit Bumrah", 93, "Bowler"),
                    Player("Arshdeep Singh", 2, "Bowler")
                ),
                awayLineup = listOf(
                    Player("Babar Azam", 56, "Batsman", isCaptain = true),
                    Player("Mohammad Rizwan", 16, "Wicket-Keeper", isWicketKeeper = true),
                    Player("Fakhar Zaman", 39, "Batsman"),
                    Player("Usman Khan", 77, "Batsman"),
                    Player("Shadab Khan", 7, "All-Rounder"),
                    Player("Iftikhar Ahmed", 95, "All-Rounder"),
                    Player("Imad Wasim", 9, "All-Rounder"),
                    Player("Shaheen Shah Afridi", 10, "Bowler"),
                    Player("Naseem Shah", 71, "Bowler"),
                    Player("Haris Rauf", 12, "Bowler"),
                    Player("Mohammad Amir", 5, "Bowler")
                )
            ),

            // Football: World Cup
            Match(
                id = "match_fb_wc_1",
                sport = SportType.FOOTBALL,
                league = League.WORLD_CUP,
                homeTeam = "Argentina",
                awayTeam = "Brazil",
                homeTeamCode = "ARG",
                awayTeamCode = "BRA",
                homeFlagEmoji = "🇦🇷",
                awayFlagEmoji = "🇧🇷",
                homeScore = "-",
                awayScore = "-",
                status = MatchStatus.UPCOMING,
                matchTime = "20:00 EST",
                date = "2026-09-30",
                venue = "MetLife Stadium, New York",
                liveClock = "Upcoming",
                statusSummary = "Superclásico de las Américas",
                winProbabilityHome = 48,
                winProbabilityDraw = 26,
                winProbabilityAway = 26,
                aiPredictionBangla = "⚡ বিশ্বকাপ ক্লাসিকো! লিওনেল মেসির প্লেমেকিং এবং এমি মার্তিনেজের বিশ্বস্ত হাত আর্জেন্টিনাকে ভারসাম্য দিচ্ছে। ব্রাজিলের তরুণ আক্রমণভাগে গতি থাকলেও আর্জেন্টিনা অভিজ্ঞতায় ফেভারিট।",
                aiPredictionEnglish = "⚡ World Cup Superclasico! Messi's creative genius meets Brazil's explosive flair. Argentina's midfield cohesion yields a 48% win expectancy.",
                homeFormation = "4-3-3",
                awayFormation = "4-2-3-1",
                homeLineup = listOf(
                    Player("Emiliano Martínez", 23, "GK"),
                    Player("Nahuel Molina", 26, "DF"),
                    Player("Cristian Romero", 13, "DF"),
                    Player("Nicolás Otamendi", 19, "DF"),
                    Player("Nicolás Tagliafico", 3, "DF"),
                    Player("Rodrigo De Paul", 7, "MF"),
                    Player("Enzo Fernández", 24, "MF"),
                    Player("Alexis Mac Allister", 20, "MF"),
                    Player("Lionel Messi", 10, "FW", isCaptain = true),
                    Player("Julián Álvarez", 9, "FW"),
                    Player("Lautaro Martínez", 22, "FW")
                ),
                awayLineup = listOf(
                    Player("Alisson", 1, "GK"),
                    Player("Danilo", 2, "DF", isCaptain = true),
                    Player("Marquinhos", 4, "DF"),
                    Player("Gabriel Magalhães", 14, "DF"),
                    Player("Wendell", 6, "DF"),
                    Player("Bruno Guimarães", 5, "MF"),
                    Player("João Gomes", 15, "MF"),
                    Player("Rodrygo", 10, "FW"),
                    Player("Lucas Paquetá", 8, "MF"),
                    Player("Vinícius Júnior", 7, "FW"),
                    Player("Endrick", 9, "FW")
                )
            ),

            // Cricket: ICC World Cup
            Match(
                id = "match_cr_cwc_1",
                sport = SportType.CRICKET,
                league = League.ICC_WORLD_CUP,
                homeTeam = "Australia",
                awayTeam = "England",
                homeTeamCode = "AUS",
                awayTeamCode = "ENG",
                homeFlagEmoji = "🇦🇺",
                awayFlagEmoji = "🏴󠁧󠁢󠁥󠁮󠁧󠁿",
                homeScore = "-",
                awayScore = "-",
                status = MatchStatus.UPCOMING,
                matchTime = "13:30 AEST",
                date = "2026-10-02",
                venue = "Sydney Cricket Ground (SCG)",
                liveClock = "Oct 2",
                statusSummary = "ODI World Cup Blockbuster",
                winProbabilityHome = 54,
                winProbabilityDraw = 0,
                winProbabilityAway = 46,
                aiPredictionBangla = "⚡ সিডনির ব্যাটিং ট্র্যাকে হাই-স্কোরিং ম্যাচ প্রত্যাশিত। প্যাট কামিন্স ও স্টার্কের নতুন বলের সুইং ইংল্যান্ডের টপ অর্ডারকে চ্যালেঞ্জ জানাবে। প্রেডিকশন: অস্ট্রেলিয়ার জয়ের সম্ভাবনা ৫৪%।",
                aiPredictionEnglish = "⚡ High-scoring affair expected on SCG's true deck. Cummins and Starc's opening bursts will test England's aggressive top order.",
                homeFormation = "Playing XI",
                awayFormation = "Playing XI",
                homeLineup = listOf(
                    Player("Travis Head", 62, "Batsman"),
                    Player("Mitchell Marsh", 8, "All-Rounder", isCaptain = true),
                    Player("Steven Smith", 49, "Batsman"),
                    Player("Marnus Labuschagne", 33, "Batsman"),
                    Player("Josh Inglis", 48, "Wicket-Keeper", isWicketKeeper = true),
                    Player("Glenn Maxwell", 32, "All-Rounder"),
                    Player("Marcus Stoinis", 17, "All-Rounder"),
                    Player("Pat Cummins", 30, "Bowler"),
                    Player("Mitchell Starc", 56, "Bowler"),
                    Player("Adam Zampa", 88, "Bowler"),
                    Player("Josh Hazlewood", 38, "Bowler")
                ),
                awayLineup = listOf(
                    Player("Phil Salt", 21, "Batsman"),
                    Player("Jos Buttler", 63, "Wicket-Keeper", isCaptain = true, isWicketKeeper = true),
                    Player("Harry Brook", 88, "Batsman"),
                    Player("Ben Duckett", 17, "Batsman"),
                    Player("Liam Livingstone", 23, "All-Rounder"),
                    Player("Sam Curran", 58, "All-Rounder"),
                    Player("Chris Woakes", 19, "All-Rounder"),
                    Player("Jofra Archer", 22, "Bowler"),
                    Player("Adil Rashid", 95, "Bowler"),
                    Player("Mark Wood", 33, "Bowler"),
                    Player("Reece Topley", 14, "Bowler")
                )
            ),

            // Cricket: PSL
            Match(
                id = "match_cr_psl_1",
                sport = SportType.CRICKET,
                league = League.PSL,
                homeTeam = "Lahore Qalandars",
                awayTeam = "Karachi Kings",
                homeTeamCode = "LQ",
                awayTeamCode = "KK",
                homeFlagEmoji = "🟢",
                awayFlagEmoji = "🔵",
                homeScore = "-",
                awayScore = "-",
                status = MatchStatus.UPCOMING,
                matchTime = "19:00 PKT",
                date = "2026-10-05",
                venue = "Gaddafi Stadium, Lahore",
                liveClock = "Oct 5",
                statusSummary = "PSL Rivalry Derby",
                winProbabilityHome = 52,
                winProbabilityDraw = 0,
                winProbabilityAway = 48,
                aiPredictionBangla = "⚡ লাহোর কালান্দার্সের বোলিং আক্রমণ অত্যন্ত শক্তিশালী। শাহীন আফ্রিদি ও হারিস রউফের পেস লাহোরের মাঠে তাদের ফেভারিট রাখবে।",
                aiPredictionEnglish = "⚡ Lahore Qalandars' fierce pace battery of Shaheen & Rauf gives them a slight home advantage in front of a packed Gaddafi Stadium.",
                homeFormation = "Playing XI",
                awayFormation = "Playing XI",
                homeLineup = listOf(
                    Player("Fakhar Zaman", 39, "Batsman"),
                    Player("Sahibzada Farhan", 91, "Batsman"),
                    Player("Rassie van der Dussen", 72, "Batsman"),
                    Player("Shai Hope", 4, "Wicket-Keeper", isWicketKeeper = true),
                    Player("Sikandar Raza", 24, "All-Rounder"),
                    Player("David Wiese", 96, "All-Rounder"),
                    Player("Shaheen Shah Afridi", 10, "Bowler", isCaptain = true),
                    Player("Jahandad Khan", 31, "All-Rounder"),
                    Player("Haris Rauf", 12, "Bowler"),
                    Player("Zaman Khan", 29, "Bowler"),
                    Player("Tayyab Abbas", 17, "Bowler")
                ),
                awayLineup = listOf(
                    Player("Shan Masood", 94, "Batsman", isCaptain = true),
                    Player("Tim Seifert", 43, "Wicket-Keeper", isWicketKeeper = true),
                    Player("James Vince", 14, "Batsman"),
                    Player("Shoaib Malik", 18, "All-Rounder"),
                    Player("Kieron Pollard", 55, "All-Rounder"),
                    Player("Irfan Khan Niazi", 26, "Batsman"),
                    Player("Hasan Ali", 32, "Bowler"),
                    Player("Daniel Sams", 2, "All-Rounder"),
                    Player("Mir Hamza", 9, "Bowler"),
                    Player("Tabraiz Shamsi", 68, "Bowler"),
                    Player("Zahid Mahmood", 27, "Bowler")
                )
            ),

            // Football: Serie A
            Match(
                id = "match_fb_seriea_1",
                sport = SportType.FOOTBALL,
                league = League.SERIE_A,
                homeTeam = "Inter Milan",
                awayTeam = "Juventus",
                homeTeamCode = "INT",
                awayTeamCode = "JUV",
                homeFlagEmoji = "🔵⚫",
                awayFlagEmoji = "⚪⚫",
                homeScore = "-",
                awayScore = "-",
                status = MatchStatus.UPCOMING,
                matchTime = "20:45 CET",
                date = "2026-10-08",
                venue = "San Siro, Milan",
                liveClock = "Oct 8",
                statusSummary = "Derby d'Italia",
                winProbabilityHome = 47,
                winProbabilityDraw = 29,
                winProbabilityAway = 24,
                aiPredictionBangla = "⚡ ডার্বি ডি'ইতালিয়া! সান সিরোতে ইন্টার মিলানের রক্ষণভাগ ও লাউতারো মার্তিনেজের ফিনিশিং তাদের কিছুটা এগিয়ে রাখবে। প্রেডিকশন: ১-০ বা ২-১ ইন্টার।",
                aiPredictionEnglish = "⚡ Derby d'Italia at San Siro! Inzaghi's 3-5-2 system and Lautaro's lethal form provide the edge against Motta's Juventus.",
                homeFormation = "3-5-2",
                awayFormation = "4-2-3-1",
                homeLineup = listOf(
                    Player("Yann Sommer", 1, "GK"),
                    Player("Benjamin Pavard", 28, "DF"),
                    Player("Francesco Acerbi", 15, "DF"),
                    Player("Alessandro Bastoni", 95, "DF"),
                    Player("Denzel Dumfries", 2, "MF"),
                    Player("Nicolò Barella", 23, "MF"),
                    Player("Hakan Çalhanoğlu", 20, "MF"),
                    Player("Henrikh Mkhitaryan", 22, "MF"),
                    Player("Federico Dimarco", 32, "MF"),
                    Player("Marcus Thuram", 9, "FW"),
                    Player("Lautaro Martínez", 10, "FW", isCaptain = true)
                ),
                awayLineup = listOf(
                    Player("Michele Di Gregorio", 29, "GK"),
                    Player("Nicolò Savona", 37, "DF"),
                    Player("Federico Gatti", 4, "DF"),
                    Player("Bremer", 3, "DF"),
                    Player("Andrea Cambiaso", 27, "DF"),
                    Player("Manuel Locatelli", 5, "MF", isCaptain = true),
                    Player("Khéphren Thuram", 19, "MF"),
                    Player("Francisco Conceição", 7, "FW"),
                    Player("Teun Koopmeiners", 8, "MF"),
                    Player("Kenan Yıldız", 10, "FW"),
                    Player("Dušan Vlahović", 9, "FW")
                )
            ),

            // Football: Bundesliga
            Match(
                id = "match_fb_bundesliga_1",
                sport = SportType.FOOTBALL,
                league = League.BUNDESLIGA,
                homeTeam = "Bayern Munich",
                awayTeam = "Borussia Dortmund",
                homeTeamCode = "BAY",
                awayTeamCode = "BVB",
                homeFlagEmoji = "🔴⚪",
                awayFlagEmoji = "🟡⚫",
                homeScore = "-",
                awayScore = "-",
                status = MatchStatus.UPCOMING,
                matchTime = "18:30 CET",
                date = "2026-10-12",
                venue = "Allianz Arena, Munich",
                liveClock = "Oct 12",
                statusSummary = "Der Klassiker",
                winProbabilityHome = 62,
                winProbabilityDraw = 20,
                winProbabilityAway = 18,
                aiPredictionBangla = "⚡ ডের ক্লাসিকের! হ্যারি কেইনের গোল স্কোরিং ফর্ম এবং বায়ার্নের হোম অ্যাডভান্টেজ তাদের পরিষ্কার ফেভারিট বানাচ্ছে। প্রেডিকশন: ৩-১ বায়ার্ন মিউনিখ।",
                aiPredictionEnglish = "⚡ Der Klassiker at Allianz Arena. Harry Kane's relentless finishing puts Bayern heavily ahead with a 62% win rate projection.",
                homeFormation = "4-2-3-1",
                awayFormation = "4-2-3-1",
                homeLineup = listOf(
                    Player("Manuel Neuer", 1, "GK", isCaptain = true),
                    Player("Konrad Laimer", 27, "DF"),
                    Player("Dayot Upamecano", 2, "DF"),
                    Player("Kim Min-jae", 3, "DF"),
                    Player("Alphonso Davies", 19, "DF"),
                    Player("Joshua Kimmich", 6, "MF"),
                    Player("Aleksandar Pavlović", 45, "MF"),
                    Player("Michael Olise", 17, "FW"),
                    Player("Jamal Musiala", 42, "MF"),
                    Player("Serge Gnabry", 7, "FW"),
                    Player("Harry Kane", 9, "FW")
                ),
                awayLineup = listOf(
                    Player("Gregor Kobel", 1, "GK"),
                    Player("Julian Ryerson", 26, "DF"),
                    Player("Waldemar Anton", 3, "DF"),
                    Player("Nico Schlotterbeck", 4, "DF"),
                    Player("Ramy Bensebaini", 5, "DF"),
                    Player("Emre Can", 23, "MF", isCaptain = true),
                    Player("Pascal Groß", 13, "MF"),
                    Player("Marcel Sabitzer", 20, "MF"),
                    Player("Julian Brandt", 10, "MF"),
                    Player("Jamie Bynoe-Gittens", 43, "FW"),
                    Player("Serhou Guirassy", 9, "FW")
                )
            ),

            // Cricket: Big Bash League
            Match(
                id = "match_cr_bbl_1",
                sport = SportType.CRICKET,
                league = League.BIG_BASH,
                homeTeam = "Perth Scorchers",
                awayTeam = "Sydney Sixers",
                homeTeamCode = "PS",
                awayTeamCode = "SYS",
                homeFlagEmoji = "🟠",
                awayFlagEmoji = "💖",
                homeScore = "-",
                awayScore = "-",
                status = MatchStatus.UPCOMING,
                matchTime = "16:15 AWST",
                date = "2026-10-15",
                venue = "Optus Stadium, Perth",
                liveClock = "Oct 15",
                statusSummary = "BBL Final Rematch",
                winProbabilityHome = 55,
                winProbabilityDraw = 0,
                winProbabilityAway = 45,
                aiPredictionBangla = "⚡ অপটাস স্টেডিয়ামের পেস ও বাউন্সে পার্থ স্করচার্সের ফাস্ট বোলাররা ভয়ঙ্কর। সিডনি সিক্সার্সের অভিজ্ঞতার বিপরীতে স্করচার্সের হোম রেকর্ড দুর্দান্ত।",
                aiPredictionEnglish = "⚡ The Optus stadium pitch offers extra pace & bounce. Scorchers' home fortress record tilts the probability 55% in their favor.",
                homeFormation = "Playing XI",
                awayFormation = "Playing XI",
                homeLineup = listOf(
                    Player("Sam Fanning", 12, "Batsman"),
                    Player("Josh Inglis", 48, "Wicket-Keeper", isWicketKeeper = true),
                    Player("Aaron Hardie", 21, "All-Rounder"),
                    Player("Ashton Turner", 70, "Batsman", isCaptain = true),
                    Player("Laurie Evans", 32, "Batsman"),
                    Player("Cooper Connolly", 5, "All-Rounder"),
                    Player("Nick Hobson", 22, "Batsman"),
                    Player("Ashton Agar", 18, "All-Rounder"),
                    Player("Andrew Tye", 68, "Bowler"),
                    Player("Jason Behrendorff", 65, "Bowler"),
                    Player("Lance Morris", 28, "Bowler")
                ),
                awayLineup = listOf(
                    Player("Josh Philippe", 22, "Wicket-Keeper", isWicketKeeper = true),
                    Player("Daniel Hughes", 16, "Batsman"),
                    Player("James Vince", 14, "Batsman"),
                    Player("Moises Henriques", 21, "All-Rounder", isCaptain = true),
                    Player("Jordan Silk", 28, "Batsman"),
                    Player("Jack Edwards", 18, "All-Rounder"),
                    Player("Hayden Kerr", 7, "All-Rounder"),
                    Player("Sean Abbott", 77, "Bowler"),
                    Player("Ben Dwarshuis", 27, "Bowler"),
                    Player("Jackson Bird", 9, "Bowler"),
                    Player("Todd Murphy", 36, "Bowler")
                )
            ),

            // Cricket: Ashes
            Match(
                id = "match_cr_ashes_1",
                sport = SportType.CRICKET,
                league = League.ASHES,
                homeTeam = "England",
                awayTeam = "Australia",
                homeTeamCode = "ENG",
                awayTeamCode = "AUS",
                homeFlagEmoji = "🏴󠁧󠁢󠁥󠁮󠁧󠁿",
                awayFlagEmoji = "🇦🇺",
                homeScore = "-",
                awayScore = "-",
                status = MatchStatus.UPCOMING,
                matchTime = "11:00 BST",
                date = "2026-10-18",
                venue = "Lord's, London",
                liveClock = "Oct 18",
                statusSummary = "The Ashes Test Match",
                winProbabilityHome = 46,
                winProbabilityDraw = 16,
                winProbabilityAway = 38,
                aiPredictionBangla = "⚡ দ্য অ্যাশেজ লর্ডসে! বাজবল ট্যাকটিক্স এবং লর্ডসের স্লোপে অ্যান্ডারসন-ব্রডের উত্তরসূরিদের সুইং ইংল্যান্ডকে সুবিধা দেবে। তবে কামিন্স-স্মিথ টেস্টে সবসময় বিপজ্জনক।",
                aiPredictionEnglish = "⚡ The Ashes at Lord's! Bazball aggression meets Australian grit. Weather and slope conditions predict an electrifying contest.",
                homeFormation = "Playing XI",
                awayFormation = "Playing XI",
                homeLineup = listOf(
                    Player("Zak Crawley", 6, "Batsman"),
                    Player("Ben Duckett", 17, "Batsman"),
                    Player("Ollie Pope", 23, "Batsman"),
                    Player("Joe Root", 66, "Batsman"),
                    Player("Harry Brook", 88, "Batsman"),
                    Player("Ben Stokes", 55, "All-Rounder", isCaptain = true),
                    Player("Jamie Smith", 44, "Wicket-Keeper", isWicketKeeper = true),
                    Player("Chris Woakes", 19, "All-Rounder"),
                    Player("Gus Atkinson", 35, "Bowler"),
                    Player("Mark Wood", 33, "Bowler"),
                    Player("Shoaib Bashir", 67, "Bowler")
                ),
                awayLineup = listOf(
                    Player("Usman Khawaja", 1, "Batsman"),
                    Player("Steven Smith", 49, "Batsman"),
                    Player("Marnus Labuschagne", 33, "Batsman"),
                    Player("Cameron Green", 42, "All-Rounder"),
                    Player("Travis Head", 62, "Batsman"),
                    Player("Mitchell Marsh", 8, "All-Rounder"),
                    Player("Alex Carey", 4, "Wicket-Keeper", isWicketKeeper = true),
                    Player("Pat Cummins", 30, "Bowler", isCaptain = true),
                    Player("Mitchell Starc", 56, "Bowler"),
                    Player("Nathan Lyon", 67, "Bowler"),
                    Player("Josh Hazlewood", 38, "Bowler")
                )
            ),

            // Cricket: Asia Cup
            Match(
                id = "match_cr_asiacup_1",
                sport = SportType.CRICKET,
                league = League.ASIA_CUP,
                homeTeam = "Bangladesh",
                awayTeam = "Sri Lanka",
                homeTeamCode = "BAN",
                awayTeamCode = "SL",
                homeFlagEmoji = "🇧🇩",
                awayFlagEmoji = "🇱🇰",
                homeScore = "-",
                awayScore = "-",
                status = MatchStatus.UPCOMING,
                matchTime = "15:00 BST",
                date = "2026-10-21",
                venue = "R. Premadasa Stadium, Colombo",
                liveClock = "Oct 21",
                statusSummary = "Naagin Derby Asia Cup Clash",
                winProbabilityHome = 50,
                winProbabilityDraw = 0,
                winProbabilityAway = 50,
                aiPredictionBangla = "⚡ এশিয়া কাপের বহুল প্রতীক্ষিত ম্যাচ! কলম্বোর স্পিন উইকেটে মিরাজ ও সাকিবের অভিজ্ঞতা বাংলাদেশের জন্য বড় শক্তি। শ্রীলঙ্কার পাথিরানা ও হাসারাঙ্গা যেকোনো মুহূর্ত বদলে দিতে পারে। ফিফটি-ফিফটি উত্তেজনাপূর্ণ লড়াই!",
                aiPredictionEnglish = "⚡ The famous Asia Cup rivalry! Spin will dominate at Premadasa. Shakib and Miraz against Hasaranga and Theekshana makes this an even 50-50 contest.",
                homeFormation = "Playing XI",
                awayFormation = "Playing XI",
                homeLineup = listOf(
                    Player("Tanzid Hasan Tamim", 97, "Batsman"),
                    Player("Litton Das", 16, "Wicket-Keeper", isWicketKeeper = true),
                    Player("Najmul Hossain Shanto", 99, "Batsman", isCaptain = true),
                    Player("Towhid Hridoy", 77, "Batsman"),
                    Player("Shakib Al Hasan", 75, "All-Rounder"),
                    Player("Mahmudullah Riyad", 30, "All-Rounder"),
                    Player("Jaker Ali", 80, "Batsman"),
                    Player("Mehidy Hasan Miraz", 53, "All-Rounder"),
                    Player("Rishad Hossain", 32, "Bowler"),
                    Player("Taskin Ahmed", 3, "Bowler"),
                    Player("Mustafizur Rahman", 90, "Bowler")
                ),
                awayLineup = listOf(
                    Player("Pathum Nissanka", 18, "Batsman"),
                    Player("Kusal Mendis", 13, "Wicket-Keeper", isWicketKeeper = true),
                    Player("Kusal Perera", 8, "Batsman"),
                    Player("Kamindu Mendis", 21, "All-Rounder"),
                    Player("Charith Asalanka", 72, "Batsman", isCaptain = true),
                    Player("Dasun Shanaka", 7, "All-Rounder"),
                    Player("Wanindu Hasaranga", 49, "All-Rounder"),
                    Player("Dunith Wellalage", 24, "All-Rounder"),
                    Player("Maheesh Theekshana", 61, "Bowler"),
                    Player("Matheesha Pathirana", 81, "Bowler"),
                    Player("Dilshan Madushanka", 98, "Bowler")
                )
            ),

            // Football: Europa League
            Match(
                id = "match_fb_uel_1",
                sport = SportType.FOOTBALL,
                league = League.EUROPA_LEAGUE,
                homeTeam = "Manchester United",
                awayTeam = "AS Roma",
                homeTeamCode = "MUN",
                awayTeamCode = "ROM",
                homeFlagEmoji = "🔴",
                awayFlagEmoji = "🟡🔴",
                homeScore = "-",
                awayScore = "-",
                status = MatchStatus.UPCOMING,
                matchTime = "20:00 BST",
                date = "2026-10-23",
                venue = "Old Trafford, Manchester",
                liveClock = "Oct 23",
                statusSummary = "Europa League Marquee Clash",
                winProbabilityHome = 53,
                winProbabilityDraw = 25,
                winProbabilityAway = 22,
                aiPredictionBangla = "⚡ ওল্ড ট্র্যাফোর্ডে ম্যান ইউনাইটেডের ব্রুনো ফার্নান্দেস ও গারনাচোর গতি রোমার ডিফেন্সকে পরীক্ষায় ফেলবে। প্রেডিকশন: ২-১ ম্যানচেস্টার ইউনাইটেড।",
                aiPredictionEnglish = "⚡ Bruno Fernandes' playmaking and home support give Man United a solid 53% win likelihood against Roma.",
                homeFormation = "4-2-3-1",
                awayFormation = "3-4-2-1",
                homeLineup = listOf(
                    Player("André Onana", 24, "GK"),
                    Player("Noussair Mazraoui", 3, "DF"),
                    Player("Matthijs de Ligt", 4, "DF"),
                    Player("Lisandro Martínez", 6, "DF"),
                    Player("Diogo Dalot", 20, "DF"),
                    Player("Kobbie Mainoo", 37, "MF"),
                    Player("Manuel Ugarte", 25, "MF"),
                    Player("Alejandro Garnacho", 17, "FW"),
                    Player("Bruno Fernandes", 8, "MF", isCaptain = true),
                    Player("Marcus Rashford", 10, "FW"),
                    Player("Rasmus Højlund", 9, "FW")
                ),
                awayLineup = listOf(
                    Player("Mile Svilar", 99, "GK"),
                    Player("Gianluca Mancini", 23, "DF"),
                    Player("Evan Ndicka", 5, "DF"),
                    Player("Angeliño", 3, "DF"),
                    Player("Zeki Çelik", 19, "DF"),
                    Player("Bryan Cristante", 4, "MF"),
                    Player("Manu Koné", 17, "MF"),
                    Player("Lorenzo Pellegrini", 7, "MF", isCaptain = true),
                    Player("Paulo Dybala", 21, "FW"),
                    Player("Matías Soulé", 18, "FW"),
                    Player("Artem Dovbyk", 11, "FW")
                )
            ),

            // Football: Copa Libertadores
            Match(
                id = "match_fb_copa_1",
                sport = SportType.FOOTBALL,
                league = League.COPA_LIBERTADORES,
                homeTeam = "Flamengo",
                awayTeam = "River Plate",
                homeTeamCode = "FLA",
                awayTeamCode = "RIV",
                homeFlagEmoji = "🔴⚫",
                awayFlagEmoji = "⚪🔴",
                homeScore = "-",
                awayScore = "-",
                status = MatchStatus.UPCOMING,
                matchTime = "21:30 BRT",
                date = "2026-10-25",
                venue = "Maracanã, Rio de Janeiro",
                liveClock = "Oct 25",
                statusSummary = "Copa Libertadores Semifinal",
                winProbabilityHome = 52,
                winProbabilityDraw = 26,
                winProbabilityAway = 22,
                aiPredictionBangla = "⚡ ঐতিহাসিক মারাকানায় কোপা লিবার্তাদোরেস দ্বৈরথ! ফ্ল্যামেঙ্গোর আক্রমণভাগ এবং রিভার প্লেটের কঠোর ট্যাকটিক্যাল লড়াই। প্রেডিকশন: ১-০ ফ্ল্যামেঙ্গো জয়।",
                aiPredictionEnglish = "⚡ Copa Libertadores epic duel at Maracanã. Flamengo's home intensity edges River Plate in an intense atmosphere.",
                homeFormation = "4-2-3-1",
                awayFormation = "4-3-1-2",
                homeLineup = listOf(
                    Player("Agustín Rossi", 1, "GK"),
                    Player("Guillermo Varela", 2, "DF"),
                    Player("Fabrício Bruno", 15, "DF"),
                    Player("Léo Pereira", 4, "DF"),
                    Player("Ayrton Lucas", 6, "DF"),
                    Player("Erick Pulgar", 5, "MF"),
                    Player("Nicolás de la Cruz", 18, "MF"),
                    Player("Gerson", 8, "MF", isCaptain = true),
                    Player("Giorgian de Arrascaeta", 14, "MF"),
                    Player("Bruno Henrique", 27, "FW"),
                    Player("Pedro", 9, "FW")
                ),
                awayLineup = listOf(
                    Player("Franco Armani", 1, "GK", isCaptain = true),
                    Player("Fabricio Bustos", 16, "DF"),
                    Player("Germán Pezzella", 6, "DF"),
                    Player("Paulo Díaz", 17, "DF"),
                    Player("Marcos Acuña", 24, "DF"),
                    Player("Matías Kranevitter", 5, "MF"),
                    Player("Santiago Simón", 31, "MF"),
                    Player("Ignacio Fernández", 26, "MF"),
                    Player("Claudio Echeverri", 19, "MF"),
                    Player("Facundo Colidio", 11, "FW"),
                    Player("Miguel Borja", 9, "FW")
                )
            ),

            // Football: Nations League
            Match(
                id = "match_fb_unl_1",
                sport = SportType.FOOTBALL,
                league = League.NATIONS_LEAGUE,
                homeTeam = "France",
                awayTeam = "Italy",
                homeTeamCode = "FRA",
                awayTeamCode = "ITA",
                homeFlagEmoji = "🇫🇷",
                awayFlagEmoji = "🇮🇹",
                homeScore = "-",
                awayScore = "-",
                status = MatchStatus.UPCOMING,
                matchTime = "20:45 CEST",
                date = "2026-10-26",
                venue = "Stade de France, Paris",
                liveClock = "Oct 26",
                statusSummary = "UEFA Nations League League A",
                winProbabilityHome = 54,
                winProbabilityDraw = 26,
                winProbabilityAway = 20,
                aiPredictionBangla = "⚡ নেশন্স লিগের হেভিওয়েট ম্যাচ! ঘরের মাঠে এমবাপ্পে ও ডেম্বেলের গতির সামনে ইতালির নতুন প্রজন্মের ডিফেন্স বড় পরীক্ষার মুখে পড়বে। প্রেডিকশন: ফ্রান্স ফেভারিট (৫৪%)।",
                aiPredictionEnglish = "⚡ Blockbuster Nations League fixture in Paris. France's attacking pace with Mbappé gives Les Bleus a 54% win edge.",
                homeFormation = "4-3-3",
                awayFormation = "3-5-1-1",
                homeLineup = listOf(
                    Player("Mike Maignan", 16, "GK"),
                    Player("Jules Koundé", 5, "DF"),
                    Player("William Saliba", 17, "DF"),
                    Player("Dayot Upamecano", 4, "DF"),
                    Player("Theo Hernández", 22, "DF"),
                    Player("Aurélien Tchouaméni", 8, "MF"),
                    Player("N'Golo Kanté", 13, "MF"),
                    Player("Warren Zaïre-Emery", 18, "MF"),
                    Player("Ousmane Dembélé", 7, "FW"),
                    Player("Kylian Mbappé", 10, "FW", isCaptain = true),
                    Player("Bradley Barcola", 20, "FW")
                ),
                awayLineup = listOf(
                    Player("Gianluigi Donnarumma", 1, "GK", isCaptain = true),
                    Player("Giovanni Di Lorenzo", 22, "DF"),
                    Player("Alessandro Bastoni", 21, "DF"),
                    Player("Riccardo Calafiori", 5, "DF"),
                    Player("Andrea Cambiaso", 20, "MF"),
                    Player("Davide Frattesi", 8, "MF"),
                    Player("Samuele Ricci", 6, "MF"),
                    Player("Sandro Tonali", 7, "MF"),
                    Player("Federico Dimarco", 3, "MF"),
                    Player("Lorenzo Pellegrini", 10, "MF"),
                    Player("Mateo Retegui", 9, "FW")
                )
            )
        ).map { enrichMatchWithTvAndVideos(it) }
    }

    private fun enrichMatchWithTvAndVideos(match: Match): Match {
        val isFootball = match.sport == SportType.FOOTBALL

        // Double Chance calculations based on win probabilities
        val homeProb = match.winProbabilityHome
        val drawProb = match.winProbabilityDraw
        val awayProb = match.winProbabilityAway

        val dc1X = (homeProb + drawProb).coerceIn(40, 95)
        val dc12 = (homeProb + awayProb).coerceIn(45, 95)
        val dcX2 = (awayProb + drawProb).coerceIn(35, 92)

        val bestPick = when {
            dc1X >= dc12 && dc1X >= dcX2 -> "1X (হোম জয় বা ড্র - $dc1X% নিশ্চিত)"
            dc12 >= dc1X && dc12 >= dcX2 -> "12 (যেকোনো এক দলের জয় / ড্র নেই - $dc12% নিশ্চিত)"
            else -> "X2 (অ্যাওয়ে জয় বা ড্র - $dcX2% নিশ্চিত)"
        }

        val explanation = if (isFootball) {
            "উভয় দলের বর্তমান আক্রমণভাগ ও রক্ষণভাগের ফর্ম অনুযায়ী $bestPick সবচেয়ে সেরা ও নিরাপদ বাজি।"
        } else {
            "পিচ কন্ডিশন ও ডেথ ওভারের তীব্র লড়াইয়ে $bestPick সবচেয়ে নিরাপদ পিক।"
        }

        val updatedAtoz = match.atoz.copy(
            doubleChance1X = dc1X,
            doubleChance12 = dc12,
            doubleChanceX2 = dcX2,
            doubleChanceBestPick = bestPick,
            doubleChanceExplanation = explanation
        )

        // TV Channels
        val tvChannels = if (isFootball) {
            listOf(
                LiveTvChannel("tv_fb_1", "T Sports Live HD", "Full HD 1080p", "বাংলা ধারাভাষ্য", true, "Server 1 • 60 FPS"),
                LiveTvChannel("tv_fb_2", "Sony Sports Ten 2 HD", "Ultra HD 4K", "English Audio", true, "Server 2 • Ultra HD"),
                LiveTvChannel("tv_fb_3", "TNT Sports 1 (UK)", "Full HD 1080p", "English Studio", true, "Server 3 • Fast Ping"),
                LiveTvChannel("tv_fb_4", "DAZN 1 European Live", "1080p 60fps", "Multi-Language", true, "Server 4 • Low Latency")
            )
        } else {
            listOf(
                LiveTvChannel("tv_cr_1", "T Sports Cricket Live", "Full HD 1080p", "বাংলা ধারাভাষ্য", true, "Server 1 • Bangladesh"),
                LiveTvChannel("tv_cr_2", "Star Sports 1 HD", "Ultra HD 4K", "English & Hindi", true, "Server 2 • Fast Stream"),
                LiveTvChannel("tv_cr_3", "Willow Cricket HD (USA)", "Full HD 1080p", "English Audio", true, "Server 3 • Global"),
                LiveTvChannel("tv_cr_4", "Sky Sports Cricket HD", "1080p 60fps", "UK Broadcast", true, "Server 4 • Low Latency")
            )
        }

        // Video Highlights & Clips
        val videos = if (isFootball) {
            listOf(
                LiveVideoItem(
                    id = "vid_${match.id}_1",
                    title = "🔴 ${match.homeTeam} vs ${match.awayTeam} - Live Game TV Stream (HD)",
                    timeOrMinute = match.liveClock,
                    duration = "LIVE",
                    badge = "LIVE TV",
                    channelOrSource = "GoalWicket Sports TV",
                    quality = "1080p 60fps",
                    views = "240K watching",
                    videoSimDescription = "Live game TV stream with multi-angle tactical camera & dynamic ball-by-ball analysis"
                ),
                LiveVideoItem(
                    id = "vid_${match.id}_2",
                    title = "⚽ Spectactular Goal Highlight & Tactical Replay",
                    timeOrMinute = "36'",
                    duration = "01:45",
                    badge = "GOAL VIDEO",
                    channelOrSource = "GoalWicket Highlights",
                    quality = "1080p HD",
                    views = "89K views",
                    videoSimDescription = "Thunderous strike into the top corner beating the goalkeeper at full stretch!"
                ),
                LiveVideoItem(
                    id = "vid_${match.id}_3",
                    title = "⚡ Key Red & Yellow Card Incident Replay",
                    timeOrMinute = "55'",
                    duration = "00:55",
                    badge = "VAR & CARD",
                    channelOrSource = "Referee Review TV",
                    quality = "1080p HD",
                    views = "45K views",
                    videoSimDescription = "VAR review of crucial tackle and referee booking explanation"
                ),
                LiveVideoItem(
                    id = "vid_${match.id}_4",
                    title = "🎬 Full Match Highlights & Post-Game Analysis",
                    timeOrMinute = "Full Time",
                    duration = "10:20",
                    badge = "HIGHLIGHTS",
                    channelOrSource = "GoalWicket Studio",
                    quality = "1080p 60fps",
                    views = "175K views",
                    videoSimDescription = "Complete extended match highlights, managers interview and tactical breakdown"
                )
            )
        } else {
            listOf(
                LiveVideoItem(
                    id = "vid_${match.id}_1",
                    title = "🔴 ${match.homeTeam} vs ${match.awayTeam} - Live Cricket TV Stream (HD)",
                    timeOrMinute = match.liveClock,
                    duration = "LIVE",
                    badge = "LIVE TV",
                    channelOrSource = "GoalWicket Cricket TV",
                    quality = "1080p 60fps",
                    views = "320K watching",
                    videoSimDescription = "High-definition ball-by-ball stream with live Hawkeye pitch mapping & Bengali audio"
                ),
                LiveVideoItem(
                    id = "vid_${match.id}_2",
                    title = "💥 Massive Six into the 2nd Tier Over Mid-Wicket",
                    timeOrMinute = "18.3 ov",
                    duration = "00:45",
                    badge = "MAXIMUM 6",
                    channelOrSource = "Super Sixes TV",
                    quality = "1080p HD",
                    views = "112K views",
                    videoSimDescription = "Huge 98-meter six high into the crowd with incredible crowd roar!"
                ),
                LiveVideoItem(
                    id = "vid_${match.id}_3",
                    title = "🔥 Dramatic Wicket: Yorker Shatters Stumps Replay",
                    timeOrMinute = "17.4 ov",
                    duration = "01:10",
                    badge = "WICKET CLIP",
                    channelOrSource = "Fast Bowlers Hub",
                    quality = "1080p HD",
                    views = "95K views",
                    videoSimDescription = "Toe-crushing 146 kph reverse swing yorker knocking down middle stump!"
                ),
                LiveVideoItem(
                    id = "vid_${match.id}_4",
                    title = "🎬 Full Innings Highlights & Top Moments",
                    timeOrMinute = "20.0 ov",
                    duration = "12:15",
                    badge = "HIGHLIGHTS",
                    channelOrSource = "GoalWicket Cricket Studio",
                    quality = "1080p 60fps",
                    views = "210K views",
                    videoSimDescription = "Extended highlights of all boundaries, wickets, and thrilling finish"
                )
            )
        }

        return match.copy(
            atoz = updatedAtoz,
            liveTvChannels = tvChannels,
            videoHighlights = videos
        )
    }
}
