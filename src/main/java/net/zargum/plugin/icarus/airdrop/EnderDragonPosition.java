package net.zargum.plugin.icarus.airdrop;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.util.Vector;

@Getter @Setter
@RequiredArgsConstructor
public class EnderDragonPosition {

    private final Location startLocation, endLocation;
    private Location currentLocation;
    private Vector position;

}
