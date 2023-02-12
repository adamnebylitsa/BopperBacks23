package kanin.wiimote;

import wiiusej.wiiusejevents.physicalevents.ExpansionEvent;
import wiiusej.wiiusejevents.physicalevents.IREvent;
import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent;
import wiiusej.wiiusejevents.utils.WiimoteListener;
import wiiusej.wiiusejevents.wiiuseapievents.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import static kanin.wiimote.Main.wm;

public class EventHandler implements WiimoteListener {

    private Socket server;
    private PrintWriter serverInput;
    public EventHandler() throws IOException {
        server = new Socket("localhost", 54000);
        serverInput = new PrintWriter(server.getOutputStream(), true);
    }
    
    private short last = 0;
    private boolean rumble=false;
    public void print(String v) {
        System.out.println(v);
    }
    
    @Override
    public void onButtonsEvent(WiimoteButtonsEvent e) {
        try{
            short pressed = e.getButtonsJustPressed();
            if(pressed != last) {
                switch(pressed){
                    case ButtonIDs.a:
                        print("A");
                        if(!rumble)
                            wm.activateRumble();
                        else
                            wm.deactivateRumble();
                        rumble = !rumble;
                        break;
                    case ButtonIDs.b:
                        print("B");
                        break;
                    case ButtonIDs.up:
                        print("Up");
                        break;
                    case ButtonIDs.down:
                        print("Down");
                        break;
                    case ButtonIDs.left:
                        print("Left");
                        break;
                    case ButtonIDs.right:
                        print("Right");
                        break;
                    case ButtonIDs.minus1:
                    case ButtonIDs.minus:
                        print("-");
                        break;
                    case ButtonIDs.plus1:
                    case ButtonIDs.plus:
                        print("+");
                        break;
                    case ButtonIDs.home:
                        print("Home");
                        break;
                    case ButtonIDs.one:
                        print("1");
                        break;
                    case ButtonIDs.two:
                        print("2");
                        break;
                }
                last = e.getButtonsJustPressed(); //stifles the spam print
            }
        }catch(Exception err){err.printStackTrace();}
    }

    @Override
    public void onIrEvent(IREvent e) {

    }

    private boolean idle = true;
    @Override
    public void onMotionSensingEvent(MotionSensingEvent e) {
        print(e.getRawAcceleration().toString());
        if(e.getRawAcceleration().getY()>=128) {
            serverInput.println("mine");
            idle = false;
        } else if(e.getRawAcceleration().getZ()>=128) {
            serverInput.println("place");
            idle = false;
        } else if(e.getRawAcceleration().getX()>=128) {
            serverInput.println("attack");
            idle = false;
        } else if(!idle) {
            serverInput.println("idle");
            idle = true;
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