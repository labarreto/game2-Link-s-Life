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

/**
 *
 * @author ldbruby95
 */


// based off of legend of zelda
public class Game2 extends World {
    int screenWIDTH = 700;
    int screenHEIGHT = 500;
    static Hero hero;
    int lives;
    int score;

    WorldImage background;
    LinkedList<Enemy> enemies;

    /**
     * @param args the command line arguments
     */
    
        public Game2(int width, int height, int lives, int score, Hero hero, 
            LinkedList<Enemy> enemies) {
        this.screenWIDTH = width;
        this.screenHEIGHT = height;
        this.hero = hero;
        this.enemies = enemies;
        this.lives = lives;
        this.score = score;
    }
        
            public World onKeyEvent(String ke) {
        hero.moveLink(ke);
        return new Game2(this.screenWIDTH, this.screenHEIGHT, this.lives, 
                this.score, hero, this.enemies);
    }
    public static void main(String[] args) {
        // TODO code application logic here
    }


    public WorldImage makeImage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
