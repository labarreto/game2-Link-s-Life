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
    
        public Rupees(Posn pin, int time) {
        this.time = time;
        this.pin = pin;
        this.rupees = new FromFileImage(pin, "blueRupee.png");
        this.width = rupees.getWidth();
        this.height = rupees.getHeight();
    }

    public Rupees(Posn pin) {
        this(pin, 15);
        
    }
    
}
