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
public class Rupees {
    Posn pin;
    WorldImage rupees;
    int width;
    int height;
    int time;
    
        public Rupees(int time) {
        this.time = time;
        this.pin = new Posn(Utility.randInt(10,690), Utility.randInt(10,490));
        this.rupees = new FromFileImage(pin, "blueRupee.png");
        this.width = rupees.getWidth();
        this.height = rupees.getHeight();
    }

    public Rupees() {
        this(15);
    }
    
    public WorldImage rupeeImage() {
        return new FromFileImage(pin, "blueRupee.png");
    }
    
    public Posn getPin( ) {
        return pin;
    }

    boolean collectedHuh(Hero hero) {
        int a = this.pin.x;
        int b = hero.pin.x;
        int c = this.pin.y;
        int d = hero.pin.y;

        int halfHeroWidth = hero.width / 2;
        int halfHeroHeight = hero.height / 2;
        int halfRupWidth = width / 2;
        int halfRupHeight = height / 2;

        if (Math.abs(a - b) < (halfHeroWidth )
                && (Math.abs(c - d) < (halfHeroHeight))) {
            return true;
        } else {
            return false;
        }
    }
    
    boolean explodingHuh(Explosion explosion) {
         int a = this.pin.x;
        int b = explosion.pin.x;
        int c = this.pin.y;
        int d = explosion.pin.y;

        int halfEWidth = explosion.width / 2;
        int halfEHeight = explosion.height / 2;
        int halfRupWidth = width / 2;
        int halfRupHeight = height / 2;

        if (Math.abs(a - b) < (halfEWidth )
                && (Math.abs(c - d) < (halfEHeight))) {
            return true;
        } else {
            return false;
        }
    }
    
}
