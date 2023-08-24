package skyblockqol.features.chat

import skyblockqol.config.skyblockqolConfig
import skyblockqol.utils.TextUtils
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.client.event.sound.PlaySoundEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

private val abiphoneFormat = "✆ (\\w+) ✆ ".toRegex()
private var lastRing: Long = 0

object AbiphoneDND {
    //BLOCK ABIPHONE SOUNDS
    @SubscribeEvent(receiveCanceled = false, priority = EventPriority.LOW)
    fun onSound(event: PlaySoundEvent) {
        if (!skyblockqolConfig.abiDND) return
        if (System.currentTimeMillis() - lastRing < 5000) {
            if (event.name == "note.pling" && event.sound.volume == 0.69f && event.sound.pitch == 1.6666666f) {
                event.result = null
            }
        }
    }

	fun handle(event: ClientChatReceivedEvent, unformatted: String) {
		if (!skyblockqolConfig.abiDND) return
		if (unformatted matches abiphoneFormat && !unformatted.contains("Elle") && !unformatted.contains("Dean")) {
			val matchResult = abiphoneFormat.find(unformatted)
			event.isCanceled = true
			lastRing = System.currentTimeMillis()
			if (skyblockqolConfig.abiCallerID) {
				val blocked = if (Math.random() < .001) "Breefing"
				else matchResult?.groups?.get(1)?.value
				TextUtils.info("§6Call blocked from $blocked!")
			}
		}
		if (unformatted.startsWith("✆ Ring...") && unformatted.endsWith("[PICK UP]")
			&& System.currentTimeMillis() - lastRing < 5000
		) {
			event.isCanceled = true
		}
	}
}