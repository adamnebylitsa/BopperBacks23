package com.kanin.wiiloveminecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.Hand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("wii-love-minecraft")
public class WiiLoveMinecraft {

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public WiiLoveMinecraft() throws InterruptedException {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
    public void pollMote(TickEvent.ServerTickEvent e) {
        
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent e) {
        Minecraft mc = Minecraft.getInstance();
        if(e.getEntity().isEntityEqual(mc.player)) {
            Thread backgroundTask = new Thread(() -> {
                try {
                    ServerSocket server = new ServerSocket(54000);
                    Socket client = server.accept();
                    mc.player.sendChatMessage("Remote input connected");
                    Scanner clientInput = new Scanner(client.getInputStream());
                    InputMappings.Input holding = null;
                    while(true) {
                        if(mc.player == null) {
                            return;
                        }
                        String command = clientInput.nextLine();
                        mc.player.sendChatMessage(command);
                        switch(command) {
                            case "attack":
                                mc.player.setActiveHand(Hand.MAIN_HAND);
                                mc.player.swingArm(Hand.MAIN_HAND);
                                mc.player.sendChatMessage("swumg");
                                break;
                            case "mine":
                                holding = mc.gameSettings.keyBindAttack.getKey();
                                InputMappings.Input key = holding;
                                Minecraft.getInstance().deferTask(() -> {
                                    KeyBinding.setKeyBindState(key, true);
                                });
                                break;
                            case "place":
                                mc.player.setActiveHand(Hand.MAIN_HAND);
                                mc.deferTask(() -> {
                                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKey(), true);
                                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKey(), false);
                                });
                                break;
                            case "jump":
                                mc.deferTask(() -> {
                                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKey(), true);
                                    try { Thread.sleep(100); } catch (InterruptedException ex) { ex.printStackTrace(); }
                                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKey(), false);
                                });
                                break;
                            case "idle":
                                if(holding != null) {
                                    key = holding;
                                    mc.deferTask(() -> {
                                        KeyBinding.setKeyBindState(key, false);
                                    });
                                }
                                break;
                            default:
                                mc.player.sendChatMessage("Invalid command: " + command);
                        }
                    }
                }  catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            backgroundTask.start();
        }
    }
    
    
    @SubscribeEvent
    public void wiiControlPlayer(PlayerInteractEvent.RightClickItem e) {
        KeyBinding.setKeyBindState(Minecraft.getInstance().gameSettings.keyBindForward.getKey(), e.getItemStack().getCount() == 3);
    }
    
}
