package de.igamblenull.scpsl.factions;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;

public interface Faction {
    String getFactionName();
    String getDisplayFactionName();

    List<Player> getPlayers();
    Inventory getStartInventory();

    Vector getSpawnPoint();

    int getStartHP();
    int getStartAHP();

    void addPlayer(Player p);
    void removePlayer(Player p);

    void addStartItem(ItemStack item);
    void setStartInventory(Inventory inv);

    void addSpawnPoint(Vector spawnPoint);
}
