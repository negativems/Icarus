package net.zargum.plugin.icarus.events;

import lombok.Getter;
import lombok.Setter;
import net.zargum.plugin.icarus.messages.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class PlayerRepairEvent extends Event implements Cancellable {

    public final Player player;
    @Getter public HandlerList handlers;
    @Setter @Getter private boolean cancelled;

    public PlayerRepairEvent(Player player, ItemStack item) {
        this.player = player;
        item.setDurability(item.getType().getMaxDurability());
        player.sendMessage(Messages.REPAIR_SUCCESS.toString());
    }

}
