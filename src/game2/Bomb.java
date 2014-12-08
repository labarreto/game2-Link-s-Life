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
public class Bomb {

    Posn pin;
    WorldImage bomb;
    int width;
    int height;
    int time;

    public Bomb(Posn pin, int time) {
        this.time = time;
        this.pin = pin;
        this.bomb = new FromFileImage(pin, "bomb.png");
        this.width = bomb.getWidth();
        this.height = bomb.getHeight();
    }

    public Bomb(Posn pin) {
        this(pin, 0);
        
    }
    


}
