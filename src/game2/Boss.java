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

    public Boss() {
        this(new Posn(SCREENWIDTH / 2, SCREENHEIGHT / 2));
    }

    public Boss(Posn pin) {
        this.pin = pin;
        bossFileName = "boss.png";
        boss = new FromFileImage(pin, bossFileName);
        this.width = boss.getWidth();
        this.height = boss.getHeight();
    }

    public WorldImage bossImage() {
        return boss.getMovedTo(pin);
    }

    Boss randomMove(int n) {
        int outBoundsRight = 700;
        int outBoundsLeft = 0;
        int outBoundsUp = 0;
        int outBoundsDown = 500;
        if (this.pin.x < outBoundsRight || this.pin.x > outBoundsLeft ||
                this.pin.y < outBoundsDown  || this.pin.y > outBoundsUp) {
        return new Boss(new Posn(this.pin.x + this.randomInt(n),
                this.pin.y + this.randomInt(n)));
    } else {
            // move Boss back to center when going out of bounds.
            return new Boss();
        }
    }


    int randomInt(int n) {
        return -n + (new Random().nextInt(2 * n + 1));
    }

}
