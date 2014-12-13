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

public class Explosion {
    
    int time;
    Posn pin;
    WorldImage explosion;
    int width;
    int height;
    
    public Explosion(Posn pin, int time) {
        this.time = time;
        this.pin = pin;
        this.explosion = new FromFileImage(pin, "explosion.png");
        this.width = explosion.getWidth();
        this.height = explosion.getHeight();
     
        
    }
    
    public Explosion(Posn pin) {
        this(pin,0);
    }
     
    public WorldImage explosionImage() {
        return new FromFileImage(pin, "explosion.png");
    }
    
    boolean collisionHuh(Hero hero){
          int a = this.pin.x;
        int b = hero.pin.x;
        int c = this.pin.y;
        int d = hero.pin.y;

        int halfHeroWidth = hero.width / 2;
        int halfHeroHeight = hero.height / 2;

        if (Math.abs(a - b) < (halfHeroWidth)
                && (Math.abs(c - d) < (halfHeroHeight))) {
            return true;
        } else {
            return false;
        }
    }
    
    
}
