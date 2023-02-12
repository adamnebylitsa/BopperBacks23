package kanin.wiimote;

import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;

import java.awt.*;
import java.io.IOException;

public class Main {
    
    public static Wiimote wm;
    public static void main(String[] args) throws IOException {
        Wiimote[] remotes = WiiUseApiManager.getWiimotes(1, true); 
        wm = remotes[0];
        wm.activateMotionSensing(); 
        wm.addWiiMoteEventListeners(new EventHandler()); 
    }
}
