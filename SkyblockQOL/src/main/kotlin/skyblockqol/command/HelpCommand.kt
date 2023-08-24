package skyblockqol.command

import skyblockqol.utils.TextUtils
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender

class HelpCommand : ClientCommandBase("qolhelp") {
    @Throws(CommandException::class)
    override fun processCommand(sender: ICommandSender, args: Array<String>) {

        TextUtils.info("  §7/enchantrune - toggles enchant rune visibility.", false)
        TextUtils.info("  §7/fairy - toggles healer fairy visibility.", false)
        TextUtils.info("  §7/hl - helps change highlighted leap player on the fly.", false)
        TextUtils.info("  §7/farmcontrols - swaps some keybinds and adjusts sens to be better suited for farming", false)
    }
}