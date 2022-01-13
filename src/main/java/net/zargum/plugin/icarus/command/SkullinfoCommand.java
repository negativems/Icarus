package net.zargum.plugin.icarus.command;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.utils.Reflections;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Collection;

public class SkullinfoCommand extends ServerCommand<Icarus> {

    private final Icarus plugin;

    public SkullinfoCommand(Icarus plugin){
        super("skullinfo","Get information of a skull from your hand");
        this.plugin = plugin;
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;

        if (player.getItemInHand() == null || player.getItemInHand().getType() != Material.SKULL_ITEM) {
            player.sendMessage(Messages.NOTHING_IN_HAND.toString());
            return;
        }

        ItemStack item = player.getItemInHand();
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();

        GameProfile profile = (GameProfile) Reflections.getValue(skullMeta, "profile");
        if (profile == null) {
            player.sendMessage(Messages.SKULLINFO_PROFILE_ERROR.toString());
            return;
        }

        Collection<Property> properties = profile.getProperties().get("textures");
        if (!properties.stream().findFirst().isPresent()) {
            player.sendMessage(Messages.NO_TEXTURES_FOUND.toString());
            return;
        }
        Property property = properties.stream().findFirst().get();

        if (skullMeta.getOwner() != null) plugin.log(Messages.SKULLINFO_OWNER.toString(skullMeta.getOwner()));
        if (property.getSignature() != null) plugin.log(Messages.SKULLINFO_SIGNATURE.toString(property.getSignature()));
        if (property.getValue() != null) plugin.log(Messages.SKULLINFO_VALUE.toString(property.getValue()));
        player.sendMessage(Messages.SKULLINFO_SEND.toString());
    }
}
