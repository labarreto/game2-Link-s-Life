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
    String heroFileName;
    WorldImage link;
    int width;
    int height;

    public Hero() {
        this.pin = new Posn(Utility.randInt(0, width), Utility.randInt(0, height));
        heroFileName = "link.png";
        link = new FromFileImage(pin, heroFileName);
        this.width = link.getWidth();
        this.height = link.getHeight();
    }   
     public Hero(Posn pin) {
        this.pin = pin;
        heroFileName = "link.png";
        link = new FromFileImage(pin, heroFileName);
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

        
        Hero link = new Hero(new Posn(Utility.randInt(0, width),Utility.randInt(0,height)));

        if (ke.equals("right") && ((this.pin.x + 10) <= outBoundsRight)) {
            this.pin = new Posn(this.pin.x + 10, this.pin.y);
            return new Hero(this.pin);
        } else if (ke.equals("left") && ((this.pin.x - 10) >= outBoundsLeft)) {
            this.pin = new Posn(this.pin.x - 10, this.pin.y);
            return new Hero(this.pin);       
        } else if (ke.equals("up") && ((this.pin.y - 10) >= outBoundsUp)) {
            this.pin = new Posn(this.pin.x, this.pin.y - 10);
            return new Hero(this.pin);
        } else if (ke.equals("down") && ((this.pin.y + 10) <= outBoundsDown)) {
            this.pin = new Posn(this.pin.x, this.pin.y + 10);
            return new Hero(this.pin);
        }
        return this;
    }

}


