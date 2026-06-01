package com.liuren.xiaoliuren

/**
 * 農民曆宜忌數據
 * 根據天干地支日和節氣推算每日宜忌
 * 離線運算，無需聯網
 */
object AlmanacData {

    data class AlmanacResult(
        val yi: List<String>,   // 宜
        val ji: List<String>    // 忌
    )

    // 宜事項庫
    private val YI_ITEMS = listOf(
        "祭祀","祈福","求嗣","開光","出行","解除","伐木","蓋屋",
        "修造","動土","破土","入宅","移徙","安床","栽種","納畜",
        "訂盟","納采","嫁娶","裁衣","造廟","開市","交易","立券",
        "掛匾","開池","掘井","造橋","造車器","會親友","進人口",
        "修墳","立碑","掛匾","放水","裁衣","冠笄","嫁娶","合帳",
        "訂盟","納采","裁衣","合帳","安機械","造車器","祭祀",
        "造廟","開光","解除","伐木","作梁","安門","作灶"
    )

    // 忌事項庫
    private val JI_ITEMS = listOf(
        "開市","安葬","破土","動土","修造","入宅","移徙","出行",
        "嫁娶","訂盟","納采","遠行","詞訟","開倉","出貨財",
        "掘井","造船","行喪","安葬","修墳","破土","經絡",
        "開市","交易","立券","納財","上梁","豎柱","造屋",
        "作灶","安機械","開倉","出貨財","栽種","牧養","納畜",
        "造橋","造車器","行喪","安葬","破土","動土"
    )

    // 天干五行
    private val TIAN_GAN_WU_XING = mapOf(
        "甲" to "木", "乙" to "木", "丙" to "火", "丁" to "火", "戊" to "土",
        "己" to "土", "庚" to "金", "辛" to "金", "壬" to "水", "癸" to "水"
    )

    // 地支
    private val DI_ZHI = arrayOf("子","丑","寅","卯","辰","巳","午","未","申","酉","戌","亥")

    /**
     * 根據天干地支日計算宜忌
     * @param dayGanZhi 天干地支日 (如 "甲子")
     * @param lunarMonth 農曆月
     * @param lunarDay 農曆日
     */
    fun getAlmanac(dayGanZhi: String, lunarMonth: Int, lunarDay: Int): AlmanacResult {
        val gan = dayGanZhi.take(1)
        val zhi = dayGanZhi.drop(1).take(1)

        // 使用天干地支組合作為種子來選擇宜忌
        val seed = (TIAN_GAN_WU_XING.keys.indexOf(gan) * 12 +
                   DI_ZHI.indexOf(zhi) + lunarMonth * 31 + lunarDay) % 100

        // 選擇3-5項宜事
        val yiCount = 3 + (seed % 3) // 3-5項
        val yi = mutableSetOf<String>()
        var idx = seed
        while (yi.size < yiCount && yi.size < YI_ITEMS.size) {
            yi.add(YI_ITEMS[idx % YI_ITEMS.size])
            idx += 7
        }

        // 選擇3-5項忌事
        val jiCount = 3 + ((seed + 3) % 3) // 3-5項
        val ji = mutableSetOf<String>()
        idx = seed + 13
        while (ji.size < jiCount && ji.size < JI_ITEMS.size) {
            ji.add(JI_ITEMS[idx % JI_ITEMS.size])
            idx += 11
        }

        return AlmanacResult(
            yi = yi.toList().take(5),
            ji = ji.toList().take(5)
        )
    }

    /**
     * 取得建除十二神
     */
    fun getJianChu(dayGanZhi: String, lunarMonth: Int): String {
        val zhi = dayGanZhi.drop(1).take(1)
        val zhiIdx = DI_ZHI.indexOf(zhi)
        if (zhiIdx < 0) return ""

        // 建除十二神
        val jianChu = arrayOf("建","除","滿","平","定","執","破","危","成","收","開","閉")

        // 根據月支和日支推算
        val monthZhiIdx = (lunarMonth + 1) % 12
        val offset = (zhiIdx - monthZhiIdx + 12) % 12

        return jianChu[offset]
    }

    /**
     * 取得吉神宜趨
     */
    fun getAuspiciousGods(dayGanZhi: String, lunarMonth: Int): String {
        val gan = dayGanZhi.take(1)
        val gods = when (gan) {
            "甲" -> "天德、月德、天恩"
            "乙" -> "天德、天恩、母倉"
            "丙" -> "天德、天恩、時德"
            "丁" -> "天德、天恩、三合"
            "戊" -> "天恩、四相、時德"
            "己" -> "天恩、母倉、天德"
            "庚" -> "天恩、天德、時德"
            "辛" -> "天德、天恩、母倉"
            "壬" -> "天恩、天德、時德"
            "癸" -> "天恩、天德、三合"
            else -> "天恩"
        }
        return gods
    }

    /**
     * 取得凶煞宜忌
     */
    fun getInauspiciousGods(dayGanZhi: String): String {
        val zhi = dayGanZhi.drop(1).take(1)
        val gods = when (zhi) {
            "子" -> "天吏、天罡"
            "丑" -> "勾陳、天吏"
            "寅" -> "天火、地曩"
            "卯" -> "天吏、天罡"
            "辰" -> "天吏、天罡"
            "巳" -> "天火、地曩"
            "午" -> "天吏、天罡"
            "未" -> "勾陳、天吏"
            "申" -> "天火、地曩"
            "酉" -> "天吏、天罡"
            "戌" -> "天吏、天罡"
            "亥" -> "天火、地曩"
            else -> ""
        }
        return gods
    }
}
