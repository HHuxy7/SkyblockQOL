package skyblockqol.features.chat

import skyblockqol.config.skyblockqolConfig
import skyblockqol.utils.TextUtils

object VanquisherTrigger {
    fun handle(message: String) {
        if (!skyblockqolConfig.vanqBroadcast) return
        if (message == "A Vanquisher is spawning nearby!") {
            TextUtils.sendMessage("/patcher sendcoords")
        }
    }
}