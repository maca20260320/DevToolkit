package com.liuren.xiaoliuren

/**
 * 小六壬核心演算法
 * 嚴格遵循傳統規則
 */
object XiaoLiuRen {

    // 六神名稱 (1-6)
    val LIU_SHEN = arrayOf("", "大安", "留連", "速喜", "赤口", "小吉", "空亡")

    // 六神五行
    val LIU_SHEN_WU_XING = arrayOf("", "木", "木", "火", "金", "水", "土")

    // 六神方位
    val LIU_SHEN_FANG_WEI = arrayOf("", "東方", "北方", "南方", "西方", "中央", "四方")

    // 六神吉凶 (1=吉, 2=平, 3=凶)
    val LIU_SHEN_JI_XIONG = arrayOf(0, 1, 2, 1, 3, 1, 3)

    // 六神顏色
    val LIU_SHEN_COLORS = arrayOf("", "#FF3B30", "#007AFF", "#FF3B30", "#34C759", "#FF3B30", "#34C759")

    // 六神斷語（每卦10行標準斷語）
    val LIU_SHEN_DUAN_YU = arrayOf(
        "", // index 0 unused
        // 大安 (1) - 吉
        """大安事事昌，求財在坤方
失物去不遠，宅舍保安康
行人身未動，病者主無恙
將軍回田野，細與細推詳
尋人不出門，失物南方找
官事有祥和，財喜自然到""",
        // 留連 (2) - 平
        """留連事難成，求謀日未明
官事只宜緩，去者未回程
失物南方見，急討方心稱
更須防口舌，人口且平平
尋人未見面，失物北方藏
凡事須等待，心急也無妨""",
        // 速喜 (3) - 吉
        """速喜喜來臨，求財向南行
失物申未午，逢人路上尋
官事有福德，病者無禍侵
田宅六畜吉，行人有信音
尋人即刻見，失物當天回
官事即和解，喜氣自來臨""",
        // 赤口 (4) - 凶
        """赤口主口舌，官非切宜防
失物速速找，行人有驚慌
六畜多作怪，病者出西方
更須防咒詛，誠恐染瘟殃
尋人難相見，失物難追回
官事多不順，口舌是非來""",
        // 小吉 (5) - 吉
        """小吉最吉昌，路上好商量
陰人來報喜，失物在坤方
行人即便至，交關甚是強
凡事皆和合，病者叩窮蒼
尋人即將至，失物很快回
官事有和解，喜氣自然來""",
        // 空亡 (6) - 凶
        """空亡事不祥，陰人少主張
求財無利益，行人有災殃
失物尋不見，官事有刑傷
病人逢暗鬼，解禳保安康
尋人無蹤跡，失物難尋找
官事多不利，凡事皆徒勞"""
    )

    /**
     * 取餘運算，餘數為0時對應6（空亡）
     */
    private fun safeMod(n: Int, divisor: Int): Int {
        val result = n % divisor
        return if (result == 0) divisor else result
    }

    /**
     * 時間起卦（傳統小六壬方法）
     * @param lunarMonth 農曆月 (1-12)
     * @param lunarDay 農曆日 (1-30)
     * @param hourNumber 時辰數字 (子=1, 丑=2, ..., 亥=12)
     * @return 六神編號 (1-6)
     */
    fun timeDivination(lunarMonth: Int, lunarDay: Int, hourNumber: Int): Int {
        val sum = lunarMonth + lunarDay + hourNumber
        return safeMod(sum, 6)
    }

    /**
     * 數字起卦（玩法一：直接除以6）
     * 任意正整數，直接除以6取餘數
     */
    fun numberDivination(number: Long): Int {
        val absNumber = Math.abs(number)
        return safeMod((absNumber % 6).toInt(), 6)
    }

    /**
     * 數字起卦（玩法二：逐位相加再除以6）
     * 多位數先各位數字相加求和，再除以6
     */
    fun numberDigitSumDivination(number: Long): Int {
        val absNumber = Math.abs(number)
        var sum = 0L
        var temp = absNumber
        while (temp > 0) {
            sum += temp % 10
            temp /= 10
        }
        return safeMod((sum % 6).toInt(), 6)
    }

    /**
     * 取得六神名稱
     */
    fun getShenName(shenNumber: Int): String {
        return if (shenNumber in 1..6) LIU_SHEN[shenNumber] else "未知"
    }

    /**
     * 取得六神顏色
     */
    fun getShenColor(shenNumber: Int): String {
        return if (shenNumber in 1..6) LIU_SHEN_COLORS[shenNumber] else "#000000"
    }

    /**
     * 取得吉凶等級文字
     */
    fun getJiXiongText(shenNumber: Int): String {
        return when (LIU_SHEN_JI_XIONG[shenNumber]) {
            1 -> "吉"
            2 -> "平"
            3 -> "凶"
            else -> ""
        }
    }

    /**
     * 取得六神斷語
     */
    fun getDuanYu(shenNumber: Int): String {
        return if (shenNumber in 1..6) LIU_SHEN_DUAN_YU[shenNumber] else ""
    }

    /**
     * 取得五行
     */
    fun getWuXing(shenNumber: Int): String {
        return if (shenNumber in 1..6) LIU_SHEN_WU_XING[shenNumber] else ""
    }

    /**
     * 取得方位
     */
    fun getFangWei(shenNumber: Int): String {
        return if (shenNumber in 1..6) LIU_SHEN_FANG_WEI[shenNumber] else ""
    }
}
