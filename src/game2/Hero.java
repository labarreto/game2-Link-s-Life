/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game2;

import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldimages.*;
import java.util.*;
import java.awt.Color;

/**
 *
 * @author ldbruby95
 */
public class Hero {

    Posn pin;
    WorldImage link;
    int width;
    int height;
    static int SCREENWIDTH = 700;
    static int SCREENHEIGHT = 500;


    


    public Hero(Posn pin) {
        this.pin = pin;
        this.link = new FromFileImage(pin, "linkDOWN.png");
        this.width = link.getWidth();
        this.height = link.getHeight();
    }

    public Hero(Posn pin, String heroFileName) {
        this.pin = pin;
        this.link = new FromFileImage(pin, heroFileName);
        this.width = link.getWidth();
        this.height = link.getHeight();
    }

    public WorldImage linkImage() {
        return link.getMovedTo(pin);
    }

    public Hero moveLink(String ke) {
        int outBoundsRight = 700;
        int outBoundsLeft = 0;
        int outBoundsUp = 0;
        int outBoundsDown = 500;
//
//        Hero link = new Hero(new Posn(Utility.randInt(0, width), Utility.randInt(0, height)), "linkDOWN.png");

        if (ke.equals("right") && ((this.pin.x + 10) <= outBoundsRight)) {
            this.pin = new Posn(this.pin.x + 10, this.pin.y);
            
            return new Hero(this.pin, "linkRIGHT.png");
            
        } else if (ke.equals("left") && ((this.pin.x - 10) >= outBoundsLeft)) {
            Posn pin2 = new Posn(this.pin.x - 10, this.pin.y);
            return new Hero(pin2, "linkLEFT.png");
        } else if (ke.equals("up") && ((this.pin.y - 10) >= outBoundsUp)) {
            Posn pin2 = new Posn(this.pin.x, this.pin.y - 10);
            return new Hero(pin2, "linkUP.png");
        } else if (ke.equals("down") && ((this.pin.y + 10) <= outBoundsDown)) {
            Posn pin2 = new Posn(this.pin.x, this.pin.y + 10);
            return new Hero(pin2, "linkDOWN.png");
        }
        return this;
    }

}
