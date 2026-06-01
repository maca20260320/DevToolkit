package com.liuren.xiaoliuren

/**
 * 農曆轉換器 - 1900-2100年
 * 使用標準農曆查找表實現公曆↔農曆轉換
 */
object LunarCalendar {

    // 農曆數據表 (1900-2100)
    // 每個元素編碼一年的農曆信息:
    // Bits 0-3: 閏月月份 (0=無閏月, 1-12=閏某月)
    // Bits 4-15: 12個月大小月 (bit=1→大月30天, bit=0→小月29天)
    // Bit 16: 閏月大小 (0=29天, 1=30天)
    private val LUNAR_INFO = intArrayOf(
        0x04bd8,0x04ae0,0x0a570,0x054d5,0x0d260,0x0d950,0x16554,0x056a0,0x09ad0,0x055d2, // 1900-1909
        0x04ae0,0x0a5b6,0x0a4d0,0x0d250,0x1d255,0x0b540,0x0d6a0,0x0ada2,0x095b0,0x14977, // 1910-1919
        0x04970,0x0a4b0,0x0b4b5,0x06a50,0x06d40,0x1ab54,0x02b60,0x09570,0x052f2,0x04970, // 1920-1929
        0x064b0,0x074a3,0x0ea50,0x06b58,0x05ac0,0x0ab60,0x096d5,0x092e0,0x0c960,0x0d954, // 1930-1939
        0x0d4a0,0x0da50,0x07552,0x056a0,0x0abb7,0x025d0,0x092d0,0x0cab5,0x0a950,0x0b4a0, // 1940-1949
        0x0baa4,0x0ad50,0x055d9,0x04ba0,0x0a5b0,0x15176,0x052b0,0x0a930,0x07954,0x06aa0, // 1950-1959
        0x0ad50,0x05b52,0x04b60,0x0a6e6,0x0a4e0,0x0d260,0x0ea65,0x0d530,0x05aa0,0x076a3, // 1960-1969
        0x096d0,0x04afb,0x04ad0,0x0a4d0,0x1d0b6,0x0d250,0x0d520,0x0dd45,0x0b5a0,0x056d0, // 1970-1979
        0x055b2,0x049b0,0x0a577,0x0a4b0,0x0aa50,0x1b255,0x06d20,0x0ada0,0x14b63,0x09370, // 1980-1989
        0x049f8,0x04970,0x064b0,0x168a6,0x0ea50,0x06b20,0x1a6c4,0x0aae0,0x092e0,0x0d2e3, // 1990-1999
        0x0c960,0x0d557,0x0d4a0,0x0da50,0x05d55,0x056a0,0x0a6d0,0x055d4,0x052d0,0x0a9b8, // 2000-2009
        0x0a950,0x0b4a0,0x0b6a6,0x0ad50,0x055a0,0x0aba4,0x0a5b0,0x052b0,0x0b273,0x06930, // 2010-2019
        0x07337,0x06aa0,0x0ad50,0x14b55,0x04b60,0x0a570,0x054e4,0x0d160,0x0e968,0x0d520, // 2020-2029
        0x0daa0,0x16aa6,0x056d0,0x04ae0,0x0a9d4,0x0a4d0,0x0d150,0x0f252,0x0d520           // 2030-2038
    )

    private val LUNAR_YEAR_START = 1900

    // 每月天數
    private val SOLAR_MONTH_DAYS = intArrayOf(31,28,31,30,31,30,31,31,30,31,30,31)

    data class LunarDate(
        val year: Int,
        val month: Int,
        val day: Int,
        val isLeapMonth: Boolean,
        val yearGanZhi: String,  // 天干地支年
        val monthGanZhi: String, // 天干地支月
        val dayGanZhi: String    // 天干地支日
    )

    fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }

    fun getSolarMonthDays(year: Int, month: Int): Int {
        return if (month == 2 && isLeapYear(year)) 29 else SOLAR_MONTH_DAYS[month - 1]
    }

    /**
     * 取得農曆某年閏月月份 (0=無閏月)
     */
    private fun lunarLeapMonth(year: Int): Int {
        val idx = year - LUNAR_YEAR_START
        if (idx < 0 || idx >= LUNAR_INFO.size) return 0
        return LUNAR_INFO[idx] and 0xf
    }

    /**
     * 取得農曆某年閏月天數
     */
    private fun lunarLeapDays(year: Int): Int {
        if (lunarLeapMonth(year) != 0) {
            val idx = year - LUNAR_YEAR_START
            return if (idx in LUNAR_INFO.indices && LUNAR_INFO[idx] and 0x10000 != 0) 30 else 29
        }
        return 0
    }

    /**
     * 取得農曆某月的天數
     * @param month 農曆月 1-12
     */
    private fun lunarMonthDays(year: Int, month: Int): Int {
        val idx = year - LUNAR_YEAR_START
        if (idx < 0 || idx >= LUNAR_INFO.size || month < 1 || month > 12) return 29
        // bits 4-15 對應月 1-12: bit (3+month) 對應月 month
        return if (LUNAR_INFO[idx] and (1 shl (3 + month)) != 0) 30 else 29
    }

    /**
     * 取得農曆某年總天數
     */
    private fun lunarYearDays(year: Int): Int {
        var sum = 348 // 12個小月共348天
        val idx = year - LUNAR_YEAR_START
        if (idx < 0 || idx >= LUNAR_INFO.size) return 354
        val info = LUNAR_INFO[idx]
        // bits 4-15 對應月 1-12: 每個 set bit 代表該月為大月(+1天)
        for (month in 1..12) {
            if (info and (1 shl (3 + month)) != 0) sum += 1
        }
        return sum + lunarLeapDays(year)
    }

    /**
     * 儒略日計算
     */
    private fun julianDay(year: Int, month: Int, day: Int): Long {
        val a = (14 - month) / 12
        val y = year + 4800 - a
        val m = month + 12 * a - 3
        return day.toLong() + (153L * m + 2) / 5 + 365L * y + y / 4 - y / 100 + y / 400 - 32045
    }

    /**
     * 公曆轉農曆
     */
    fun solarToLunar(solarYear: Int, solarMonth: Int, solarDay: Int): LunarDate {
        // 邊界檢查
        if (solarYear < 1900 || solarYear > 2100) {
            throw IllegalArgumentException("年份超出範圍: $solarYear (需1900-2100)")
        }
        if (solarMonth < 1 || solarMonth > 12) {
            throw IllegalArgumentException("月份超出範圍: $solarMonth")
        }
        if (solarDay < 1 || solarDay > 31) {
            throw IllegalArgumentException("日期超出範圍: $solarDay")
        }

        val targetJD = julianDay(solarYear, solarMonth, solarDay)

        // 從1900年正月初一開始推算
        var year = LUNAR_YEAR_START
        var remaining = (targetJD - julianDay(LUNAR_YEAR_START, 1, 31)).toInt()

        if (remaining < 0) {
            throw IllegalArgumentException("日期早於1900年農曆正月初一")
        }

        // 確定農曆年
        var daysInYear = lunarYearDays(year)
        while (remaining >= daysInYear && year <= 2100) {
            remaining -= daysInYear
            year++
            if (year > 2100) break
            daysInYear = lunarYearDays(year)
        }

        // 確定農曆月和日
        val leapMonth = lunarLeapMonth(year)
        var lunarMonth = 1
        var isLeapMonth = false
        var day = 1

        for (pass in 0..13) {  // 最多13個月（12+1閏月），加安全上限
            if (remaining <= 0 || lunarMonth > 12) break

            val daysInMonth = if (isLeapMonth) {
                lunarLeapDays(year)
            } else {
                lunarMonthDays(year, lunarMonth)
            }

            if (remaining < daysInMonth) {
                day = remaining + 1
                break
            }

            remaining -= daysInMonth

            if (!isLeapMonth && leapMonth > 0 && lunarMonth == leapMonth) {
                // 正常月之後接閏月
                isLeapMonth = true
            } else {
                // 閏月之後（或無閏月），進入下個月
                isLeapMonth = false
                lunarMonth++
            }
        }

        // 安全修正：確保月日在有效範圍
        lunarMonth = lunarMonth.coerceIn(1, 12)
        day = day.coerceIn(1, 30)

        // 計算天干地支
        val ganZhiYear = getGanZhiYear(year)
        val ganZhiMonth = getGanZhiMonth(year, lunarMonth)
        val ganZhiDay = getGanZhiDay(solarYear, solarMonth, solarDay)

        return LunarDate(
            year = year,
            month = lunarMonth,
            day = day,
            isLeapMonth = isLeapMonth,
            yearGanZhi = ganZhiYear,
            monthGanZhi = ganZhiMonth,
            dayGanZhi = ganZhiDay
        )
    }

    private val TIAN_GAN = arrayOf("甲","乙","丙","丁","戊","己","庚","辛","壬","癸")
    private val DI_ZHI = arrayOf("子","丑","寅","卯","辰","巳","午","未","申","酉","戌","亥")
    private val SHENG_XIAO = arrayOf("鼠","牛","虎","兔","龍","蛇","馬","羊","猴","雞","狗","豬")

    private fun getGanZhiYear(year: Int): String {
        val ganIdx = ((year - 4) % 10 + 10) % 10
        val zhiIdx = ((year - 4) % 12 + 12) % 12
        return "${TIAN_GAN[ganIdx]}${DI_ZHI[zhiIdx]}年"
    }

    private fun getGanZhiMonth(year: Int, lunarMonth: Int): String {
        // 甲己之年丙作首 — 甲己年正月為丙寅
        val yearGanIdx = ((year - 4) % 10 + 10) % 10
        val baseMonthGan = when (yearGanIdx) {
            0, 5 -> 2 // 甲己 → 丙
            1, 6 -> 4 // 乙庚 → 戊
            2, 7 -> 6 // 丙辛 → 庚
            3, 8 -> 8 // 丁壬 → 壬
            4, 9 -> 0 // 戊癸 → 甲
            else -> 2
        }
        val ganIdx = ((baseMonthGan + lunarMonth - 1) % 10 + 10) % 10
        val zhiIdx = ((lunarMonth + 1) % 12 + 12) % 12 // 正月為寅(2)
        return "${TIAN_GAN[ganIdx]}${DI_ZHI[zhiIdx]}月"
    }

    private fun getGanZhiDay(year: Int, month: Int, day: Int): String {
        // 基準日：2000年1月7日為甲子日
        val baseJD = julianDay(2000, 1, 7)
        val targetJD = julianDay(year, month, day)
        val diff = ((targetJD - baseJD) % 60).toInt()
        val adjusted = ((diff % 60) + 60) % 60  // 確保非負
        return "${TIAN_GAN[adjusted % 10]}${DI_ZHI[adjusted % 12]}日"
    }

    fun getMonthName(month: Int, isLeap: Boolean): String {
        val names = arrayOf("","正","二","三","四","五","六","七","八","九","十","冬","臘")
        val safeMonth = month.coerceIn(1, 12)
        return if (isLeap) "閏${names[safeMonth]}" else "${names[safeMonth]}月"
    }

    fun getDayName(day: Int): String {
        val tens = arrayOf("初","十","廿","卅")
        val ones = arrayOf("","一","二","三","四","五","六","七","八","九","十")
        val safeDay = day.coerceIn(1, 30)
        return when {
            safeDay == 10 -> "初十"
            safeDay == 20 -> "二十"
            safeDay == 30 -> "三十"
            safeDay < 11 -> "初${ones[safeDay]}"
            safeDay < 20 -> "十${ones[safeDay - 10]}"
            safeDay < 30 -> "廿${ones[safeDay - 20]}"
            else -> "三十"
        }
    }

    fun getShengXiao(year: Int): String {
        return SHENG_XIAO[((year - 4) % 12 + 12) % 12]
    }
}
