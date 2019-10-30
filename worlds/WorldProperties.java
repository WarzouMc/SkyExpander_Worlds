package fr.WarzouMc.SkyExpanderInternalPlugin.utils.plugin.worlds;

import fr.WarzouMc.SkyExpanderInternalPlugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class WorldProperties{

    private Main main;
    private Player player;
    private String playerName;
    private String worldName;

    public WorldProperties(Main main, Player player, String worldName){
        this.main = main;
        this.player = player;
        this.playerName = player.getName();
        this.worldName = worldName;
    }

    public void fileWorld(){
        File worldFile = new File(main.getDataFolder() + File.separator + "worlds");
        File worldConfigFile = new File(main.getDataFolder() + File.separator + "worlds" + File.separator + worldName +".yml");
        if (!worldFile.mkdir()){
            worldFile.mkdir();
        }

        if (!worldConfigFile.exists()){
            try {
                worldConfigFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void filePlayer() {
        File worldFile = new File(main.getDataFolder() + File.separator + "worlds" + File.separator + worldName +".yml");
        FileConfiguration worldConfigFile = YamlConfiguration.loadConfiguration(worldFile);
        if (!worldConfigFile.contains(playerName)){
            worldConfigFile.createSection(playerName);
            worldConfigFile.set(playerName, getWorldSpawn());
            try {
                worldConfigFile.save(worldFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public WorldList getWorldPropertiesFromName(String name){
        for (WorldList worldList : WorldList.values()) {
            if (worldList.getName().equalsIgnoreCase(name)) {
                return worldList;
            }
        }
        return null;
    }

    public WorldList getWorldPropertiesFromName(){
        for (WorldList worldList : WorldList.values()) {
            if (worldList.getName().equalsIgnoreCase(worldName)) {
                return worldList;
            }
        }
        return null;
    }

    public double[] getWorldSpawn(){
        WorldList world = getWorldPropertiesFromName();
        Location location = doublesToLocation(world.spawn());
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        double yaw = location.getYaw();
        double pitch = location.getPitch();
        return new double[] {x,y,z,yaw,pitch};
    }

    public void saveLastWorldLocation(){
        fileWorld();
        filePlayer();
        File worldFile = new File(main.getDataFolder() + File.separator + "worlds" + File.separator + worldName +".yml");
        FileConfiguration worldConfigFile = YamlConfiguration.loadConfiguration(worldFile);

        worldConfigFile.set(playerName, loc());
        try {
            worldConfigFile.save(worldFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Location getLastWorldLocation(){
        Location location = new Location(Bukkit.getWorld(worldName), loc()[0], loc()[1], loc()[2], (float)loc()[3], (float)loc()[4]);
        return location;
    }

    public void teleport(){
        WorldList world = getWorldPropertiesFromName();
        if (world.isRespawn()){
            Location location = doublesToLocation(world.spawn());
            player.teleport(location);
            return;
        }
        player.teleport(doublesToLocation(getSavedLoc()));
    }

    public void teleportSpawn(){
        WorldList world = getWorldPropertiesFromName();
        player.teleport(doublesToLocation(world.spawn()));
    }

    public Location doublesToLocation(double[] doubles){
        return new Location(Bukkit.getWorld(worldName), doubles[0], doubles[1], doubles[2], (float)doubles[3], (float)doubles[4]);
    }

    private double[] loc(){
        double x = player.getLocation().getX();
        double y = player.getLocation().getY();
        double z = player.getLocation().getZ();
        double yaw = player.getLocation().getYaw();
        double pitch = player.getLocation().getPitch();

        return new double[] {x,y,z,yaw,pitch};
    }

    private double[] getSavedLoc(){
        fileWorld();
        filePlayer();
        File worldFile = new File(main.getDataFolder() + File.separator + "worlds" + File.separator + worldName +".yml");
        FileConfiguration worldConfigFile = YamlConfiguration.loadConfiguration(worldFile);
        List<Double> list = worldConfigFile.getDoubleList(playerName);

        double x = list.get(0);
        double y = list.get(1);
        double z = list.get(2);
        double yaw = list.get(3);
        double pitch = list.get(4);

        return new double[] {x,y,z,yaw,pitch};
    }

}
