package skyblockqol.command

import skyblockqol.events.SlayerTypeChangeEvent
import skyblockqol.utils.TextUtils
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender
import net.minecraftforge.common.MinecraftForge

class ResetSlayerTracker : ClientCommandBase("dsr") {
    @Throws(CommandException::class)
    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        MinecraftForge.EVENT_BUS.post(SlayerTypeChangeEvent())
        TextUtils.info("§6Slayer Tracker session data reset.")
    }
}