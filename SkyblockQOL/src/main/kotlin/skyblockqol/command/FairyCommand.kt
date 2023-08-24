package skyblockqol.command

import skyblockqol.skyblockqol.Companion.config
import skyblockqol.utils.TextUtils
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender

class FairyCommand : ClientCommandBase("fairy") {
    @Throws(CommandException::class)
    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        config.hideHealerFairy = !config.hideHealerFairy
        TextUtils.toggledMessage("Healer Fairy Hider", config.hideHealerFairy)
        config.save()
    }
}