package com.oxywire.oxytowns.config;

import com.oxywire.oxytowns.OxyTownsPlugin;
import com.oxywire.oxytowns.config.messaging.Message;
import com.oxywire.oxytowns.economy.ItemEconomy;
import com.oxywire.oxytowns.entities.types.PlotType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import java.time.ZoneId;
import java.util.*;

@Getter
@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
@ConfigSerializable
public final class Config {

    @Setting
    private ItemEconomy claimCost = new ItemEconomy(Material.DIAMOND, 5);

    @Setting
    private ItemEconomy upkeepCost = new ItemEconomy(Material.DIAMOND, 1);

    @Setting
    private int maxClaimRadius = 1;

    @Setting
    private String banConsoleCommand = "spawn <player>";

    @Setting
    private Map<PlotType, Plot> plots = Map.of(
        PlotType.FARM, new Plot(EnumSet.of(Material.CARROTS, Material.POTATOES, Material.WHEAT), EnumSet.noneOf(EntityType.class), Set.of()),
        PlotType.MOB_FARM, new Plot(EnumSet.noneOf(Material.class), EnumSet.of(EntityType.COW, EntityType.PIG, EntityType.SHEEP, EntityType.CHICKEN), Set.of()),
        PlotType.ARENA, new Plot(EnumSet.noneOf(Material.class), EnumSet.of(EntityType.PLAYER), Set.of("/fly"))
    );

    @Setting
    private Map<com.oxywire.oxytowns.entities.types.Upgrade, Config.Upgrade> upgrades = new LinkedHashMap<>(Map.of(
        com.oxywire.oxytowns.entities.types.Upgrade.CLAIMS, new Config.Upgrade("Claims", Map.of(
            15, new ItemEconomy(Material.DIAMOND, 10),
            20, new ItemEconomy(Material.DIAMOND, 20),
            50, new ItemEconomy(Material.DIAMOND, 30),
            100, new ItemEconomy(Material.DIAMOND, 40),
            200, new ItemEconomy(Material.DIAMOND, 50),
            350, new ItemEconomy(Material.DIAMOND, 60),
            500, new ItemEconomy(Material.DIAMOND, 70)
        )),
        com.oxywire.oxytowns.entities.types.Upgrade.MEMBERS, new Config.Upgrade("Members", Map.of(
            10, new ItemEconomy(Material.DIAMOND, 10),
            25, new ItemEconomy(Material.DIAMOND, 20),
            50, new ItemEconomy(Material.DIAMOND, 30),
            75, new ItemEconomy(Material.DIAMOND, 40),
            100, new ItemEconomy(Material.DIAMOND, 50),
            150, new ItemEconomy(Material.DIAMOND, 60),
            250, new ItemEconomy(Material.DIAMOND, 70)
        )),
        com.oxywire.oxytowns.entities.types.Upgrade.VAULT_AMOUNT, new Config.Upgrade("Vault Amount", Map.of(
            2, new ItemEconomy(Material.DIAMOND, 10),
            3, new ItemEconomy(Material.DIAMOND, 20),
            4, new ItemEconomy(Material.DIAMOND, 30),
            5, new ItemEconomy(Material.DIAMOND, 40),
            6, new ItemEconomy(Material.DIAMOND, 50),
            7, new ItemEconomy(Material.DIAMOND, 60),
            8, new ItemEconomy(Material.DIAMOND, 70)
        ))
    ));

    @Setting
    private boolean allowPvpInWilderness = false;

    @Setting
    private List<String> blacklistedWorlds = List.of(
        "resource_world"
    );

    @Setting
    private Upkeep upkeep = new Upkeep();

    @Setting
    private TownChat townChat = new TownChat();

    public static Config get() {
        return OxyTownsPlugin.configManager.get(Config.class);
    }

    @Getter
    @ConfigSerializable
    public static final class Upkeep {
        @Setting
        private boolean enabled = true;

        @Setting
        private ItemEconomy cost = new ItemEconomy(Material.DIAMOND, 1);

        @Setting
        private int hour = 12;

        @Setting
        private ZoneId timezone = ZoneId.of("America/New_York");
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @ConfigSerializable
    public static final class Plot {
        @Setting
        private Set<Material> blocks = EnumSet.noneOf(Material.class);

        @Setting
        private Set<EntityType> entities = EnumSet.noneOf(EntityType.class);

        @Setting
        private Set<String> blacklistedCommands = new HashSet<>();
    }

    @Getter
    @ConfigSerializable
    public static final class TownChat {
        @Setting
        private boolean enabled = true;

        @Setting
        private Message format = new Message().setMessage("<blue>[Town] <white><sender>: <gray><message>");
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @ConfigSerializable
    public static final class Upgrade {
        @Setting
        private String displayName;

        @Setting
        private Map<Integer, ItemEconomy> upgrade;
    }
}
