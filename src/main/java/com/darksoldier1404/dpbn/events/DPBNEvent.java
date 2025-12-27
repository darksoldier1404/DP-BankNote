package com.darksoldier1404.dpbn.events;

import com.darksoldier1404.dpbn.Banknote;
import com.darksoldier1404.dpbn.functions.DPBNFunction;
import com.darksoldier1404.dppc.api.essentials.MoneyAPI;
import com.darksoldier1404.dppc.api.inventory.DInventory;
import com.darksoldier1404.dppc.events.dinventory.DInventoryCloseEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import static com.darksoldier1404.dpbn.Banknote.getInstance;

public class DPBNEvent implements Listener {
    private static final Banknote plugin = getInstance();

    @EventHandler
    public void onInventoryClose(DInventoryCloseEvent e) {
        DInventory inv = e.getDInventory();
        Player p = (Player) e.getPlayer();
        if (!inv.isValidHandler(plugin)) return;
        if (inv.isValidChannel(1)) { // banknote item editor
            ItemStack banknoteItem = inv.getItem(13);
            plugin.getConfig().set("Settings.BanknoteItem", banknoteItem);
            plugin.saveConfig();
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("banknote_item_updated"));
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getHand() == EquipmentSlot.OFF_HAND) {
            if (!DPBNFunction.isAllowOffHandRedeem()) return;
        }
        Player p = e.getPlayer();
        ItemStack item = e.getItem();
        if (item == null) return;
        if (!DPBNFunction.isBanknoteItem(item)) return;
        e.setCancelled(true);
        double amount = DPBNFunction.getBanknoteAmount(item);
        int itemAmount = item.getAmount();
        item.setAmount(itemAmount - 1);
        MoneyAPI.addMoney(p, amount);
        p.sendMessage(plugin.getPrefix() + plugin.getLang().getWithArgs("banknote_redeemed", String.valueOf(amount)));
    }
}