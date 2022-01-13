package net.zargum.plugin.icarus.region.managers;

import net.zargum.plugin.icarus.region.Flag;
import net.zargum.plugin.icarus.region.FlagState;
import net.zargum.plugin.icarus.region.Region;
import net.zargum.plugin.icarus.region.RegionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RegionPriorityManager {

    private final RegionManager manager;

    public RegionPriorityManager(RegionManager manager) {
        this.manager = manager;
    }

    public boolean isHighest(String regionName1, String regionName2) {
        Region region1 = manager.getRegion(regionName1);
        Region region2 = manager.getRegion(regionName2);
        return region1.getPriority() > region2.getPriority();
    }

    public Region getHighest(Region region1, Region region2) {
        if (region1.getPriority() > region2.getPriority()) return region1;
        else return region2;
    }

    public Region getHighest(List<Region> regions) {
        if (regions.size() == 0) throw new IllegalStateException("Invalid list");
        Region highest = regions.get(0);
        for (Region region : regions) {
            if (region.getPriority() > highest.getPriority()) {
                highest = region;
            }
        }
        return highest;
    }

    public Region getHighestOnFlag(List<Region> regions, Flag flag) {
        if (regions.size() == 0) throw new IllegalStateException("Invalid list");
        regions = regions.stream().filter(region -> region.getFlags().containsKey(flag)).collect(Collectors.toList());
        Region highest = regions.get(0);
        for (Region region : regions) {
            if (region.getPriority() > highest.getPriority()) {
                highest = region;
            }
        }
        return highest;
    }

}
