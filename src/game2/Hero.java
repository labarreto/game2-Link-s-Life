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

    public WorldImage linkImage() {
//        link.getMovedTo(pin);
        return link.getMovedImage(this.pin.x, this.pin.y);
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
            this.pin = new Posn(this.pin.x - 10, this.pin.y);
            return new Hero(this.pin, "linkLEFT.png");
        } else if (ke.equals("up") && ((this.pin.y - 10) >= outBoundsUp)) {
            this.pin = new Posn(this.pin.x, this.pin.y - 10);
            return new Hero(this.pin, "linkUP.png");
        } else if (ke.equals("down") && ((this.pin.y + 10) <= outBoundsDown)) {
            this.pin = new Posn(this.pin.x, this.pin.y + 10);
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

        
        if (Math.abs(a - b) < (halfEnemyWidth)
                && (Math.abs(c - d) < (halfEnemyHeight))) {

            return true;
        } else {
            return false;
        }
    }

}

