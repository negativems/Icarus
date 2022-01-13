package net.zargum.plugin.icarus.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.textures.Texture;
import net.zargum.zlib.utils.JavaUtils;
import net.zargum.zlib.utils.Reflections;
import net.zargum.zlib.utils.item.SkullBuilder;
import org.apache.commons.lang3.EnumUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class SkullCommand extends ServerCommand<Icarus> {

    public SkullCommand() {
        super("skull", "Get the head of a player.", "<player>");
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {

        Player player = (Player) sender;
        if (args.length == 1) {
            String skullName = args[0];
            SkullBuilder skullBuilder = new SkullBuilder();
            if (Reflections.getValue(Texture.class, skullName.toUpperCase()) != null) {
                skullBuilder.setTexture((Texture) Reflections.getValue(Texture.class, skullName.toUpperCase()));
            } else {
                skullBuilder.setOwner(skullName);
            }
            ItemStack skullItem = skullBuilder.setDisplayName(ChatColor.YELLOW + skullName).build();
            player.getInventory().addItem(skullItem);
            player.sendMessage(Messages.COMMAND_SKULL.toString(skullName));
            return;
        }
        player.sendMessage("Tienes que especificar un usuario.");
    }
}
