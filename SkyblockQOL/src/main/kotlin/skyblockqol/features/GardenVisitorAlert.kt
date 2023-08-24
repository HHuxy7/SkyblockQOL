package skyblockqol.features

import skyblockqol.skyblockqol
import skyblockqol.skyblockqol.Companion.mc
import skyblockqol.config.skyblockqolConfig
import skyblockqol.utils.TabListUtils
import skyblockqol.utils.TitleUtils
import skyblockqol.utils.Utils
import net.minecraft.client.audio.SoundCategory

object GardenVisitorAlert {
    private var hasSentAlert = false
    private var lastAlert = 0

    fun alert() {
        if (!skyblockqolConfig.notifyMaxVisitors) return
        if (!Utils.isInSkyblock()) return

        if (TabListUtils.area != "Garden") {
            hasSentAlert = false
        }

        if (TabListUtils.maxVisitors && !hasSentAlert) {
            val color = Utils.getColorString(skyblockqolConfig.bestiaryNotifColor)
            TitleUtils.drawStringForTime("${color}Max Visitors", 5000)


            val prevNote = mc.gameSettings.getSoundLevel(SoundCategory.RECORDS)
            mc.gameSettings.setSoundLevel(SoundCategory.RECORDS, 1f)
            mc.thePlayer.playSound("note.pling", 1f * skyblockqolConfig.bestiaryNotifVol, .3f)
            mc.thePlayer.playSound("note.pling", 1f * skyblockqolConfig.bestiaryNotifVol, .6f)
            mc.thePlayer.playSound("note.pling", 1f * skyblockqolConfig.bestiaryNotifVol, .9f)
            mc.gameSettings.setSoundLevel(SoundCategory.RECORDS, prevNote)
            hasSentAlert = true
            lastAlert = System.currentTimeMillis().toInt()
        } else if (!TabListUtils.maxVisitors) hasSentAlert = false

        val timeSinceLastAlert = System.currentTimeMillis().toInt() - lastAlert

        if (TabListUtils.maxVisitors && hasSentAlert && timeSinceLastAlert > 5000 && skyblockqolConfig.persistentAlert) {
            lastAlert = System.currentTimeMillis().toInt()
            val color = Utils.getColorString(skyblockqolConfig.bestiaryNotifColor)
            TitleUtils.drawStringForTime("${color}Max Visitors", 5000)
            skyblockqol.mc.thePlayer.playSound("note.pling", 1f * skyblockqolConfig.bestiaryNotifVol, .3f)
            skyblockqol.mc.thePlayer.playSound("note.pling", 1f * skyblockqolConfig.bestiaryNotifVol, .6f)
            skyblockqol.mc.thePlayer.playSound("note.pling", 1f * skyblockqolConfig.bestiaryNotifVol, .9f)
        }
    }

}