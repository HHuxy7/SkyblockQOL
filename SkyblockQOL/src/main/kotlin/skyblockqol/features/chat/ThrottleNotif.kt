package skyblockqol.features.chat

import skyblockqol.skyblockqol
import skyblockqol.config.skyblockqolConfig
import skyblockqol.utils.TabListUtils
import skyblockqol.utils.TextUtils
import net.minecraftforge.client.event.ClientChatReceivedEvent

object ThrottleNotif {
    private var lastThrottle: Long = 0
    fun handle(event: ClientChatReceivedEvent, unformatted: String) {
        if (unformatted == "This menu has been throttled! Please slow down..." && skyblockqol.config.throttleNotifier
            && TabListUtils.area == "Dungeon"
        ) {
            event.isCanceled = true
	        if (!skyblockqolConfig.throttleNotifierSpam && System.currentTimeMillis() - lastThrottle > 8000) {
                TextUtils.sendPartyChatMessage(skyblockqol.config.customMessage)
            } else if (skyblockqolConfig.throttleNotifierSpam) {
                TextUtils.sendPartyChatMessage(skyblockqol.config.customMessage)
            }
            lastThrottle = System.currentTimeMillis()
        }
    }
}