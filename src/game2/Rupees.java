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
    
    
    public boolean beingCollected(Hero hero) {
        
    }
    
}
