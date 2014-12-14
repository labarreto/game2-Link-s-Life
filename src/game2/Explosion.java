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
    
    boolean explodingHero(Hero hero) {
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
    
    boolean explodingRupees(Rupees rup) {
            int a = this.pin.x;
        int b = rup.pin.x;
        int c = this.pin.y;
        int d = rup.pin.y;

        int halfRupWidth = rup.width / 2;
        int halfRupHeight = rup.height / 2;

        if (Math.abs(a - b) < (halfRupWidth)
                && (Math.abs(c - d) < (halfRupHeight))) {
            return true;
        } else {
            return false;
        }
    }
    
    boolean explodingEnemies(Enemy enemy) {
        int a = this.pin.x;
        int b = enemy.pin.x;
        int c = this.pin.y;
        int d = enemy.pin.y;

        int halfEnWidth = enemy.width / 2;
        int halfEnHeight = enemy.height / 2;

        if (Math.abs(a - b) < (halfEnWidth)
                && (Math.abs(c - d) < (halfEnHeight))) {
            return true;
        } else {
            return false;
        }
    }
    
    boolean explodingBoss(Boss boss) {
        int a = this.pin.x;
        int b = boss.pin.x;
        int c = this.pin.y;
        int d = boss.pin.y;

        int halfBWidth = boss.width / 2;
        int halfBHeight = boss.height / 2;

        if (Math.abs(a - b) < (halfBWidth)
                && (Math.abs(c - d) < (halfBHeight))) {
            return true;
        } else {
            return false;
        }
    }
    
    boolean explodingBomb (Bomb bomb) {
        int a = this.pin.x;
        int b = bomb.pin.x;
        int c = this.pin.y;
        int d = bomb.pin.y;

        int halfBWidth = bomb.width / 2;
        int halfBHeight = bomb.height / 2;

        if (Math.abs(a - b) < (halfBWidth)
                && (Math.abs(c - d) < (halfBHeight))) {
            return true;
        } else {
            return false;
        }
    }
    
    public Explosion incTime() {
        return new Explosion(pin, time + 1);
    }
    

    }
   
