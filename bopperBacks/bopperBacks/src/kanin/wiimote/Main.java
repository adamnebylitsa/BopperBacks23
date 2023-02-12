package kanin.wiimote;

import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;

import java.awt.*;

public class Main{

    public static Robot sudo;
    public static Wiimote wm;
    public static void main(String[] args) throws AWTException {
        sudo = new Robot();
        Wiimote[] remotes = WiiUseApiManager.getWiimotes(1, true); 
        wm = remotes[0];
        wm.activateMotionSensing(); 
        wm.addWiiMoteEventListeners(new EventHandler()); 
    }
}
