package net.zargum.plugin.icarus.region;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public class Region {

    private String name;
    private Location firstLocation;
    private Location secondLocation;
    private Map<Flag, FlagState> flags = new HashMap<>();
    private int priority = 1;

    public Region(String id, Location firstLocation, Location secondLocation) {
        this.name = id;
        this.firstLocation = firstLocation;
        this.secondLocation = secondLocation;
    }

    public double getMaxX() {
        return Math.max(firstLocation.getX(), secondLocation.getX());
    }
    public double getMaxY() {
        return Math.max(firstLocation.getY(), secondLocation.getY());
    }
    public double getMaxZ() {
        return Math.max(firstLocation.getZ(), secondLocation.getZ());
    }

    public double getMinX() {
        return Math.min(firstLocation.getX(), secondLocation.getX());
    }
    public double getMinY() {
        return Math.min(firstLocation.getY(), secondLocation.getY());
    }
    public double getMinZ() {
        return Math.min(firstLocation.getZ(), secondLocation.getZ());
    }
}
