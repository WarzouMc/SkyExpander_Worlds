package fr.WarzouMc.SkyExpanderInternalPlugin.utils.plugin.worlds;

import org.bukkit.*;

public enum WorldList {

    Minage("minage", WorldType.NORMAL, World.Environment.NORMAL, new double[] {0.5, 72, 0.5, 90.0f, 0.0f}, false),
    Ressource1("ressource1", WorldType.FLAT, World.Environment.NORMAL, new double[] {0.5, 100, 0.5, 90.0f, 0.0f}, true),
    Build("SkyExpander", WorldType.NORMAL, World.Environment.NORMAL, new double[] {0.5, 62, 0.5, 90.0f, 0.0f}, false);

    private String name;
    private WorldType worldType;
    private World.Environment environment;
    private double[] location;
    private boolean respawn;
    WorldList(String name, WorldType worldType, World.Environment environment, double[] location, boolean respawn) {
        this.name = name;
        this.worldType = worldType;
        this.environment = environment;
        this.location = location;
        this.respawn = respawn;
    }

    public String getName() {
        return name;
    }

    public WorldType getWorldType() {
        return worldType;
    }

    public World.Environment getEnvironment() {
        return environment;
    }

    public double[] spawn() {
        return location;
    }

    public boolean isRespawn() {
        return respawn;
    }
}
