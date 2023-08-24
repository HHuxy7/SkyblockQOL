package skyblockqol.features.chat

import skyblockqol.skyblockqol
import skyblockqol.config.skyblockqolConfig
import net.minecraft.client.audio.SoundCategory
import net.minecraftforge.client.event.ClientChatReceivedEvent

object DoubleHookDing {
    fun handle(event: ClientChatReceivedEvent, unformatted: String) {
        if (unformatted.startsWith("It's a Double Hook!")) {
            if (skyblockqolConfig.doubleHookDing) {
                val prevNote = skyblockqol.mc.gameSettings.getSoundLevel(SoundCategory.RECORDS)
                skyblockqol.mc.gameSettings.setSoundLevel(SoundCategory.RECORDS, 1f)
                skyblockqol.mc.thePlayer.playSound("note.pling", 1f * skyblockqolConfig.secretSoundVolume, 1f)
                skyblockqol.mc.gameSettings.setSoundLevel(SoundCategory.RECORDS, prevNote)
            }
            if (skyblockqolConfig.removeHookMessage) event.isCanceled = true
        }
    }
}