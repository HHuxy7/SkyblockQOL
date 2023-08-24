package skyblockqol.features.dungeons

import skyblockqol.skyblockqol.Companion.mc
import skyblockqol.config.skyblockqolConfig
import skyblockqol.utils.ScoreBoardUtils
import skyblockqol.utils.TabListUtils
import skyblockqol.utils.WorldRenderUtils
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object ArcherHighlight {
    /**The following code is broken but interesting because with SBA it makes RGB PEOPLE
    @SubscribeEvent
    fun onRenderLiving(event: RenderLivingEvent.Pre<*>) {
        if (!skyblockqolConfig.archerBox) return
        if (TabListUtils.area != "Dungeon") return
        if (!ScoreBoardUtils.isInM7 && !skyblockqolConfig.archerBoxEverywhere) return
        if (event.entity !is EntityPlayer) return
        val name = event.entity.name ?: return
        if (name != TabListUtils.archerName) return
        if (mc.thePlayer.positionVector.yCoord > 45 && !skyblockqolConfig.archerBoxEverywhere) return
        if (mc.thePlayer.name == name) return
        val (x, y, z) = WorldRenderUtils.fixRenderPos(event.x, event.y, event.z)
        WorldRenderUtils.drawCustomBox(
            x - .5,
            1.0,
            y,
            2.0,
            z - .5,
            1.0,
            skyblockqolConfig.archBoxColor.toJavaColor(),
            3f,
            phase = false
        )
    }
    */

    @SubscribeEvent
    fun onRenderWorldLast(event: RenderWorldLastEvent) {
        if (!skyblockqolConfig.archerBox) return
        if (TabListUtils.area != "Dungeon") return
        if (!ScoreBoardUtils.isInM7 && !skyblockqolConfig.archerBoxEverywhere) return
        val players = mc.theWorld.playerEntities.filterNotNull()
        players.forEach {
            val name = it.name ?: return@forEach
            if (name != TabListUtils.archerName) return@forEach
            if (mc.thePlayer.positionVector.yCoord > 45 && !skyblockqolConfig.archerBoxEverywhere) return@forEach
            if (mc.thePlayer.name == name) return@forEach
            val x = it.lastTickPosX + ((it.posX - it.lastTickPosX) * event.partialTicks)
            val y = it.lastTickPosY + ((it.posY - it.lastTickPosY) * event.partialTicks)
            val z = it.lastTickPosZ + ((it.posZ - it.lastTickPosZ) * event.partialTicks)
            WorldRenderUtils.drawCustomBox(
                x - .5,
                1.0,
                y,
                2.0,
                z - .5,
                1.0,
                skyblockqolConfig.archBoxColor.toJavaColor(),
                3f,
                phase = false
            )
        }
    }
}