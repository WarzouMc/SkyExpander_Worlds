package fr.WarzouMc.SkyExpanderInternalPlugin.utils.plugin.worlds;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;

public class Worlds {

    public void load(String name){
        WorldCreator worldCreator = new WorldCreator(name);
        Bukkit.createWorld(worldCreator);
    }

    public void load(){
        for (WorldList world : WorldList.values()) {
            WorldCreator worldCreator = new WorldCreator(world.getName());
            worldCreator.type(world.getWorldType());
            worldCreator.environment(world.getEnvironment());
            Bukkit.createWorld(worldCreator);
        }
    }

}
