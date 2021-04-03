package de.igamblenull.scpsl.game;

import de.igamblenull.scpsl.Main;
import de.igamblenull.scpsl.factions.Faction;
import de.igamblenull.scpsl.factions.human.*;
import de.igamblenull.scpsl.factions.scp.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GameManager {
    private final Main main;
    public HashMap<String, Faction> factions = new HashMap<>();
    public HashMap<Player, String> players = new HashMap<>();
    public boolean roundRunning = false;
    private boolean timerRunning = false;
    private int lobbyTimerTaskID = -1;
    private int timeLeft = -1;
    private int spawnPatternCounter = 0;
    private String[] spawnPattern;
    final String[] scps = new String[] {
            "049",
            "096",
            "173",
            "106",
            "93953",
            "93989"
    };

    public GameManager(Main main) {
        this.main = main;
        setupFactions();
        spawnPattern = main.config.getString("spawnPattern").split("");
        boolean error = false;
        for(int i = 0; i < spawnPattern.length; i++) {
            if (spawnNumberToString(spawnPattern[i]) == "idk") {
                error = true;
            }
        }
        if(error) {
            for(Player p : main.getServer().getOnlinePlayers()) {
                if(p.isOp()) {
                    p.sendMessage("[SCP:SL] Wrong spawn pattern, the plugin will now be deactivated!");
                }
            }
            main.getServer().getPluginManager().disablePlugin(main);
        }
    }

    public void startLobby() {
        setupFactions();
    }

    public void startRound() {
        roundRunning = true;
        Random random = new Random();
        List<Player> playerList = new ArrayList<>(players.keySet());
        for(int i = 0; i < players.size(); i++) {
            Player p = playerList.get(random.nextInt(playerList.size()));
            if(spawnPatternCounter + 1 >= spawnPattern.length) {
                spawnPatternCounter = 0;
            }

            String factionName = spawnNumberToString(spawnPattern[spawnPatternCounter++]);

            if(factionName == "scp") {
                factionName = scps[random.nextInt(scps.length)];
            }

            players.replace(p, factionName);
            factions.get(factionName).addPlayer(p);
            p.sendMessage(factionName);
            playerList.remove(p);
        }
    }

    private String spawnNumberToString(String s) {
        switch (s) {
            case "0":
                return "scp";

            case "1":
                return "facility";

            case "3":
                return "scientist";

            case "4":
                return "dboy";

            default:
                return "idk";
        }
    }

    public void endRound() {

    }

    public void printStats() {

    }

    public void joinPlayer(Player p) {
        if(players.containsKey(p)) {
            p.sendMessage("[SCP:SL] You have already joined");
            return;
        }

        if(main.config.getInt("maxPlayer") <= players.size()) {
            p.sendMessage("[SCP:SL] The game is full!");
            return;
        }
        p.sendMessage("[SCP:SL] Game joined");
        players.put(p, "none");

        if(main.config.getInt("minPlayer") <= players.size() && !timerRunning) {
            if(timeLeft == -1) {
                timeLeft = main.config.getInt("lobbytime");
            }
            lobbyTimerTaskID = main.getServer().getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
                @Override
                public void run() {
                    timeLeft--;
                    if(timeLeft == 0) {
                        main.getServer().getScheduler().cancelTask(lobbyTimerTaskID);
                        startRound();
                    }
                    for(Player p : players.keySet()) {
                        p.setLevel(timeLeft);
                    }
                }
            }, 0, 20);
        }
    }

    public void leavePlayer(Player p) {
        if(!players.containsKey(p)) {
            p.sendMessage("[SCP:SL] You are not in a game!");
            return;
        }
        p.sendMessage("[SCP:SL] Game left");
        p.setLevel(0);
        players.remove(p);

        if(main.config.getInt("minPlayer") > players.size() && lobbyTimerTaskID != -1) {
            main.getServer().getScheduler().cancelTask(lobbyTimerTaskID);
            for(Player pp : players.keySet()) {
                pp.setLevel(0);
            }
            timeLeft = -1;
        }
    }

    private void setupFactions() {
        factions.clear();
        Inventory inventory = Bukkit.createInventory(null, 54, "SCHWING");

        DBoy dboy = new DBoy();
        inventory.addItem(new ItemStack(Material.ORANGE_CONCRETE));
        dboy.setStartInventory(inventory);
        factions.put("dboy", dboy);

        Scientist scientist = new Scientist();
        factions.put("scientist", scientist);

        Facility facility = new Facility();
        factions.put("facility", facility);

        MTF mtf = new MTF();
        factions.put("mtf", mtf);

        Chaos chaos = new Chaos();
        factions.put("chaos", chaos);

        Doc doc = new Doc();
        factions.put("049", doc);

        Hund1 hund1 = new Hund1();
        factions.put("93953", hund1);

        Hund2 hund2 = new Hund2();
        factions.put("93989", hund2);

        Larry larry = new Larry();
        factions.put("096", larry);

        Peanut peanut = new Peanut();
        factions.put("173", peanut);

        Shyguy shyguy = new Shyguy();
        factions.put("106", shyguy);
    }
}
