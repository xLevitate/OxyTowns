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
import com.oxywire.oxytowns.utils.ItemHelper;
import net.kyori.adventure.text.minimessage.tag.resolver.Formatter;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public final class DepositCommand {

    private final OxyTownsPlugin plugin;

    public DepositCommand(final OxyTownsPlugin plugin) {
        this.plugin = plugin;
    }

    @CommandMethod("town|t deposit <amount>")
    @CommandDescription("Deposit money into your town's bank")
    @MustBeInTown
    public void onDeposit(final Player sender, final @SendersTown Town town, final @Argument("amount") @Range(min = "1") int amount) {
        final Messages messages = Messages.get();
        if (ItemHelper.removeItems(sender, Material.DIAMOND, amount)) {
            town.addWorth(amount);
            messages.getTown().getBank().getDepositSuccessful().send(town, Placeholder.unparsed("player", sender.getName()), Formatter.number("amount", amount));
            return;
        }

        messages.getPlayer().getErrorCannotAffordDeposit().send(sender, Formatter.number("amount", amount));
    }
}
