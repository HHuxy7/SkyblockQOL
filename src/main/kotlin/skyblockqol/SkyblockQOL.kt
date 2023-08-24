package skyblockqol

import skyblockqol.command.*
import skyblockqol.config.skyblockqolConfig
import skyblockqol.events.ChatEvent
import skyblockqol.features.*
import skyblockqol.features.chat.AbiphoneDND
import skyblockqol.features.dungeons.*
import skyblockqol.features.rift.EffigyWaypoint
import skyblockqol.features.rift.IchorHighlight
import skyblockqol.features.rift.SteakDisplay
import skyblockqol.utils.*
import kotlinx.coroutines.CoroutineScope
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent
import org.lwjgl.input.Keyboard
import java.io.File
import kotlin.coroutines.EmptyCoroutineContext

@Mod(
    modid = skyblockqol.MOD_ID,
    name = skyblockqol.MOD_NAME,
    version = skyblockqol.MOD_VERSION,
    clientSideOnly = true
)
class skyblockqol {

    var lastLongUpdate: Long = 0
    var lastLongerUpdate: Long = 0

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        val directory = File(event.modConfigurationDirectory, "skyblockqol")
        directory.mkdirs()
        val cch = ClientCommandHandler.instance

        // REGISTER COMMANDS HERE        // Help Commands
        cch.registerCommand(HelpCommand())

        // General
        cch.registerCommand(EnchantRuneCommand())
        cch.registerCommand(FairyCommand())
        cch.registerCommand(SettingsCommand())
        cch.registerCommand(JoinDungeonCommand())
        cch.registerCommand(LeapNameCommand())
        cch.registerCommand(HurtCamCommand())
        cch.registerCommand(FarmingControlSchemeCommand())
        cch.registerCommand(DynamicKeyCommand())
        cch.registerCommand(ResetSlayerTracker())
    }

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
        config.init()
        // REGISTER Classes and such HERE
        val mcBus = MinecraftForge.EVENT_BUS
        mcBus.register(this)
        mcBus.register(MemoryLeakFix)
        mcBus.register(ChatEvent)
        mcBus.register(NametagCleaner)
        mcBus.register(TitleUtils)
        mcBus.register(ArachneTimer)
        mcBus.register(MatchoAlert)
        mcBus.register(Croesus)
        mcBus.register(ContainerNameUtil)
        mcBus.register(DungeonLeap)
        mcBus.register(AbiphoneDND)
        mcBus.register(KeeperWaypoints)
        mcBus.register(ScalableTooltips)
        mcBus.register(GardenVisitorAlert)
        mcBus.register(DragonFeatures)
        mcBus.register(HideHealerFairy)
        mcBus.register(SecretSounds)
        mcBus.register(BlazeSlayerFeatures)
        mcBus.register(WorldRenderUtils)
        mcBus.register(IchorHighlight)
        mcBus.register(SteakDisplay)
        mcBus.register(ArcherHighlight)
        mcBus.register(ReaperDisplay)
        mcBus.register(ImpactDisplay)
        mcBus.register(EffigyWaypoint)
        mcBus.register(SlayerTrackerUtil)

        keyBinds.forEach(ClientRegistry::registerKeyBinding)
    }

    @SubscribeEvent
    fun onTick(event: ClientTickEvent) {
        if (skyblockqolConfig.noReverse3rdPerson && mc.gameSettings.thirdPersonView == 2)
            mc.gameSettings.thirdPersonView = 0

        if (event.phase == TickEvent.Phase.START && display != null) {
            mc.displayGuiScreen(display)
            display = null
        }

        val currTime = System.currentTimeMillis()
        if (currTime - lastLongUpdate > 1000) { // long update
            alarmClock()
            brokenHypeNotif()
            GardenVisitorAlert.alert()
            MatchoAlert.alert()
            // Now I don't have to fetch the entries for multiple things, this just updates and caches
            // the data structure on 1s cooldown
            TabListUtils.parseTabEntries()
            DragonFeatures.updateDragonDead()
            EffigyWaypoint.checkEffigies()
            SlayerTrackerUtil.updateSessionTime()
            lastLongUpdate = currTime
        }

        if (currTime - lastLongerUpdate > 5000) { // longer update
            MemoryLeakFix.clearBlankStands()
            lastLongerUpdate = currTime
        }
    }

    @SubscribeEvent
    fun onKey(event: KeyInputEvent) {
        if (keyBinds[0].isPressed) config.openGui()
        if (keyBinds[1].isPressed) {
            skyblockqolConfig.noReverse3rdPerson = !skyblockqolConfig.noReverse3rdPerson
            TextUtils.toggledMessage("No Selfie Camera", skyblockqolConfig.noReverse3rdPerson)
        }
        if (keyBinds[2].isPressed) {
            FarmingControlSchemeCommand.toggleControls();
        }
        if (keyBinds[3].isPressed) {
            TextUtils.sendMessage("/${skyblockqolConfig.dynamicCommandString}")
        }
    }

    companion object {
        const val MOD_ID = "skyblockqol"
        const val MOD_NAME = "Skyblock QOL"
        const val MOD_VERSION = "1.2.5"
        const val CHAT_PREFIX = "§f<skyblockqol>§r"

        val mc: Minecraft = Minecraft.getMinecraft()
        var config = skyblockqolConfig
        var display: GuiScreen? = null
        val scope = CoroutineScope(EmptyCoroutineContext)

        val keyBinds = arrayOf(
            KeyBinding("Open Settings", Keyboard.KEY_RSHIFT, "Skyblock QOL"),
            KeyBinding("Toggle Selfie Setting", Keyboard.KEY_NONE, "Skyblock QOL"),
            KeyBinding("Toggle Farming Controls", Keyboard.KEY_NONE, "Skyblock QOL"),
            KeyBinding("Dynamic Key", Keyboard.KEY_NONE, "Skyblock QOL")
        )
    }

}
