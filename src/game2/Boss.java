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
        return new Boss(new Posn(this.pin.x + this.randomInt(n),
                this.pin.y + this.randomInt(n)));
    }

    /**
     * helper method to generate a random number in the range -n to n
     */
    int randomInt(int n) {
        return -n + (new Random().nextInt(2 * n + 1));
    }
}
