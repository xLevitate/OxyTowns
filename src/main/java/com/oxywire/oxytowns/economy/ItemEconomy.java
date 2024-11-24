package com.oxywire.oxytowns.economy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ConfigSerializable
public class ItemEconomy {

    @Setting
    private Material material;

    @Setting
    private int amount;

    /**
     * Check if a player has enough of the required item
     *
     * @param player The player to check
     * @return true if the player has enough items, false otherwise
     */
    public boolean hasEnough(Player player) {
        return player.getInventory().containsAtLeast(new ItemStack(material), amount);
    }

    /**
     * Take the required items from a player
     *
     * @param player The player to take items from
     * @return true if items were successfully taken, false otherwise
     */
    public boolean take(Player player) {
        if (!hasEnough(player)) {
            return false;
        }

        player.getInventory().removeItem(new ItemStack(material, amount));
        return true;
    }

    /**
     * Give the items to a player
     *
     * @param player The player to give items to
     */
    public void give(Player player) {
        if (player.getInventory().firstEmpty() != -1) {
            player.getInventory().addItem(new ItemStack(material, amount));
        } else {
            player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(material, amount));
        }
    }

    /**
     * Create a new ItemStack with the specified material and amount
     *
     * @return The created ItemStack
     */
    public ItemStack toItemStack() {
        return new ItemStack(material, amount);
    }

    /**
     * Format the item requirement as a string
     *
     * @return Formatted string like "5x DIAMOND"
     */
    public String format() {
        return amount + "x " + material.name();
    }

    /**
     * Check if this ItemEconomy instance is valid (has a material and positive amount)
     *
     * @return true if valid, false otherwise
     */
    public boolean isValid() {
        return material != null && amount > 0;
    }

    /**
     * Create a copy of this ItemEconomy instance
     *
     * @return A new ItemEconomy instance with the same values
     */
    public ItemEconomy copy() {
        return new ItemEconomy(material, amount);
    }

    /**
     * Multiply the amount by a factor
     *
     * @param factor The multiplication factor
     * @return A new ItemEconomy instance with the multiplied amount
     */
    public ItemEconomy multiply(double factor) {
        return new ItemEconomy(material, (int)(amount * factor));
    }

    @Override
    public String toString() {
        return format();
    }
}
