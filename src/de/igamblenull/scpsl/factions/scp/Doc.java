package de.igamblenull.scpsl.factions.scp;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Doc implements SCP {
    private final String factionName = "049";
    private final String DisplayFactionName = "SCP-049";
    private final int startHP = 20;
    private final int startAHP = 0;
    private Inventory inventory;
    private List<Player> players = new ArrayList<Player>();
    private Vector spawnPoint = new Vector();

    @Override
    public String getFactionName() {
        return factionName;
    }

    @Override
    public String getDisplayFactionName() {
        return DisplayFactionName;
    }

    @Override
    public int getStartHP() {
        return startHP;
    }

    @Override
    public int getStartAHP() {
        return startAHP;
    }

    @Override
    public void addPlayer(Player p) {
        if(players.contains(p)) {
            players.remove(p);
        }
        players.add(p);
    }

    @Override
    public void removePlayer(Player p) {
        if(players.contains(p)) {
            players.remove(p);
        }
    }

    @Override
    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public Inventory getStartInventory() {
        return inventory;
    }

    @Override
    public Vector getSpawnPoint() {
        return spawnPoint;
    }

    @Override
    public void addStartItem(ItemStack item) {
        inventory.addItem(item);
    }

    @Override
    public void setStartInventory(Inventory inv) {
        inventory = inv;
    }

    @Override
    public void addSpawnPoint(Vector spawnPoint) {
        this.spawnPoint = spawnPoint;
    }
}
