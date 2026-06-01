package com.liuren.xiaoliuren

/**
 * 十二時辰對照表
 * 每個時辰對應2小時，以及一個數字(1-12)
 */
object ChineseHour {

    data class HourInfo(
        val name: String,       // 時辰名
        val number: Int,        // 對應數字 (子=1, 丑=2, ..., 亥=12)
        val range: String       // 時間範圍
    )

    private val HOURS = listOf(
        HourInfo("子", 1, "23:00-01:00"),
        HourInfo("丑", 2, "01:00-03:00"),
        HourInfo("寅", 3, "03:00-05:00"),
        HourInfo("卯", 4, "05:00-07:00"),
        HourInfo("辰", 5, "07:00-09:00"),
        HourInfo("巳", 6, "09:00-11:00"),
        HourInfo("午", 7, "11:00-13:00"),
        HourInfo("未", 8, "13:00-15:00"),
        HourInfo("申", 9, "15:00-17:00"),
        HourInfo("酉", 10, "17:00-19:00"),
        HourInfo("戌", 11, "19:00-21:00"),
        HourInfo("亥", 12, "21:00-23:00")
    )

    /**
     * 根據24小時制時間取得對應的時辰資訊
     * @param hour 0-23的小時數
     * @return 對應的時辰資訊
     */
    fun getHourInfo(hour: Int): HourInfo {
        val adjustedHour = if (hour == 23) 0 else hour
        val index = (adjustedHour + 1) / 2
        return HOURS[index]
    }

    /**
     * 取得時辰對應的數字（用於小六壬演算）
     * 子=1, 丑=2, ..., 亥=12
     */
    fun getHourNumber(hour: Int): Int {
        return getHourInfo(hour).number
    }

    /**
     * 取得時辰名稱
     */
    fun getHourName(hour: Int): String {
        return getHourInfo(hour).name + "時"
    }

    /**
     * 取得完整的時辰描述（如：子時 23:00-01:00）
     */
    fun getFullDescription(hour: Int): String {
        val info = getHourInfo(hour)
        return "${info.name}時（${info.range}）"
    }
}
