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

public class Boss {

    Posn pin;
    String bossFileName;
    WorldImage boss;
    int width;
    int height;
    static int SCREENWIDTH = 700;
    static int SCREENHEIGHT = 500;
    int lastmove;

    // completely to the right x + 50, y
    // pi/4 x + 25, y - 25
    // pi/2 x, y - 50
    // 3pi/4 x - 25, y - 25
    // pi
    // 5pi/4
    // 3pi/2
    // 7pi/4
    public Boss() {
        this(new Posn(SCREENWIDTH / 2 + 100, SCREENHEIGHT / 2 + 100));
    }

    public Boss(Posn pin) {
        this(pin, 0);
    }

    public Boss(Posn pin, int lastmove) {
        this.pin = pin;
        bossFileName = "boss.png";
        boss = new FromFileImage(pin, bossFileName);
        this.width = boss.getWidth();
        this.height = boss.getHeight();
        this.lastmove = lastmove;
    }

    public WorldImage bossImage() {
        return boss.getMovedTo(pin);
    }

    public Boss bossMove() {


        int x = this.pin.x;
        int y = this.pin.y;
        
        if (lastmove == 0) {
            return new Boss(new Posn(x + 25, y), 0);
        } else if (lastmove == 1) {
            return new Boss(new Posn(x + 15, y - 15), 1);
        } else if (lastmove == 2) {
            return new Boss(new Posn(x, y - 25), 2);
        } else if (lastmove == 3) {
            return new Boss(new Posn(x - 15, y - 15), 3);
        } else if (lastmove == 4) {
            return new Boss(new Posn(x - 25, y), 4);
        } else if (lastmove == 5) {
            return new Boss(new Posn(x - 15, y + 15), 5);
        } else if (lastmove == 6) {
            return new Boss(new Posn(x, y + 25), 6);
        } else {
            return new Boss(new Posn(x + 15, y + 15), 7);
        }
    }

    public Boss changeDir() {
        if (outOfBounds()) {
        int pickRand = Utility.randInt(0, 7);
        if (pickRand == 0) {
            return new Boss(pin, 0);
        } else if (pickRand == 1) {
            return new Boss(pin, 1);
        } else if (pickRand == 2) {
            return new Boss(pin, 2);
        } else if (pickRand == 3) {
            return new Boss(pin, 3);
        } else if (pickRand == 4) {
            return new Boss(pin, 4);
        } else if (pickRand == 5) {
            return new Boss(pin, 5);
        } else if (pickRand == 6) {
            return new Boss(pin, 6);
        } else {
            return new Boss(pin, 7);
        }
       } else {
            return this;
        }
    }

    public boolean outOfBounds() {
        return (this.pin.x > 650 || this.pin.x <= 50 || this.pin.y > 450 || this.pin.y <= 50);

    }

    public boolean collisionHuh(Hero hero) {
        int a = this.pin.x;
        int b = hero.pin.x;
        int c = this.pin.y;
        int d = hero.pin.y;

        int halfHeroWidth = hero.width / 2;
        int halfHeroHeight = hero.height / 2;
        int halfBossW = width / 2;
        int halfBossH = height / 2;

        if (Math.abs(a - b) < (halfHeroWidth + halfBossW)
                && (Math.abs(c - d) < (halfHeroHeight + halfBossH))) {
            return true;
        } else {
            return false;
        }
    }
}
