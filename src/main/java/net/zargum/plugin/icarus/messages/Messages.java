package net.zargum.plugin.icarus.messages;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.zlib.chat.ChatUtils;
import net.zargum.zlib.utils.ColorUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public enum Messages {

    // Other
    LOG("&d%1&f: &f%2 &d> &f%3"),
    LOG_TARGET("&d%1 &fto &d%2&f: &f%3 &d> &f%4"),
    REPAIR_SUCCESS("&a# &7You have fixed your item."),
    HAS_COOLDOWN("&c# &7Wait %1 seconds to execute this command."),
    ARG_ONLY_CONSOLE("&c# &7That argument can be used only by the console."),
    ARG_ONLY_PLAYER("&c# &7That argument can be used only by a player."),
    IN_DEVELOPMENT("&c# &7In development."),
    OPTION_NOT_EXISTS("&c# &7That option does not exist."),
    COMMAND_UNIQUEID_MESSAGE("&a%1&e's unique id is: &7%2"),
    COMMAND_CENTER_TELEPORTED("&bYou have been teleported to the center of your location."),
    NOT_HOLDING("&c# &7You are not holding anything."),

    COMMAND_METADATA(
            "&aMeta:",
            "%1",
            "&aFlags",
            "%2"
    ),

    AIRDROP_ANNOUNCE(
            "",
            ChatUtils.getCenteredMessage("&8(&c&l!&8) &6&lA DRAGON HAS BEEN SIGHTED WITH A HUGE LOOT &8(&c&l!&8)"),
            ChatUtils.getCenteredMessage("&eIT SEEMS GOING BETWEEN &650,50&e and &6-50,-50&e."),
            ""
    ),

    // Credits,
    LOG_CREDIT_SET("&d%1&7 sets &d%2&7's credits to &e%3&7. &7(&e%4&7 -> &e%3&7)"),
    LOG_CREDIT_GIVE("&d%1&7 gives to &d%2&7 &e%3&7 credits. &7(&e%4&7 -> &e%5&7)"),
    LOG_CREDIT_TAKE("&d%1&7 takes from &d%2&7 &e%3&7 credits. &7(&e%4&7 -> &e%5&7)"),
    COMMAND_CREDITS_INFO("&a%1 have &e%2&a credits."),
    COMMAND_CREDITS_CHANGED("&a# &e%1&7's credits has changed from &e%2&7 to &e%3."),
    COMMAND_CREDITS_ERROR_TAKE("&c# &7You can not take more credits than %1's credits."),
    COMMAND_CREDITS_NO_CREDITS("&c%1 do not have any credits."),
    COMMAND_CREDITS_MESSAGE("&7You can buy credits through our store: &ewww.zargum.net/store"),
    CREDITS_NPC_ARGUMENTS(
            "&eDid you say credits!? I have a lot of credits!",
            "&e&nClick here to buy me credits."
    ),

    // Gamemode
    LOG_GAMEMODE_CHANGED("&d%1&7 changed gamemode from &e%2&7 to &e%3&7."),
    LOG_GAMEMODE_CHANGED_TARGET("&d%1&7 changed &d%2&7's gamemode from &e%3&7 to &e%4&7."),
    COMMAND_GAMEMODE_SUCCESS("&7Gamemode changed to &d%1&7."),
    COMMAND_GAMEMODE_OTHER_SUCCESS("&7Gamemode of &d%1&7 changed to &d%2&7."),
    COMMAND_GAMEMODE_TARGET("&eYour gamemode has been changed to &d%1&e."),
    NOT_GAMEMODE("&c# &7That is not a gamemode."),

    // Worlds
    LOG_WORLD_CREATED("&d%1&7 created world '&e%2&7'. (%3)"),
    LOG_WORLD_CREATED_VOID("&d%1&7 created &evoid&7 world '&e%2&7'. (%3)"),
    LOG_WORLD_DELETED("&d%1&7 deleted world '&e%2&7'."),
    LOG_WORLD_SET_FLAG("&d%1&7 &e%2&7 flag &e%2&7 from world &e%3&7."),
    WORLD_SETFLAG_ALREADY_EXISTS("&7(&6World&7) &cThat flag is already %1 on the world."),
    WORLD_LOADING("&7(&6World&7) &7Loading world &e%1&7."),
    WORLD_LOADED("&7(&6World&7) &aWorld &e%1&a loaded successfully."),
    WORLD_ALREADY_EXIST("&7(&6World&7) &cWorld %1 already exist."),
    WORLD_ALREADY_LOADED("&7(&6World&7) &cWorld %1 already loaded."),
    WORLD_DELETED_ERROR("&7(&6World&7) &cError deleting world &e%1&c."),
    WORLD_NOT_EXIST("&7(&6World&7) &cWorld %1 not exist or not loaded."),
    WORLD_DELETE_SPAWN("&7(&6World&7) &cCan not delete world %1 because the spawn location is on it."),
    WORLD_SETFLAG("&7(&6World&7) &7Setted flag (&e%1&7=&e%2&7) to world &e%3&7."),
    WORLD_DELETED_DEFAULT_WORLD(
            "&cYou can not delete default world.",
            "&7Stop the server, delete world folder and change level-name in server.properties to other world."),
    WORLD_INFO(
            "&e#&6&m---------&r  &fWORLD INFO&r  &6&m---------&e#",
            "&e# &7Name: &f%1",
            "&e# &7Players: &f%2",
            "&e# &7Flags: &f%3",
            "&e# &7Spawn location: &f%4, %5, %6",
            "&e#&6&m-----------------------------&e#"
    ),

    // Flags
    FLAG_STATE_NOT_EXISTS("&cThat flag state not exists. (default, allow, deny)"),
    FLAG_NOT_BOOLEAN("&cThat is not valid state. (default, allow, deny)"),
    FLAG_NOT_EXISTS("&cThat flag not exists."),
    FLAG_LIST("&7Current flags:\n&e%1"),

    // Region
    LOG_REGION_RENAMED("&d%1&7 renamed region '&e%2&7' to '&e%3&7'."),
    REGION_LOG("&7(&6REGION&7) &7Event altered &e%1&7 in regions: &e%2&7."),
    REGION_LOGS_ENABLED("&7(&6REGION&7) &7You are now watching region logs."),
    REGION_LOGS_DISABLED("&7(&6REGION&7) &7You are disabled region logs."),
    REGION_CREATED("&7(&6REGION&7) &7Region &e%1&7 created."),
    REGION_DELETED("&7(&6REGION&7) &7Region &e%1&7 deleted."),
    REGION_SELECTED("&7(&6REGION&7) &7Region &e%1&7 selected."),
    REGION_FLAGSTATE("&7(&6REGION&7) &e%1&7 is &e%2&7 in your location."),
    REGION_SETFLAG("&7(&6REGION&7) &7Setted flag (&e%1&7=&e%2&7) to region &e%3&7."),
    REGION_SETPRIORITY("&7(&6REGION&7) &7Setted priority to &e%1&7 for region &e%2&7."),
    REGION_RENAMED("&7(&6REGION&7) &7Region &e%1&7 renamed to &e%2&7."),
    REGION_ALREADY_CREATED("&7(&6REGION&7) &cRegion &e%1 &cis already created."),
    REGION_NOT_EXISTS("&7(&6REGION&7) &cRegion &e%1&c not exists."),
    REGION_NOT_CUBOID("&7(&6REGION&7) &cThat selection is not a cuboid."),
    REGION_NOT_SELECTED("&7(&6REGION&7) &cYou need to make a world edit selection before create a region."),
    REGION_NOT_IN_REGION("&7(&6REGION&7) &cNo regions found in your location."),
    REGION_REGIONS("&7(&6REGION&7) &7There is &e%1&7 regions in your location:\n&e%2"),
    REGION_LIST_EMPTY("&7(&6REGION&7) &7There is no regions created."),
    REGION_LIST("&7(&6REGION&7) &7There is &e%1&7 regions:\n&e%2"),
    REGION_INFO(
            "&e#&6&m---------&r  &fREGION INFO&r  &6&m---------&e#",
            "&e# &7ID: &f%1",
            "&e# &7World: &f%2",
            "&e# &7Location from: &f%3",
            "&e# &7Location to: &f%4",
            "&e# &7Flags: &f%5",
            "&e# &7Priority: &f%6",
            "&e#&6&m------------------------------&e#"
    ),

    // Hologram
    LOG_HOLOGRAM_UPDATE("&d%1&7 has updated the hologram '&b%2&7'."),
    LOG_HOLOGRAM_UPDATE_LOCATION("&d%1&7 has updated location from the hologram '&b%2&7'."),
    LOG_HOLOGRAM_ADDLINE("&d%1&7 has added line '&r%2&7' to the hologram '&b%3&7'."),
    LOG_HOLOGRAM_REMOVELINE("&d%1&7 has removed line '&b%2&7' from the hologram '&b%3&7'."),
    LOG_HOLOGRAM_HIDE("&d%1&7 has hide hologram '&b%2&7'."),
    LOG_HOLOGRAM_SHOW("&d%1&7 has shown hologram '&b%2&7'."),
    LOG_HOLOGRAM_CREATED("&d%1&7 created hologram '&b%2&7'."),
    LOG_HOLOGRAM_DELETE("&d%1&7 deleted hologram '&b%2&7'."),
    LOG_HOLOGRAM_AUTOUPDATE("&d%1&7 &b%2&7 auto-updating to hologram '&b%3&7'."),
    HOLOGRAM_AUTOUPDATE("&7(&3HOLOGRAM&7) &7Has been &b%2&7 auto-updating to hologram &b%1&7."),
    HOLOGRAM_SETLINE_INVALID_INDEX("&7(&3HOLOGRAM&7) &cInvalid hologram line. (1 to %1)"),
    HOLOGRAM_LIST("&7(&3HOLOGRAM&7) &7There is &b%1&7 holograms:\n%2"),
    HOLOGRAM_LIST_NONE("&7(&3HOLOGRAM&7) &cThere is no hologram created."),
    HOLOGRAM_UPDATE("&7(&3HOLOGRAM&7) &aYou updated the hologram '&b%1&a'."),
    HOLOGRAM_CREATED("&7(&3HOLOGRAM&7) &aHologram '&b%1&a' created successfully."),
    HOLOGRAM_DELETED("&7(&3HOLOGRAM&7) &aHologram '&b%1&a' deleted successfully."),
    HOLOGRAM_NEAR_NONE("&7(&3HOLOGRAM&7) &cYou do not have holograms within %1 blocks."),
    HOLOGRAM_LINE_ADDED("&7(&3HOLOGRAM&7) &7Line '&b%1&7' added to hologram '&b%2&7'."),
    HOLOGRAM_LINE_SETTED("&7(&3HOLOGRAM&7) &7Setted line &b%1&7 from hologram '&b%2&7'."),
    HOLOGRAM_LINE_REMOVED("&7(&3HOLOGRAM&7) &7Line &b%1 &7removed from hologram '&b%2&7'."),
    HOLOGRAM_SET_LOCATION("&7(&3HOLOGRAM&7) &7Updated location of hologram '&b%1&7'."),
    HOLOGRAM_ALREADY_CREATED("&7(&3HOLOGRAM&7) &cHologram '&b%1&c' is already created."),
    HOLOGRAM_NEAR(
            "&7(&3HOLOGRAM&7) &7You have &b%1&7 holograms within &b%2&7 blocks:",
            "&b%3"
    ),
    HOLOGRAM_NOT_EXISTS("&7(&3HOLOGRAM&7) &cThat hologram not exist."),
    HOLOGRAM_LINE_NOT_EXISTS("&7(&3HOLOGRAM&7) &cThat hologram line does not exists."),
    HOLOGRAM_REMOVE_LAST_LINE("&7(&3HOLOGRAM&7) &cThere is only one line, delete the hologram with /hologram delete %1"),
    HOLOGRAM_ALREADY_HIDDEN("&7(&3HOLOGRAM&7) &cHologram '&b%1&7' is already hidden."),
    HOLOGRAM_ALREADY_SHOWN("&7(&3HOLOGRAM&7) &cHologram '&b%1&7' is already unhidden."),
    HOLOGRAM_INFO(
            "&b#&3&m---------&r  &fHOLOGRAM INFO&r  &3&m---------&b#",
            "&b# &7ID: &f%1",
            "&b# &7Location: &f%2, %3, %4 in %5",
            "&b# &7Auto update: &f%6",
            "&b# &7Content:",
            "%7",
            "&b#&3&m-----------------------------"
    ),

    // NPC
    LOG_NPC_CREATED_DELETED("&d%1&7 %2 NPC '&e%3&7' at &e%4&7,&e %5&7,&e %6&7 in &e%7&7."),
    LOG_NPC_ADDLINE("&d%1&7 has added line '&r%2&7' for the NPC '&e%3&7'."),
    LOG_NPC_REMOVELINE("&d%1&7 has removed line '&r%2&7' for the NPC '&e%3&7'."),
    LOG_NPC_HIDE("&d%1&7 has hide %2 of the NPC '&e%3&7'."),
    LOG_NPC_SHOW("&d%1&7 has shown %2 of the NPC '&e%3&7'."),
    LOG_NPC_UPDATE("&d%1&7 has updated %2 of the NPC '&e%3&7'."),
    LOG_NPC_UPDATENPC("&d%1&7 has updated NPC '&e%2&7'."),
    LOG_NPC_HAND_DELETE("&d%1&7 deleted item in hand from NPC '&e%2&7'."),
    NPC_NOT_HOLOGRAM("&8(&5NPC&8) &cThat NPC does not have hologram attached."),
    NPC_DISTANCE("&8(&5NPC&8) &d%1&7 is &e%2&7 meters of you."),
    NPC_LIST("&8(&5NPC&8)&7 There is &e%1&7 NPCs:\n%2"),
    NPC_LIST_NONE("&8(&5NPC&8)&c There are no NPCs created."),
    NPC_UPDATE("&8(&5NPC&8)&7 You updated the NPC '&d%1&7'."),
    NPC_DELETED_HAND("&8(&5NPC&8)&7 Item from NPC '&d%1&7' has been deleted."),
    NPC_CREATED("&8(&5NPC&8)&7 NPC '&d%1&7' created successfully."),
    NPC_LINE_ADDED("&8(&5NPC&8)&7 Hologram line '&r%1&7' added to NPC '&d%2&7'."),
    NPC_LINE_REMOVED("&8(&5NPC&8)&7 Hologram at line &d%1&7 removed from NPC &d%2&7."),
    NPC_LINE_UPDATED("&8(&5NPC&8)&7 Line &d%1&7 from NPC &d%2&7 has been updated."),
    NPC_SET_LOCATION("&8(&5NPC&8)&7 Updated location from NPC &d%1&7."),
    NPC_ALREADY_CREATED("&8(&5NPC&8)&c That NPC is already created."),
    NPC_DELETED("&8(&5NPC&8)&7 NPC &d%1 &7deleted successfully."),
    NPC_SET_DISPLAYNAME("&8(&5NPC&8)&7 NPC &d%1&7 has now the display name &e%2&7."),
    NPC_SET_SKIN("&8(&5NPC&8)&7 Skin '&d%1&7' setted to NPC '&d%2&7' successfully."),
    NPC_SET_HAND("&8(&5NPC&8)&7 You put '&d%1&7' to hand of NPC '&d%2&7'."),
    NPC_SET_ARMOR("&8(&5NPC&8)&7 Armor from NPC '&d%1&7' updated."),
    NPC_WORLD_NOT_MATCH("&8(&5NPC&8)&c You are not in the same world as that NPC."),
    NPC_NOT_EXISTS("&8(&5NPC&8)&c That NPC not exist."),
    NPC_NAME_HIDDEN("&8(&5NPC&8)&a Name from NPC '&d%1&a' is now &ehidden&a."),
    NPC_NAME_UNHIDDEN("&8(&5NPC&8)&a Name from NPC '&d%1&a' is now &eunhidden&a."),
    NPC_NAME_ALREADY_HIDDEN("&8(&5NPC&8)&c Name from NPC '&d%1&c' is already hidden."),
    NPC_NAME_ALREADY_UNHIDDEN("&8(&5NPC&8)&c Name from NPC '&d%1&c' is already unhidden."),
    NPC_INFO (
            "&d#&5&m---------&r  &fNPC INFO&r  &5&m---------&d#",
            "&d# &7ID: &f%1",
            "&d# &7World: &f%2",
            "&d# &7Location: &f%3, %4, %5",
            "&d# &7Self Skin: &f%6",
            "&d# &7Hand: &f%7",
            "&d# &7Hide name: &f%8",
            "&d# &7Tablisted: &f%9",
            "&d# &7Holograms: &f%10",
            "&d#&5&m-----------------------------"
    ),

    IGNORE_ADDED("&a# &7You are now ignoring &e%1&7."),
    IGNORE_REMOVED("&a# &7You can now hear &e%1&7."),
    NOT_NUMBER("&c# &7That is not a valid number."),
    VANISH_ENABLED("&a# &7Vanish enabled."),
    LUNAR_REQUIRED("&c# &7You need Lunar Client to use that."),
    COMMAND_PLAYTIME_OTHER(
            "&7%1 played for &e%2",
            "&7and joined &e%3&7 times!"
    ),
    COMMAND_PLAYTIME(
            "&8&l&m----*----------------*----",
            "&7You played for &e%1",
            "&7and joined &e%2&7 times!",
            "&8&l&m----*----------------*----"
    ),
    COMMAND_WORLD_PREGEN_SUCCESS("&a# &7World pre-generated successfully. (%1 chunks) (%2 x %2 blocks)"),
    COMMAND_WORLD_INVALID_PREGEN_SIZE("&c# &7Invalid pre-generator size."),
    COMMAND_STAFF_MODULE("&7[&bStaff&7] &fLunar Client %1 %2."),
    KILL_ENITIES(
            "&5[&7%1&5]",
            "&7(&eEntities&7) &7You have killed &e%2 &7entities."
    ),
    LUNAR_EMOTES_STOP("&c# &7If your emote still active, disable it with &eleft click&7."),
    LUNAR_EMOTES_USAGE("&c# &7You can use all of them with &e/%1 <number (1-57)> &b.\nThere is 57 emotes at the moment."),
    LUNAR_LOGIN_TITLE("&5Welcome to Zargum Network"),
    LUNAR_AUTHENTICATED("&a# &7You have been authenticated with Lunar Client."),
    LUNAR_AUTHENTICATED_WARN("Thanks for using Lunar Client at Zargum Network"),
    LUNAR_INVALID_EMOTE("&c# &7That is not valid emote! \n&e# &7There is 57 emotes available, use them with &e/%1 <number>"),
    LUNAR_EMOTES_NOT_ON_CLIENT("&c# &7You must be on Lunar Client to perform emotes."),
    LUNAR_PLAYERS("&b# &7Players using Lunar Client (&b%1&7):"),
    LUNAR_EMPTY_PLAYERS("&c# &7There is no one currently using Lunar Client."),
    LUNAR_IS_ON("&a# &7That player is on lunar client."),
    LUNAR_IS_NOT_ON("&a# &7That player is not on lunar client."),
    SKULLINFO_SEND("&a# &7Messages sent to console."),
    SKULLINFO_OWNER("&d# &7Skull owner: &f%1"),
    SKULLINFO_SIGNATURE("&d# &7Skull signature: &f%1"),
    SKULLINFO_VALUE("&d# &7Skull value: &f%1"),
    SKULLINFO_PROFILE_ERROR("&c# &7That skull does not have player properties."),
    NO_TEXTURES_FOUND("&c# &7No textures found."),
    SKULL_NAME("&eYou are looking at &6%1&e's head."),
    PREFIX("&8(&5Icarus&8) &r"),
    ONLY_CONSOLE("&c# &7Only console can use that command."),
    ONLY_PLAYER("&c# &7Only players can use this command."),
    PLAYER_NOT_EXISTS("&c# &7That player has never joined."),
    PLAYER_NOT_ONLINE("&c# &7That player is not online."),
    NO_PERMISSION("&c# &7You do not have permission."),
    INVENTORY_EMPTY("&c# &7You do not have nothing in your inventory."),
    NO_STAFF_ONLINE("&c# &7There is no staff online."),
    REQUIRE_ASSISTANCE("&e# &d%1 &7requires assistance &e» &f%2"),
    NOTHING_IN_HAND("&c# &7You do not have nothing in your hand."),
    PLAYER_KILL("&bYou have killed &e%1 &7(&e+%2xp&7)"),
    PLAYER_KILL_XP_UPGRADE("&bYou reach level &e%1&b!."),
    STAFF_CONNECTED("&7Staff &e%1 &aconnected."),
    STAFF_DISCONNECTED("&7Staff &e%1 &cdisconnected."),
    WORLD_DELETED("&7World &e%1 &7deleted."),
    WORLD_ENVIRONMENT_ERROR("&7(&dWORLD&7) &c%1 is not a valid environment. &7(normal, nether or the_end/end)"),
    COMMAND_WORLD_CREATED("&eWorld created successfully."),
    COMMAND_TOP_TELEPORTED("&a#&7 You have been teleported to the highest block."),
    COMMAND_RENAME_ALREADY_UNNAMED("&cYour held item already has no name."),
    COMMAND_RENAME_ALREADY_NAMED("&cYour held item is already named this."),
    RENAMING_EMPTY_SUCCESS("&aYou removed your item's name."),
    RENAMING_SUCCESS("&aYou renamed your item to &r%1&a."),
    HOLOGRAM_USAGE(
            "&cHologram usage:",
            "&c- /hologram create <ID> &7Create a hologram with an ID.",
            "&c- /hologram delete <ID> &7Delete a hologram.",
            "&c- /hologram edit <ID> <line> <text> &7Edit lines from an hologram.",
            "&c- /hologram view <ID> &7View in chat current lines of the hologram",
            "&c- /hologram location <ID> &7Update the location of the hologram."
    ),
    COMMAND_LIST_RANKS("&5[%1&5]"),
    COMMAND_LIST_PLAYERS("&5Players &d(&f%1/%2&d)&5: %3"),
    COMMAND_LOG("&7Server logs &d%1&7."),
    TELEPORTED("&7You have been teleported to &d%1&7."),
    TELEPORT_CANCELLED("&c# &7Teleport cancelled."),
    COMMAND_TELEPORT_SUCCESS("&7You have teleported to &d%1&7."),
    COMMAND_TELEPORT_HERE_SUCCESS("&7You teleport &d%1&7 to you."),
    COMMAND_TELEPORT_ALL_SUCCESS("&7You teleported all the server to you."),
    COMMAND_TELEPORT_ALL_NO_PLAYERS("&cThere is no players to teleport."),
    COMMAND_TELEPORT_LOCATION_ERROR("&cERROR: That is not a location."),
    COMMAND_ENCHANT_CANT_UPDATE("&cThat item has the same enchantment level that you tried to enchant."),
    COMMAND_ENCHANT_CANT_REMOVE("&cCan not delete enchantment %1."),
    COMMAND_ENCHANT_ADDED("&aEnchantment %1 added."),
    COMMAND_ENCHANT_UPDATED("&aEnchantment %1 updated."),
    COMMAND_ENCHANT_REMOVED("&cEnchantment %1 removed."),
    COMMAND_ENCHANT_UNKNOWN(
            "&cUnknown enchantment level.",
            "&7Check available enchantments level with /enchant."
    ),
    COMMAND_ENCHANT_LIST(
            "&7&m---------------------",
            "&7Enchantments for that item: &a%1",
            "",
            "&7Other enchantments: &e%2",
            "&7&m---------------------"
    ),
    COMMAND_HELPOP_SENT("&dYour message has been sent to all the staff members."),
    COMMAND_HELPOP_COOLDOWNED("&cYou can not request assistance until %1."),
    COMMAND_SPAWN_TELEPORTING("&e# &7Teletransportandote en &d%1 &7segundos."),
    COMMAND_SPAWN_SUCCESS("&a# &7Teleported to spawn."),
    COMMAND_SETSPAWN_SET("&a# &7Spawn setted."),
    COMMAND_SETSPAWN_INVALID_YAW("&c# &7Invalid yaw, must be between -180 and 180."),
    COMMAND_SETSPAWN_INVALID_PITCH("&c# &7Invalid pitch, must be between -90 and 90."),
    COMMAND_BROADCAST("%1"),
    COMMAND_SKULL("&e# &7There is the &d%1 &7skull."),
    COMMAND_FLY("&a# &7Fly mode &e%1"),
    COMMAND_FLY_TO_OTHER("&a# &7Fly mode &e%1 &7to &e%2"),
    COMMAND_MSG_SENT("&e[&a&l⬉ &r%1 &e] &r%2"),
    COMMAND_MSG_RECIVED("&e[&c&l⬊ &r%1 &e] &r%2"),
    COMMAND_MSG_SAME("&c# &7You can not send private messages to yourself."),
    COMMAND_MSG_TRIED_VANISHED("&e# &e%1&7 tried to send you an private message, but you are vanished."),
    COMMAND_REPLY_EMPTY("&cYou are not in a conversation."),
    COMMAND_HEALTH("&eYou have been healed."),
    COMMAND_HEALTH_OTHER("&b%1 &ehave been healed."),
    COMMAND_HEALTH_RECIVED("&eYou have been healed by a staff."),
    COMMAND_FEED("&eYou have been feeded."),
    COMMAND_FEED_OTHER("&b%1 &ehave been feeded."),
    COMMAND_FEED_RECIVED("&eYou have been feeded by a staff."),
    COMMAND_SPEED_FLY("&eFlight speed setted to &d%1&e."),
    COMMAND_SPEED_WALK("&eWalk speed setted to &d%1&e."),
    COMMAND_SPEED_FLY_OTHER("&eFlight speed for &d%1 &esetted to &d%2&e."),
    COMMAND_SPEED_WALK_OTHER("&eWalk speed for &d%1 &esetted to &d%2&e."),
    COMMAND_VOTE_CHECKING("&6Checking..."),
    COMMAND_VOTE_HAS_COOLDOWN("&cWait %1 seconds to execute this command."),
    COMMAND_VOTE_ALREADY("&aYou already voted."),
    COMMAND_VOTE_BETTER_RANK("&aThanks for voting, your rank is better or equal than &7%1&6."),
    COMMAND_VOTE_RESETED("&aVotes reseted"),
    COMMAND_VOTE_END("&cThere is no claims today, wait %1 to next %2 &cvotes."),
    COMMAND_VOTE_NOT_VOTED(
            "&7&m--------------------------",
            "&cYou have not voted yet!",
            " ",
            "&6Go to &ewww.namemc.com/server/beta.zargum.net &6to vote.",
            " ",
            "&eVote to receive &a%1 &efor five days.",
            "&eClaim your rank with &7/vote check &eagain.",
            " ",
            "&eIt is limited to &6%1 &eclaims per day.",
            "&7&m--------------------------"
    ),
    COMMAND_VOTE_USAGE(
            "&7&m--------------------------",
            "&6Go to &ewww.namemc.com/server/beta.zargum.net &6to vote.",
            " ",
            "&eVote to receive &a%1 rank &efor five days.",
            "&eClaim your rank with &7/vote check &eagain.",
            " ",
            "&eIt is limited to &6%2 &eclaims per day.",
            "&7&m--------------------------"
    ),
    COMMAND_VOTE_SUCCESS(
            "&7&m--------------------------",
            "&eThanks for voting!",
            "&bEnjoy your rank!",
            " ",
            "&eYou can also buy this rank in &bstore.zargum.net &ethere is an &f&l%1 DISCOUNT",
            "&7&m--------------------------"
    );

    private final String value;

    Messages(String message) {
        this.value = message;
    }

    Messages(String... message) {
        StringBuilder t = new StringBuilder();
        for (String s : message) {
            t.append(s).append("\n");
        }
        this.value = t.toString();
    }

    @Override
    public String toString() {
        return ColorUtils.translate(value);
    }

    public String toString(String... args) {
        String result = this.value;
        int i = 1;
        for (String arg : args) result = result.replaceAll("%" + i++, arg);
        return ColorUtils.translate(result);
    }

    public String toString(int arg) {
        return toString(arg + "");
    }

    public String[] toStringList() {
        String t = this.value;
        return ColorUtils.translate(t.split("\n"));
    }

    public String[] toStringList(String... args) {
        String t = this.value;
        int i = 1;
        for (String arg : args) t = t.replaceAll("%" + i++, arg);
        return ColorUtils.translate(t.split("\n"));
    }

    public void toNPCArgument(Player player, String NPCName, ChatColor nameColor) {
        String argumentString = this.value;
        String[] arguments = ColorUtils.translate(argumentString.split("\n"));

        new BukkitRunnable() {
            int i = 0;

            @Override
            public void run() {
                player.sendMessage(nameColor + NPCName + ChatColor.GRAY + ": " + arguments[i]);
                i++;
            }
        }.runTaskTimerAsynchronously(Icarus.getInstance(), 20, 2 * 20);
    }
}
