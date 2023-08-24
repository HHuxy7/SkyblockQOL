package skyblockqol.overlays

import cc.polyfrost.oneconfig.hud.TextHud
import skyblockqol.config.skyblockqolConfig
import skyblockqol.utils.TabListUtils
import skyblockqol.utils.Utils

class GardenInfoHud : TextHud(false) {
    override fun getLines(lines: MutableList<String>?, example: Boolean) {
        if (!Utils.isInSkyblock()) return
        if (TabListUtils.area != "Garden") return
        var i = 0
        if (skyblockqolConfig.gardenMilestoneDisplay) {
            lines?.add(i, TabListUtils.gardenMilestone)
            ++i
        }
        if (skyblockqolConfig.visitorInfo) {
            lines?.add(i, "Visitors: ${TabListUtils.numVisitors} - ${TabListUtils.timeTillNextVisitor}")
            ++i
        }
        if (skyblockqolConfig.composterAlert) {
            if (TabListUtils.emptyComposter) {
                lines?.add(i, "Empty Composter!")
                ++i
            }
        }
    }
}