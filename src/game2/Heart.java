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
public class Heart {
    Posn pin;
    WorldImage hearts;
    int width;
    int height;
    int time;
    
        public Heart() {
  
        this.pin = new Posn(Utility.randInt(10,690), Utility.randInt(10,490));
        this.hearts = new FromFileImage(pin, "heart.png");
        this.width = hearts.getWidth();
        this.height = hearts.getHeight();
    }

    public Heart(Posn pin) {

    }
    
    public WorldImage heartImage() {
        return new FromFileImage(pin, "heart.png");
    }
    
    public Posn getPin( ) {
        return pin;
    }

//    boolean collectedHuh(Hero hero) {
//        int a = this.pin.x;
//        int b = hero.pin.x;
//        int c = this.pin.y;
//        int d = hero.pin.y;
//
//        int halfHeroWidth = hero.width / 2;
//        int halfHeroHeight = hero.height / 2;
//
//        if (Math.abs(a - b) < (halfHeroWidth + width)
//                && (Math.abs(c - d) < (halfHeroHeight +height))) {
//            return true;
//        } else {
//            return false;
//        }
//    }
    
}
