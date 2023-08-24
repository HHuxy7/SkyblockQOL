package skyblockqol.utils

import com.google.gson.Gson
import skyblockqol.skyblockqol.Companion.mc
import skyblockqol.config.skyblockqolConfig
import net.minecraft.util.EnumChatFormatting
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection
import java.util.*

object Utils {
	fun stripColorCodes(string: String): String {
		return string.replace("§.".toRegex(), "")
	}

	fun isInSkyblock(): Boolean {
		if (mc.theWorld == null || mc.thePlayer == null) return false
        if (mc.isSingleplayer) return false
		if (mc.thePlayer.clientBrand?.contains("hypixel", true) == false) return false
        val objective = mc.thePlayer.worldScoreboard.getObjectiveInDisplaySlot(1) ?: return false
		return stripColorCodes(objective.displayName).contains("skyblock", true)
	}

	fun getColorString(int: Int): String {
		return if (int == 16) "§z" else EnumChatFormatting.values()[int].toString()
	}

	/**
	 * method that takes an amount of time described by a millisecond amount and translates it to a readable string.
	 */
	fun formatTime(millisAmount: Long, includeSubSeconds: Boolean = false): String {
		if (millisAmount < 0)
			return ""
		val sb: StringBuilder = StringBuilder()
		var millis = millisAmount

		if (millis / (3600 * 1000) >= 1) {
			val hours: Long = millisAmount / (3600 * 1000)
			sb.append("$hours:")
			millis -= (hours * 3600 * 1000)
		}
		if (millis / (60 * 1000) >= 1) {
			val minutes = millis / (60 * 1000)
			if (sb.isNotEmpty() && minutes < 10) {
				sb.append(0)
			}
			sb.append("$minutes:")
			millis -= (minutes * 60 * 1000)
		} else if (sb.isNotEmpty()) {
			sb.append("00:")
		}

		if (millis / 1000 >= 1) {
			val seconds = millis / 1000
			if (sb.isNotEmpty() && seconds < 10) {
				sb.append(0)
			}
			sb.append(seconds)
			millis -= (seconds * 1000)
		} else if (sb.isNotEmpty()) {
			sb.append("00")
		}

		if (includeSubSeconds) {
			sb.append('.')
			if (millis < 100) {
				sb.append(0)
				if (millis < 10) {
					sb.append(0)
				}
			}
			sb.append("$millis")
		}
		return sb.toString()
	}
}