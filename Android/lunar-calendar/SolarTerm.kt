package com.liuren.xiaoliuren

import java.util.Calendar

/**
 * 二十四節氣計算
 * 使用天文算法根據太陽黃經計算節氣日期
 */
object SolarTerm {

    data class SolarTermInfo(
        val name: String,           // 節氣名稱
        val date: String,           // 日期 MM-DD
        val daysUntilNext: Int      // 距離下一個節氣的天數
    )

    // 二十四節氣名稱 (按順序)
    val TERM_NAMES = arrayOf(
        "小寒","大寒","立春","雨水","驚蟄","春分",
        "清明","穀雨","立夏","小滿","芒種","夏至",
        "小暑","大暑","立秋","處暑","白露","秋分",
        "寒露","霜降","立冬","小雪","大雪","冬至"
    )

    // 節氣對應的太陽黃經度數 (每隔15度)
    private val TERM_LONGITUDES = DoubleArray(24) { it * 15.0 }

    // 節氣月份對應 (0=小寒在1月)
    private val TERM_MONTHS = intArrayOf(
        1,1,2,2,3,3,4,4,5,5,6,6,
        7,7,8,8,9,9,10,10,11,11,12,12
    )

    // 近似日期表 (每個節氣大約的日期 - 中間值)
    private val APPROX_DATES = intArrayOf(
        6,20,4,19,6,21,5,20,6,21,6,21,
        7,23,7,23,7,23,8,23,7,22,7,22
    )

    /**
     * 計算儒略日
     */
    private fun julianDay(year: Int, month: Int, day: Double): Double {
        val m = month.toDouble()
        val y = if (m <= 2) year - 1 else year
        val mm = if (m <= 2) m + 12 else m
        val A = (y / 100).toInt()
        val B = 2 - A + (A / 4)
        return (365.25 * (y + 4716)).toInt() + (30.6001 * (mm + 1)).toInt() + day + B - 1524.5
    }

    /**
     * 計算太陽黃經 (近似算法)
     */
    private fun solarLongitude(jd: Double): Double {
        val T = (jd - 2451545.0) / 36525.0
        // 平黃經
        val L0 = 280.46646 + 36000.76983 * T + 0.0003032 * T * T
        // 平近點角
        val M = 357.52911 + 35999.05029 * T - 0.0001537 * T * T
        val Mrad = Math.toRadians(M)
        // 中心方程
        val C = (1.9146 - 0.004817 * T) * Math.sin(Mrad) +
                (0.019993 - 0.000101 * T) * Math.sin(2 * Mrad) +
                0.00029 * Math.sin(3 * Mrad)
        var lon = L0 + C
        // 章動修正
        val omega = 125.04 - 1934.136 * T
        lon = lon - 0.00569 - 0.00478 * Math.sin(Math.toRadians(omega))
        lon = lon % 360
        if (lon < 0) lon += 360
        return lon
    }

    /**
     * 計算指定年份某個節氣的日期
     * @param year 年份
     * @param termIndex 節氣索引 (0-23, 0=小寒)
     */
    private fun getTermDate(year: Int, termIndex: Int): Calendar {
        val month = TERM_MONTHS[termIndex]
        val approxDay = APPROX_DATES[termIndex]

        // 從近似日期開始搜索
        var jd = julianDay(year, month, approxDay.toDouble())

        // 計算目標黃經
        val targetLon = TERM_LONGITUDES[termIndex]

        // 搜索 (±15天)
        var bestJd = jd
        var bestDiff = 360.0
        for (offset in -15..15) {
            val testJd = jd + offset
            val lon = solarLongitude(testJd)
            var diff = Math.abs(lon - targetLon)
            if (diff > 180) diff = 360 - diff
            if (diff < bestDiff) {
                bestDiff = diff
                bestJd = testJd
            }
        }

        // 精確搜索
        var d = -1.0
        while (d <= 1.0) {
            val testJd = bestJd + d
            val lon = solarLongitude(testJd)
            var diff = Math.abs(lon - targetLon)
            if (diff > 180) diff = 360 - diff
            if (diff < bestDiff) {
                bestDiff = diff
                bestJd = testJd
            }
            d += 0.01
        }

        // 轉換為日期
        val cal = Calendar.getInstance()
        // 儒略日轉公曆
        val a = (bestJd + 0.5).toLong()
        val b = a + 1524
        val c = ((b - 122.1) / 365.25).toLong()
        val d2 = (365.25 * c).toLong()
        val e = ((b - d2) / 30.6001).toLong()
        val day = (b - d2 - (30.6001 * e).toLong()).toInt()
        val m = if (e < 14) (e - 1).toInt() else (e - 13).toInt()
        val y = if (m > 2) c - 4716 else c - 4715
        cal.set(y.toInt(), m - 1, day)
        return cal
    }

    /**
     * 取得當前節氣資訊
     * @param year 公曆年
     * @param month 公曆月
     * @param day 公曆日
     * @return SolarTermInfo 當前節氣信息
     */
    fun getCurrentSolarTerm(year: Int, month: Int, day: Int): SolarTermInfo {
        val now = Calendar.getInstance()
        now.set(year, month - 1, day, 12, 0, 0)

        // 計算當年所有24個節氣的日期
        val termDates = Array(24) { i -> getTermDate(year, i) }

        // 找到當前所在的節氣
        var currentTermIdx = 0
        for (i in 0..23) {
            if (now.after(termDates[i]) || now.equals(termDates[i])) {
                currentTermIdx = i
            }
        }

        // 計算距離下一個節氣的天數
        val nextTermIdx = (currentTermIdx + 1) % 24
        val nextYear = if (nextTermIdx == 0 && currentTermIdx == 23) year + 1 else year
        val nextTermDate = if (nextTermIdx == 0 && currentTermIdx == 23) {
            getTermDate(year + 1, nextTermIdx)
        } else {
            termDates[nextTermIdx]
        }

        val diffMillis = nextTermDate.timeInMillis - now.timeInMillis
        val daysUntilNext = (diffMillis / (1000 * 60 * 60 * 24)).toInt()

        val currentMonth = TERM_MONTHS[currentTermIdx]
        val currentDay = APPROX_DATES[currentTermIdx]

        return SolarTermInfo(
            name = TERM_NAMES[currentTermIdx],
            date = String.format("%02d-%02d", currentMonth, currentDay),
            daysUntilNext = daysUntilNext.coerceAtLeast(0)
        )
    }

    /**
     * 判斷某日是否為節氣日
     */
    fun isSolarTermDay(year: Int, month: Int, day: Int): String? {
        for (i in 0..23) {
            val termDate = getTermDate(year, i)
            val tMonth = termDate.get(Calendar.MONTH) + 1
            val tDay = termDate.get(Calendar.DAY_OF_MONTH)
            if (tMonth == month && tDay == day) {
                return TERM_NAMES[i]
            }
        }
        return null
    }
}
