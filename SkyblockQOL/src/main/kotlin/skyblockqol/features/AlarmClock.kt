package skyblockqol.features

import skyblockqol.skyblockqol.Companion.mc
import skyblockqol.config.skyblockqolConfig
import skyblockqol.utils.ScoreBoardUtils
import skyblockqol.utils.TitleUtils
import skyblockqol.utils.Utils

var lastUpdate: Long = 0

fun alarmClock() {
    // CHECK IF IN SKYBLOCK
    if (!Utils.isInSkyblock()) return
    // CHECK TIME
    val currTime: Long = System.currentTimeMillis()
    val lines = ScoreBoardUtils.getLines()
    for (l in lines) {
        // ZOMBIE VILLAGER
        if (skyblockqolConfig.notifyZombieVillager && l.contains("8:00pm") && (currTime - lastUpdate) > 15000) {
            lastUpdate = currTime
            val color = Utils.getColorString(skyblockqolConfig.bestiaryNotifColor)
            TitleUtils.drawStringForTime("${color}Zombie Villager", 5000)
            if (skyblockqolConfig.bestiaryAlertSounds)
                mc.thePlayer.playSound("mob.villager.yes", 1f * skyblockqolConfig.bestiaryNotifVol, 0f)
        }
        // GHASTS
        else if (skyblockqolConfig.notifyGhast && l.contains("9:00pm") && (currTime - lastUpdate) > 15000) {
            lastUpdate = currTime
            val color = Utils.getColorString(skyblockqolConfig.bestiaryNotifColor)
            TitleUtils.drawStringForTime("${color}Ghast", 5000)
            if (skyblockqolConfig.bestiaryAlertSounds)
                mc.thePlayer.playSound("mob.ghast.scream", 1f * skyblockqolConfig.bestiaryNotifVol, 1f)
        }

    }

}