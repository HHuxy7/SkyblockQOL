package skyblockqol.features

import skyblockqol.skyblockqol.Companion.mc
import skyblockqol.config.skyblockqolConfig
import skyblockqol.utils.TabListUtils
import skyblockqol.utils.TitleUtils
import skyblockqol.utils.Utils
import net.minecraft.client.audio.SoundCategory

object MatchoAlert {

    var hasSentAlert = false

    fun alert() {
        if (!skyblockqolConfig.notifyMatcho) return
        if (!Utils.isInSkyblock()) return

        if (TabListUtils.area != "Crimson Isle") {
            hasSentAlert = false
        }

        if (TabListUtils.explosivity && !hasSentAlert) {
            val color = Utils.getColorString(skyblockqolConfig.bestiaryNotifColor)
            TitleUtils.drawStringForTime("${color}Matcho", 5000)
            if (skyblockqolConfig.bestiaryAlertSounds) {
                val prevVol = mc.gameSettings.getSoundLevel(SoundCategory.MOBS)
                mc.gameSettings.setSoundLevel(SoundCategory.MOBS, 1f)
                mc.thePlayer.playSound("mob.villager.yes", 1f * skyblockqolConfig.bestiaryNotifVol, 0f)
                mc.gameSettings.setSoundLevel(SoundCategory.MOBS, prevVol)
            }
            hasSentAlert = true
        } else if (!TabListUtils.explosivity) hasSentAlert = false
    }
}