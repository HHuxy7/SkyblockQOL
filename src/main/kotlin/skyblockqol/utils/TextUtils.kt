package skyblockqol.utils

import skyblockqol.skyblockqol
import net.minecraft.util.ChatComponentText

object TextUtils {
	fun info(text: String, prefix: Boolean = true) {
		if (skyblockqol.mc.thePlayer == null) return

		val textPrefix = if (prefix) "${skyblockqol.CHAT_PREFIX} " else ""
		skyblockqol.mc.thePlayer.addChatMessage(ChatComponentText("$textPrefix$text§r"))
	}

	fun toggledMessage(message: String, state: Boolean) {
		val stateText = if (state) "§aON" else "§cOFF"
		info("§9Toggled $message §8[$stateText§8]§r")
	}

	fun sendPartyChatMessage(message: String) {
		sendMessage("/pc $message")
	}

	fun sendMessage(message: String) {
		skyblockqol.mc.thePlayer.sendChatMessage(message)
	}
}