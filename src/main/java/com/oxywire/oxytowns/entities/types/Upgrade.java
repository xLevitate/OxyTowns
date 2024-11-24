package com.oxywire.oxytowns.entities.types;

import com.oxywire.oxytowns.config.Config;
import com.oxywire.oxytowns.economy.ItemEconomy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.minimessage.tag.resolver.Formatter;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.Map;

import com.oxywire.oxytowns.config.Config;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.minimessage.tag.resolver.Formatter;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Getter
@AllArgsConstructor
public enum Upgrade {
    CLAIMS(8),
    MEMBERS(3),
    VAULT_AMOUNT(0);

    private final int defaultValue;

    public String getDisplayName() {
        return Config.get().getUpgrades().get(this).getDisplayName();
    }

    public Map<Integer, ItemEconomy> getTiers() {
        return Config.get().getUpgrades().get(this).getUpgrade();
    }

    public TagResolver[] getPlaceholders(int tier) {
        Map<Integer, ItemEconomy> tiers = getTiers();
        ItemEconomy cost = tiers.values().toArray(ItemEconomy[]::new)[tier];
        return new TagResolver[]{
            Placeholder.unparsed("upgrade", getDisplayName()),
            Formatter.number("amount", tiers.keySet().toArray(Integer[]::new)[tier]),
            Formatter.number("tier", tier + 1),
            Placeholder.unparsed("material", cost.getMaterial().name()),
            Formatter.number("price", cost.getAmount())
        };
    }

    public boolean canAfford(org.bukkit.entity.Player player, int tier) {
        ItemEconomy cost = getTiers().values().toArray(ItemEconomy[]::new)[tier];
        return player.getInventory().containsAtLeast(
            new ItemStack(cost.getMaterial()),
            cost.getAmount()
        );
    }

    public void takeCost(org.bukkit.entity.Player player, int tier) {
        ItemEconomy cost = getTiers().values().toArray(ItemEconomy[]::new)[tier];
        player.getInventory().removeItem(
            new ItemStack(cost.getMaterial(), cost.getAmount())
        );
    }
}
