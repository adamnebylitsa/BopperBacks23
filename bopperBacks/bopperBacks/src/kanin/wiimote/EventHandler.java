package kanin.wiimote;

import wiiusej.wiiusejevents.physicalevents.ExpansionEvent;
import wiiusej.wiiusejevents.physicalevents.IREvent;
import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent;
import wiiusej.wiiusejevents.utils.WiimoteListener;
import wiiusej.wiiusejevents.wiiuseapievents.*;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import static kanin.wiimote.ButtonIDs.*;
import static kanin.wiimote.Main.sudo;
import static kanin.wiimote.Main.wm;

public class EventHandler implements WiimoteListener {
    
    private short last = 0;
    private boolean rumble=false;
    @Override
    public void onButtonsEvent(WiimoteButtonsEvent e) {
        try{
            if(e.getButtonsJustPressed()!=last) {
                switch(e.getButtonsJustPressed()){
                    case a:
                        print("A");
                        if(!rumble)
                            wm.activateRumble();
                        else
                            wm.deactivateRumble();
                        rumble=!rumble;
                        break;
                    case b:
                        print("B");
                        StringSelection selection = new StringSelection("sendToBot('i eat ass');");
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        clipboard.setContents(selection, selection);
                        sudo.keyPress(KeyEvent.VK_CONTROL);
                        sudo.keyPress(KeyEvent.VK_V);
                        sudo.keyRelease(KeyEvent.VK_CONTROL);
                        sudo.keyRelease(KeyEvent.VK_V);
                        sudo.keyPress(KeyEvent.VK_ENTER);
                        sudo.keyRelease(KeyEvent.VK_ENTER);
                        break;
                    case up:
                        print("Up");
                        sudo.keyPress(KeyEvent.VK_UP);
                        sudo.keyRelease(KeyEvent.VK_UP);
                        break;
                    case down:
                        print("Down");
                        sudo.keyPress(KeyEvent.VK_DOWN);
                        sudo.keyRelease(KeyEvent.VK_DOWN);
                        break;
                    case left:
                        print("Left");
                        sudo.keyPress(KeyEvent.VK_LEFT);
                        sudo.keyRelease(KeyEvent.VK_LEFT);
                        break;
                    case right:
                        print("Right");
                        sudo.keyPress(KeyEvent.VK_RIGHT);
                        sudo.keyRelease(KeyEvent.VK_RIGHT);
                        break;
                    case minus1:
                    case minus:
                        print('-');
                        break;
                    case plus1:
                    case plus:
                        print('+');
                        break;
                    case home:
                        sudo.keyPress(KeyEvent.VK_WINDOWS);
                        sudo.keyPress(KeyEvent.VK_TAB);
                        sudo.keyRelease(KeyEvent.VK_WINDOWS);
                        sudo.keyRelease(KeyEvent.VK_TAB);
                        print("Home");
                        break;
                    case one:
                        print(1);
                        break;
                    case two:
                        print(2);
                        break;
                }
                last = e.getButtonsJustPressed(); //stifles the spam print
            }   
        }catch(Exception err){err.printStackTrace();}
    }

    @Override
    public void onIrEvent(IREvent e) {

    }

    @Override
    public void onMotionSensingEvent(MotionSensingEvent e) {
        print(""+e.getOrientation().getPitch());
        print(e.getRawAcceleration().toString());
        if(e.getRawAcceleration().getY()>=128){
            int cmd;
            if(e.getOrientation().getPitch()>24)
                cmd = KeyEvent.VK_DOWN;
            else if(e.getOrientation().getPitch()<-45){
                cmd = KeyEvent.VK_UP;
                print(45);
            }
            else
                return;
            sudo.keyPress(KeyEvent.VK_WINDOWS);
            sudo.keyPress(cmd);
            sudo.keyRelease(KeyEvent.VK_WINDOWS);
            sudo.keyRelease(cmd);
        }
    }

    @Override
    public void onExpansionEvent(ExpansionEvent e) {

    }

    @Override
    public void onStatusEvent(StatusEvent e) {

    }

    @Override
    public void onDisconnectionEvent(DisconnectionEvent e) {
        print("Wii Remote Disconnected");
    }

    @Override
    public void onNunchukInsertedEvent(NunchukInsertedEvent e) {

    }

    @Override
    public void onNunchukRemovedEvent(NunchukRemovedEvent e) {

    }

    @Override
    public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent e) {

    }

    @Override
    public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent e) {

    }

    @Override
    public void onClassicControllerInsertedEvent(ClassicControllerInsertedEvent e) {

    }

    @Override
    public void onClassicControllerRemovedEvent(ClassicControllerRemovedEvent e) {

    }
}
