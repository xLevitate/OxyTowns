package com.oxywire.oxytowns.command.commands.town.sub;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.specifier.Range;
import com.oxywire.oxytowns.OxyTownsPlugin;
import com.oxywire.oxytowns.command.annotation.MustBeInTown;
import com.oxywire.oxytowns.command.annotation.SendersTown;
import com.oxywire.oxytowns.config.Messages;
import com.oxywire.oxytowns.entities.impl.town.Town;
import com.oxywire.oxytowns.entities.types.perms.Permission;
import net.kyori.adventure.text.minimessage.tag.resolver.Formatter;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class WithdrawCommand {

    private final OxyTownsPlugin plugin;

    public WithdrawCommand(final OxyTownsPlugin plugin) {
        this.plugin = plugin;
    }

    @CommandMethod("town|t withdraw <amount>")
    @CommandDescription("Withdraw money from your town's bank")
    @MustBeInTown
    public void onWithdraw(final Player sender, final @SendersTown Town town, final @Argument("amount") @Range(min = "1") int amount) {
        final Messages messages = Messages.get();
        if (!town.hasPermission(sender.getUniqueId(), Permission.WITHDRAW)) {
            messages.getTown().getBank().getErrorWithdrawNotAllowed().send(sender);
            return;
        }

        if (town.getBankValue() < amount) {
            messages.getTown().getBank().getErrorCannotAfford().send(sender);
            return;
        }

        if (sender.getInventory().firstEmpty() == -1) {
            messages.getTown().getBank().getInventoryFull().send(sender);
            return;
        }

        sender.getInventory().addItem(new ItemStack(Material.DIAMOND, amount));

        town.removeWorth(amount);
        messages.getTown().getBank().getWithdrawSuccessful().send(
            town,
            Placeholder.unparsed("player", sender.getName()),
            Formatter.number("amount", amount)
        );

        return;
    }
}
