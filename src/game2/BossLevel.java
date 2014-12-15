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
public class BossLevel extends World {

    static int screenWIDTH = 700;
    static int screenHEIGHT = 500;
    String backFileName = new String("background.png");
    Hero hero;
    Key key;
    int lives;
    int score;
    int money;
    int kills;

    Boss boss;
    int bosslives;

    WorldImage background;
    LinkedList<Heart> rupees;
    LinkedList<Enemy> enemies;
    LinkedList<Bomb> bombs;

    public BossLevel(int width, int height, int lives, int bosslives, int score, Hero hero,
            Boss boss, LinkedList<Heart> rupees) {
        this.boss = boss;
        this.screenWIDTH = width;
        this.screenHEIGHT = height;
        this.lives = lives;
        this.score = score;
        this.hero = hero;
        this.boss = boss;
        this.bosslives = bosslives;
        this.rupees = rupees;
    }
    
   

    public World onKeyEvent(String ke) {
        if (ke.equals("up") || ke.equals("left") || ke.equals("right") || ke.equals("down")) {

            boolean canMove = true;
            Hero extra = this.hero.moveLink(ke);
            
            if (boss.collisionHuh(extra)) {
                canMove = false; 
                extra = this.hero;
            }
            
            if (canMove) {
                return new BossLevel(this.screenWIDTH, this.screenHEIGHT,
                this.lives, this.bosslives, this.score, extra, this.boss, this.rupees);
            } else
                return this;
        } else
            return this;
    }

    public WorldImage makeImage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
