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
    Enemy enemy;
    boolean hasKey;
    String heroFileName = "linkDOWNpng";

   

    public Hero(Posn pin, String heroFileName) {
        this.pin = pin;
        this.link = new FromFileImage(pin, heroFileName);
        this.width = link.getWidth();
        this.height = link.getHeight();
    }

    public Hero(Posn pin, WorldImage link) {
        this.pin = pin;
        this.link = link;
        this.width = link.getWidth();
        this.height = link.getHeight();
    }
    
    public Hero(Posn pin) {
        this.pin = pin;
        this.link = new FromFileImage(pin, heroFileName);
        this.width = link.getWidth();
        this.height = link.getHeight();
    }
     

    public WorldImage linkImage() {
//        link.getMovedTo(pin);
        return link.getMovedTo(pin);
    }

    public Hero moveLink(String ke) {
        int outBoundsRight = 700;
        int outBoundsLeft = 0;
        int outBoundsUp = 0;
        int outBoundsDown = 500;
//
//        Hero link = new Hero(new Posn(Utility.randInt(0, width), Utility.randInt(0, height)), "linkDOWN.png");

        if (ke.equals("right") && ((this.pin.x + 20) <= outBoundsRight)) {
            this.pin = new Posn(this.pin.x + 20, this.pin.y);

            return new Hero(this.pin, "linkRIGHT.png");

        } else if (ke.equals("left") && ((this.pin.x - 20) >= outBoundsLeft)) {
            this.pin = new Posn(this.pin.x - 20, this.pin.y);
            return new Hero(this.pin, "linkLEFT.png");
        } else if (ke.equals("up") && ((this.pin.y - 20) >= outBoundsUp)) {
            this.pin = new Posn(this.pin.x, this.pin.y - 20);
            return new Hero(this.pin, "linkUP.png");
        } else if (ke.equals("down") && ((this.pin.y + 20) <= outBoundsDown)) {
            this.pin = new Posn(this.pin.x, this.pin.y + 20);
            return new Hero(this.pin, "linkDOWN.png");
        } else {
            return this;
        }
    }
    

    public boolean collisionHuh(Enemy enemy) {
        
        
        int a = this.pin.x;
        int b = enemy.pin.x;
        int c = this.pin.y;
        int d = enemy.pin.y;
        
        int halfEnemyWidth = enemy.width/2;
        int halfEnemyHeight = enemy.height/2;
        int halfHW = width/2;
        int halfHH = height/2;

        
        if (Math.abs(a - b) < (halfEnemyWidth + halfHW)
                && (Math.abs(c - d) < (halfEnemyHeight+ halfHH))) {

            return true;
        } else {
            return false;
        }
    }
    
    
//    public boolean explodingHuh(Explosion explosion) {
//        
//    }
    
    public boolean collectingRupees(Heart rupees) {
        int a = this.pin.x;
        int b = rupees.getPin().x;
        int c = this.pin.y;
        int d = rupees.getPin().y;
        
        int rw = rupees.width / 2;
        int rh = rupees.height / 2;
        int hw = width / 2 ;
        int hh = height / 2;
        
        if ( ( Math.abs( a - b ) < (rw + hw) ) && 
                ( Math.abs(c - d) < (rh + hh)  ) ) {
            return true;
        } else {
            return false;
        
    }
    }
    public Posn getPin( ) {
        return pin;
    }
}

