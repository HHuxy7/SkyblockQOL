package skyblockqol.features.chat

import skyblockqol.utils.TextUtils
import net.minecraftforge.client.event.ClientChatReceivedEvent

object FakeMsg {
    private val qolRegex = "^From \\[MVP(\\+|\\+\\+)] Monkey: c:".toRegex()
    fun handle(event: ClientChatReceivedEvent, unformatted: String) {
        if (unformatted.contains(qolRegex)) {
            event.isCanceled = true
            val message = unformatted.replace(qolRegex, "").replace("&", "§").trim()
            TextUtils.info(message, false)
        }
    }
}