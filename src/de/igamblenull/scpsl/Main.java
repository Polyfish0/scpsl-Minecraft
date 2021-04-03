package de.igamblenull.scpsl;

import de.igamblenull.scpsl.commands.SCPCommand;
import de.igamblenull.scpsl.game.GameManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {
    public FileConfiguration config = this.getConfig();
    public GameManager gameManager;

    @Override
    public void onEnable() {
        gameManager = new GameManager(this);
        saveDefaultConfig();
        registerEvents();

        SCPCommand scpCommand = new SCPCommand(this);
        getCommand("scp").setExecutor(scpCommand);
    }

    private void registerEvents() {

    }
}
