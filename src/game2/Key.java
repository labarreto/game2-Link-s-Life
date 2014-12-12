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



public class Key {
    Posn pin;
    WorldImage key;
    int width;
    int height;
    boolean canAppear;
    int screenWidth = 700;
    int screenHeight = 500;
    
    public Key(Posn pin, boolean canAppear) {
        this.pin = pin;
        this.key = new FromFileImage(pin, "key.png");
        this.width = key.getWidth();
        this.height = key.getHeight();

    }
    
    public Key() {
        this(new Posn(Utility.randInt(10, 450), Utility.randInt(10, 690)),
                true);
    }
    
    public WorldImage keyImage() {
        return new FromFileImage(pin, "key.png");
    }
    
}
