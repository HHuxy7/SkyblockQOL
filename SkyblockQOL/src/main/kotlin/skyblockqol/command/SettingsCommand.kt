package skyblockqol.command

import skyblockqol.skyblockqol
import net.minecraft.command.ICommandSender

class SettingsCommand : ClientCommandBase("qol") {
    override fun processCommand(sender: ICommandSender?, args: Array<out String>?) {
        skyblockqol.config.openGui()
    }
}